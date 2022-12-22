package ro.moment.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.moment.api.domain.Group;
import ro.moment.api.domain.User;
import ro.moment.api.domain.dto.GroupDto;
import ro.moment.api.domain.dto.UserDto;
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

    public void save(GroupDto groupDto) {
        validateGroup(groupDto);
        groupRepository.save(groupDto.toDomain());
    }

    //adds a new Member to an existing group
    public void addMember(GroupDto group, UserDto user){
        Group groupDomain = group.toDomain();
        groupDomain.addUser(user.toDomain());
        groupRepository.save(groupDomain);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
