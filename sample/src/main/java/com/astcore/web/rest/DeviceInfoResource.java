package com.astcore.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.astcore.service.DeviceInfoService;
import com.astcore.service.dto.DeviceInfoDTO;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing DeviceInfo.
 */
@RestController
@RequestMapping("/api")
public class DeviceInfoResource {

    private final Logger log = LoggerFactory.getLogger(DeviceInfoResource.class);

    private static final String ENTITY_NAME = "deviceInfo";

    private final DeviceInfoService deviceInfoService;

    public DeviceInfoResource(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    /**
     * POST  /device-infos : Create a new deviceInfo.
     *
     * @param deviceInfoDTO the deviceInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceInfoDTO, or with status 400 (Bad Request) if the deviceInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-infos")
    @Timed
    public ResponseEntity<DeviceInfoDTO> createDeviceInfo(@Valid @RequestBody DeviceInfoDTO deviceInfoDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceInfo : {}", deviceInfoDTO);
        if (deviceInfoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deviceInfo cannot already have an ID")).body(null);
        }
        DeviceInfoDTO result = deviceInfoService.save(deviceInfoDTO);
        return ResponseEntity.created(new URI("/api/device-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /device-infos : Updates an existing deviceInfo.
     *
     * @param deviceInfoDTO the deviceInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceInfoDTO,
     * or with status 400 (Bad Request) if the deviceInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the deviceInfoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/device-infos")
    @Timed
    public ResponseEntity<DeviceInfoDTO> updateDeviceInfo(@Valid @RequestBody DeviceInfoDTO deviceInfoDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceInfo : {}", deviceInfoDTO);
        if (deviceInfoDTO.getId() == null) {
            return createDeviceInfo(deviceInfoDTO);
        }
        DeviceInfoDTO result = deviceInfoService.save(deviceInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deviceInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /device-infos : get all the deviceInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deviceInfos in body
     */
    @GetMapping("/device-infos")
    @Timed
    public ResponseEntity<List<DeviceInfoDTO>> getAllDeviceInfos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DeviceInfos");
        Page<DeviceInfoDTO> page = deviceInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/device-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /device-infos/:id : get the "id" deviceInfo.
     *
     * @param id the id of the deviceInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/device-infos/{id}")
    @Timed
    public ResponseEntity<DeviceInfoDTO> getDeviceInfo(@PathVariable Long id) {
        log.debug("REST request to get DeviceInfo : {}", id);
        DeviceInfoDTO deviceInfoDTO = deviceInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceInfoDTO));
    }

    /**
     * DELETE  /device-infos/:id : delete the "id" deviceInfo.
     *
     * @param id the id of the deviceInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/device-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeviceInfo(@PathVariable Long id) {
        log.debug("REST request to delete DeviceInfo : {}", id);
        deviceInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/device-infos?query=:query : search for the deviceInfo corresponding
     * to the query.
     *
     * @param query the query of the deviceInfo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/device-infos")
    @Timed
    public ResponseEntity<List<DeviceInfoDTO>> searchDeviceInfos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DeviceInfos for query {}", query);
        Page<DeviceInfoDTO> page = deviceInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/device-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /devices/user-loged : get all the devices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/device-infos/user-loged")
    @Timed
    public ResponseEntity<List<DeviceInfoDTO>> getDevicesByUserLoged(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Devices getDevicesByUserLoged");
        Page<DeviceInfoDTO> page = deviceInfoService.getDevicesByUserLoged(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
