package dika.spring.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinksEntityDto {
    private String tgRef;

    private String instRef;

    private String vkRef;

}
