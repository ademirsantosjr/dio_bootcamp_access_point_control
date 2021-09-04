package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.EventDTO;
import one.dio.accesspointcontrol.exception.EventNotFoundException;

@Api("Manages Access Point Control")
public interface EventSwaggerDocs {
    
    @ApiOperation(value = "Creates Event (e.g. 'regular-day', 'compensation')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Event was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public EventDTO create(EventDTO eventDTO);

    @ApiOperation(value = "Returns all Events in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Events in the database")
    })
    public List<EventDTO> findAll();
    
    @ApiOperation(value = "Returns an Event according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "An Event with given ID was successfully found"),
        @ApiResponse(code = 404, message = "Event not found with given ID")
    })
    public EventDTO findById(long id) throws EventNotFoundException;

    @ApiOperation(value = "Updates Event according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Event with given ID was successfully updated"),
        @ApiResponse(code = 404, message = "Event not found with given ID")
    })
    public EventDTO update(EventDTO eventDTO) throws EventNotFoundException;

    @ApiOperation(value = "Deletes Event according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Event with given ID was successfully deleted"),
        @ApiResponse(code = 404, message = "There is no Event with given ID")
    })
    public void deleteById(long id) throws EventNotFoundException;
}
