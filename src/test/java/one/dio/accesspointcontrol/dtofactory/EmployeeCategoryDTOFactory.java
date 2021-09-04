package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.EmployeeCategoryDTO;

@Builder
public class EmployeeCategoryDTOFactory {
    
    @Builder.Default
    private long id = 1l;

    @Builder.Default
    private String description = "regular";

    public EmployeeCategoryDTO dto() {
        return new EmployeeCategoryDTO(id, description);
    }
}
