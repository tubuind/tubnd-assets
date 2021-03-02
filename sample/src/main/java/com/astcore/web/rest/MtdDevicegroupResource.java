package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdDevicegroupService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdDevicegroupDTO;
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
 * REST controller for managing MtdDevicegroup.
 */
@RestController
@RequestMapping("/api")
public class MtdDevicegroupResource {

    private final Logger log = LoggerFactory.getLogger(MtdDevicegroupResource.class);

    private static final String ENTITY_NAME = "mtdDevicegroup";

    private final MtdDevicegroupService mtdDevicegroupService;

    public MtdDevicegroupResource(MtdDevicegroupService mtdDevicegroupService) {
        this.mtdDevicegroupService = mtdDevicegroupService;
    }

    /**
     * POST  /mtd-devicegroups : Create a new mtdDevicegroup.
     *
     * @param mtdDevicegroupDTO the mtdDevicegroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdDevicegroupDTO, or with status 400 (Bad Request) if the mtdDevicegroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-devicegroups")
    @Timed
    public ResponseEntity<MtdDevicegroupDTO> createMtdDevicegroup(@Valid @RequestBody MtdDevicegroupDTO mtdDevicegroupDTO) throws URISyntaxException {
        log.debug("REST request to save MtdDevicegroup : {}", mtdDevicegroupDTO);
        if (mtdDevicegroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdDevicegroup cannot already have an ID")).body(null);
        }
        MtdDevicegroupDTO result = mtdDevicegroupService.save(mtdDevicegroupDTO);
        return ResponseEntity.created(new URI("/api/mtd-devicegroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-devicegroups : Updates an existing mtdDevicegroup.
     *
     * @param mtdDevicegroupDTO the mtdDevicegroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdDevicegroupDTO,
     * or with status 400 (Bad Request) if the mtdDevicegroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdDevicegroupDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-devicegroups")
    @Timed
    public ResponseEntity<MtdDevicegroupDTO> updateMtdDevicegroup(@Valid @RequestBody MtdDevicegroupDTO mtdDevicegroupDTO) throws URISyntaxException {
        log.debug("REST request to update MtdDevicegroup : {}", mtdDevicegroupDTO);
        if (mtdDevicegroupDTO.getId() == null) {
            return createMtdDevicegroup(mtdDevicegroupDTO);
        }
        MtdDevicegroupDTO result = mtdDevicegroupService.save(mtdDevicegroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdDevicegroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-devicegroups : get all the mtdDevicegroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdDevicegroups in body
     */
    @GetMapping("/mtd-devicegroups")
    @Timed
    public ResponseEntity<List<MtdDevicegroupDTO>> getAllMtdDevicegroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdDevicegroups");
        Page<MtdDevicegroupDTO> page = mtdDevicegroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-devicegroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-devicegroups/:id : get the "id" mtdDevicegroup.
     *
     * @param id the id of the mtdDevicegroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdDevicegroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-devicegroups/{id}")
    @Timed
    public ResponseEntity<MtdDevicegroupDTO> getMtdDevicegroup(@PathVariable Long id) {
        log.debug("REST request to get MtdDevicegroup : {}", id);
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdDevicegroupDTO));
    }

    /**
     * DELETE  /mtd-devicegroups/:id : delete the "id" mtdDevicegroup.
     *
     * @param id the id of the mtdDevicegroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-devicegroups/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdDevicegroup(@PathVariable Long id) {
        log.debug("REST request to delete MtdDevicegroup : {}", id);
        mtdDevicegroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-devicegroups?query=:query : search for the mtdDevicegroup corresponding
     * to the query.
     *
     * @param query the query of the mtdDevicegroup search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-devicegroups")
    @Timed
    public ResponseEntity<List<MtdDevicegroupDTO>> searchMtdDevicegroups(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdDevicegroups for query {}", query);
        Page<MtdDevicegroupDTO> page = mtdDevicegroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-devicegroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
