package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(produces = {"application/json"})
    public Iterable<Company> list() {
        return companyRepository.findAll();
    }

    @PostMapping(produces = {"application/json"})
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public Company update(@PathVariable Long id, @RequestBody Company company) throws NotFoundException {
        Optional<Company> fetchedCompany = companyRepository.findById(id);
        if (fetchedCompany.isPresent()) {
            Company modifyCompany = fetchedCompany.get();
            modifyCompany.setName(company.getName());
            return companyRepository.save(modifyCompany);
        }
        throw new NotFoundException("Company id not found");
    }
}
