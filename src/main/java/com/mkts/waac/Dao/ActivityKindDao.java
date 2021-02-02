package com.mkts.waac.Dao;

import com.mkts.waac.models.ActivityKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityKindDao extends JpaRepository<ActivityKind, Integer> {

}
