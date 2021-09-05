package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.AccessLevelDTO;

@Builder
public class AccessLevelDTOFactory {
    
    @Builder.Default
    private long id = 1l;
    
    @Builder.Default
    private String description = "Manager";

    public AccessLevelDTO dto() {        
        return new AccessLevelDTO(id, description);
    }
}
