package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.DateFormatDTO;

@Builder
public class DateFormatDTOFactory {
    
    @Builder.Default
    private long id = 1l;
    
    @Builder.Default
    private String description = "regular format";

    public DateFormatDTO dto() {        
        return new DateFormatDTO(id, description);
    }
}
