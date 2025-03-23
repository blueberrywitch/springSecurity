package dika.spring.security.mapper;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", imports = {java.util.Collections.class, dika.spring.security.enums.Roles.class,
        java.util.UUID.class})
public interface UserMapper {

    @Mapping(source = "linksEntity", target = "linksEntityDTO")
    UserResponseDto toDTO(User user);

    @Mapping(source = "linksEntityDTO", target = "linksEntity")
    @Mapping(target = "externalId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(dto.getRole() == null ? Collections.singletonList(Roles.USER) : dto.getRole())")
    User fromDTO(UserRequestDto dto);

    @Mapping(target = "id", ignore = true)
    LinksEntity fromDTO(LinksEntityDto linksEntity);

    LinksEntityDto toDTO(LinksEntity linksEntity);
}
