package dika.spring.security.service;

import dika.spring.security.dto.reqest.LoginDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

    String getUsernameFromCookie(HttpServletRequest request);

    void cleanCookie(HttpServletResponse response);

    Cookie getCookie(UserRequestDto user);

    Cookie getCookie(LoginDto loginDTO);
}
