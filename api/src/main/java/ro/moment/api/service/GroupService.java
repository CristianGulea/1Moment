package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.exceptions.ValidationException;
import ro.moment.api.repository.GroupRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

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

    public List<GroupDto> findAll() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDto> dtos = new ArrayList<GroupDto>();

        groups.forEach(g -> dtos.add(new GroupDto(g)));
        return dtos;
    }

    public GroupDto findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);

        return group.map(GroupDto::new).orElse(null);
    }

    public void save(GroupDto groupDto) {
        validateGroup(groupDto);
        Group group = groupDto.toDomain();
        group.setCreatedDate(LocalDate.now());
        groupRepository.save(group);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
