package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.AccessLevelDTO;
import one.dio.accesspointcontrol.exception.AccessLevelNotFoundException;

@Api("Manages Access Point Control")
public interface AccessLevelControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Access Level (e.g. 'manager', 'supervidor')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Access Level was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public AccessLevelDTO create(AccessLevelDTO companyDTO);

    @ApiOperation(value = "Returns all Access Levels in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Access Levels in the database")
    })
    public List<AccessLevelDTO> findAll();
    
    @ApiOperation(value = "Returns an Access Level according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "An Access Level with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Access Level not found with given ID")
    })
    public AccessLevelDTO findById(long id) throws AccessLevelNotFoundException;

    @ApiOperation(value = "Updates an Access Level according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Access Level with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Access Level not found with given ID")
    })
    public AccessLevelDTO update(AccessLevelDTO companyDTO) throws AccessLevelNotFoundException;

    @ApiOperation(value = "Deletes an Access Level according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Access Level with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Access Level with given ID")
    })
    public void deleteById(long id) throws AccessLevelNotFoundException;
}
