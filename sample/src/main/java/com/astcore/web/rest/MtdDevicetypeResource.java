package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdDevicetypeService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdDevicetypeDTO;
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
 * REST controller for managing MtdDevicetype.
 */
@RestController
@RequestMapping("/api")
public class MtdDevicetypeResource {

    private final Logger log = LoggerFactory.getLogger(MtdDevicetypeResource.class);

    private static final String ENTITY_NAME = "mtdDevicetype";

    private final MtdDevicetypeService mtdDevicetypeService;

    public MtdDevicetypeResource(MtdDevicetypeService mtdDevicetypeService) {
        this.mtdDevicetypeService = mtdDevicetypeService;
    }

    /**
     * POST  /mtd-devicetypes : Create a new mtdDevicetype.
     *
     * @param mtdDevicetypeDTO the mtdDevicetypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdDevicetypeDTO, or with status 400 (Bad Request) if the mtdDevicetype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-devicetypes")
    @Timed
    public ResponseEntity<MtdDevicetypeDTO> createMtdDevicetype(@Valid @RequestBody MtdDevicetypeDTO mtdDevicetypeDTO) throws URISyntaxException {
        log.debug("REST request to save MtdDevicetype : {}", mtdDevicetypeDTO);
        if (mtdDevicetypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdDevicetype cannot already have an ID")).body(null);
        }
        MtdDevicetypeDTO result = mtdDevicetypeService.save(mtdDevicetypeDTO);
        return ResponseEntity.created(new URI("/api/mtd-devicetypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-devicetypes : Updates an existing mtdDevicetype.
     *
     * @param mtdDevicetypeDTO the mtdDevicetypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdDevicetypeDTO,
     * or with status 400 (Bad Request) if the mtdDevicetypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdDevicetypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-devicetypes")
    @Timed
    public ResponseEntity<MtdDevicetypeDTO> updateMtdDevicetype(@Valid @RequestBody MtdDevicetypeDTO mtdDevicetypeDTO) throws URISyntaxException {
        log.debug("REST request to update MtdDevicetype : {}", mtdDevicetypeDTO);
        if (mtdDevicetypeDTO.getId() == null) {
            return createMtdDevicetype(mtdDevicetypeDTO);
        }
        MtdDevicetypeDTO result = mtdDevicetypeService.save(mtdDevicetypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdDevicetypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-devicetypes : get all the mtdDevicetypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdDevicetypes in body
     */
    @GetMapping("/mtd-devicetypes")
    @Timed
    public ResponseEntity<List<MtdDevicetypeDTO>> getAllMtdDevicetypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdDevicetypes");
        Page<MtdDevicetypeDTO> page = mtdDevicetypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-devicetypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-devicetypes/:id : get the "id" mtdDevicetype.
     *
     * @param id the id of the mtdDevicetypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdDevicetypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-devicetypes/{id}")
    @Timed
    public ResponseEntity<MtdDevicetypeDTO> getMtdDevicetype(@PathVariable Long id) {
        log.debug("REST request to get MtdDevicetype : {}", id);
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdDevicetypeDTO));
    }

    /**
     * DELETE  /mtd-devicetypes/:id : delete the "id" mtdDevicetype.
     *
     * @param id the id of the mtdDevicetypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-devicetypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdDevicetype(@PathVariable Long id) {
        log.debug("REST request to delete MtdDevicetype : {}", id);
        mtdDevicetypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-devicetypes?query=:query : search for the mtdDevicetype corresponding
     * to the query.
     *
     * @param query the query of the mtdDevicetype search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-devicetypes")
    @Timed
    public ResponseEntity<List<MtdDevicetypeDTO>> searchMtdDevicetypes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdDevicetypes for query {}", query);
        Page<MtdDevicetypeDTO> page = mtdDevicetypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-devicetypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
