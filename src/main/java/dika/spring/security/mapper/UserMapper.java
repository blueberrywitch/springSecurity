package dika.spring.security.mapper;

import dika.spring.security.dto.LinksEntityDTO;
import dika.spring.security.dto.reqest.UserRequestDTO;
import dika.spring.security.dto.response.UserResponseDTO;
import dika.spring.security.model.LinksEntity;
import dika.spring.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", imports = {java.util.Collections.class, dika.spring.security.enums.Roles.class})
public interface UserMapper {

    @Mapping(source = "linksEntity", target = "linksEntityDTO")
    UserResponseDTO toDTO(User user);

    @Mapping(source = "linksEntityDTO", target = "linksEntity")
    @Mapping(target = "role", expression = "java(dto.getRole() == null ? Collections.singletonList(Roles.USER) : dto.getRole())")
    User fromDTO(UserRequestDTO dto);

    LinksEntity fromDTO(LinksEntityDTO linksEntity);

    LinksEntityDTO toDTO(LinksEntity linksEntity);
}
