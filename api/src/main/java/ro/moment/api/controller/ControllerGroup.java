package ro.moment.api.controller;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ro.moment.api.domain.Group;
import ro.moment.api.repository.GroupRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class ControllerGroup {
    GroupRepository groupRepository;

    //returns the list of all stored groups
    public List<Group> findAll(){
        return groupRepository.findAll();
    }

    //if the given group has a valid name, save the group in the db, else do nothing for now
    //TODO: create separate class for validation, throw exception when the given group is not valid
    public void save(Group group){
        if (valid_group(group)){
            groupRepository.save(group);
        }
    }

    //Verifies if the given group is valid
    //For now it only checks the name of the group to match the imposed pattern
    //Only names composed by letters are accepted
    private boolean valid_group(@NotNull Group group){
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z]+$");
        Matcher matcher = pattern.matcher(group.getName());
        return matcher.matches();
    }
}
