package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.CalendarDTO;
import one.dio.accesspointcontrol.exception.CalendarNotFoundException;

@Api("Manages Access Point Control")
public interface CalendarControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Calendar (e.g. 'manager', 'supervidor')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Calendar was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public CalendarDTO create(CalendarDTO calendarDTO);

    @ApiOperation(value = "Returns all Calendars in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Calendars in the database")
    })
    public List<CalendarDTO> findAll();
    
    @ApiOperation(value = "Returns a Calendar according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A Calendar with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Calendar not found with given ID")
    })
    public CalendarDTO findById(long id) throws CalendarNotFoundException;

    @ApiOperation(value = "Updates a Calendar according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Calendar with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Calendar not found with given ID")
    })
    public CalendarDTO update(CalendarDTO calendarDTO) throws CalendarNotFoundException;

    @ApiOperation(value = "Deletes a Calendar according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Calendar with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Calendar with given ID")
    })
    public void deleteById(long id) throws CalendarNotFoundException;
}
