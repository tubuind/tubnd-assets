package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdEcosectors;

/**
 * Spring Data JPA repository for the MtdEcosectors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdEcosectorsRepository extends JpaRepository<MtdEcosectors,Long> {
	//	@Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1", countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1", nativeQuery = true)
	//	Page<MtdEcosectors> findByIsdel(Integer isdel, Pageable pageable);
	
	@Query(value = "select a from MtdEcosectors a where a.isdel=0", countQuery = "select count(a) from MtdEcosectors a where a.isdel=0")
	Page<MtdEcosectors> findAll(Pageable pageable);
	
	Page<MtdEcosectors> findByEconameLikeIgnoreCaseAndIsdel(Pageable pageable, String econame, Integer isdel);
}
