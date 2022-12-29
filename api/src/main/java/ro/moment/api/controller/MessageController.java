package ro.moment.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.service.MessageService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MessageDto> getAll() {
        System.out.println("Get all messages ...");
        return messageService.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getByGroupId(@RequestParam String id) {
        System.out.println("Get by group id " + id);

        List<MessageDto> result = messageService.findMessageByGroupId(Long.valueOf(id));
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id) {
        System.out.println("Get by id " + id);

        MessageDto message = messageService.findById(Long.valueOf(id));
        if (message == null)
            return new ResponseEntity<String>("Message not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<MessageDto>(message, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody MessageDto message) {
        try {
            messageService.save(message);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<MessageDto>(message, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        System.out.println("Deleting message ... " + id);
        try {
            messageService.deleteById(Long.valueOf(id));
            return new ResponseEntity<Message>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Ctrl Delete message exception");
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {//era RepositoryException
        return e.getMessage();
    }
}


