package ro.moment.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.service.MessageService;
import ro.moment.api.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MessageDto> getAll(@RequestHeader("Authorization") String token) {
        System.out.println("Get all messages ...");
        token = token.replaceFirst("Bearer ", "");
        return messageService.findAll(token);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getByGroupId(@RequestParam Long id, @RequestHeader("Authorization") String token) {
        System.out.println("Get by group id " + id);
        token = token.replaceFirst("Bearer ", "");

        List<MessageDto> result = messageService.findMessageByGroupId(id, token);
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        System.out.println("Get by id " + id);
        token = token.replaceFirst("Bearer ", "");

        MessageDto message = messageService.findById(id, token);
        if (message == null)
            return new ResponseEntity<String>("Message not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<MessageDto>(message, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody MessageDto message,@RequestHeader("Authorization") String token) {
        try {
            token = token.replaceFirst("Bearer ", "");
            messageService.save(message,token);
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

      /*
    Metoda ce imi returneaza toate mesajele pentru un mesaj parinte
     */


    @RequestMapping(method = RequestMethod.GET, params = "parentId")
    public ResponseEntity<?> getMessagesByParentMessageId(@RequestParam Long parentId, @RequestHeader("Authorization") String token) {
        System.out.println("Get messages by parent message Id " + parentId);
        token = token.replaceFirst("Bearer ", "");

        List<MessageDto> result = messageService.findMessagesByParenMessageId(parentId, token);
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }



    /*
    Metoda ce imi returneaza toate mesajele unui utilizator, din toate grupurile din care face parte
    Sunt returnate doar mesajele principale (adica cele care nu au un mesaj parinte), nu si comentariile
   */

    @RequestMapping(method = RequestMethod.GET, params="username")
    public ResponseEntity<?> getMessagesForOneUser(@RequestParam  String username, @RequestHeader("Authorization") String token) {
        User user = this.userService.findUserByUsername(username);
        System.out.println("Get messages for one user " + username + " and id = " + user.getId());
        token = token.replaceFirst("Bearer ", "");

        List<MessageDto> result = messageService.findAllByUserIdDtos(user.getId(), token);
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }



    /*
    Metoda ce imi returneaza sub-mesajul (comentariul) cel mai popular al unui mesaj
    */

    @RequestMapping( method = RequestMethod.GET, params="usernameForLikes" )
    public ResponseEntity<?> getMostPopularMessageByParentMessageId(@RequestParam  String usernameForLikes, @RequestHeader("Authorization") String token) {
        User user = this.userService.findUserByUsername(usernameForLikes);
        System.out.println("Get most popular message " + usernameForLikes);
        token = token.replaceFirst("Bearer ", "");

        List<MessageDto> result = messageService.mostPopularMessages(user.getId(), token);
        return new ResponseEntity<List<MessageDto>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/like", method = RequestMethod.PATCH)
    public ResponseEntity<?> like(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        System.out.println("Like message " + id);
        token = token.replaceFirst("Bearer ", "");

        if (messageService.likeMessage(id, token))
            return new ResponseEntity<>("liked", HttpStatus.OK);

        return new ResponseEntity<>("Unable to like", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}/dislike", method = RequestMethod.PATCH)
    public ResponseEntity<?> dislike(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        System.out.println("Dislike message " + id);
        token = token.replaceFirst("Bearer ", "");

        if (messageService.dislikeMessage(id, token))
            return new ResponseEntity<>("disliked", HttpStatus.OK);

        return new ResponseEntity<>("Unable to dislike", HttpStatus.BAD_REQUEST);
    }
}