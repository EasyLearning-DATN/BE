package com.poly.easylearning.repo;

import com.poly.easylearning.entity.User;
import com.poly.easylearning.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface IUserInfoRepo extends JpaRepository<UserInfo, Integer> {
}
