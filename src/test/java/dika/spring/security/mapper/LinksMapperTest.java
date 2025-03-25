package dika.spring.security.mapper;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.model.LinksEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinksMapperTest extends AbstractMapperTest {

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

        LinksEntity result = linksMapper.fromDTO(linksEntityDto);

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

        LinksEntityDto result = linksMapper.toDTO(linksEntity);

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

        LinksEntityDto result = linksMapper.toDTO(linksEntity);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
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

        LinksEntity result = linksMapper.fromDTO(linksEntityDto);

        assertEquals(linksEntityDto.getInstRef(), result.getInstRef());
        assertEquals(linksEntityDto.getTgRef(), result.getTgRef());
        assertEquals(linksEntityDto.getVkRef(), result.getVkRef());
    }
}
