package com.astcore.repository.search;

import com.astcore.domain.MtdOrganization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdOrganization entity.
 */
public interface MtdOrganizationSearchRepository extends ElasticsearchRepository<MtdOrganization, Long> {
}
