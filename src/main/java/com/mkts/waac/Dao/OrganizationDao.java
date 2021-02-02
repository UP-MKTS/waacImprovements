package com.mkts.waac.Dao;

import com.mkts.waac.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, Integer> {

}
