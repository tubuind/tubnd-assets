package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.CifArea;


/**
 * Spring Data JPA repository for the CifArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CifAreaRepository extends JpaRepository<CifArea,Long> {
	@Query(value = "select a from CifArea a, CifMaster b where a.cifMaster.id=b.id and b.login=:login", countQuery = "select count(a) from CifArea a, CifMaster b where a.cifMaster.id=b.id and b.login=:login")
    Page<CifArea> getAreasByUname(@Param("login") String login, Pageable pageable);
	
	Page<CifArea> findByCifareanameLikeIgnoreCaseAndIsdel(Pageable pageable, String cifareaname, Integer isdel);
}
