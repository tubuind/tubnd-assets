package com.astcore.repository.search;

import com.astcore.domain.CifArea;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CifArea entity.
 */
public interface CifAreaSearchRepository extends ElasticsearchRepository<CifArea, Long> {
}
