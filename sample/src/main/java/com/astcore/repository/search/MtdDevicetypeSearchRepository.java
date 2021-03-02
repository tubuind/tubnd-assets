package com.astcore.repository.search;

import com.astcore.domain.MtdDevicetype;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdDevicetype entity.
 */
public interface MtdDevicetypeSearchRepository extends ElasticsearchRepository<MtdDevicetype, Long> {
}
