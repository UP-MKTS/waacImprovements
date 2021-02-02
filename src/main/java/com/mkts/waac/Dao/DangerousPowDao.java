package com.mkts.waac.Dao;

import com.mkts.waac.models.DangerousPow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DangerousPowDao extends JpaRepository<DangerousPow, Integer> {

}
