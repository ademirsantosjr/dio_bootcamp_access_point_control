package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.CompanyDTO;
import one.dio.accesspointcontrol.exception.CompanyNotFoundException;

@Api("Manages Access Point Control")
public interface CompanyControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Company (e.g. 'regular-day', 'compensation')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Company was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public CompanyDTO create(CompanyDTO companyDTO);

    @ApiOperation(value = "Returns all Companies in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Companies in the database")
    })
    public List<CompanyDTO> findAll();
    
    @ApiOperation(value = "Returns a Company according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A Company with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Company not found with given ID")
    })
    public CompanyDTO findById(long id) throws CompanyNotFoundException;

    @ApiOperation(value = "Updates Company according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Company with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Company not found with given ID")
    })
    public CompanyDTO update(CompanyDTO companyDTO) throws CompanyNotFoundException;

    @ApiOperation(value = "Deletes a Company according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Company with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Company with given ID")
    })
    public void deleteById(long id) throws CompanyNotFoundException;
}
