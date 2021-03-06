package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventNotFoundException extends Exception{
    
    public EventNotFoundException(long id) {
        super(String.format("There is no Event with given ID=%s", id));
    }
}
