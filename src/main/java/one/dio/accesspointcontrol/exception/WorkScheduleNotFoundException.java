package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkScheduleNotFoundException extends Exception{
    
    public WorkScheduleNotFoundException(long id) {
        super(String.format("No Work Schedule with given ID=%s", id));
    }
}
