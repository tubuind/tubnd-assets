package com.astcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.astcore.service.dto.DeviceTransactionDTO;
import com.astcore.service.dto.DeviceTransactionDataDTO;
import com.astcore.service.dto.DeviceTransactionDatasDTO;

/**
 * Service Interface for managing DeviceTransaction.
 */
public interface DeviceTransactionService {

    /**
     * Save a deviceTransaction.
     *
     * @param deviceTransactionDTO the entity to save
     * @return the persisted entity
     */
    DeviceTransactionDTO save(DeviceTransactionDTO deviceTransactionDTO);

    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceTransactionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" deviceTransaction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DeviceTransactionDTO findOne(Long id);

    /**
     *  Delete the "id" deviceTransaction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the deviceTransaction corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceTransactionDTO> search(String query, Pageable pageable);
    
    /**
     * Save a deviceTransactionData.
     *
     * @param deviceTransactionDTO the entity to save
     * @return the persisted entity
     */
    boolean saveDeviceData(DeviceTransactionDataDTO deviceTransactionDataDTO);
    
    /**
     * Save a deviceTransactionData.
     *
     * @param deviceTransactionDTO the entity to save
     * @return the persisted entity
     */
    boolean saveDeviceDataMultiType(DeviceTransactionDatasDTO deviceTransactionDatasDTO);
    
    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceTransactionDTO> getDeviceTransactionsByAreaIdTypeUser(Long cifAreaId, String valueType, Pageable pageable);
    
    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceTransactionDTO> getDeviceTransactionsByAreaIdUser(Long cifAreaId, Pageable pageable);
    
    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceTransactionDTO> getDeviceTransactionsByAreaIdDateUser(Long cifAreaId, String fromDate, String toDate, Pageable pageable);
}
