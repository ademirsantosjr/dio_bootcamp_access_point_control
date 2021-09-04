package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.EmployeeCategoryDTO;
import one.dio.accesspointcontrol.exception.EmployeeCategoryNotFoundException;

@Api("Manages Access Point Control")
public interface EmployeeCategoryControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Employee Category (e.g. 'regular', 'temporary')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Employee Category was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public EmployeeCategoryDTO create(EmployeeCategoryDTO employeeCategoryDTO);

    @ApiOperation(value = "Returns all Employee Categories in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Employee Categories in the database")
    })
    public List<EmployeeCategoryDTO> findAll();
    
    @ApiOperation(value = "Returns an Employee Category according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "An Employee Category with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Employee Category not found with given ID")
    })
    public EmployeeCategoryDTO findById(long id) throws EmployeeCategoryNotFoundException;

    @ApiOperation(value = "Updates Employee Category according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Employee Category with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Employee Category not found with given ID")
    })
    public EmployeeCategoryDTO update(EmployeeCategoryDTO employeeCategoryDTO) throws EmployeeCategoryNotFoundException;

    @ApiOperation(value = "Deletes Employee Category according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Employee Category with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Employee Category with given ID")
    })
    public void deleteById(long id) throws EmployeeCategoryNotFoundException;
}
