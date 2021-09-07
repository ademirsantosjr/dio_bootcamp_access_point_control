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
import one.dio.accesspointcontrol.dto.DateFormatDTO;
import one.dio.accesspointcontrol.exception.DateFormatNotFoundException;
import one.dio.accesspointcontrol.service.DateFormatService;

@RestController
@RequestMapping("/api/v1/dateformats")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DateFormatController implements DateFormatControllerSwaggerDocs {

    DateFormatService dateFormatService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DateFormatDTO create(@RequestBody @Valid DateFormatDTO dateFormatLevelDTO) {
        return dateFormatService.create(dateFormatLevelDTO);
    }

    @GetMapping
    public List<DateFormatDTO> findAll() {
        return dateFormatService.findAll();
    }

    @GetMapping("/{id}")
    public DateFormatDTO findById(@PathVariable long id) throws DateFormatNotFoundException {
        return dateFormatService.findById(id);
    }
    
    @PutMapping
    public DateFormatDTO update(@RequestBody DateFormatDTO dateFormatLevelDTO) throws DateFormatNotFoundException {
        return dateFormatService.update(dateFormatLevelDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws DateFormatNotFoundException {
        dateFormatService.deleteById(id);
    }
}
