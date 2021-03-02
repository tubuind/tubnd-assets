package com.astcore.repository.search;

import com.astcore.domain.MtdEcosectors;
import com.astcore.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdEcosectors entity.
 */
public interface MtdEcosectorsSearchRepository extends ElasticsearchRepository<MtdEcosectors, Long> {
	Page<MtdEcosectors> findByIsdel(Integer isdel, Pageable pageable);
	
	Page<MtdEcosectors> findAllByEconameLike(Pageable pageable, String econame);
}
