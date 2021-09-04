package one.dio.accesspointcontrol.dtofactory;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.EventDTO;

@Builder
public class EventDTOFactory {
    
    @Builder.Default
    private long id = 1l;

    @Builder.Default
    private String name = "regular journay";

    @Builder.Default
    private String description = "from monday to friday only";

    public EventDTO dto() {
        return new EventDTO(id, name, description);
    }
}
