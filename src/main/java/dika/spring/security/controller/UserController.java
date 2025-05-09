package dika.spring.security.controller;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.dto.response.UserResponseDto;
import dika.spring.security.service.LoginService;
import dika.spring.security.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PermitAll
    @GetMapping
    public ModelAndView getUser(ModelMap model, HttpServletRequest request) {
        model.addAttribute("isAdmin", userService.isAdmin());
        model.addAttribute("username", loginService.getUsernameFromCookie(request));
        model.addAttribute("users", userService.findAll());
        return new ModelAndView("user", model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(HttpServletRequest request, HttpServletResponse response) {
        userService.deleteByUsername(
                (loginService.getUsernameFromCookie(request)));
        loginService.cleanCookie(response);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{externalId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID externalId,
                                                      @Valid @ModelAttribute UserResponseDto user,
                                                      @ModelAttribute LinksEntityDto linksEntityDTO) {
        userService.update(externalId, user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/**")
    public String errorPage() {
        return "errorPage";
    }
}
