package com.astcore.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.astcore.domain.MtdCustomergroup;

/**
 * Spring Data Elasticsearch repository for the MtdCustomergroup entity.
 */
public interface MtdCustomergroupSearchRepository extends ElasticsearchRepository<MtdCustomergroup, Long> {
	Page<MtdCustomergroup> findAllByCustgroupnameLike(Pageable pageable, String custgroupname);
}
