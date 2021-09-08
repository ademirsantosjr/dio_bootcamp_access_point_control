package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.DepartmentDTO;
import one.dio.accesspointcontrol.exception.DepartmentNotFoundException;

@Api("Manages Access Point Control")
public interface DepartmentControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Department (e.g. 'supervision', 'factory')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Department was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public DepartmentDTO create(DepartmentDTO departmentDTO);

    @ApiOperation(value = "Returns all Departments in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Departments in the database")
    })
    public List<DepartmentDTO> findAll();
    
    @ApiOperation(value = "Returns a Department according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A Department with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Department not found with given ID")
    })
    public DepartmentDTO findById(long id) throws DepartmentNotFoundException;

    @ApiOperation(value = "Updates a Department according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Department with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Department not found with given ID")
    })
    public DepartmentDTO update(DepartmentDTO departmentDTO) throws DepartmentNotFoundException;

    @ApiOperation(value = "Deletes a Department according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Department with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Department with given ID")
    })
    public void deleteById(long id) throws DepartmentNotFoundException;
}
