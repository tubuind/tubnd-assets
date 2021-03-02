package com.astcore.repository.search;

import com.astcore.domain.MtdUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdUnit entity.
 */
public interface MtdUnitSearchRepository extends ElasticsearchRepository<MtdUnit, Long> {
}
