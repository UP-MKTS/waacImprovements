package com.mkts.waac.Dao;

import com.mkts.waac.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface RoleDao extends JpaRepository<Role, Serializable> {

    Role findByName(String name);
}
