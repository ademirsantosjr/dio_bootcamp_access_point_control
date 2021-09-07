package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DateFormatNotFoundException extends Exception{
    
    public DateFormatNotFoundException(long id) {
        super(String.format("There is no Date Format with given ID=%s", id));
    }
}
