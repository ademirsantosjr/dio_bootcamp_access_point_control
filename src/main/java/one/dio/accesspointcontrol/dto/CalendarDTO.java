package one.dio.accesspointcontrol.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.dio.accesspointcontrol.model.DateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarDTO {
    
    private long id;
    
    @NotNull
    private DateFormat dateFormat;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime specialDate;
}
