package one.dio.accesspointcontrol.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO {
    
    private long id;
    
    @NotBlank
    private String name;

    @NotBlank
    @CNPJ(message = "Informed value is not a valid cnpj")
    private String cnpj;

    @NotBlank
    private String cep;

    @NotBlank
    private String address;    

    @NotBlank
    private String phone;
}
