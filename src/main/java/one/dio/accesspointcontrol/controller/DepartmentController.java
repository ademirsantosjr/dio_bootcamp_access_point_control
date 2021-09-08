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
import one.dio.accesspointcontrol.dto.DepartmentDTO;
import one.dio.accesspointcontrol.exception.DepartmentNotFoundException;
import one.dio.accesspointcontrol.service.DepartmentService;

@RestController
@RequestMapping("/api/v1/departments")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentController implements DepartmentControllerSwaggerDocs {

    DepartmentService departmentService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTO create(@RequestBody @Valid DepartmentDTO departmentDTO) {
        return departmentService.create(departmentDTO);
    }

    @GetMapping
    public List<DepartmentDTO> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public DepartmentDTO findById(@PathVariable long id) throws DepartmentNotFoundException {
        return departmentService.findById(id);
    }
    
    @PutMapping
    public DepartmentDTO update(@RequestBody DepartmentDTO departmentDTO) throws DepartmentNotFoundException {
        return departmentService.update(departmentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws DepartmentNotFoundException {
        departmentService.deleteById(id);
    }
}
