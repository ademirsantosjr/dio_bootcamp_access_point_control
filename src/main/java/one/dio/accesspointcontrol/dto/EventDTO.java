package one.dio.accesspointcontrol.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    
    private long id;
    
    @NotBlank
    private String name;
    
    private String description;
}
