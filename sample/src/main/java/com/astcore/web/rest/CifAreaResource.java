package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.CifAreaService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.CifAreaDTO;
import com.astcore.service.dto.DeviceInfoDTO;

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
 * REST controller for managing CifArea.
 */
@RestController
@RequestMapping("/api")
public class CifAreaResource {

    private final Logger log = LoggerFactory.getLogger(CifAreaResource.class);

    private static final String ENTITY_NAME = "cifArea";

    private final CifAreaService cifAreaService;

    public CifAreaResource(CifAreaService cifAreaService) {
        this.cifAreaService = cifAreaService;
    }

    /**
     * POST  /cif-areas : Create a new cifArea.
     *
     * @param cifAreaDTO the cifAreaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cifAreaDTO, or with status 400 (Bad Request) if the cifArea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cif-areas")
    @Timed
    public ResponseEntity<CifAreaDTO> createCifArea(@Valid @RequestBody CifAreaDTO cifAreaDTO) throws URISyntaxException {
        log.debug("REST request to save CifArea : {}", cifAreaDTO);
        if (cifAreaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cifArea cannot already have an ID")).body(null);
        }
        CifAreaDTO result = cifAreaService.save(cifAreaDTO);
        return ResponseEntity.created(new URI("/api/cif-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cif-areas : Updates an existing cifArea.
     *
     * @param cifAreaDTO the cifAreaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cifAreaDTO,
     * or with status 400 (Bad Request) if the cifAreaDTO is not valid,
     * or with status 500 (Internal Server Error) if the cifAreaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cif-areas")
    @Timed
    public ResponseEntity<CifAreaDTO> updateCifArea(@Valid @RequestBody CifAreaDTO cifAreaDTO) throws URISyntaxException {
        log.debug("REST request to update CifArea : {}", cifAreaDTO);
        if (cifAreaDTO.getId() == null) {
            return createCifArea(cifAreaDTO);
        }
        CifAreaDTO result = cifAreaService.save(cifAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cifAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cif-areas : get all the cifAreas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cifAreas in body
     */
    @GetMapping("/cif-areas")
    @Timed
    public ResponseEntity<List<CifAreaDTO>> getAllCifAreas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CifAreas");
        Page<CifAreaDTO> page = cifAreaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cif-areas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cif-areas/:id : get the "id" cifArea.
     *
     * @param id the id of the cifAreaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cifAreaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cif-areas/{id}")
    @Timed
    public ResponseEntity<CifAreaDTO> getCifArea(@PathVariable Long id) {
        log.debug("REST request to get CifArea : {}", id);
        CifAreaDTO cifAreaDTO = cifAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cifAreaDTO));
    }

    /**
     * DELETE  /cif-areas/:id : delete the "id" cifArea.
     *
     * @param id the id of the cifAreaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cif-areas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCifArea(@PathVariable Long id) {
        log.debug("REST request to delete CifArea : {}", id);
        cifAreaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cif-areas?query=:query : search for the cifArea corresponding
     * to the query.
     *
     * @param query the query of the cifArea search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cif-areas")
    @Timed
    public ResponseEntity<List<CifAreaDTO>> searchCifAreas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CifAreas for query {}", query);
        Page<CifAreaDTO> page = cifAreaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cif-areas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /devices/user-loged : get all the devices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cif-areas/user-loged")
    @Timed
    public ResponseEntity<List<CifAreaDTO>> getDevicesByUserLoged(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Devices getDevicesByUserLoged");
        Page<CifAreaDTO> page = cifAreaService.getAreasByUserLoged(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
