package one.dio.accesspointcontrol.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.dio.accesspointcontrol.dto.WorkScheduleDTO;

@Api("Manages Access Point Control")
public interface WorkScheduleControllerSwaggerDocs {
    
    @ApiOperation(value = "Creates Work Schedule (e.g. 'full-time', 'part-time', 'flexible')")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Work Schedule was successfully created"),
        @ApiResponse(code = 400, message = "One or more required fields are missing.")
    })
    WorkScheduleDTO create(WorkScheduleDTO workScheduleDTO);
}
