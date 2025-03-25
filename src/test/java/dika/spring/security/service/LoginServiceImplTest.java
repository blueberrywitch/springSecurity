package dika.spring.security.service;

import dika.spring.security.dto.reqest.LoginDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.enums.Roles;
import dika.spring.security.model.User;
import dika.spring.security.service.impl.LoginServiceImpl;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServiceImplTest extends Mocks {

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

        assertNotNull(jwtCookie);
        assertEquals("token123", jwtCookie.getValue());
        assertTrue(jwtCookie.isHttpOnly());
        assertEquals("/", jwtCookie.getPath());
        assertEquals(3600, jwtCookie.getMaxAge());
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

        assertNotNull(jwtCookie);
        assertEquals("token123", jwtCookie.getValue());
        assertTrue(jwtCookie.isHttpOnly());
        assertEquals("/", jwtCookie.getPath());
        assertEquals(3600, jwtCookie.getMaxAge());
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
    public void testGetUsernameFromCookieFound() {
        Cookie jwtCookie = new Cookie("jwt", "token123");
        when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
        when(jwtTokenProvider.validateAndGetUserId("token123")).thenReturn(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("Anacondaz");
        when(userService.findById(1L)).thenReturn(user);

        String username = loginServiceImpl.getUsernameFromCookie(request);

        assertEquals("Anacondaz", username);
    }

    @Test
    public void testGetUsernameFromCookieNoCookies() {
        when(request.getCookies()).thenReturn(null);

        String username = loginServiceImpl.getUsernameFromCookie(request);

        assertNull(username);
    }

    @Test
    public void testGetUsernameFromCookieNoJwtCookie() {
        Cookie otherCookie = new Cookie("someOtherCookie", "someValue");
        when(request.getCookies()).thenReturn(new Cookie[]{otherCookie});

        String username = loginServiceImpl.getUsernameFromCookie(request);

        assertNull(username);
    }
}
