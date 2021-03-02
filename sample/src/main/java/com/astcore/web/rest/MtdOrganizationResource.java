package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdOrganizationService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdOrganizationDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MtdOrganization.
 */
@RestController
@RequestMapping("/api")
public class MtdOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(MtdOrganizationResource.class);

    private static final String ENTITY_NAME = "mtdOrganization";

    private final MtdOrganizationService mtdOrganizationService;

    public MtdOrganizationResource(MtdOrganizationService mtdOrganizationService) {
        this.mtdOrganizationService = mtdOrganizationService;
    }

    /**
     * POST  /mtd-organizations : Create a new mtdOrganization.
     *
     * @param mtdOrganizationDTO the mtdOrganizationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdOrganizationDTO, or with status 400 (Bad Request) if the mtdOrganization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-organizations")
    @Timed
    public ResponseEntity<MtdOrganizationDTO> createMtdOrganization(@Valid @RequestBody MtdOrganizationDTO mtdOrganizationDTO) throws URISyntaxException {
        log.debug("REST request to save MtdOrganization : {}", mtdOrganizationDTO);
        if (mtdOrganizationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdOrganization cannot already have an ID")).body(null);
        }
        MtdOrganizationDTO result = mtdOrganizationService.save(mtdOrganizationDTO);
        return ResponseEntity.created(new URI("/api/mtd-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-organizations : Updates an existing mtdOrganization.
     *
     * @param mtdOrganizationDTO the mtdOrganizationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdOrganizationDTO,
     * or with status 400 (Bad Request) if the mtdOrganizationDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdOrganizationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-organizations")
    @Timed
    public ResponseEntity<MtdOrganizationDTO> updateMtdOrganization(@Valid @RequestBody MtdOrganizationDTO mtdOrganizationDTO) throws URISyntaxException {
        log.debug("REST request to update MtdOrganization : {}", mtdOrganizationDTO);
        if (mtdOrganizationDTO.getId() == null) {
            return createMtdOrganization(mtdOrganizationDTO);
        }
        MtdOrganizationDTO result = mtdOrganizationService.save(mtdOrganizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-organizations : get all the mtdOrganizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdOrganizations in body
     */
    @GetMapping("/mtd-organizations")
    @Timed
    public ResponseEntity<List<MtdOrganizationDTO>> getAllMtdOrganizations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdOrganizations");
        Page<MtdOrganizationDTO> page = mtdOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-organizations/:id : get the "id" mtdOrganization.
     *
     * @param id the id of the mtdOrganizationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdOrganizationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-organizations/{id}")
    @Timed
    public ResponseEntity<MtdOrganizationDTO> getMtdOrganization(@PathVariable Long id) {
        log.debug("REST request to get MtdOrganization : {}", id);
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdOrganizationDTO));
    }

    /**
     * DELETE  /mtd-organizations/:id : delete the "id" mtdOrganization.
     *
     * @param id the id of the mtdOrganizationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-organizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdOrganization(@PathVariable Long id) {
        log.debug("REST request to delete MtdOrganization : {}", id);
        mtdOrganizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-organizations?query=:query : search for the mtdOrganization corresponding
     * to the query.
     *
     * @param query the query of the mtdOrganization search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-organizations")
    @Timed
    public ResponseEntity<List<MtdOrganizationDTO>> searchMtdOrganizations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdOrganizations for query {}", query);
        Page<MtdOrganizationDTO> page = mtdOrganizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
