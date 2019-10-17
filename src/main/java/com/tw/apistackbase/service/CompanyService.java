package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> findAll(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        if (page != null && pageSize != null) {
            pageRequest = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        }

        return companyRepository.findAll(pageRequest);
    }

    public Company findByNameContaining(String name) {
        return companyRepository.findByNameContaining(name);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    public void delete(Company deletedCompany) {
        companyRepository.delete(deletedCompany);
    }
}
