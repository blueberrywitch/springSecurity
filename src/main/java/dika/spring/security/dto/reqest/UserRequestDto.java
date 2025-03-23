package dika.spring.security.dto.reqest;

import dika.spring.security.dto.LinksEntityDto;
import dika.spring.security.enums.Roles;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDto {

    @NotNull(message = "Username не может быть null")
    @Size(min = 3)
    private String username;

    @NotNull(message = "Password не может быть null")
    @Size(min = 3)
    private String password;

    private List<Roles> role;

    private LinksEntityDto linksEntityDTO;
}
