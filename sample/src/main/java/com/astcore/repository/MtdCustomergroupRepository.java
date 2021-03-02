package com.astcore.repository;

import com.astcore.domain.DeviceInfo;
import com.astcore.domain.MtdCustomergroup;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MtdCustomergroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdCustomergroupRepository extends JpaRepository<MtdCustomergroup,Long> {
	@Query(value = "select a from MtdCustomergroup a where a.isdel=0", countQuery = "select count(a) from MtdCustomergroup a where a.isdel=0")
	Page<MtdCustomergroup> findAll(Pageable pageable);
	
	Page<MtdCustomergroup> findByCustgroupnameLikeIgnoreCaseAndIsdel(Pageable pageable, String custgroupname, Integer isdel);
}
