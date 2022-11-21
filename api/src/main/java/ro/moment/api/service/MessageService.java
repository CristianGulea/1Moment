package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Message;
import ro.moment.api.repository.LikeRepository;
import ro.moment.api.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final LikeRepository likeRepository;

    private void validateMessage(Message message) {
        //todo: validate fields
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findMessageByGroupId(Long id) {
        return messageRepository.findMessageByGroupId(id);
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public Message save(Message message) {
        validateMessage(message);
        return messageRepository.save(message);
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }
}
