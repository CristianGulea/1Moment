package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.Subscription;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.repository.*;

import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SubscriptionRepository subscriptionRepository;

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

    public List<MessageDto> findAll() {
        List<Message> messages = messageRepository.findByPublishDateBefore(LocalDateTime.now());
        return convertToMessageDTOs(messages);
    }

    public List<MessageDto> findMessageByGroupId(Long id) {
        List<Message> messages = messageRepository.findByPublishDateBeforeAndGroup_Id(LocalDateTime.now(),id);
        return convertToMessageDTOs(messages);
    }


    public MessageDto findById(Long id) {
        Optional<Message> message = messageRepository.findById(id);

        return message.map(this::convertToMessageDTO).orElse(null);
    }

    public void save(MessageDto messageDto) {
        if (messageDto.getPublishDate() == null || messageDto.getPublishDate().isBefore(LocalDateTime.now())) {
            messageDto.setCreatedDate(LocalDate.now());
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


    public List<MessageDto> findMessagesByParenMessageId(Long id) {
        List<Message> messages = messageRepository.findMessagesByParentMessageId(id);

        return convertToMessageDTOs(messages);
    }

    private List<Message> findAllByUserId(Long id) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(id);

        List<Message> foundMessages = new ArrayList<Message>();

        for (int i=0;i<subscriptions.size();i++)
        {
            List<Message> auxiliar = new ArrayList<Message>();
            auxiliar = messageRepository.findMessagesByGroupName(subscriptions.get(i).getGroup().getName());
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


    public List<MessageDto> findAllByUserIdDtos(Long id) {
        List<Message> messages = findAllByUserId(id);
        return convertToMessageDTOs(messages);
    }

    private MessageDto convertToMessageDTO(Message message) {
        MessageDto dto = new MessageDto(message);
        dto.setLiked(
                likeRepository.existsLikeByUserIdAndMessageId(
                        message.getUser().getId(),
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
    private List<MessageDto> convertToMessageDTOs(List<Message> messages) {
        List<MessageDto> dtos = new ArrayList<MessageDto>();
        messages.forEach(m -> dtos.add(convertToMessageDTO(m)));
        return dtos;
    }

    private MessageDto mostPopularMessageByParentMessageId(Long id) {
        List<Message> messages = messageRepository.findMessagesByParentMessageId(id);


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
            return convertToMessageDTO(messages.get(0));
            }
            return convertToMessageDTO(mesajGasit);
        }

        return null;

    }

    public List<MessageDto> mostPopularMessages(Long userId){
        List<Message> mesajeUser = findAllByUserId(userId);
        List<MessageDto> result = new ArrayList<MessageDto>();

        for (Message m: mesajeUser){
            if (mostPopularMessageByParentMessageId(m.getId())!=null){
                result.add(mostPopularMessageByParentMessageId(m.getId()));
            }
        }

        return result;

    }


}
