package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdCountryService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdCountryDTO;
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
 * REST controller for managing MtdCountry.
 */
@RestController
@RequestMapping("/api")
public class MtdCountryResource {

    private final Logger log = LoggerFactory.getLogger(MtdCountryResource.class);

    private static final String ENTITY_NAME = "mtdCountry";

    private final MtdCountryService mtdCountryService;

    public MtdCountryResource(MtdCountryService mtdCountryService) {
        this.mtdCountryService = mtdCountryService;
    }

    /**
     * POST  /mtd-countries : Create a new mtdCountry.
     *
     * @param mtdCountryDTO the mtdCountryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdCountryDTO, or with status 400 (Bad Request) if the mtdCountry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-countries")
    @Timed
    public ResponseEntity<MtdCountryDTO> createMtdCountry(@Valid @RequestBody MtdCountryDTO mtdCountryDTO) throws URISyntaxException {
        log.debug("REST request to save MtdCountry : {}", mtdCountryDTO);
        if (mtdCountryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdCountry cannot already have an ID")).body(null);
        }
        MtdCountryDTO result = mtdCountryService.save(mtdCountryDTO);
        return ResponseEntity.created(new URI("/api/mtd-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-countries : Updates an existing mtdCountry.
     *
     * @param mtdCountryDTO the mtdCountryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdCountryDTO,
     * or with status 400 (Bad Request) if the mtdCountryDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdCountryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-countries")
    @Timed
    public ResponseEntity<MtdCountryDTO> updateMtdCountry(@Valid @RequestBody MtdCountryDTO mtdCountryDTO) throws URISyntaxException {
        log.debug("REST request to update MtdCountry : {}", mtdCountryDTO);
        if (mtdCountryDTO.getId() == null) {
            return createMtdCountry(mtdCountryDTO);
        }
        MtdCountryDTO result = mtdCountryService.save(mtdCountryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdCountryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-countries : get all the mtdCountries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdCountries in body
     */
    @GetMapping("/mtd-countries")
    @Timed
    public ResponseEntity<List<MtdCountryDTO>> getAllMtdCountries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdCountries");
        Page<MtdCountryDTO> page = mtdCountryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-countries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-countries/:id : get the "id" mtdCountry.
     *
     * @param id the id of the mtdCountryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdCountryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-countries/{id}")
    @Timed
    public ResponseEntity<MtdCountryDTO> getMtdCountry(@PathVariable Long id) {
        log.debug("REST request to get MtdCountry : {}", id);
        MtdCountryDTO mtdCountryDTO = mtdCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdCountryDTO));
    }

    /**
     * DELETE  /mtd-countries/:id : delete the "id" mtdCountry.
     *
     * @param id the id of the mtdCountryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-countries/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdCountry(@PathVariable Long id) {
        log.debug("REST request to delete MtdCountry : {}", id);
        mtdCountryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-countries?query=:query : search for the mtdCountry corresponding
     * to the query.
     *
     * @param query the query of the mtdCountry search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-countries")
    @Timed
    public ResponseEntity<List<MtdCountryDTO>> searchMtdCountries(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdCountries for query {}", query);
        Page<MtdCountryDTO> page = mtdCountryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mtd-countries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
