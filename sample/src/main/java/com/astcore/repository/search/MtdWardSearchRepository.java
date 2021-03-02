package com.astcore.repository.search;

import com.astcore.domain.MtdWard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdWard entity.
 */
public interface MtdWardSearchRepository extends ElasticsearchRepository<MtdWard, Long> {
}
