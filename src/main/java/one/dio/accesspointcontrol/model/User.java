package one.dio.accesspointcontrol.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class User {
    
    @Id
    private long id;
    
    @ManyToOne
    private EmployeeCategory employeeCategory;
    
    private String name;
    
    @ManyToOne
    private Company company;
    
    @ManyToOne
    private AccessLevel accessLevel;
    
    @ManyToOne
    private WorkSchedule workDay;
    
    private BigDecimal tolerance;
    
    private LocalDateTime startWorkDay;
    
    private LocalDateTime endWorkDay;
}
