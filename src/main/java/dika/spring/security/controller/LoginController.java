package dika.spring.security.controller;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.dto.reqest.LoginDto;
import dika.spring.security.dto.reqest.UserRequestDto;
import dika.spring.security.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping("/access")
    public String loginPost(@ModelAttribute LoginDto loginDTO, HttpServletResponse response) {
        response.addCookie(loginService.createCookie(loginService.login(loginDTO)));
        return "redirect:/user";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserRequestDto user,
                               @ModelAttribute LinksEntityDto linksEntityDTO,
                               HttpServletResponse response) {
        user.setLinksEntityDTO(linksEntityDTO);
        response.addCookie(loginService.createCookie(loginService.registration(user)));
        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        loginService.cleanCookie(response);
        return "redirect:/login";
    }

    @GetMapping("/**")
    public String errorPage() {
        return "errorPage";
    }
}
