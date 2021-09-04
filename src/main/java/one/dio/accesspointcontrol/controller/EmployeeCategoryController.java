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
import one.dio.accesspointcontrol.dto.EmployeeCategoryDTO;
import one.dio.accesspointcontrol.exception.EmployeeCategoryNotFoundException;
import one.dio.accesspointcontrol.service.EmployeeCategoryService;

@RestController
@RequestMapping("/api/v1/employeecategories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeCategoryController implements EmployeeCategoryControllerSwaggerDocs {

    EmployeeCategoryService employeeCategoryService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeCategoryDTO create(@RequestBody @Valid EmployeeCategoryDTO employeeCategoryDTO) {
        return employeeCategoryService.create(employeeCategoryDTO);
    }

    @GetMapping
    public List<EmployeeCategoryDTO> findAll() {
        return employeeCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeCategoryDTO findById(@PathVariable long id) throws EmployeeCategoryNotFoundException {
        return employeeCategoryService.findById(id);
    }
    
    @PutMapping
    public EmployeeCategoryDTO update(@RequestBody EmployeeCategoryDTO employeeCategoryDTO) throws EmployeeCategoryNotFoundException {
        return employeeCategoryService.update(employeeCategoryDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws EmployeeCategoryNotFoundException {
        employeeCategoryService.deleteById(id);
    }
}
