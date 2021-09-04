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
import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.exception.WorkScheduleNotFoundException;
import one.dio.accesspointcontrol.service.WorkScheduleService;

@RestController
@RequestMapping("/api/v1/workschedules")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WorkScheduleController implements WorkScheduleControllerSwaggerDocs {

    WorkScheduleService workScheduleService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkScheduleDTO create(@RequestBody @Valid WorkScheduleDTO workScheduleDTO) {
        return workScheduleService.create(workScheduleDTO);
    }

    @GetMapping
    public List<WorkScheduleDTO> findAll() {
        return workScheduleService.findAll();
    }

    @GetMapping("/{id}")
    public WorkScheduleDTO findById(@PathVariable long id) throws WorkScheduleNotFoundException {
        return workScheduleService.findById(id);
    }
    
    @PutMapping
    public WorkScheduleDTO update(@RequestBody WorkScheduleDTO workScheduleDTO) throws WorkScheduleNotFoundException {
        return workScheduleService.update(workScheduleDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws WorkScheduleNotFoundException {
        workScheduleService.deleteById(id);
    }
}
