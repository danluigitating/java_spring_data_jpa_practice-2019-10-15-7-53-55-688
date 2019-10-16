package com.tw.apistackbase.repository;

import com.tw.apistackbase.core.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository <Company, Long> {
    @Query("Select C From Company C where C.name = :NAME")
    Company findOneByName(@Param("NAME") String name);
}
