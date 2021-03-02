package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdWardService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdWardDTO;
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
 * REST controller for managing MtdWard.
 */
@RestController
@RequestMapping("/api")
public class MtdWardResource {

    private final Logger log = LoggerFactory.getLogger(MtdWardResource.class);

    private static final String ENTITY_NAME = "mtdWard";

    private final MtdWardService mtdWardService;

    public MtdWardResource(MtdWardService mtdWardService) {
        this.mtdWardService = mtdWardService;
    }

    /**
     * POST  /mtd-wards : Create a new mtdWard.
     *
     * @param mtdWardDTO the mtdWardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdWardDTO, or with status 400 (Bad Request) if the mtdWard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-wards")
    @Timed
    public ResponseEntity<MtdWardDTO> createMtdWard(@Valid @RequestBody MtdWardDTO mtdWardDTO) throws URISyntaxException {
        log.debug("REST request to save MtdWard : {}", mtdWardDTO);
        if (mtdWardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdWard cannot already have an ID")).body(null);
        }
        MtdWardDTO result = mtdWardService.save(mtdWardDTO);
        return ResponseEntity.created(new URI("/api/mtd-wards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-wards : Updates an existing mtdWard.
     *
     * @param mtdWardDTO the mtdWardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdWardDTO,
     * or with status 400 (Bad Request) if the mtdWardDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdWardDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-wards")
    @Timed
    public ResponseEntity<MtdWardDTO> updateMtdWard(@Valid @RequestBody MtdWardDTO mtdWardDTO) throws URISyntaxException {
        log.debug("REST request to update MtdWard : {}", mtdWardDTO);
        if (mtdWardDTO.getId() == null) {
            return createMtdWard(mtdWardDTO);
        }
        MtdWardDTO result = mtdWardService.save(mtdWardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdWardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-wards : get all the mtdWards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdWards in body
     */
    @GetMapping("/mtd-wards")
    @Timed
    public ResponseEntity<List<MtdWardDTO>> getAllMtdWards(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdWards");
        Page<MtdWardDTO> page = mtdWardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-wards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-wards/:id : get the "id" mtdWard.
     *
     * @param id the id of the mtdWardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdWardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-wards/{id}")
    @Timed
    public ResponseEntity<MtdWardDTO> getMtdWard(@PathVariable Long id) {
        log.debug("REST request to get MtdWard : {}", id);
        MtdWardDTO mtdWardDTO = mtdWardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdWardDTO));
    }

    /**
     * DELETE  /mtd-wards/:id : delete the "id" mtdWard.
     *
     * @param id the id of the mtdWardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-wards/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdWard(@PathVariable Long id) {
        log.debug("REST request to delete MtdWard : {}", id);
        mtdWardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-wards?query=:query : search for the mtdWard corresponding
     * to the query.
     *
     * @param query the query of the mtdWard search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-wards")
    @Timed
    public ResponseEntity<List<MtdWardDTO>> searchMtdWards(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdWards for query {}", query);
        Page<MtdWardDTO> page = mtdWardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-wards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /mtd-wards : get all the mtdWards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdWards in body
     */
    @GetMapping("/mtd-wards/get-by-district-id/{idDistrict}")
    @Timed
    public ResponseEntity<List<MtdWardDTO>> getAllMtdWardsByDistrictId(@PathVariable Long idDistrict, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdWards");
        Page<MtdWardDTO> page = mtdWardService.getWardByDistrict(idDistrict, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-wards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
