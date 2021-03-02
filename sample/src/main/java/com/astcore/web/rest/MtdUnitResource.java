package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdUnitService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdUnitDTO;
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
 * REST controller for managing MtdUnit.
 */
@RestController
@RequestMapping("/api")
public class MtdUnitResource {

    private final Logger log = LoggerFactory.getLogger(MtdUnitResource.class);

    private static final String ENTITY_NAME = "mtdUnit";

    private final MtdUnitService mtdUnitService;

    public MtdUnitResource(MtdUnitService mtdUnitService) {
        this.mtdUnitService = mtdUnitService;
    }

    /**
     * POST  /mtd-units : Create a new mtdUnit.
     *
     * @param mtdUnitDTO the mtdUnitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdUnitDTO, or with status 400 (Bad Request) if the mtdUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-units")
    @Timed
    public ResponseEntity<MtdUnitDTO> createMtdUnit(@Valid @RequestBody MtdUnitDTO mtdUnitDTO) throws URISyntaxException {
        log.debug("REST request to save MtdUnit : {}", mtdUnitDTO);
        if (mtdUnitDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdUnit cannot already have an ID")).body(null);
        }
        MtdUnitDTO result = mtdUnitService.save(mtdUnitDTO);
        return ResponseEntity.created(new URI("/api/mtd-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-units : Updates an existing mtdUnit.
     *
     * @param mtdUnitDTO the mtdUnitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdUnitDTO,
     * or with status 400 (Bad Request) if the mtdUnitDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdUnitDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-units")
    @Timed
    public ResponseEntity<MtdUnitDTO> updateMtdUnit(@Valid @RequestBody MtdUnitDTO mtdUnitDTO) throws URISyntaxException {
        log.debug("REST request to update MtdUnit : {}", mtdUnitDTO);
        if (mtdUnitDTO.getId() == null) {
            return createMtdUnit(mtdUnitDTO);
        }
        MtdUnitDTO result = mtdUnitService.save(mtdUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-units : get all the mtdUnits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdUnits in body
     */
    @GetMapping("/mtd-units")
    @Timed
    public ResponseEntity<List<MtdUnitDTO>> getAllMtdUnits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdUnits");
        Page<MtdUnitDTO> page = mtdUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-units/:id : get the "id" mtdUnit.
     *
     * @param id the id of the mtdUnitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdUnitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-units/{id}")
    @Timed
    public ResponseEntity<MtdUnitDTO> getMtdUnit(@PathVariable Long id) {
        log.debug("REST request to get MtdUnit : {}", id);
        MtdUnitDTO mtdUnitDTO = mtdUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdUnitDTO));
    }

    /**
     * DELETE  /mtd-units/:id : delete the "id" mtdUnit.
     *
     * @param id the id of the mtdUnitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdUnit(@PathVariable Long id) {
        log.debug("REST request to delete MtdUnit : {}", id);
        mtdUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-units?query=:query : search for the mtdUnit corresponding
     * to the query.
     *
     * @param query the query of the mtdUnit search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-units")
    @Timed
    public ResponseEntity<List<MtdUnitDTO>> searchMtdUnits(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdUnits for query {}", query);
        Page<MtdUnitDTO> page = mtdUnitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
