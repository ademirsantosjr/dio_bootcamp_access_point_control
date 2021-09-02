package on.dio.accesspointcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import on.dio.accesspointcontrol.model.WorkDay;
import on.dio.accesspointcontrol.service.WorkDayService;

@RestController
@RequestMapping("/workday")
public class WorkDayController {

    @Autowired
    WorkDayService workDayService;
    
    @PostMapping
    public WorkDay create(@RequestBody WorkDay workDay) {
        return workDayService.save(workDay);
    }

    @GetMapping
    public List<WorkDay> findAll() {
        return workDayService.findAll();
    }

    @GetMapping("/{id}")
    public WorkDay findById(@RequestParam("id") long id) throws Exception {
        return workDayService.findById(id).orElseThrow(() -> new Exception("Work Day not found!"));
    }

}
