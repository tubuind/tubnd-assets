package com.astcore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.astcore.service.CifDeviceService;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.astcore.service.dto.CifDeviceDTO;
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
 * REST controller for managing CifDevice.
 */
@RestController
@RequestMapping("/api")
public class CifDeviceResource {

    private final Logger log = LoggerFactory.getLogger(CifDeviceResource.class);

    private static final String ENTITY_NAME = "cifDevice";

    private final CifDeviceService cifDeviceService;

    public CifDeviceResource(CifDeviceService cifDeviceService) {
        this.cifDeviceService = cifDeviceService;
    }

    /**
     * POST  /cif-devices : Create a new cifDevice.
     *
     * @param cifDeviceDTO the cifDeviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cifDeviceDTO, or with status 400 (Bad Request) if the cifDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cif-devices")
    @Timed
    public ResponseEntity<CifDeviceDTO> createCifDevice(@Valid @RequestBody CifDeviceDTO cifDeviceDTO) throws URISyntaxException {
        log.debug("REST request to save CifDevice : {}", cifDeviceDTO);
        if (cifDeviceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cifDevice cannot already have an ID")).body(null);
        }
        CifDeviceDTO result = cifDeviceService.save(cifDeviceDTO);
        return ResponseEntity.created(new URI("/api/cif-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cif-devices : Updates an existing cifDevice.
     *
     * @param cifDeviceDTO the cifDeviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cifDeviceDTO,
     * or with status 400 (Bad Request) if the cifDeviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the cifDeviceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cif-devices")
    @Timed
    public ResponseEntity<CifDeviceDTO> updateCifDevice(@Valid @RequestBody CifDeviceDTO cifDeviceDTO) throws URISyntaxException {
        log.debug("REST request to update CifDevice : {}", cifDeviceDTO);
        if (cifDeviceDTO.getId() == null) {
            return createCifDevice(cifDeviceDTO);
        }
        CifDeviceDTO result = cifDeviceService.save(cifDeviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cifDeviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cif-devices : get all the cifDevices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cifDevices in body
     */
    @GetMapping("/cif-devices")
    @Timed
    public ResponseEntity<List<CifDeviceDTO>> getAllCifDevices(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CifDevices");
        Page<CifDeviceDTO> page = cifDeviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cif-devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cif-devices/:id : get the "id" cifDevice.
     *
     * @param id the id of the cifDeviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cifDeviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cif-devices/{id}")
    @Timed
    public ResponseEntity<CifDeviceDTO> getCifDevice(@PathVariable Long id) {
        log.debug("REST request to get CifDevice : {}", id);
        CifDeviceDTO cifDeviceDTO = cifDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cifDeviceDTO));
    }

    /**
     * DELETE  /cif-devices/:id : delete the "id" cifDevice.
     *
     * @param id the id of the cifDeviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cif-devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteCifDevice(@PathVariable Long id) {
        log.debug("REST request to delete CifDevice : {}", id);
        cifDeviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cif-devices?query=:query : search for the cifDevice corresponding
     * to the query.
     *
     * @param query the query of the cifDevice search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cif-devices")
    @Timed
    public ResponseEntity<List<CifDeviceDTO>> searchCifDevices(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CifDevices for query {}", query);
        Page<CifDeviceDTO> page = cifDeviceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cif-devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
