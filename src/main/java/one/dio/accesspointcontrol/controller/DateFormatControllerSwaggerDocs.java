package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.DateFormatDTO;
import one.dio.accesspointcontrol.exception.DateFormatNotFoundException;

@Api("Manages Access Point Control")
public interface DateFormatControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Date Format (e.g. 'us-format', 'custom')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Date Format was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public DateFormatDTO create(DateFormatDTO dateFormatDTO);

    @ApiOperation(value = "Returns all Date Formats in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Date Formats in the database")
    })
    public List<DateFormatDTO> findAll();
    
    @ApiOperation(value = "Returns a Date Format according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A Date Format with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Date Format not found with given ID")
    })
    public DateFormatDTO findById(long id) throws DateFormatNotFoundException;

    @ApiOperation(value = "Updates a Date Format according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Date Format with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Date Format not found with given ID")
    })
    public DateFormatDTO update(DateFormatDTO dateFormatDTO) throws DateFormatNotFoundException;

    @ApiOperation(value = "Deletes a Date Format according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Date Format with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Date Format with given ID")
    })
    public void deleteById(long id) throws DateFormatNotFoundException;
}
