package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdProvinceService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdDistrictDTO;
import com.astcore.service.dto.MtdProvinceDTO;
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
 * REST controller for managing MtdProvince.
 */
@RestController
@RequestMapping("/api")
public class MtdProvinceResource {

    private final Logger log = LoggerFactory.getLogger(MtdProvinceResource.class);

    private static final String ENTITY_NAME = "mtdProvince";

    private final MtdProvinceService mtdProvinceService;

    public MtdProvinceResource(MtdProvinceService mtdProvinceService) {
        this.mtdProvinceService = mtdProvinceService;
    }

    /**
     * POST  /mtd-provinces : Create a new mtdProvince.
     *
     * @param mtdProvinceDTO the mtdProvinceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdProvinceDTO, or with status 400 (Bad Request) if the mtdProvince has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-provinces")
    @Timed
    public ResponseEntity<MtdProvinceDTO> createMtdProvince(@Valid @RequestBody MtdProvinceDTO mtdProvinceDTO) throws URISyntaxException {
        log.debug("REST request to save MtdProvince : {}", mtdProvinceDTO);
        if (mtdProvinceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdProvince cannot already have an ID")).body(null);
        }
        MtdProvinceDTO result = mtdProvinceService.save(mtdProvinceDTO);
        return ResponseEntity.created(new URI("/api/mtd-provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-provinces : Updates an existing mtdProvince.
     *
     * @param mtdProvinceDTO the mtdProvinceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdProvinceDTO,
     * or with status 400 (Bad Request) if the mtdProvinceDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdProvinceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-provinces")
    @Timed
    public ResponseEntity<MtdProvinceDTO> updateMtdProvince(@Valid @RequestBody MtdProvinceDTO mtdProvinceDTO) throws URISyntaxException {
        log.debug("REST request to update MtdProvince : {}", mtdProvinceDTO);
        if (mtdProvinceDTO.getId() == null) {
            return createMtdProvince(mtdProvinceDTO);
        }
        MtdProvinceDTO result = mtdProvinceService.save(mtdProvinceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdProvinceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-provinces : get all the mtdProvinces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdProvinces in body
     */
    @GetMapping("/mtd-provinces")
    @Timed
    public ResponseEntity<List<MtdProvinceDTO>> getAllMtdProvinces(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdProvinces");
        Page<MtdProvinceDTO> page = mtdProvinceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-provinces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-provinces/:id : get the "id" mtdProvince.
     *
     * @param id the id of the mtdProvinceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdProvinceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-provinces/{id}")
    @Timed
    public ResponseEntity<MtdProvinceDTO> getMtdProvince(@PathVariable Long id) {
        log.debug("REST request to get MtdProvince : {}", id);
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdProvinceDTO));
    }

    /**
     * DELETE  /mtd-provinces/:id : delete the "id" mtdProvince.
     *
     * @param id the id of the mtdProvinceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-provinces/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdProvince(@PathVariable Long id) {
        log.debug("REST request to delete MtdProvince : {}", id);
        mtdProvinceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-provinces?query=:query : search for the mtdProvince corresponding
     * to the query.
     *
     * @param query the query of the mtdProvince search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-provinces")
    @Timed
    public ResponseEntity<List<MtdProvinceDTO>> searchMtdProvinces(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdProvinces for query {}", query);
        Page<MtdProvinceDTO> page = mtdProvinceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-provinces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /mtd-districts : get all the mtdDistricts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdDistricts in body
     */
    @GetMapping("/mtd-provinces/get-by-country-id/{idCountry}")
    @Timed
    public ResponseEntity<List<MtdProvinceDTO>> getAllMtdProvincesByCountryId(@PathVariable Long idCountry, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdDistricts");
        Page<MtdProvinceDTO> page = mtdProvinceService.getProvinceByCountry(idCountry, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-districts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
