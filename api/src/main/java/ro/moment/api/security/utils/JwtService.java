package ro.moment.api.security.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.moment.api.domain.User;
import ro.moment.api.repository.UserRepository;
import ro.moment.api.security.exceptions.RefreshTokenExpiredException;
import ro.moment.api.security.tokens.Token;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtService {
    private final Key key;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.accessTokenExpiration}")
    private int accessTokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private int refreshTokenExpiration;

    public JwtService(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String sub, Map<String, Object> claims, Integer millisToExpire) {
        return Jwts.builder()
                .setSubject(sub)
                .addClaims(claims)
                .setExpiration(Date.from(Instant.now().plus(millisToExpire, ChronoUnit.MILLIS)))
                .signWith(key)
                .compact();
    }

    public Map<String, Object> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Token getTokensAtLogin(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            String rolesString = convertAuthoritiesListToString(user);
            Token tokens = new Token();
            tokens.setAccessToken(createToken(user.getUsername(), Map.of("roles", rolesString),
                    accessTokenExpiration));
            tokens.setRefreshToken(createToken(user.getUsername(), Map.of("roles", rolesString),
                    refreshTokenExpiration));
            return tokens;
        } else {
            throw new Exception("Incorrect password");
        }
    }

    public User getUserByToken(String token) {
        Map<String, Object> claims;
        claims = parseClaims(token);
        String username = (String) claims.get("sub");
        return userRepository.findByUsername(username);
    }

    public Token getTokensAtRefresh(String refreshToken) throws RefreshTokenExpiredException {
        Map<String, Object> claims;
        try {
            claims = parseClaims(refreshToken);
            String username = (String) claims.get("sub");
            User user = userRepository.findByUsername(username);
            String rolesString = convertAuthoritiesListToString(user);
            Token newTokens = new Token();
            newTokens.setAccessToken(
                    createToken(user.getUsername(), Map.of("roles", rolesString),
                            accessTokenExpiration));
            newTokens.setRefreshToken(refreshToken);
            return newTokens;
        } catch (ExpiredJwtException ex) {
            throw new RefreshTokenExpiredException("Expired refresh token");
        }
    }

    private String convertAuthoritiesListToString(User user) {
        return user.findAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

}
