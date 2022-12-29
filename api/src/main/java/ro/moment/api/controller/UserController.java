package ro.moment.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.LoginDetails;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.security.exceptions.RefreshTokenExpiredException;
import ro.moment.api.security.tokens.Token;
import ro.moment.api.service.UserService;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll() {
        System.out.println("Get all users ...");
        return userService.findAll();
    }

    @PostMapping("/login")
    public Token loginUser(@RequestBody LoginDetails details) throws Exception {
        return userService.getTokensAtLogin(details.getUsername(), details.getPassword());
    }

    @PostMapping("/refresh")
    public Token refreshAccessToken(@RequestBody String refreshToken) throws RefreshTokenExpiredException {
        return userService.getTokensAtRefresh(refreshToken);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        System.out.println("Get by id " + id);
        UserDto user = userService.findById(Long.valueOf(id));
        if (user == null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }


    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        System.out.println("Get by username " + username);
        UserDto user = userService.findByUsername(username);
        if (user == null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody UserDto user) {
        try{
            userService.save(user);
        } catch(ValidationException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	    }
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        System.out.println("Deleting user ... " + id);
        try {
            userService.deleteById(Long.valueOf(id));
            return new ResponseEntity<User>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}


