package dika.spring.security.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dika.spring.security.dto.LinksEntityDto;
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

public class UserResponseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID externalId;

    private String username;

    @Size(min = 3)
    private String password;

    private List<Roles> role;

    private LinksEntityDto linksEntityDTO;
}
