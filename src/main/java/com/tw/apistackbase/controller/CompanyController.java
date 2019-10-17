package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/all", produces = {"application/json"})
    public Iterable<Company> list(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return companyService.findAll(page, pageSize);
    }

    @GetMapping(produces = {"application/json"})
    public Company getCompany(@RequestParam(required = false) String name) {
        return companyService.findByNameContaining(name);
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        Optional<Company> fetchedCompany = companyService.findById(id);
        if (fetchedCompany.isPresent()) {
            Company modifyCompany = fetchedCompany.get();
            modifyCompany.setName(company.getName());
            Company savedCompany = companyService.save(modifyCompany);
            return new ResponseEntity<>(savedCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        Optional<Company> fetchedCompany = companyService.findById(id);
        if (fetchedCompany.isPresent()) {
            Company deletedCompany = fetchedCompany.get();
            companyService.delete(deletedCompany);
        }
    }
}
