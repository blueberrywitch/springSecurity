package dika.spring.security.mapper;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.model.LinksEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LinksMapper {

    @Mapping(target = "id", ignore = true)
    LinksEntity fromDTO(LinksEntityDto linksEntity);

    LinksEntityDto toDTO(LinksEntity linksEntity);
}
