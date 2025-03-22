package dika.spring.security.service;

import dika.spring.security.dto.reqest.LoginDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {
    String login(LoginDto loginDTO);

    String registration(UserRequestDto user);

    Cookie createCookie(String token);

    String getUsernameFromCookie(HttpServletRequest request);

    void cleanCookie(HttpServletResponse response);
}
