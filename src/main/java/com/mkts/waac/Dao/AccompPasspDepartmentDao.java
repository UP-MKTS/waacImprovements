package com.mkts.waac.Dao;

import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.AccompPasspWaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccompPasspDepartmentDao extends JpaRepository<AccompPasspDepartment, Integer> {

   List<AccompPasspDepartment> findAllByAccompPassps_Id(Integer id);

   void deleteAllByAccompPassps_Id(Integer ids);

   void deleteByAccompPassps_IdAndDepartment_Id(Integer accompPasspId, Integer departmentId);

   void deleteByAccompPassps_Id(Integer id);
}
