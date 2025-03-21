package dika.spring.security.service;

import dika.spring.security.dto.reqest.UserRequestDTO;
import dika.spring.security.dto.response.UserResponseDTO;
import dika.spring.security.enums.Roles;
import dika.spring.security.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO add(UserRequestDTO user);

    User findById(Long id);

    void deleteById(Long id);

    void update(UUID externalId, UserResponseDTO user);

    List<User> findAll();

    User findByExternalId(UUID externalId);

    void deleteByExternalId(UUID externalId);

    User findUserByUsername(String username);

    void addRoles(User user, List<Roles> roles);
}
