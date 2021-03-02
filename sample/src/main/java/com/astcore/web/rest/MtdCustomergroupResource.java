package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdCustomergroupService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdCustomergroupDTO;
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
 * REST controller for managing MtdCustomergroup.
 */
@RestController
@RequestMapping("/api")
public class MtdCustomergroupResource {

    private final Logger log = LoggerFactory.getLogger(MtdCustomergroupResource.class);

    private static final String ENTITY_NAME = "mtdCustomergroup";

    private final MtdCustomergroupService mtdCustomergroupService;

    public MtdCustomergroupResource(MtdCustomergroupService mtdCustomergroupService) {
        this.mtdCustomergroupService = mtdCustomergroupService;
    }

    /**
     * POST  /mtd-customergroups : Create a new mtdCustomergroup.
     *
     * @param mtdCustomergroupDTO the mtdCustomergroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdCustomergroupDTO, or with status 400 (Bad Request) if the mtdCustomergroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-customergroups")
    @Timed
    public ResponseEntity<MtdCustomergroupDTO> createMtdCustomergroup(@Valid @RequestBody MtdCustomergroupDTO mtdCustomergroupDTO) throws URISyntaxException {
        log.debug("REST request to save MtdCustomergroup : {}", mtdCustomergroupDTO);
        if (mtdCustomergroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdCustomergroup cannot already have an ID")).body(null);
        }
        MtdCustomergroupDTO result = mtdCustomergroupService.save(mtdCustomergroupDTO);
        return ResponseEntity.created(new URI("/api/mtd-customergroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-customergroups : Updates an existing mtdCustomergroup.
     *
     * @param mtdCustomergroupDTO the mtdCustomergroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdCustomergroupDTO,
     * or with status 400 (Bad Request) if the mtdCustomergroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdCustomergroupDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-customergroups")
    @Timed
    public ResponseEntity<MtdCustomergroupDTO> updateMtdCustomergroup(@Valid @RequestBody MtdCustomergroupDTO mtdCustomergroupDTO) throws URISyntaxException {
        log.debug("REST request to update MtdCustomergroup : {}", mtdCustomergroupDTO);
        if (mtdCustomergroupDTO.getId() == null) {
            return createMtdCustomergroup(mtdCustomergroupDTO);
        }
        MtdCustomergroupDTO result = mtdCustomergroupService.save(mtdCustomergroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdCustomergroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-customergroups : get all the mtdCustomergroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdCustomergroups in body
     */
    @GetMapping("/mtd-customergroups")
    @Timed
    public ResponseEntity<List<MtdCustomergroupDTO>> getAllMtdCustomergroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdCustomergroups");
        Page<MtdCustomergroupDTO> page = mtdCustomergroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-customergroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-customergroups/:id : get the "id" mtdCustomergroup.
     *
     * @param id the id of the mtdCustomergroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdCustomergroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-customergroups/{id}")
    @Timed
    public ResponseEntity<MtdCustomergroupDTO> getMtdCustomergroup(@PathVariable Long id) {
        log.debug("REST request to get MtdCustomergroup : {}", id);
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdCustomergroupDTO));
    }

    /**
     * DELETE  /mtd-customergroups/:id : delete the "id" mtdCustomergroup.
     *
     * @param id the id of the mtdCustomergroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-customergroups/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdCustomergroup(@PathVariable Long id) {
        log.debug("REST request to delete MtdCustomergroup : {}", id);
        mtdCustomergroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-customergroups?query=:query : search for the mtdCustomergroup corresponding
     * to the query.
     *
     * @param query the query of the mtdCustomergroup search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-customergroups")
    @Timed
    public ResponseEntity<List<MtdCustomergroupDTO>> searchMtdCustomergroups(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdCustomergroups for query {}", query);
        Page<MtdCustomergroupDTO> page = mtdCustomergroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-customergroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
