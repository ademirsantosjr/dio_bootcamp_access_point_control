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
import one.dio.accesspointcontrol.dto.CompanyDTO;
import one.dio.accesspointcontrol.exception.CompanyNotFoundException;
import one.dio.accesspointcontrol.service.CompanyService;

@RestController
@RequestMapping("/api/v1/companies")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController implements CompanyControllerSwaggerDocs {

    CompanyService companyService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO create(@RequestBody @Valid CompanyDTO companyDTO) {
        return companyService.create(companyDTO);
    }

    @GetMapping
    public List<CompanyDTO> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable long id) throws CompanyNotFoundException {
        return companyService.findById(id);
    }
    
    @PutMapping
    public CompanyDTO update(@RequestBody CompanyDTO companyDTO) throws CompanyNotFoundException {
        return companyService.update(companyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) throws CompanyNotFoundException {
        companyService.deleteById(id);
    }
}
