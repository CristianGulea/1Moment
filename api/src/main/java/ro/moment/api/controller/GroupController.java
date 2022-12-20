package ro.moment.api.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.repository.GroupRepository;
import ro.moment.api.service.GroupService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @RequestMapping( method=RequestMethod.GET)
    public List<GroupDto> getAll(){
        System.out.println("Get all groups ...");
        return groupService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);

        GroupDto group = groupService.findById(Long.valueOf(id));
        if (group == null)
            return new ResponseEntity<String>("Group not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity <GroupDto>(group, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody GroupDto group){
        groupService.save(group);
        return new ResponseEntity<GroupDto>(group, HttpStatus.CREATED);

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting group ... "+id);
        try {
            groupService.deleteById(Long.valueOf(id));
            return new ResponseEntity<Group>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete group exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public ResponseEntity<?> addMember(@RequestBody UserDto user, @RequestBody GroupDto group){
        groupService.addMember(group, user);
        return new ResponseEntity<Group>(HttpStatus.OK);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}


