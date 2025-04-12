package dika.spring.security.service.impl;

import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.exception.UserNotFoundException;
import dika.spring.security.mapper.LinksMapper;
import dika.spring.security.mapper.UserMapper;
import dika.spring.security.model.LinksDto;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import dika.spring.security.repository.UserRepository;
import dika.spring.security.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final LinksMapper linksMapper;
    private final TgPhotoServiceImpl tgPhotoService;
    private final VkPhotoServiceImpl vkPhotoService;


    @Override
    public UserResponseDto add(UserRequestDto user) {
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

    @Transactional
    @Override
    public void update(UUID externalId, UserResponseDto user) {
        User userUpdate = userRepository.findByExternalId(externalId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        updateFields(user.getUsername(), userUpdate::setUsername);
        updateFields(user.getPassword(), userUpdate::setPassword);
        LinksEntity newLink = userUpdate.getLinksEntity();

        userUpdate.setLinksEntity(updateLinks(linksMapper.fromDTO(user.getLinksEntityDTO()), newLink));
        userMapper.toDTO(userRepository.save(userUpdate));
    }

    @Transactional
    @Override
    public void updateRole(UUID externalId, List<Roles> roles) {
        User user = userRepository.findByExternalId(externalId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        user.setRole(roles);
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

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        userRepository.deleteById(user.getId());
    }

    @Override
    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch
                (a -> a.getAuthority().equals("ADMIN"));
    }

    @Override
    public Map<User, LinksDto> getPhotos() throws TelegramApiException {
        Map<User, LinksDto> map = new HashMap<>();
        List<User> users = userRepository.findAllWithLinksAndRoles();
        for (User user : users) {
            map.put(user, new LinksDto(getPhotoVK(user.getLinksEntity().getVkRef()),
                    getPhotoTG(user.getLinksEntity().getTgRef())));
        }
        return map;
    }

    private String getPhotoVK(String userID) {
        return vkPhotoService.getPhoto(userID);
    }

    private String getPhotoTG(String tg) {
        return tgPhotoService.getPhoto(tg);
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
}
