package Service;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.exception.UserNotFoundException;
import dika.spring.security.mapper.UserMapper;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import dika.spring.security.repository.UserRepository;
import dika.spring.security.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void testAdd() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUsername("Anacondaz");
        requestDto.setPassword("123");

        User user = new User();
        user.setUsername("Anacondaz");
        user.setPassword("123");

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setUsername("Anacondaz");
        responseDto.setPassword("123");

        when(userMapper.fromDTO(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(responseDto);

        UserResponseDto result = userServiceImpl.add(requestDto);

        assertEquals(responseDto, result);
    }

    @Test
    public void testFindByIdFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User result = userServiceImpl.findById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testFindByIdNotFound() {
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.findById(2L);
        });
    }

    @Test
    public void testDeleteById() {
        User user = new User();
        Long id = 1L;
        user.setId(id);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        doNothing().when(userRepository).deleteById(id);

        userServiceImpl.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateFound() {

        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef("oldTg");
        oldLinks.setInstRef("oldInst");
        oldLinks.setVkRef("oldVk");
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef("newTg");
        updatedLinks.setInstRef("newInst");
        updatedLinks.setVkRef("newVk");

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize MC", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertEquals("newTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("newInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("newVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateNotFound() {
        UUID externalId = UUID.randomUUID();
        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword("456");

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.update(externalId, dto);
        });
    }

    @Test
    public void testUpdateAllLinksNull() {

        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef(null);
        linksDto.setInstRef(null);
        linksDto.setVkRef(null);
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef(null);
        oldLinks.setInstRef(null);
        oldLinks.setVkRef(null);
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef(null);
        updatedLinks.setInstRef(null);
        updatedLinks.setVkRef(null);

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize MC", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertNull(savedUser.getLinksEntity().getTgRef());
        assertNull(savedUser.getLinksEntity().getInstRef());
        assertNull(savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateNewUserNameNull() {
        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(null);
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef("oldTg");
        oldLinks.setInstRef("oldInst");
        oldLinks.setVkRef("oldVk");
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef("newTg");
        updatedLinks.setInstRef("newInst");
        updatedLinks.setVkRef("newVk");

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Anacondaz", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertEquals("newTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("newInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("newVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateNewPasswordNull() {
        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword(null);

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef("oldTg");
        oldLinks.setInstRef("oldInst");
        oldLinks.setVkRef("oldVk");
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef("newTg");
        updatedLinks.setInstRef("newInst");
        updatedLinks.setVkRef("newVk");

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize MC", savedUser.getUsername());
        assertEquals("123", savedUser.getPassword());

        assertEquals("newTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("newInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("newVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateNewLinksNull() {
        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize Ms");
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef("oldTg");
        oldLinks.setInstRef("oldInst");
        oldLinks.setVkRef("oldVk");
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef(null);
        updatedLinks.setInstRef(null);
        updatedLinks.setVkRef(null);

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize Ms", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertEquals("oldTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("oldInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("oldVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateOldLinksNull() {
        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef(null);
        oldLinks.setInstRef(null);
        oldLinks.setVkRef(null);
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef("newTg");
        updatedLinks.setInstRef("newInst");
        updatedLinks.setVkRef("newVk");

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize MC", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertEquals("newTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("newInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("newVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateSomeOldLinksNull() {
        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef(null);
        oldLinks.setInstRef("oldInst");
        oldLinks.setVkRef(null);
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef("newTg");
        updatedLinks.setInstRef("newInst");
        updatedLinks.setVkRef("newVk");

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize MC", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertEquals("newTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("newInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("newVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateSomeNewLinksNull() {
        UUID externalId = UUID.randomUUID();

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername("Noize MC");
        dto.setPassword("456");

        LinksEntityDto linksDto = new LinksEntityDto();
        linksDto.setTgRef("newTg");
        linksDto.setInstRef("newInst");
        linksDto.setVkRef("newVk");
        dto.setLinksEntityDTO(linksDto);

        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setPassword("123");

        LinksEntity oldLinks = new LinksEntity();
        oldLinks.setTgRef("oldTg");
        oldLinks.setInstRef("oldInst");
        oldLinks.setVkRef("oldVk");
        user.setLinksEntity(oldLinks);

        LinksEntity updatedLinks = new LinksEntity();
        updatedLinks.setTgRef(null);
        updatedLinks.setInstRef("newInst");
        updatedLinks.setVkRef(null);

        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(userMapper.fromDTO(dto.getLinksEntityDTO())).thenReturn(updatedLinks);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(dto);

        userServiceImpl.update(externalId, dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Noize MC", savedUser.getUsername());
        assertEquals("456", savedUser.getPassword());

        assertEquals("oldTg", savedUser.getLinksEntity().getTgRef());
        assertEquals("newInst", savedUser.getLinksEntity().getInstRef());
        assertEquals("oldVk", savedUser.getLinksEntity().getVkRef());
    }

    @Test
    public void testUpdateRole() {
        UUID externalId = UUID.randomUUID();
        List<Roles> roles = List.of(Roles.ADMIN);
        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");
        user.setRole(List.of(Roles.USER));

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.of(user));

        userServiceImpl.updateRole(externalId, roles);

        assertEquals(roles, user.getRole());

        verify(userRepository, times(1)).findByExternalId(externalId);
    }

    @Test
    public void testUpdateRoleNotFound() {
        UUID externalId = UUID.randomUUID();
        List<Roles> roles = List.of(Roles.ADMIN);

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.updateRole(externalId, roles);
        });
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("john");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("jane");

        List<User> expectedUsers = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userServiceImpl.findAll();

        assertEquals(expectedUsers, actualUsers);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> actualUsers = userServiceImpl.findAll();

        assertNotNull(actualUsers);
        assertTrue(actualUsers.isEmpty());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findByExternalId() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.of(user));

        User result = userServiceImpl.findByExternalId(externalId);

        assertEquals(user, result);
    }

    @Test
    public void findByExternalIdNotFound() {
        UUID externalId = UUID.randomUUID();

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.findByExternalId(externalId);
        });
    }

    @Test
    public void deleteByExternalId() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setId(1L);
        user.setExternalId(externalId);
        user.setUsername("Anacondaz");

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        userServiceImpl.deleteByExternalId(externalId);

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void deleteByExternalIdNotFound() {
        UUID externalId = UUID.randomUUID();

        when(userRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.deleteByExternalId(externalId);
        });
    }

    @Test
    public void findUserByUsername() {
        String username = "Anacondaz";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userServiceImpl.findUserByUsername(username);

        assertEquals(user, result);
    }

    @Test
    public void findUserByUsernameNotFound() {
        String username = "Anacondaz";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.findUserByUsername(username);
        });
    }

    @Test
    public void deleteByUsername() {
        String username = "Anacondaz";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        userServiceImpl.deleteByUsername(username);

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void deleteByUsernameNotFound() {
        String username = "Anacondaz";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.deleteByUsername(username);
        });
    }

    @Test
    public void testIsAdminReturnsTrue() {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("user", null,
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        assertTrue(userServiceImpl.isAdmin());
    }

    @Test
    public void testIsAdminReturnsFalse() {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("user", null,
                        Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        assertFalse(userServiceImpl.isAdmin());
    }
}
