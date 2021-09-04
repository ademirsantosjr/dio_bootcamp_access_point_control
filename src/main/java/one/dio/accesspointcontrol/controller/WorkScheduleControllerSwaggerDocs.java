package one.dio.accesspointcontrol.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.exception.WorkScheduleNotFoundException;

@Api("Manages Access Point Control")
public interface WorkScheduleControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Work Schedule (e.g. 'full-time', 'part-time', 'flexible')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Work Schedule was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing")
    })
    public WorkScheduleDTO create(WorkScheduleDTO workScheduleDTO);

    @ApiOperation(value = "Returns all Work Schedules in the database")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of all Work Schedules in the database")
    })
    public List<WorkScheduleDTO> findAll();
    
    @ApiOperation(value = "Returns a Work Schedule according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Work Schedule successfully found with given ID"),
        @ApiResponse(code = 404, message = "Work Schedule not found with the given ID")
    })
    public WorkScheduleDTO findById(long id) throws WorkScheduleNotFoundException;

    @ApiOperation(value = "Updates Work Schedule according to the given ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Work Schedule successfully updated with given ID"),
        @ApiResponse(code = 404, message = "Work Schedule not found with the given ID")
    })
    public WorkScheduleDTO update(WorkScheduleDTO workScheduleDTO) throws WorkScheduleNotFoundException;
}
