package com.mkts.waac.Dao;

import com.mkts.waac.models.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WasteTypeDao extends JpaRepository<WasteType, Integer> {

    Optional<WasteType> findById(Integer id);
}
