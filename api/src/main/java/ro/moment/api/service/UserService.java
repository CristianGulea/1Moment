package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.repository.UserRepository;
import ro.moment.api.security.exceptions.RefreshTokenExpiredException;
import ro.moment.api.security.tokens.Token;
import ro.moment.api.security.utils.JwtService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    private void validateUser(UserDto userDto) {
        String errors = "";
        if(userDto.getUsername().compareTo("")==0){
            errors+="Username cannot be empty\n";
        }
        if(userDto.getUsername().length()>36){
            errors+="Username cannot be longer than 36 characters\n";
        }
        if(userDto.getPassword().compareTo("")==0){
            errors+="Password cannot be empty\n";
        }

        if(errors.compareTo("")!=0){
            throw new ValidationException(errors);
        }
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();

        users.forEach(u -> dtos.add(new UserDto(u)));
        return dtos;
    }

    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(UserDto::new).orElse(null);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }

    public void save(UserDto userDto) {
        validateUser(userDto);
        User user = userDto.toDomain();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        user.setCreatedDate(LocalDate.now());
        userRepository.save(user);
    }

    public Token getTokensAtLogin(String username, String password) throws Exception {
        return jwtService.getTokensAtLogin(username, password);
    }

    public Token getTokensAtRefresh(String refreshToken) throws RefreshTokenExpiredException {
        return jwtService.getTokensAtRefresh(refreshToken);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
