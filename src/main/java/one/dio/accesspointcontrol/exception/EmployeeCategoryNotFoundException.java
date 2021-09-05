package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeCategoryNotFoundException extends Exception{
    
    public EmployeeCategoryNotFoundException(long id) {
        super(String.format("There is no Employee Category with given ID=%s", id));
    }
}
