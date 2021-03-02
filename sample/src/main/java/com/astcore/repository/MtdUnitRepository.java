package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdUnit;


/**
 * Spring Data JPA repository for the MtdUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdUnitRepository extends JpaRepository<MtdUnit,Long> {
	@Query(value = "select a from MtdUnit a where a.isdel=0", countQuery = "select count(a) from MtdUnit a where a.isdel=0")
	Page<MtdUnit> findAll(Pageable pageable);
	
	Page<MtdUnit> findByUnitnameLikeIgnoreCaseAndIsdel(Pageable pageable, String unitname, Integer isdel);
}
