package dika.spring.security.service;

import dika.spring.security.mapper.LinksMapper;
import dika.spring.security.mapper.UserMapper;
import dika.spring.security.jwt.JwtTokenProvider;
import dika.spring.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public abstract class Mocks {
    @Mock
    public UserRepository userRepository;

    @Mock
    public UserMapper userMapper;

    @Mock
    public UserService userService;

    @Mock
    public JwtTokenProvider jwtTokenProvider;

    @Mock
    public HttpServletResponse response;

    @Mock
    public HttpServletRequest request;

    @Mock
    public LinksMapper linksMapper;

}
