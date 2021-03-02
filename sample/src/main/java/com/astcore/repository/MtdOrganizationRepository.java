package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdOrganization;


/**
 * Spring Data JPA repository for the MtdOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdOrganizationRepository extends JpaRepository<MtdOrganization,Long> {
	@Query(value = "select a from MtdOrganization a where a.isdel=0", countQuery = "select count(a) from MtdOrganization a where a.isdel=0")
	Page<MtdOrganization> findAll(Pageable pageable);
	
	Page<MtdOrganization> findByOrganizationnameLikeIgnoreCaseAndIsdel(Pageable pageable, String organizationname, Integer isdel);
}
