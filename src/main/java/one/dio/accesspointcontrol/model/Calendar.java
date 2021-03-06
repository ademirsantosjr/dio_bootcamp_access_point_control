package one.dio.accesspointcontrol.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Calendar {
    
    @Id
    private long id;

    @ManyToOne
    @NotNull
    private DateFormat dateFormat;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime specialDate;
}
