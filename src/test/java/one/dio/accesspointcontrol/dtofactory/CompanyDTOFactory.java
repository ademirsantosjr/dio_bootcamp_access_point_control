package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.CompanyDTO;

@Builder
public class CompanyDTOFactory {
    
    @Builder.Default
    private long id = 1l;
    
    @Builder.Default
    private String name = "Company xpto";

    @Builder.Default
    private String cnpj = "23.552.513/0001-00";

    @Builder.Default
    private String cep = "35003-125";

    @Builder.Default
    private String address = "Street 4";    

    @Builder.Default
    private String phone = "+55 11 7777-5555";

    public CompanyDTO dto() {
        
        return new CompanyDTO(id,
                              name,
                              cnpj,
                              cep,
                              address,
                              phone);
    }
}
