package one.dio.accesspointcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends Exception{
    
    public DepartmentNotFoundException(long id) {
        super(String.format("There is no Department with ID=%s", id));
    }
}
