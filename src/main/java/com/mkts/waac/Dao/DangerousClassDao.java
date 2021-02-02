package com.mkts.waac.Dao;

import com.mkts.waac.models.DangerousClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DangerousClassDao extends JpaRepository<DangerousClass, Integer> {

}
