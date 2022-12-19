package ro.moment.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.repository.MessageRepository;
import ro.moment.api.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        messageService.save(message);
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

      /*
    Metoda ce imi returneaza toate mesajele pentru un mesaj parinte
     */

    /*
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMessagesByParentMessageId(@RequestParam  String id) {
        System.out.println("Get messages by parent message Id " + id);

        List<MessageDto> result = messageService.findMessagesByParenMessagetId(Long.valueOf(id));
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }


     */

    /*
    Metoda ce imi returneaza toate mesajele unui utilizator, din toate grupurile din care face parte
    Sunt returnate doar mesajele principale (adica cele care nu au un mesaj parinte), nu si comentariile
   */
/*
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMessagesForOneUser(@RequestParam  String id) {
        System.out.println("Get messages for one user " + id);

        List<MessageDto> result = messageService.findAllByUserIdDtos(Long.valueOf(id));
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }

 */

    /*
    Metoda ce imi returneaza sub-mesajul (comentariul) cel mai popular al unui mesaj
    */
/*
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMostPopularMessageByParentMessageId(@RequestParam  String id) {
        System.out.println("Get most popular message " + id);

        List<MessageDto> result = messageService.mostPopularMessages(Long.valueOf(id));
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }

 */


}


