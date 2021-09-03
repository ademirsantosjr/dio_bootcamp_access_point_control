package one.dio.accesspointcontrol.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /*@GetMapping("/{id}")
    public ResponseEntity<WorkSchedule> findById(@PathVariable("id") long id) throws NoSuchElementException {
        return ResponseEntity.ok(workScheduleService.findById(id).orElseThrow(() -> new NoSuchElementException("Work Schedule not found!")));
    }
    
    @PutMapping
    public WorkSchedule update(@RequestBody WorkSchedule workSchedule) {
        return workScheduleService.save(workSchedule);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") long id) {
        workScheduleService.deleteById(id);
    }*/
}
