package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.MtdEcosectorsService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.MtdEcosectorsDTO;
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
 * REST controller for managing MtdEcosectors.
 */
@RestController
@RequestMapping("/api")
public class MtdEcosectorsResource {

    private final Logger log = LoggerFactory.getLogger(MtdEcosectorsResource.class);

    private static final String ENTITY_NAME = "mtdEcosectors";

    private final MtdEcosectorsService mtdEcosectorsService;

    public MtdEcosectorsResource(MtdEcosectorsService mtdEcosectorsService) {
        this.mtdEcosectorsService = mtdEcosectorsService;
    }

    /**
     * POST  /mtd-ecosectors : Create a new mtdEcosectors.
     *
     * @param mtdEcosectorsDTO the mtdEcosectorsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mtdEcosectorsDTO, or with status 400 (Bad Request) if the mtdEcosectors has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mtd-ecosectors")
    @Timed
    public ResponseEntity<MtdEcosectorsDTO> createMtdEcosectors(@Valid @RequestBody MtdEcosectorsDTO mtdEcosectorsDTO) throws URISyntaxException {
        log.debug("REST request to save MtdEcosectors : {}", mtdEcosectorsDTO);
        if (mtdEcosectorsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mtdEcosectors cannot already have an ID")).body(null);
        }
        MtdEcosectorsDTO result = mtdEcosectorsService.save(mtdEcosectorsDTO);
        return ResponseEntity.created(new URI("/api/mtd-ecosectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mtd-ecosectors : Updates an existing mtdEcosectors.
     *
     * @param mtdEcosectorsDTO the mtdEcosectorsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mtdEcosectorsDTO,
     * or with status 400 (Bad Request) if the mtdEcosectorsDTO is not valid,
     * or with status 500 (Internal Server Error) if the mtdEcosectorsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mtd-ecosectors")
    @Timed
    public ResponseEntity<MtdEcosectorsDTO> updateMtdEcosectors(@Valid @RequestBody MtdEcosectorsDTO mtdEcosectorsDTO) throws URISyntaxException {
        log.debug("REST request to update MtdEcosectors : {}", mtdEcosectorsDTO);
        if (mtdEcosectorsDTO.getId() == null) {
            return createMtdEcosectors(mtdEcosectorsDTO);
        }
        MtdEcosectorsDTO result = mtdEcosectorsService.save(mtdEcosectorsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mtdEcosectorsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mtd-ecosectors : get all the mtdEcosectors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mtdEcosectors in body
     */
    @GetMapping("/mtd-ecosectors")
    @Timed
    public ResponseEntity<List<MtdEcosectorsDTO>> getAllMtdEcosectors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MtdEcosectors");
        Page<MtdEcosectorsDTO> page = mtdEcosectorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mtd-ecosectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mtd-ecosectors/:id : get the "id" mtdEcosectors.
     *
     * @param id the id of the mtdEcosectorsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mtdEcosectorsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mtd-ecosectors/{id}")
    @Timed
    public ResponseEntity<MtdEcosectorsDTO> getMtdEcosectors(@PathVariable Long id) {
        log.debug("REST request to get MtdEcosectors : {}", id);
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mtdEcosectorsDTO));
    }

    /**
     * DELETE  /mtd-ecosectors/:id : delete the "id" mtdEcosectors.
     *
     * @param id the id of the mtdEcosectorsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mtd-ecosectors/{id}")
    @Timed
    public ResponseEntity<Void> deleteMtdEcosectors(@PathVariable Long id) {
        log.debug("REST request to delete MtdEcosectors : {}", id);
        mtdEcosectorsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mtd-ecosectors?query=:query : search for the mtdEcosectors corresponding
     * to the query.
     *
     * @param query the query of the mtdEcosectors search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mtd-ecosectors")
    @Timed
    public ResponseEntity<List<MtdEcosectorsDTO>> searchMtdEcosectors(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MtdEcosectors for query {}", query);
        Page<MtdEcosectorsDTO> page = mtdEcosectorsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders((query != null) ? query : "", page, "/api/_search/mtd-ecosectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
