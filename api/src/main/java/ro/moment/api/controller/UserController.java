package ro.moment.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.User;
import ro.moment.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String template = "Hello, %s!";

    @Autowired
    private UserRepository userRepository;
    /*
        @RequestMapping("/greeting")
        public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
            return String.format(template, name);
        }
     */
    @RequestMapping( method=RequestMethod.GET)
    public User[] getAll(){
        System.out.println("Get all users ...");
        List<User> result =
                StreamSupport.stream(userRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        return result.toArray(new User[0]);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);
        /*
        User user=userRepository.findById(Long.valueOf(id));
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(user, HttpStatus.OK);

         */
        Optional<User> user=userRepository.findById(Long.valueOf(id));
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity <Optional<User>>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        System.out.println("Get by id "+username);
        User user=userRepository.findByUsername(username);
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@RequestBody User user){
        userRepository.save(user);
        return user;

    }
/*
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        System.out.println("Updating user ...");
        userRepository.update(user.getId(),user);
        return user;
}

 */
    // @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting user ... "+id);
        try {
            userRepository.deleteById(Long.valueOf(id));
            return new ResponseEntity<User>(HttpStatus.OK);
        }catch (Exception ex){ //era RepositoryException
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

/*
    @RequestMapping("/{user}/name")
    public String name(@PathVariable String user){
        User result=userRepository.findBy(user);
        System.out.println("Result ..."+result);

        return result.getName();
    }

 */

    @ExceptionHandler(Exception.class)//era RepositoryException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {//era RepositoryException
        return e.getMessage();
    }
}


