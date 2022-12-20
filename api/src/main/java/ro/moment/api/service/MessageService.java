package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.Subscription;
import ro.moment.api.domain.dto.MessageDto;
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
        //todo: validate fields
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

        return message.map(MessageDto::new).orElse(null);
    }

    public void save(MessageDto messageDto) {
        validateMessage(messageDto);
        Message message = messageDto.toDomain();
        Long userId = messageDto.getUserId();       //todo: validate this
        message.getUser().setId(userId);
        Long groupId =messageDto.getGroupId();      //todo: validate this
        message.getGroup().setId(groupId);

        message.setCreatedDate(LocalDate.now());
        messageRepository.save(message);
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }


    public List<MessageDto> findMessagesByParenMessageId(Long id) {
        List<Message> messages = messageRepository.findMessagesByParentMessageId(id);
        List<MessageDto> dtos = new ArrayList<MessageDto>();

        messages.forEach(m -> dtos.add(new MessageDto(m)));
        return dtos;
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


    public List<MessageDto> findAllByUserIdDtos(Long id){
        List<Message> messages = findAllByUserId(id);

    private List<MessageDto> convertToMessageDTOs(List<Message> messages){

        List<MessageDto> dtos = new ArrayList<MessageDto>();
        messages.forEach(m -> dtos.add(new MessageDto(m)));
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

        if (messages.size()!= 0)
        {
            if (maxNumberOfLikes == 0){
            return new MessageDto(messages.get(0));
            }
            return new MessageDto(mesajGasit);
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
