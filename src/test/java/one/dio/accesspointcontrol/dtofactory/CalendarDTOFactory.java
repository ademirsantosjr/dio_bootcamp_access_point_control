package one.dio.accesspointcontrol.dtofactory;

import java.time.LocalDateTime;

import lombok.Builder;
import one.dio.accesspointcontrol.dto.CalendarDTO;
import one.dio.accesspointcontrol.model.DateFormat;

@Builder
public class CalendarDTOFactory {
    
    @Builder.Default
    private long id = 1l;
    
    @Builder.Default
    private DateFormat dateFormat = new DateFormat(1l, "dd/MM/yyyy");
    
    @Builder.Default
    private String description = "dd/MM/yyyy Week zz";
    
    @Builder.Default
    private LocalDateTime specialDate = LocalDateTime.of(2020, 5, 3, 16, 22);

    public CalendarDTO dto() {        
        return new CalendarDTO(id,
                               dateFormat,
                               description,
                               specialDate);
    }
}
