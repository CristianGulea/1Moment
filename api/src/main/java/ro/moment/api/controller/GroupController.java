package ro.moment.api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Group;
import ro.moment.api.repository.GroupRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/group")
public class GroupController {


    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping( method=RequestMethod.GET)
    public List<Group> getAll(){
        System.out.println("Get all groups ...");
        return groupRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);

        Optional<Group> group=groupRepository.findById(Long.valueOf(id));
        if (group.isEmpty())
            return new ResponseEntity<String>("Group not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity <Optional<Group>>(group, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Group group){
        groupRepository.save(group);
        return new ResponseEntity<Group>(group, HttpStatus.CREATED);

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting group ... "+id);
        try {
            groupRepository.deleteById(Long.valueOf(id));
            return new ResponseEntity<Group>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete group exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}


