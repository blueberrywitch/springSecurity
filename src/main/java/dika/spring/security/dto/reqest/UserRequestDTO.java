package dika.spring.security.dto.reqest;

import dika.spring.security.dto.LinksEntityDTO;
import dika.spring.security.enums.Roles;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDTO {

    private String username;

    @Size(min = 3)
    private String password;

    private List<Roles> role;

    private LinksEntityDTO linksEntityDTO;
}
