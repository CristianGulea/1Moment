package ro.moment.api.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.repository.UserRepository;
import ro.moment.api.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping( method=RequestMethod.GET)
    public List<UserDto> getAll(){
        System.out.println("Get all users ...");
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);
        UserDto user = userService.findById(Long.valueOf(id));
        if (user == null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity <UserDto>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        System.out.println("Get by username "+username);
        UserDto user = userService.findByUsername(username);
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody UserDto user){
        userService.save(user);
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting user ... "+id);
        try {
            userService.deleteById(Long.valueOf(id));
            return new ResponseEntity<User>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}


