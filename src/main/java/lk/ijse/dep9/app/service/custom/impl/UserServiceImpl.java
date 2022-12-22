package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.repository.ProjectRepository;
import lk.ijse.dep9.app.repository.TaskRepository;
import lk.ijse.dep9.app.repository.UserRepository;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exception.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final Transformer transformer;

    public UserServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, Transformer transformer) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userRepository.save(transformer.toUser(userDTO));
    }

    @Override
    public UserDTO verifyUser(String username, String password) {
        UserDTO user = userRepository.findById(username).map(transformer::toUserDTO)
                .orElseThrow(AuthenticationException::new);
        if (DigestUtils.sha256Hex(password).equals(user.getPassword())){
            return user;
        }
        throw new AuthenticationException();
    }

    @Override
    public UserDTO getUserAccountDetails(String username) {
        return userRepository.findById(username).map(transformer::toUserDTO).get();
    }

    @Override
    public void updateUserAccountDetails(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userRepository.save(transformer.toUser(userDTO));
    }

    @Override
    public void deleteUserAccount(String username) {
        List<Project> projectList = projectRepository.findAllProjectsByUsername(username);
        for (Project project : projectList) {
            List<Task> taskList = taskRepository.findAllTasksByProjectId(project.getId());
            taskList.forEach(task -> taskRepository.deleteById(task.getId()));
            projectRepository.deleteById(project.getId());
        }
        userRepository.deleteById(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userRepository.findById(username).map(transformer::toUserDTO).
                orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
