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
import one.dio.accesspointcontrol.dto.EventDTO;
import one.dio.accesspointcontrol.exception.EventNotFoundException;
import one.dio.accesspointcontrol.service.EventService;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController implements EventSwaggerDocs {

    EventService eventService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO create(@RequestBody @Valid EventDTO eventDTO) {
        return eventService.create(eventDTO);
    }

    @GetMapping
    public List<EventDTO> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventDTO findById(@PathVariable long id) throws EventNotFoundException {
        return eventService.findById(id);
    }
    
    @PutMapping
    public EventDTO update(@RequestBody EventDTO eventDTO) throws EventNotFoundException {
        return eventService.update(eventDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws EventNotFoundException {
        eventService.deleteById(id);
    }
}
