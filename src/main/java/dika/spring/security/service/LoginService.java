package dika.spring.security.service;

import dika.spring.security.dto.LoginDTO;
import dika.spring.security.dto.reqest.UserRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {
    String login(LoginDTO loginDTO);

    String registration(UserRequestDTO user);

    Cookie createCookie(String token);

    String getUsernameFromCookie(HttpServletRequest request);

    void cleanCookie(HttpServletResponse response);
}
