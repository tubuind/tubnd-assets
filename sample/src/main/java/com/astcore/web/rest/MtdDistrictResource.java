package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdDistrictService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdDistrictDTO;
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
 * REST controller for managing MtdDistrict.
 */
@RestController
@RequestMapping("/api")
public class MtdDistrictResource {

    private final Logger log = LoggerFactory.getLogger(MtdDistrictResource.class);

    private static final String ENTITY_NAME = "mtdDistrict";

    private final MtdDistrictService mtdDistrictService;

    public MtdDistrictResource(MtdDistrictService mtdDistrictService) {
        this.mtdDistrictService = mtdDistrictService;
    }

    /**
     * POST  /mtd-districts : Create a new mtdDistrict.
     *
     * @param mtdDistrictDTO the mtdDistrictDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdDistrictDTO, or with status 400 (Bad Request) if the mtdDistrict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-districts")
    @Timed
    public ResponseEntity<MtdDistrictDTO> createMtdDistrict(@Valid @RequestBody MtdDistrictDTO mtdDistrictDTO) throws URISyntaxException {
        log.debug("REST request to save MtdDistrict : {}", mtdDistrictDTO);
        if (mtdDistrictDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdDistrict cannot already have an ID")).body(null);
        }
        MtdDistrictDTO result = mtdDistrictService.save(mtdDistrictDTO);
        return ResponseEntity.created(new URI("/api/mtd-districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-districts : Updates an existing mtdDistrict.
     *
     * @param mtdDistrictDTO the mtdDistrictDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdDistrictDTO,
     * or with status 400 (Bad Request) if the mtdDistrictDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdDistrictDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-districts")
    @Timed
    public ResponseEntity<MtdDistrictDTO> updateMtdDistrict(@Valid @RequestBody MtdDistrictDTO mtdDistrictDTO) throws URISyntaxException {
        log.debug("REST request to update MtdDistrict : {}", mtdDistrictDTO);
        if (mtdDistrictDTO.getId() == null) {
            return createMtdDistrict(mtdDistrictDTO);
        }
        MtdDistrictDTO result = mtdDistrictService.save(mtdDistrictDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdDistrictDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-districts : get all the mtdDistricts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdDistricts in body
     */
    @GetMapping("/mtd-districts")
    @Timed
    public ResponseEntity<List<MtdDistrictDTO>> getAllMtdDistricts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdDistricts");
        Page<MtdDistrictDTO> page = mtdDistrictService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-districts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-districts/:id : get the "id" mtdDistrict.
     *
     * @param id the id of the mtdDistrictDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdDistrictDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-districts/{id}")
    @Timed
    public ResponseEntity<MtdDistrictDTO> getMtdDistrict(@PathVariable Long id) {
        log.debug("REST request to get MtdDistrict : {}", id);
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdDistrictDTO));
    }

    /**
     * DELETE  /mtd-districts/:id : delete the "id" mtdDistrict.
     *
     * @param id the id of the mtdDistrictDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-districts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdDistrict(@PathVariable Long id) {
        log.debug("REST request to delete MtdDistrict : {}", id);
        mtdDistrictService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-districts?query=:query : search for the mtdDistrict corresponding
     * to the query.
     *
     * @param query the query of the mtdDistrict search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-districts")
    @Timed
    public ResponseEntity<List<MtdDistrictDTO>> searchMtdDistricts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdDistricts for query {}", query);
        Page<MtdDistrictDTO> page = mtdDistrictService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-districts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /mtd-districts : get all the mtdDistricts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdDistricts in body
     */
    @GetMapping("/mtd-districts/get-by-province-id/{idProvince}")
    @Timed
    public ResponseEntity<List<MtdDistrictDTO>> getAllMtdDistrictsByProvinceId(@PathVariable Long idProvince, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdDistricts");
        Page<MtdDistrictDTO> page = mtdDistrictService.getDistrictByProvince(idProvince, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-districts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
