package dika.spring.security.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("errorPage"); // имя шаблона error.html
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("errorTitle", "Ошибка сервера");
        mav.addObject("errorMessage", e.getMessage());
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleUsernameExists(DataIntegrityViolationException ex) {
        ErrorFormat errorFormat = new ErrorFormat("пользователь с таким никнеймом уже существует", ex.getMessage());
        return new ResponseEntity<>(errorFormat.getMessage(), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlePasswordSizeIsWrong(ConstraintViolationException ex) {
        ErrorFormat errorFormat = new ErrorFormat("неверный размер пароля", ex.getMessage());
        return new ResponseEntity<>(errorFormat.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handlePasswordSizeIsWrong(UserNotFoundException ex) {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("errorTitle", "Пользователь с таким никнеймом уже существует");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectDataExeption.class)
    public ModelAndView handleIncorrectDataException(IncorrectDataExeption ex) {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("errorTitle", "Неверные данные");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }
}
