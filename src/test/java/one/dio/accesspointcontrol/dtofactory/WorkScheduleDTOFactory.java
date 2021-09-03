package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.WorkScheduleDTO;

@Builder
public class WorkScheduleDTOFactory {
    
    @Builder.Default
    private long id = 1l;

    @Builder.Default
    private String description = "Full-Time";

    public WorkScheduleDTO dto() {
        return new WorkScheduleDTO(id, description);
    }
}
