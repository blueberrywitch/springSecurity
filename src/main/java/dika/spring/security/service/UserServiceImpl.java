package dika.spring.security.service;

import dika.spring.security.dto.reqest.UserRequestDTO;
import dika.spring.security.dto.response.UserResponseDTO;
import dika.spring.security.enums.Roles;
import dika.spring.security.exception.UserNotFoundException;
import dika.spring.security.mapper.UserMapper;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import dika.spring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO add(UserRequestDTO user) {
        return userMapper.toDTO(userRepository.save(userMapper.fromDTO(user)));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found"));

    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(UUID externalId, UserResponseDTO user) {
        Optional<User> userOptional = userRepository.findByExternalId(externalId);
        if (userOptional.isPresent()) {
            User userUpdate = userOptional.get();
            updateFields(user.getUsername(), userUpdate::setUsername);
            updateFields(user.getPassword(), userUpdate::setPassword);
            LinksEntity newLink = userUpdate.getLinksEntity();
            userUpdate.setLinksEntity(updateLinks(userMapper.fromDTO(user.getLinksEntityDTO()), newLink));
            userMapper.toDTO(userRepository.save(userUpdate));
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByExternalId(UUID externalId) {
        return userRepository.findByExternalId(externalId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteByExternalId(UUID externalId) {
        User user = (userRepository.findByExternalId(externalId)).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        userRepository.deleteById(user.getId());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found"));
    }


    private void updateFields(String newParam, Consumer<String> oldParam) {
        if (newParam != null) {
            oldParam.accept(newParam);
        }
    }

    private LinksEntity updateLinks(LinksEntity newParam, LinksEntity oldParam) {
        if (newParam == null) {
            return oldParam;
        }
        updateFields(newParam.getTgRef(), oldParam::setTgRef);
        updateFields(newParam.getInstRef(), oldParam::setInstRef);
        updateFields(newParam.getVkRef(), oldParam::setVkRef);
        return oldParam;
    }

    @Override
    public void addRoles(User user, List<Roles> roles) {
        user.setRole(roles);
        UserRequestDTO userResponseDTO = new UserRequestDTO(user.getUsername(), user.getPassword(), user.getRole(),
                userMapper.toDTO(user.getLinksEntity()));

        userRepository.deleteById(user.getId());
        add(userResponseDTO);
        log.info("User roles {}", user.getRole());
    }

}
