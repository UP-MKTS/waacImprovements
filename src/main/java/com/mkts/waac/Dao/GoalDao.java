package com.mkts.waac.Dao;

import com.mkts.waac.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalDao extends JpaRepository<Goal, Integer> {

    Goal findFirst1ByOrderById ();
}
