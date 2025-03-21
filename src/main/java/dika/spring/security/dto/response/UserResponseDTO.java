package dika.spring.security.dto.response;

import dika.spring.security.dto.LinksEntityDTO;
import dika.spring.security.enums.Roles;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDTO {

    private UUID externalId;

    private String username;

    @Size(min = 3)
    private String password;

    private List<Roles> role;

    private LinksEntityDTO linksEntityDTO;
}
