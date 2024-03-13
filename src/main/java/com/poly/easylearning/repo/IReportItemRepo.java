package com.poly.easylearning.repo;

import com.poly.easylearning.entity.ReportItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface IReportItemRepo extends JpaRepository<ReportItem, UUID> {
}
