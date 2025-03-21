package dika.spring.security.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorFormat {
    private String message;
    private String error;
}
