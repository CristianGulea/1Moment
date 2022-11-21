package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    private void validateGroup(GroupDto groupDto) {
        //todo: validate fields
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

    public Group save(GroupDto groupDto) {
        validateGroup(groupDto);
        return groupRepository.save(groupDto.toDomain());
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
