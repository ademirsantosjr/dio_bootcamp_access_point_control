package one.dio.accesspointcontrol.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.CalendarDTO;
import one.dio.accesspointcontrol.exception.CalendarNotFoundException;
import one.dio.accesspointcontrol.service.CalendarService;

@RestController
@RequestMapping("/api/v1/calendars")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CalendarController implements CalendarControllerSwaggerDocs {

    CalendarService calendarService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalendarDTO create(@RequestBody @Valid CalendarDTO calendarDTO) {
        return calendarService.create(calendarDTO);
    }

    @GetMapping
    public List<CalendarDTO> findAll() {
        return calendarService.findAll();
    }

    @GetMapping("/{id}")
    public CalendarDTO findById(@PathVariable long id) throws CalendarNotFoundException {
        return calendarService.findById(id);
    }
    
    @PutMapping
    public CalendarDTO update(@RequestBody CalendarDTO calendarDTO) throws CalendarNotFoundException {
        return calendarService.update(calendarDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws CalendarNotFoundException {
        calendarService.deleteById(id);
    }
}
