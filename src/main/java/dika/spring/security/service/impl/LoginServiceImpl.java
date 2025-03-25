package dika.spring.security.service.impl;

import dika.spring.security.dto.reqest.LoginDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.exception.IncorrectDataException;
import dika.spring.security.jwt.JwtTokenProvider;
import dika.spring.security.UserMapper;
import dika.spring.security.model.User;
import dika.spring.security.service.LoginService;
import dika.spring.security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private static final int COOKIEMAXAGE = 3600;

    @Transactional
    @Override
    public Cookie getCookie(LoginDto loginDTO) {
        return createCookie(login(loginDTO));
    }

    @Transactional
    @Override
    public Cookie getCookie(UserRequestDto user) {
        return createCookie(registration(user));
    }


    @Override
    public String getUsernameFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userService.findById(jwtTokenProvider.validateAndGetUserId(token));
                    return user.getUsername();
                }
            }
        }
        return null;
    }

    @Override
    public void cleanCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
    }

    private String login(LoginDto loginDTO) {
        User user = userService.findUserByUsername(loginDTO.getUsername());
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new IncorrectDataException("Incorrect password");
        }
        return jwtTokenProvider.generateToken(user.getId(), user.getRole());
    }

    private String registration(UserRequestDto user) {
        userService.add(user);
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(user.getUsername());
        loginDto.setPassword(user.getPassword());
        return jwtTokenProvider.generateToken(userService.findUserByUsername
                (user.getUsername()).getId(), user.getRole());
    }

    private Cookie createCookie(String token) {
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(COOKIEMAXAGE);
        return jwtCookie;
    }
}
