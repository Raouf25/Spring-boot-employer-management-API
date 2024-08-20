package com.mak.springbootemployermanagementapi;

import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {


    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @ApiOperation(value = "Create a new employer",
            notes = "Provide an employer data to update specific employer",
            response = Employer.class)
    @Transactional
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Employer create(@RequestBody Employer item) {
        return employerService.create(item);
    }

    @ApiOperation(value = "Update an employer by id",
            notes = "Provide an id and employer data to update specific employer",
            response = Employer.class)
    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Employer update(@PathVariable(name = "id") Integer id, @RequestBody Employer item) {
        return employerService.update(id, item);
    }

    @ApiOperation(value = "Delete an employer by id",
            notes = "Provide an id to remove specific employer",
            response = void.class)
    @Transactional
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id) {
        employerService.delete(id);
    }

    @ApiOperation(value = "Finds an employer by id",
            notes = "Provide an id to look up specific employer",
            response = Employer.class)
    @Transactional
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Employer get(@PathVariable(name = "id") Integer id) {
        return employerService.get(id);
    }

    @ApiOperation(value = "List all employers",
            notes = "Provide all existing employers",
            response = Employer.class,
            responseContainer = "List")
    @Transactional
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Employer> get() {
        return employerService.get();
    }


    @GetMapping(path = "/memory-status")
    public MemoryStats getMemoryStatistics() {
        return new MemoryStats(
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().freeMemory()
        );
    }
}
