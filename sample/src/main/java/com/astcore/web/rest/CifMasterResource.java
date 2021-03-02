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

import com.astcore.service.CifMasterService;
import com.astcore.service.dto.CifMasterDTO;
import com.astcore.web.rest.util.HeaderUtil;
import com.astcore.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing CifMaster.
 */
@RestController
@RequestMapping("/api")
public class CifMasterResource {

    private final Logger log = LoggerFactory.getLogger(CifMasterResource.class);

    private static final String ENTITY_NAME = "cifMaster";

    private final CifMasterService cifMasterService;

    public CifMasterResource(CifMasterService cifMasterService) {
        this.cifMasterService = cifMasterService;
    }

    /**
     * POST  /cif-masters : Create a new cifMaster.
     *
     * @param cifMasterDTO the cifMasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cifMasterDTO, or with status 400 (Bad Request) if the cifMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cif-masters")
    @Timed
    public ResponseEntity<CifMasterDTO> createCifMaster(@Valid @RequestBody CifMasterDTO cifMasterDTO) throws URISyntaxException {
        log.debug("REST request to save CifMaster : {}", cifMasterDTO);
        if (cifMasterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cifMaster cannot already have an ID")).body(null);
        }
        CifMasterDTO result = cifMasterService.save(cifMasterDTO);
        return ResponseEntity.created(new URI("/api/cif-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cif-masters : Updates an existing cifMaster.
     *
     * @param cifMasterDTO the cifMasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cifMasterDTO,
     * or with status 400 (Bad Request) if the cifMasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the cifMasterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cif-masters")
    @Timed
    public ResponseEntity<CifMasterDTO> updateCifMaster(@Valid @RequestBody CifMasterDTO cifMasterDTO) throws URISyntaxException {
        log.debug("REST request to update CifMaster : {}", cifMasterDTO);
        if (cifMasterDTO.getId() == null) {
            return createCifMaster(cifMasterDTO);
        }
        CifMasterDTO result = cifMasterService.save(cifMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cifMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cif-masters : get all the cifMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cifMasters in body
     */
    @GetMapping("/cif-masters")
    @Timed
    public ResponseEntity<List<CifMasterDTO>> getAllCifMasters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CifMasters");
        Page<CifMasterDTO> page = cifMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cif-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cif-masters/:id : get the "id" cifMaster.
     *
     * @param id the id of the cifMasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cifMasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cif-masters/{id}")
    @Timed
    public ResponseEntity<CifMasterDTO> getCifMaster(@PathVariable Long id) {
        log.debug("REST request to get CifMaster : {}", id);
        CifMasterDTO cifMasterDTO = cifMasterService.findOne(id);
        if(cifMasterDTO.getCustparents() != null) {
        	CifMasterDTO cifMasterParentsDTO = cifMasterService.findOne(cifMasterDTO.getCustparents().longValue());
        	cifMasterDTO.setCustparentsName(cifMasterParentsDTO == null ? "" : cifMasterParentsDTO.getCustomername());
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cifMasterDTO));
    }

    /**
     * DELETE  /cif-masters/:id : delete the "id" cifMaster.
     *
     * @param id the id of the cifMasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cif-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCifMaster(@PathVariable Long id) {
        log.debug("REST request to delete CifMaster : {}", id);
        cifMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cif-masters?query=:query : search for the cifMaster corresponding
     * to the query.
     *
     * @param query the query of the cifMaster search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cif-masters")
    @Timed
    public ResponseEntity<List<CifMasterDTO>> searchCifMasters(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CifMasters for query {}", query);
        Page<CifMasterDTO> page = cifMasterService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cif-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /customers/user-loged : get the "id" customer.
     *
     * @param id the id of the customerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cif-masters/user-loged")
    @Timed
    public ResponseEntity<CifMasterDTO> getCustomerByUserLoged() {
    	CifMasterDTO cifMasterDTO = cifMasterService.findOneByUserLoged();
    	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cifMasterDTO));
    }

}
