package com.mak.springbootemployermanagementapi;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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
}
