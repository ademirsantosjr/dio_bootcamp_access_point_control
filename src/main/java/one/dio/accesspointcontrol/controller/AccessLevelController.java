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
import one.dio.accesspointcontrol.dto.AccessLevelDTO;
import one.dio.accesspointcontrol.exception.AccessLevelNotFoundException;
import one.dio.accesspointcontrol.service.AccessLevelService;

@RestController
@RequestMapping("/api/v1/accesslevels")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccessLevelController implements AccessLevelControllerSwaggerDocs {

    AccessLevelService accessLevelService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccessLevelDTO create(@RequestBody @Valid AccessLevelDTO accessLevelDTO) {
        return accessLevelService.create(accessLevelDTO);
    }

    @GetMapping
    public List<AccessLevelDTO> findAll() {
        return accessLevelService.findAll();
    }

    @GetMapping("/{id}")
    public AccessLevelDTO findById(@PathVariable long id) throws AccessLevelNotFoundException {
        return accessLevelService.findById(id);
    }
    
    @PutMapping
    public AccessLevelDTO update(@RequestBody AccessLevelDTO accessLevelDTO) throws AccessLevelNotFoundException {
        return accessLevelService.update(accessLevelDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws AccessLevelNotFoundException {
        accessLevelService.deleteById(id);
    }
}
