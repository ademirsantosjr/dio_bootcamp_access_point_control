package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.DepartmentDTO;
import one.dio.accesspointcontrol.model.AccessLevel;

@Builder
public class DepartmentDTOFactory {
    
    @Builder.Default
    private long id = 1l;
    
    @Builder.Default
    private AccessLevel accessLevel = new AccessLevel(1l, "intermediate");
    
    @Builder.Default
    private String description = "Supervision";

    public DepartmentDTO dto() {        
        return new DepartmentDTO(id, accessLevel, description);
    }
}
