package com.poly.easylearning.repo;

import com.poly.easylearning.entity.User;
import com.poly.easylearning.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserInfoRepo extends JpaRepository<UserInfo, Integer> {
}
