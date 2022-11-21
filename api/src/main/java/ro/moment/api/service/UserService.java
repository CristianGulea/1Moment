package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.UserDto;
import ro.moment.api.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private void validateUser(UserDto userDto) {
        //todo: validate fields
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<UserDto>();

        users.forEach(u -> dtos.add(new UserDto(u)));
        return dtos;
    }

    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(UserDto::new).orElse(null);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }

    public void save(UserDto user) {
        validateUser(user);
        userRepository.save(user.toDomain());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
