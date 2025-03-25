package dika.spring.security.mapper;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

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

        UserResponseDto result = mapper.toDTO(user);
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

        UserResponseDto result = mapper.toDTO(user);
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
    public void toDTOTestSomeLinksNull() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setExternalId(externalId);
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef(null);

        user.setLinksEntity(linksEntity);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef(null);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setLinksEntityDTO(linksEntityDto);
        userResponseDto.setPassword("123");
        userResponseDto.setUsername("Anacondaz");
        userResponseDto.setExternalId(externalId);
        userResponseDto.setRole(List.of(Roles.USER));

        UserResponseDto result = mapper.toDTO(user);
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
    public void fromDTOTestSomeLinksNull() {
        UUID externalId = UUID.randomUUID();
        User user = new User();
        user.setExternalId(externalId);
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef(null);

        user.setLinksEntity(linksEntity);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef(null);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLinksEntityDTO(linksEntityDto);
        userRequestDto.setPassword("123");
        userRequestDto.setUsername("Anacondaz");
        userRequestDto.setRole(List.of(Roles.USER));

        User result = mapper.fromDTO(userRequestDto);
        assertNotNull(result);
        assertEquals(userRequestDto.getPassword(), result.getPassword());
        assertEquals(userRequestDto.getRole(), result.getRole());
        assertEquals(userRequestDto.getUsername(), result.getUsername());

        assertEquals(linksEntityDto.getInstRef(), result.getLinksEntity().getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getLinksEntity().getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getLinksEntity().getVkRef());
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

        User result = mapper.fromDTO(userRequestDto);
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

        User result = mapper.fromDTO(userRequestDto);
        assertNotNull(result);
        assertEquals(userRequestDto.getPassword(), result.getPassword());
        assertEquals(userRequestDto.getRole(), result.getRole());
        assertEquals(userRequestDto.getUsername(), result.getUsername());

        assertEquals(linksEntityDto.getInstRef(), result.getLinksEntity().getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getLinksEntity().getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getLinksEntity().getVkRef());
    }

    @Test
    public void fromDtoLAllLinksNull() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef(null);
        linksEntity.setTgRef(null);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef(null);
        linksEntityDto.setTgRef(null);

        LinksEntity result = mapper.fromDTO(linksEntityDto);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }

    @Test
    public void fromDtoL() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef("qwerty");
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef("qwerty");

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef("qwerty");
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef("qwerty");

        LinksEntity result = mapper.fromDTO(linksEntityDto);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }

    @Test
    public void fromDtoLSomeLinksNull() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef(null);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef(null);

        LinksEntity result = mapper.fromDTO(linksEntityDto);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }

    @Test
    public void toDtoLSomeLinksNull() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef(null);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef(null);

        LinksEntityDto result = mapper.toDTO(linksEntity);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }

    @Test
    public void toDtoL() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef("qwerty");
        linksEntity.setInstRef("qwerty");
        linksEntity.setTgRef("qwerty");

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef("qwerty");
        linksEntityDto.setInstRef("qwerty");
        linksEntityDto.setTgRef("qwerty");

        LinksEntityDto result = mapper.toDTO(linksEntity);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }

    @Test
    public void toDtoLAllLinksNull() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setVkRef(null);
        linksEntity.setInstRef(null);
        linksEntity.setTgRef(null);

        LinksEntityDto linksEntityDto = new LinksEntityDto();
        linksEntityDto.setVkRef(null);
        linksEntityDto.setInstRef(null);
        linksEntityDto.setTgRef(null);

        LinksEntityDto result = mapper.toDTO(linksEntity);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }
}