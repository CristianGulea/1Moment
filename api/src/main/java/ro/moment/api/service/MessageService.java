package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Message;
import ro.moment.api.domain.dto.MessageDto;
import ro.moment.api.repository.GroupRepository;
import ro.moment.api.repository.LikeRepository;
import ro.moment.api.repository.MessageRepository;
import ro.moment.api.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

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

    private List<MessageDto> convertToMessageDTOs(List<Message> messages){
        List<MessageDto> dtos = new ArrayList<MessageDto>();
        messages.forEach(m -> dtos.add(new MessageDto(m)));
        return dtos;
    }
}
