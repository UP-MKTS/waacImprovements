package com.mkts.waac.Dao;

import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspWaste;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccompPasspWasteDao extends JpaRepository<AccompPasspWaste, Integer> {

    void deleteAllByAccompPassps_Id(Integer id);

    void deleteByAccompPassps_IdAndWasteTypes_Id(Integer accompPasspId, Integer wasteId);

    AccompPasspWaste findByAccompPassps_IdAndWasteTypes_Id(Integer accompPasspId, Integer wasteId);

    List<AccompPasspWaste> findAllByAccompPassps_IdAndWasteTypes_Id(Integer accompPasspId, Integer wasteId);

    AccompPasspWaste findByAccompPassps_IdAndWasteTypes_IdAndDepartment_Id(Integer accompPasspId, Integer wasteId, Integer departmentId);
}
