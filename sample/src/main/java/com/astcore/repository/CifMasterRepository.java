package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.CifMaster;


/**
 * Spring Data JPA repository for the CifMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CifMasterRepository extends JpaRepository<CifMaster,Long> {
	@Query(value = "select a from CifMaster a where a.isdel=0", countQuery = "select count(a) from CifMaster a where a.isdel=0")
	Page<CifMaster> findAll(Pageable pageable);
	
	@Query("select a from CifMaster a WHERE a.login=:userLoginUname")
	CifMaster findOneByUserLoginUname(@Param("userLoginUname") String userLoginUname);
	
	Page<CifMaster> findByCustomernameLikeIgnoreCaseAndIsdel(Pageable pageable, String customername, Integer isdel);
}
