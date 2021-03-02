package com.astcore.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astcore.service.DeviceTransactionService;
import com.astcore.service.dto.DeviceTransactionDTO;
import com.astcore.service.dto.DeviceTransactionDataDTO;
import com.astcore.service.dto.DeviceTransactionDatasDTO;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing DeviceTransaction.
 */
@RestController
@RequestMapping("/api")
public class DeviceTransactionResource {

    private final Logger log = LoggerFactory.getLogger(DeviceTransactionResource.class);

    private static final String ENTITY_NAME = "deviceTransaction";

    private final DeviceTransactionService deviceTransactionService;

    public DeviceTransactionResource(DeviceTransactionService deviceTransactionService) {
        this.deviceTransactionService = deviceTransactionService;
    }

    /**
     * POST  /device-transactions : Create a new deviceTransaction.
     *
     * @param deviceTransactionDTO the deviceTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceTransactionDTO, or with status 400 (Bad Request) if the deviceTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-transactions")
    @Timed
    public ResponseEntity<DeviceTransactionDTO> createDeviceTransaction(@Valid @RequestBody DeviceTransactionDTO deviceTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceTransaction : {}", deviceTransactionDTO);
        if (deviceTransactionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deviceTransaction cannot already have an ID")).body(null);
        }
        DeviceTransactionDTO result = deviceTransactionService.save(deviceTransactionDTO);
        return ResponseEntity.created(new URI("/api/device-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /device-transactions : Updates an existing deviceTransaction.
     *
     * @param deviceTransactionDTO the deviceTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceTransactionDTO,
     * or with status 400 (Bad Request) if the deviceTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the deviceTransactionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/device-transactions")
    @Timed
    public ResponseEntity<DeviceTransactionDTO> updateDeviceTransaction(@Valid @RequestBody DeviceTransactionDTO deviceTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceTransaction : {}", deviceTransactionDTO);
        if (deviceTransactionDTO.getId() == null) {
            return createDeviceTransaction(deviceTransactionDTO);
        }
        DeviceTransactionDTO result = deviceTransactionService.save(deviceTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deviceTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /device-transactions : get all the deviceTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deviceTransactions in body
     */
    @GetMapping("/device-transactions")
    @Timed
    public ResponseEntity<List<DeviceTransactionDTO>> getAllDeviceTransactions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DeviceTransactions");
        Page<DeviceTransactionDTO> page = deviceTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/device-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /device-transactions/:id : get the "id" deviceTransaction.
     *
     * @param id the id of the deviceTransactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceTransactionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/device-transactions/{id}")
    @Timed
    public ResponseEntity<DeviceTransactionDTO> getDeviceTransaction(@PathVariable Long id) {
        log.debug("REST request to get DeviceTransaction : {}", id);
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceTransactionDTO));
    }

    /**
     * DELETE  /device-transactions/:id : delete the "id" deviceTransaction.
     *
     * @param id the id of the deviceTransactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/device-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeviceTransaction(@PathVariable Long id) {
        log.debug("REST request to delete DeviceTransaction : {}", id);
        deviceTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/device-transactions?query=:query : search for the deviceTransaction corresponding
     * to the query.
     *
     * @param query the query of the deviceTransaction search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/device-transactions")
    @Timed
    public ResponseEntity<List<DeviceTransactionDTO>> searchDeviceTransactions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DeviceTransactions for query {}", query);
        Page<DeviceTransactionDTO> page = deviceTransactionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/device-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * POST  /device-transactions : Create a new deviceTransaction.
     *
     * @param deviceTransactionDTO the deviceTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceTransactionDTO, or with status 400 (Bad Request) if the deviceTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-transactions/send-data")
    @Timed
    public ResponseEntity<Void> sendDataDeviceTransaction(@Valid @RequestBody DeviceTransactionDataDTO deviceTransactionDataDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceTransaction : {}", deviceTransactionDataDTO.getData());
        if (deviceTransactionDataDTO.getData() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deviceTransactionData cannot already have data")).body(null);
        }
        boolean result = deviceTransactionService.saveDeviceData(deviceTransactionDataDTO);
        
        return ResponseEntity.ok().build();
    }
    
    /**
     * POST  /device-transactions : Create a new deviceTransaction.
     *
     * @param deviceTransactionDTO the deviceTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceTransactionDTO, or with status 400 (Bad Request) if the deviceTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-transactions/send-data-types")
    @Timed
    public ResponseEntity<Void> sendDataDeviceTransactions(@Valid @RequestBody DeviceTransactionDatasDTO deviceTransactionDatasDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceTransaction : {}", deviceTransactionDatasDTO.getID());
        if (deviceTransactionDatasDTO.getID() == null || deviceTransactionDatasDTO.getValues() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deviceTransactionData cannot already have data")).body(null);
        }
        boolean result = deviceTransactionService.saveDeviceDataMultiType(deviceTransactionDatasDTO);
        
        return ResponseEntity.ok().build();
    }
    
    /**
     * GET  /device-transactions : get all the deviceTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deviceTransactions in body
     */
    @GetMapping("/device-transactions/get-for-chart")
    @Timed
    public ResponseEntity<List<DeviceTransactionDTO>> getAllDeviceTransactionsByAreaIdDateUser(@RequestParam(name="cifAreaId", required=false) Long cifAreaId, @RequestParam(name="fromDate", required=false) String fromDate, @RequestParam(name="toDate", required=false) String toDate, Pageable pageable) {
        log.debug("REST request to get a page of DeviceTransactions");
        Page<DeviceTransactionDTO> page = deviceTransactionService.getDeviceTransactionsByAreaIdDateUser(cifAreaId, fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/device-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
