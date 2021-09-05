package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends Exception{
    
    public CompanyNotFoundException(long id) {
        super(String.format("There is no Company with given ID=%s", id));
    }
}
