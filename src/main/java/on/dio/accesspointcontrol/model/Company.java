package on.dio.accesspointcontrol.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Company {
    
    @Id
    private long id;
    
    private String description;
    private String cnpj;
    private String address;
    private String quarter;
    private String city;
    private String state;
    private String phone;
}
