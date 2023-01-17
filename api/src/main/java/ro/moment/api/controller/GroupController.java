package ro.moment.api.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.exceptions.ValidationException;
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
    public List<GroupDto> getAll(@RequestHeader("Authorization") String token){
        System.out.println("Get all groups ...");
        token = token.replaceFirst("Bearer ", "");
        return groupService.findAll(token);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id,@RequestHeader("Authorization") String token){
        System.out.println("Get by id "+id);
        token = token.replaceFirst("Bearer ", "");
        GroupDto group = groupService.findById(Long.valueOf(id),token);
        if (group == null)
            return new ResponseEntity<String>("Group not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity <GroupDto>(group, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody GroupDto group){
        try {
            groupService.save(group);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
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


    @RequestMapping(value = "/{id}/subscribe", method = RequestMethod.PATCH)
    public ResponseEntity<?> like(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        System.out.println("Subscribe group " + id);
        token = token.replaceFirst("Bearer ", "");

        if (groupService.subscribeGroup(id, token))
            return new ResponseEntity<>("subscribed", HttpStatus.OK);

        return new ResponseEntity<>("Unable to subscribe", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}/unsubscribe", method = RequestMethod.PATCH)
    public ResponseEntity<?> dislike(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        System.out.println("Unsubscribe group " + id);
        token = token.replaceFirst("Bearer ", "");

        if (groupService.unsubscribeGroup(id, token))
            return new ResponseEntity<>("unsubscribed", HttpStatus.OK);

        return new ResponseEntity<>("Unable to unsubscribe", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}


