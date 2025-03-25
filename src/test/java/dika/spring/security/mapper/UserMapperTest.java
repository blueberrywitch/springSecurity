package dika.spring.security.mapper;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest extends AbstractMapperTest{

    @Test
    public void toDTOTest() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setExternalId(externalId);
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));


        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef("qwerty");
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef("qwerty");

        user.setLinksEntity(linksEntity);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef("qwerty");
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef("qwerty");

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setLinksEntityDTO(linksEntityDto);
        userResponseDto.setPassword("123");
        userResponseDto.setUsername("Anacondaz");
        userResponseDto.setExternalId(externalId);
        userResponseDto.setRole(List.of(Roles.USER));

        UserResponseDto result = userMapper.toDTO(user);
        assertNotNull(result);
        assertEquals(userResponseDto.getPassword(), result.getPassword());
        assertEquals(userResponseDto.getRole(), result.getRole());
        assertEquals(userResponseDto.getUsername(), result.getUsername());
        assertEquals(userResponseDto.getExternalId(), result.getExternalId());

        assertEquals(linksEntityDto.getInstRef(), result.getLinksEntityDTO().getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getLinksEntityDTO().getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getLinksEntityDTO().getVkRef());
    }

    @Test
    public void toDTOTestAllLinksNull() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setExternalId(externalId);
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef(null);
        linksEntity.setTgRef(null);

        user.setLinksEntity(linksEntity);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef(null);
        linksEntityDto.setTgRef(null);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setLinksEntityDTO(linksEntityDto);
        userResponseDto.setPassword("123");
        userResponseDto.setUsername("Anacondaz");
        userResponseDto.setExternalId(externalId);
        userResponseDto.setRole(List.of(Roles.USER));

        UserResponseDto result = userMapper.toDTO(user);
        assertNotNull(result);
        assertEquals(userResponseDto.getPassword(), result.getPassword());
        assertEquals(userResponseDto.getRole(), result.getRole());
        assertEquals(userResponseDto.getUsername(), result.getUsername());
        assertEquals(userResponseDto.getExternalId(), result.getExternalId());

        assertEquals(linksEntityDto.getInstRef(), result.getLinksEntityDTO().getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getLinksEntityDTO().getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getLinksEntityDTO().getVkRef());
    }

    @Test
    public void fromDTOTest() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setExternalId(externalId);
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef("qwerty");
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef("qwerty");

        user.setLinksEntity(linksEntity);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef("qwerty");
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef("qwerty");

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLinksEntityDTO(linksEntityDto);
        userRequestDto.setPassword("123");
        userRequestDto.setUsername("Anacondaz");
        userRequestDto.setRole(List.of(Roles.USER));

        User result = userMapper.fromDTO(userRequestDto);
        assertNotNull(result);
        assertEquals(userRequestDto.getPassword(), result.getPassword());
        assertEquals(userRequestDto.getRole(), result.getRole());
        assertEquals(userRequestDto.getUsername(), result.getUsername());

        assertEquals(linksEntityDto.getInstRef(), result.getLinksEntity().getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getLinksEntity().getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getLinksEntity().getVkRef());
    }

    @Test
    public void fromDTOTestAllLinksNull() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setExternalId(externalId);
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef(null);
        linksEntity.setTgRef(null);

        user.setLinksEntity(linksEntity);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef(null);
        linksEntityDto.setTgRef(null);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLinksEntityDTO(linksEntityDto);
        userRequestDto.setPassword("123");
        userRequestDto.setUsername("Anacondaz");
        userRequestDto.setRole(List.of(Roles.USER));

        User result = userMapper.fromDTO(userRequestDto);
        assertNotNull(result);
        assertEquals(userRequestDto.getPassword(), result.getPassword());
        assertEquals(userRequestDto.getRole(), result.getRole());
        assertEquals(userRequestDto.getUsername(), result.getUsername());

        assertEquals(linksEntityDto.getInstRef(), result.getLinksEntity().getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getLinksEntity().getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getLinksEntity().getVkRef());
    }
}