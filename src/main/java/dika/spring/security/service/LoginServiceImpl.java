package dika.spring.security.service;

import dika.spring.security.jwt.JwtTokenProvider;
import dika.spring.security.dto.LoginDTO;
import dika.spring.security.dto.reqest.UserRequestDTO;
import dika.spring.security.exception.IncorrectDataExeption;
import dika.spring.security.mapper.UserMapper;
import dika.spring.security.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Override
    public String login(LoginDTO loginDTO) {
        log.info("Login request: {}", loginDTO);
            User user = userService.findUserByUsername(loginDTO.getUsername());
            if (!user.getPassword().equals(loginDTO.getPassword())) {
                throw new IncorrectDataExeption("Incorrect password");
            }
            return jwtTokenProvider.generateToken(user.getId(), user.getRole());
    }

    @Override
    public String registration(UserRequestDTO user) {
        userService.add(user);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(user.getUsername());
        loginDTO.setPassword(user.getPassword());
        return login(loginDTO);
    }

    @Override
    public Cookie createCookie(String token) {
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60);
        return jwtCookie;
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
}
