package Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dika.spring.security.dto.reqest.LoginDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.exception.UserNotFoundException;
import dika.spring.security.jwt.JwtTokenProvider;
import dika.spring.security.mapper.UserMapper;
import dika.spring.security.model.User;
import dika.spring.security.service.UserService;
import dika.spring.security.service.impl.LoginServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserMapper userMapper;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private LoginServiceImpl loginServiceImpl;

    @Test
    public void testGetCookie() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("Anacondaz");
        loginDto.setPassword("123");

        User user = new User();
        user.setId(1L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        when(userService.findUserByUsername("Anacondaz")).thenReturn(user);
        when(jwtTokenProvider.generateToken(1L, user.getRole())).thenReturn("token123");

        Cookie jwtCookie = loginServiceImpl.getCookie(loginDto);

        assertNotNull(jwtCookie, "Cookie should not be null");
        assertEquals("token123", jwtCookie.getValue(), "Token value should be 'token123'");
        assertTrue(jwtCookie.isHttpOnly(), "Cookie should be HTTP only");
        assertEquals("/", jwtCookie.getPath(), "Cookie path should be '/'");
        assertEquals(3600, jwtCookie.getMaxAge(), "Cookie maxAge should be 3600");
    }

    @Test
    public void testGetCookieNotFound() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("Anacondaz");
        loginDto.setPassword("123");

        when(userService.findUserByUsername("Anacondaz"))
                .thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> loginServiceImpl.getCookie(loginDto));
    }

    @Test
    public void testGetCookieWithUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("Anacondaz");
        userRequestDto.setPassword("123");
        userRequestDto.setRole(Collections.singletonList(Roles.USER));

        User user = new User();
        user.setId(2L);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(Collections.singletonList(Roles.USER));

        lenient().when(userService.add(userRequestDto)).thenReturn(null);
        lenient().when(userService.findUserByUsername("Anacondaz")).thenReturn(user);
        when(jwtTokenProvider.generateToken(2L, user.getRole())).thenReturn("token123");

        Cookie jwtCookie = loginServiceImpl.getCookie(userRequestDto);

        verify(userService).add(userRequestDto);

        assertNotNull(jwtCookie, "Cookie should not be null");
        assertEquals("token123", jwtCookie.getValue(), "Token value should be 'token123'");
        assertTrue(jwtCookie.isHttpOnly(), "Cookie should be HTTP only");
        assertEquals("/", jwtCookie.getPath(), "Cookie path should be '/'");
        assertEquals(3600, jwtCookie.getMaxAge(), "Cookie maxAge should be 3600");
    }

    @Test
    public void testGetCookieNotFoundUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("Anacondaz");
        userRequestDto.setPassword("123");

        when(userService.findUserByUsername("Anacondaz"))
                .thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> loginServiceImpl.getCookie(userRequestDto));
    }

    @Test
    public void testCleanCookie() {
        loginServiceImpl.cleanCookie(response);

        verify(response).addCookie(argThat(cookie ->
                "jwt".equals(cookie.getName()) &&
                        cookie.getValue() == null &&
                        cookie.isHttpOnly() &&
                        "/".equals(cookie.getPath()) &&
                        cookie.getMaxAge() == 0
        ));
    }

    @Test
    public void testGetUsernameFromCookie_Found() {
        Cookie jwtCookie = new Cookie("jwt", "token123");
        when(request.getCookies()).thenReturn(new Cookie[]{ jwtCookie });
        when(jwtTokenProvider.validateAndGetUserId("token123")).thenReturn(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("Anacondaz");
        when(userService.findById(1L)).thenReturn(user);

        String username = loginServiceImpl.getUsernameFromCookie(request);

        assertEquals("Anacondaz", username, "Username should be 'Anacondaz' when jwt cookie is present");
    }

    @Test
    public void testGetUsernameFromCookie_NoCookies() {
        when(request.getCookies()).thenReturn(null);

        String username = loginServiceImpl.getUsernameFromCookie(request);

        assertNull(username, "Username should be null when no cookies are present");
    }

    @Test
    public void testGetUsernameFromCookie_NoJwtCookie() {
        Cookie otherCookie = new Cookie("someOtherCookie", "someValue");
        when(request.getCookies()).thenReturn(new Cookie[]{ otherCookie });

        String username = loginServiceImpl.getUsernameFromCookie(request);

        assertNull(username, "Username should be null when no 'jwt' cookie is present");
    }
}
