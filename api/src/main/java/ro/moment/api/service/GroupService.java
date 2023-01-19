package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.*;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.repository.GroupRepository;
import ro.moment.api.repository.SubscriptionRepository;
import ro.moment.api.security.utils.JwtService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final JwtService jwtService;
    private final SubscriptionRepository subscriptionRepository;

    private void validateGroup(GroupDto groupDto) {
        StringBuilder builder = new StringBuilder();

        if (groupDto.getName() != null) {
            if (groupDto.getName().isEmpty()) {
                builder.append("Group name should contain at least one letter\n");
            }
        }
        else {
            builder.append("Group name should not be null\n");
        }

        if (!builder.isEmpty()) {
            throw new ValidationException(builder.toString());
        }
    }

    public List<GroupDto> findAll(String token) {
        User user = jwtService.getUserByToken(token);
        List<Group> groups = groupRepository.findAll();
        List<GroupDto> dtos = new ArrayList<GroupDto>();

        groups.forEach(g -> {
            GroupDto groupDto = new GroupDto(g);
            if(subscriptionRepository.existsByUserIdAndGroupId(user.getId(),g.getId()))
                groupDto.setSubscribed(true);
            dtos.add(new GroupDto(g));
        });
        return dtos;
    }

    public GroupDto findById(Long id, String token) {
        User user = jwtService.getUserByToken(token);
        Optional<Group> group = groupRepository.findById(id);
        GroupDto groupDto;
        if(group.isPresent()){
            groupDto = new GroupDto(group.get());
            if(subscriptionRepository.existsByUserIdAndGroupId(user.getId(),group.get().getId()))
                groupDto.setSubscribed(true);
        }else{
            groupDto=null;
        }
        return groupDto;
    }

    public void save(GroupDto groupDto) {
        validateGroup(groupDto);
        Group group = groupDto.toDomain();
        group.setCreatedDate(LocalDate.now());
        group = groupRepository.save(group);
        groupDto.setId(group.getId());
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public boolean subscribeGroup(Long groupId, String userToken) {
        User user = jwtService.getUserByToken(userToken);
        Optional<Group> group = groupRepository.findById(groupId);

        if (group.isEmpty() || subscriptionRepository.existsByUserIdAndGroupId(user.getId(), groupId)) {
            return false;
        }
        subscriptionRepository.save(new Subscription(user, group.get()));
        return true;
    }

    public boolean unsubscribeGroup(Long groupId, String userToken) {
        User user = jwtService.getUserByToken(userToken);

        Subscription subscription = subscriptionRepository.getByUserIdAndGroupId(user.getId(), groupId);
        if (subscription == null) {
            return false;
        }

        subscriptionRepository.delete(subscription);
        return true;
    }
}
