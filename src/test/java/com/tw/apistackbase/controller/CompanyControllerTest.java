package com.tw.apistackbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.core.CompanyProfile;
import com.tw.apistackbase.core.Employee;
import com.tw.apistackbase.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    void should_get_all_companies_when_get_companies() throws Exception {
        List<Company> companies= new ArrayList<>();
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.add("page", "1");
        requestParams.add("pageSize", "5");

        Company company = new Company();
        company.setId(1L);
        company.setName("Dan");

        List<Employee> employees= new ArrayList<>();
        company.setEmployees(employees);

        CompanyProfile profile= new CompanyProfile();
        company.setProfile(profile);

        companies.add(company);

        when(companyService.findAll(anyInt(),any())).thenReturn(companies);

        ResultActions result = mockMvc.perform(get("/companies/all").params(requestParams));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Dan")))
                .andExpect(jsonPath("$[0].employees").exists())
                .andExpect(jsonPath("$[0].profile").exists());
    }

    @Test
    void should_get_specific_company_when_get_companies() throws Exception {

        Company company = new Company();
        company.setId(1L);
        company.setName("Dan");

        List<Employee> employees= new ArrayList<>();
        company.setEmployees(employees);

        CompanyProfile profile= new CompanyProfile();
        company.setProfile(profile);
        
        when(companyService.findByNameContaining(any())).thenReturn(company);

        ResultActions result = mockMvc.perform(get("/companies/"));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Dan")))
                .andExpect(jsonPath("$.employees").exists())
                .andExpect(jsonPath("$.profile").exists());
    }

    @Test
    void should_add_new_company_when_post_companies() throws Exception {
        Company company = new Company();
        company.setId(1L);
        company.setName("Dan");

        List<Employee> employees= new ArrayList<>();
        company.setEmployees(employees);

        CompanyProfile profile= new CompanyProfile();
        company.setProfile(profile);

        when(companyService.save(any())).thenReturn(company);

        ResultActions resultActions = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(company)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Dan")))
                .andExpect(jsonPath("$.employees").exists())
                .andExpect(jsonPath("$.profile").exists());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    public static String asJsonString(final Company obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}