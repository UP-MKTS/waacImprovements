package com.mkts.waac.Dao;

import com.mkts.waac.models.Department;
import com.mkts.waac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserDao extends JpaRepository<User, Serializable> {

    User findByEmail(String email);

    User findByUserName(String userName);

    User findFirstByDepartment(Department department);
}
