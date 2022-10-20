package com.tuto.rate.repository;

import com.tuto.rate.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RateRepository extends JpaRepository<Rate, String> {
    List<Rate> findByDate(Date date);
    Rate findByDateAndCode(Date date, String code);
}
