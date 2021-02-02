package com.mkts.waac.Dao;


import com.mkts.waac.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DepartmentDao extends JpaRepository<Department, Integer> {

}
