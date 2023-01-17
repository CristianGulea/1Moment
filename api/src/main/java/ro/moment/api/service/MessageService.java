package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Like;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.Subscription;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.repository.*;
import ro.moment.api.security.utils.JwtService;

import java.util.*;

import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final JwtService jwtService;

    private void validateMessage(MessageDto messageDto) {
        StringBuilder builder = new StringBuilder();

        if (messageDto.getUserId() == null) {
            builder.append("Message UserId should not be null\n");
        }

        if (messageDto.getGroupId() == null) {
            builder.append("Message GroupId should not be null\n");
        }

        if (messageDto.getTitle() == null) {
            builder.append("Message Title should not be null\n");
        }

        if (messageDto.getContent() == null) {
            builder.append("Message Content should not be null\n");
        }

        if (!builder.isEmpty()) {
            throw new ValidationException(builder.toString());
        }
    }

    public List<MessageDto> findAll(String token) {
        List<Message> messages = messageRepository.findByPublishDateBefore(LocalDateTime.now());
        return convertToMessageDTOs(messages, token);
    }

    public List<MessageDto> findMessageByGroupId(Long id, String token) {
        List<Message> messages = messageRepository.findByGroupIdAndPublishDateBefore(id, LocalDateTime.now());
        return convertToMessageDTOs(messages, token);
    }


    public MessageDto findById(Long id, String token) {
        Optional<Message> message = messageRepository.findById(id);

        return message.map(value -> convertToMessageDTO(value, token)).orElse(null);
    }

    public void save(MessageDto messageDto) {
        if (messageDto.getPublishDate() == null || messageDto.getPublishDate().isBefore(LocalDateTime.now())) {
            messageDto.setPublishDate(LocalDateTime.now());
        }

        validateMessage(messageDto);
        Message message = messageDto.toDomain();
        Long userId = messageDto.getUserId();       //todo: validate this
        message.getUser().setId(userId);
        Long groupId =messageDto.getGroupId();      //todo: validate this
        message.getGroup().setId(groupId);

        messageRepository.save(message);
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }


    public List<MessageDto> findMessagesByParenMessageId(Long id, String token) {
        List<Message> messages = messageRepository.findMessagesByParentMessageIdAndPublishDateBefore(id, LocalDateTime.now());

        return convertToMessageDTOs(messages, token);
    }

    private List<Message> findAllByUserId(Long id) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(id);

        List<Message> foundMessages = new ArrayList<Message>();

        for (int i=0;i<subscriptions.size();i++)
        {
            List<Message> auxiliar = new ArrayList<Message>();
            auxiliar = messageRepository.findByGroupIdAndPublishDateBeforeAndParentMessageId(subscriptions.get(i).getGroup().getId(), LocalDateTime.now(),null);
            for (int j=0;j<auxiliar.size();j++)
            {
                foundMessages.add(auxiliar.get(j));
            }

        }

        List<Message> finalFoundMessages = new ArrayList<Message>();

        for (int i=0;i<foundMessages.size();i++)
        {
            if (foundMessages.get(i).getParentMessage() == null)
                finalFoundMessages.add(foundMessages.get(i));
        }


        return finalFoundMessages;
    }


    public List<MessageDto> findAllByUserIdDtos(Long id, String token) {
        List<Message> messages = findAllByUserId(id);
        return convertToMessageDTOs(messages, token);
    }

    private MessageDto convertToMessageDTO(Message message, String userToken) {
        MessageDto dto = new MessageDto(message);
        User user = jwtService.getUserByToken(userToken);
        dto.setLiked(
                likeRepository.existsLikeByUserIdAndMessageId(
                        user.getId(),
                        message.getId()
                )
        );
        dto.setLikeCount(
                likeRepository.countByMessageId(
                        message.getId()
                )
        );
        return dto;
    }
    private List<MessageDto> convertToMessageDTOs(List<Message> messages, String token) {
        List<MessageDto> dtos = new ArrayList<MessageDto>();
        messages.forEach(m -> dtos.add(convertToMessageDTO(m, token)));
        return dtos;
    }

    private MessageDto mostPopularMessageByParentMessageId(Long id, String token) {
        List<Message> messages = messageRepository.findMessagesByParentMessageIdAndPublishDateBefore(id, LocalDateTime.now());


        long maxNumberOfLikes = 0;
        Message mesajGasit = new Message();

        for (int i=0;i<messages.size();i++)
        {
            long numberOfLikes = likeRepository.countByMessageId(messages.get(i).getId());
            if (numberOfLikes>maxNumberOfLikes)
                {
                    maxNumberOfLikes=numberOfLikes;
                    mesajGasit = messages.get(i);
                }

        }

        if (messages.size() != 0)
        {
            if (maxNumberOfLikes == 0){
            return convertToMessageDTO(messages.get(0), token);
            }
            return convertToMessageDTO(mesajGasit, token);
        }

        return null;

    }

    public List<MessageDto> mostPopularMessages(Long userId, String token){
        List<Message> mesajeUser = findAllByUserId(userId);
        List<MessageDto> result = new ArrayList<MessageDto>();

        for (Message m: mesajeUser){
            if (mostPopularMessageByParentMessageId(m.getId(), token)!=null){
                result.add(mostPopularMessageByParentMessageId(m.getId(), token));
            }
        }

        return result;

    }

    public boolean likeMessage(Long messageId, String userToken) {
        User user = jwtService.getUserByToken(userToken);
        Message message = messageRepository.findMessageByIdAndPublishDateBefore(messageId, LocalDateTime.now());

        if (user == null || message == null) {
            return false;
        }

        if (likeRepository.existsLikeByUserIdAndMessageId(user.getId(), messageId)) {
            return false;
        }

        likeRepository.save(new Like(user, message));
        return true;
    }

    public boolean dislikeMessage(Long messageId, String userToken) {
        User user = jwtService.getUserByToken(userToken);
        Message message = messageRepository.findMessageByIdAndPublishDateBefore(messageId, LocalDateTime.now());

        if (user == null || message == null) {
            return false;
        }

        Like like = likeRepository.getLikeByUserIdAndMessageId(user.getId(), messageId);
        if (like == null) {
            return false;
        }

        likeRepository.delete(like);
        return true;
    }
}
