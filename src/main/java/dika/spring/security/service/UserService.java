package dika.spring.security.service;

import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.model.LinksDto;
import dika.spring.security.model.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    UserResponseDto add(UserRequestDto user);

    User findById(Long id);

    void deleteById(Long id);

    void update(UUID externalId, UserResponseDto user);

    List<User> findAll();

    User findByExternalId(UUID externalId);

    void deleteByExternalId(UUID externalId);

    User findUserByUsername(String username);

    void updateRole(UUID externalId, List<Roles> roles);

    boolean isAdmin();

    void deleteByUsername(String username);

    Map<User, LinksDto> getPhotos() throws TelegramApiException;
}
