package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CalendarNotFoundException extends Exception{
    
    public CalendarNotFoundException(long id) {
        super(String.format("There is no Calendar with given ID=%s", id));
    }
}
