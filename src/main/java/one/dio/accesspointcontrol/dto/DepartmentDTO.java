package one.dio.accesspointcontrol.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.dio.accesspointcontrol.model.AccessLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    
    private long id;

    @NotNull
    private AccessLevel accessLevel;

    @NotBlank
    private String description;
}
