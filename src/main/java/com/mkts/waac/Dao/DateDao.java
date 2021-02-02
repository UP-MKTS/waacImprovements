package com.mkts.waac.Dao;

import com.mkts.waac.models.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateDao extends JpaRepository<Date, Integer> {

}
