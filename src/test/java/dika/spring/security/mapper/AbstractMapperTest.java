package dika.spring.security.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;

public abstract class AbstractMapperTest {

    protected UserMapper userMapper;
    protected LinksMapper linksMapper;

    @BeforeEach
    public void initMappers() {
        userMapper = Mappers.getMapper(UserMapper.class);
        linksMapper = Mappers.getMapper(LinksMapper.class);
    }
}