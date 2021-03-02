package com.astcore.repository.search;

import com.astcore.domain.MtdCountry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdCountry entity.
 */
public interface MtdCountrySearchRepository extends ElasticsearchRepository<MtdCountry, Long> {
}
