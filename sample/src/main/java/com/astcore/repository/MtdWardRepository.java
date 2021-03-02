package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdWard;


/**
 * Spring Data JPA repository for the MtdWard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdWardRepository extends JpaRepository<MtdWard,Long> {
	@Query(value = "select a from MtdWard a where a.isdel=0", countQuery = "select count(a) from MtdWard a where a.isdel=0")
	Page<MtdWard> findAll(Pageable pageable);
	
	@Query(value = "select a from MtdWard a where a.isdel=0 and a.mtdDistrict.id=:idDistrict", countQuery = "select count(a) from MtdWard a where a.isdel=0 and a.mtdDistrict.id=:idDistrict")
	Page<MtdWard> getWardByDistrict(@Param("idDistrict") Long idDistrict, Pageable pageable);
	
	Page<MtdWard> findByWardnameLikeIgnoreCaseAndIsdel(Pageable pageable, String wardname, Integer isdel);
}
