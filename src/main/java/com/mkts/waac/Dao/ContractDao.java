package com.mkts.waac.Dao;

import com.mkts.waac.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDao extends JpaRepository<Contract, Integer> {

}
