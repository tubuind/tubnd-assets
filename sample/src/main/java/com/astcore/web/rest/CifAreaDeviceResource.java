package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.CifAreaDeviceService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.CifAreaDeviceDTO;
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
 * REST controller for managing CifAreaDevice.
 */
@RestController
@RequestMapping("/api")
public class CifAreaDeviceResource {

    private final Logger log = LoggerFactory.getLogger(CifAreaDeviceResource.class);

    private static final String ENTITY_NAME = "cifAreaDevice";

    private final CifAreaDeviceService cifAreaDeviceService;

    public CifAreaDeviceResource(CifAreaDeviceService cifAreaDeviceService) {
        this.cifAreaDeviceService = cifAreaDeviceService;
    }

    /**
     * POST  /cif-area-devices : Create a new cifAreaDevice.
     *
     * @param cifAreaDeviceDTO the cifAreaDeviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cifAreaDeviceDTO, or with status 400 (Bad Request) if the cifAreaDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cif-area-devices")
    @Timed
    public ResponseEntity<CifAreaDeviceDTO> createCifAreaDevice(@Valid @RequestBody CifAreaDeviceDTO cifAreaDeviceDTO) throws URISyntaxException {
        log.debug("REST request to save CifAreaDevice : {}", cifAreaDeviceDTO);
        if (cifAreaDeviceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cifAreaDevice cannot already have an ID")).body(null);
        }
        CifAreaDeviceDTO result = cifAreaDeviceService.save(cifAreaDeviceDTO);
        return ResponseEntity.created(new URI("/api/cif-area-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cif-area-devices : Updates an existing cifAreaDevice.
     *
     * @param cifAreaDeviceDTO the cifAreaDeviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cifAreaDeviceDTO,
     * or with status 400 (Bad Request) if the cifAreaDeviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the cifAreaDeviceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cif-area-devices")
    @Timed
    public ResponseEntity<CifAreaDeviceDTO> updateCifAreaDevice(@Valid @RequestBody CifAreaDeviceDTO cifAreaDeviceDTO) throws URISyntaxException {
        log.debug("REST request to update CifAreaDevice : {}", cifAreaDeviceDTO);
        if (cifAreaDeviceDTO.getId() == null) {
            return createCifAreaDevice(cifAreaDeviceDTO);
        }
        CifAreaDeviceDTO result = cifAreaDeviceService.save(cifAreaDeviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cifAreaDeviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cif-area-devices : get all the cifAreaDevices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cifAreaDevices in body
     */
    @GetMapping("/cif-area-devices")
    @Timed
    public ResponseEntity<List<CifAreaDeviceDTO>> getAllCifAreaDevices(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CifAreaDevices");
        Page<CifAreaDeviceDTO> page = cifAreaDeviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cif-area-devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cif-area-devices/:id : get the "id" cifAreaDevice.
     *
     * @param id the id of the cifAreaDeviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cifAreaDeviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cif-area-devices/{id}")
    @Timed
    public ResponseEntity<CifAreaDeviceDTO> getCifAreaDevice(@PathVariable Long id) {
        log.debug("REST request to get CifAreaDevice : {}", id);
        CifAreaDeviceDTO cifAreaDeviceDTO = cifAreaDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cifAreaDeviceDTO));
    }

    /**
     * DELETE  /cif-area-devices/:id : delete the "id" cifAreaDevice.
     *
     * @param id the id of the cifAreaDeviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cif-area-devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteCifAreaDevice(@PathVariable Long id) {
        log.debug("REST request to delete CifAreaDevice : {}", id);
        cifAreaDeviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cif-area-devices?query=:query : search for the cifAreaDevice corresponding
     * to the query.
     *
     * @param query the query of the cifAreaDevice search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cif-area-devices")
    @Timed
    public ResponseEntity<List<CifAreaDeviceDTO>> searchCifAreaDevices(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CifAreaDevices for query {}", query);
        Page<CifAreaDeviceDTO> page = cifAreaDeviceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cif-area-devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
