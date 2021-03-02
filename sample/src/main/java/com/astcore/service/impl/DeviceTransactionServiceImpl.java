package com.astcore.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.config.Constants;
import com.astcore.domain.DeviceInfo;
import com.astcore.domain.DeviceTransaction;
import com.astcore.repository.DeviceInfoRepository;
import com.astcore.repository.DeviceTransactionRepository;
import com.astcore.repository.search.DeviceTransactionSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.DeviceTransactionService;
import com.astcore.service.dto.DeviceTransactionDTO;
import com.astcore.service.dto.DeviceTransactionDataDTO;
import com.astcore.service.dto.DeviceTransactionDatasDTO;
import com.astcore.service.mapper.DeviceTransactionMapper;
import com.astcore.service.util.GeneralUtil;

/**
 * Service Implementation for managing DeviceTransaction.
 */
@Service
@Transactional
public class DeviceTransactionServiceImpl implements DeviceTransactionService{

    private final Logger log = LoggerFactory.getLogger(DeviceTransactionServiceImpl.class);

    private final DeviceTransactionRepository deviceTransactionRepository;

    private final DeviceTransactionMapper deviceTransactionMapper;

    private final DeviceTransactionSearchRepository deviceTransactionSearchRepository;
    
    private final DeviceInfoRepository deviceInfoRepository;

    public DeviceTransactionServiceImpl(DeviceTransactionRepository deviceTransactionRepository, DeviceTransactionMapper deviceTransactionMapper, DeviceTransactionSearchRepository deviceTransactionSearchRepository, DeviceInfoRepository deviceInfoRepository) {
        this.deviceTransactionRepository = deviceTransactionRepository;
        this.deviceTransactionMapper = deviceTransactionMapper;
        this.deviceTransactionSearchRepository = deviceTransactionSearchRepository;
        this.deviceInfoRepository = deviceInfoRepository;
    }

    /**
     * Save a deviceTransaction.
     *
     * @param deviceTransactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeviceTransactionDTO save(DeviceTransactionDTO deviceTransactionDTO) {
        log.debug("Request to save DeviceTransaction : {}", deviceTransactionDTO);
        DeviceTransaction deviceTransaction = deviceTransactionMapper.toEntity(deviceTransactionDTO);
        
      //set UserId, CreateDate
        if (deviceTransaction.getId() == null) {
        	deviceTransaction.setIsdel(0);
        	deviceTransaction.setCreateby(SecurityUtils.getCurrentUserLogin());
        	deviceTransaction.setCreatedate(new Date());
        }
        deviceTransaction.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        deviceTransaction.setLastmodifydate(new Date());
        
        deviceTransaction = deviceTransactionRepository.save(deviceTransaction);
        DeviceTransactionDTO result = deviceTransactionMapper.toDto(deviceTransaction);
        deviceTransactionSearchRepository.save(deviceTransaction);
        return result;
    }

    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceTransactions");
        return deviceTransactionRepository.findAll(pageable)
            .map(deviceTransactionMapper::toDto);
    }

    /**
     *  Get one deviceTransaction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeviceTransactionDTO findOne(Long id) {
        log.debug("Request to get DeviceTransaction : {}", id);
        DeviceTransaction deviceTransaction = deviceTransactionRepository.findOne(id);
        return deviceTransactionMapper.toDto(deviceTransaction);
    }

    /**
     *  Delete the  deviceTransaction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceTransaction : {}", id);
        deviceTransactionRepository.delete(id);
        deviceTransactionSearchRepository.delete(id);
    }

    /**
     * Search for the deviceTransaction corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceTransactionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DeviceTransactions for query {}", query);
        //Page<DeviceTransaction> result = deviceTransactionSearchRepository.search(queryStringQuery(query), pageable);
        Page<DeviceTransaction> result = deviceTransactionRepository.findByDevicecodeLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(deviceTransactionMapper::toDto);
    }
    
    /**
     * Save a deviceTransaction.
     *
     * @param deviceTransactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public boolean saveDeviceData(DeviceTransactionDataDTO deviceTransactionDataDTO) {
        log.debug("Request to save DeviceTransaction : {}", deviceTransactionDataDTO.getData());
        
        List<DeviceTransaction> lstDeviceTransaction = new ArrayList<DeviceTransaction>();
        Date valueDate = null;
        
        //Parse data
        String[] datas = deviceTransactionDataDTO.getData().split(";");
        
        log.debug("length : {}", datas.length);
        
        if (datas != null && datas.length > 0) {
        	for (String item : datas) {
        		
        		log.debug("item : {}", item);
        		
				String[] arrItem = item.split("=");
				if (arrItem.length == 2 && arrItem[0].length() > 0) {
					
					log.debug("arrItem 0 : {}", arrItem[0]);
					log.debug("arrItem 1 : {}", arrItem[1]);
					
					if (!Constants.DEVICE_TRANS_SYMBOL_DATE.equalsIgnoreCase(arrItem[0].trim()) && GeneralUtil.tryConvertStrToDecimal(arrItem[1].trim())) {
						DeviceTransaction itemDeviceTrans = new DeviceTransaction();
						itemDeviceTrans.setDevicecode(arrItem[0]);
						itemDeviceTrans.setCurrentvalue(new BigDecimal(arrItem[1].trim()));
						lstDeviceTransaction.add(itemDeviceTrans);
					} else {
						DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				    	try {
							valueDate = (arrItem[1].trim().length() == 19) ? format.parse(arrItem[1].trim()) : null;
						} catch (ParseException e) {
							log.debug("Date format incorect : {}", arrItem[1]);
						}
					}
				}
			}
        }
        
        //Insert into DB
        if (valueDate != null && lstDeviceTransaction.size() > 0) {
        	
        	log.debug("Date : {}", valueDate);
        	
        	for (DeviceTransaction item : lstDeviceTransaction) {
        		DeviceInfo deviceInfo = deviceInfoRepository.findOneByDevicecode(item.getDevicecode());
        		
        		log.debug("DeviceInfo : {}", deviceInfo);
        		
        		if (deviceInfo != null) {
        			item.setDeviceInfo(deviceInfo);
        			item.setActive(1);
        			item.setIsdel(0);
        			item.setTransdate(new Date());
        			item.setValuedate(valueDate);
        			item.setCreateby(SecurityUtils.getCurrentUserLogin());
        			item.setCreatedate(new Date());
        			
        			deviceTransactionRepository.save(item);
        			
        		}
			}
        }
        
        return true;
    }
    
    /**
     * Save a deviceTransaction.
     *
     * @param deviceTransactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public boolean saveDeviceDataMultiType(DeviceTransactionDatasDTO deviceTransactionDatasDTO) {
        log.debug("Request to save DeviceTransaction : {}", deviceTransactionDatasDTO.getID());
        
        if (deviceTransactionDatasDTO.getID() != null) {
        	DeviceInfo deviceInfo = deviceInfoRepository.findOneByDevicecode(deviceTransactionDatasDTO.getID());
        	
        	if (deviceInfo != null && deviceTransactionDatasDTO.getValues() != null) {
        		//Parse data
                String[] datas = deviceTransactionDatasDTO.getValues().split("\\|");
                
                log.debug("length : {}", datas.length);
                
                if (datas != null && datas.length > 0) {
                	for (String item : datas) {
                		
                		log.debug("item : {}", item);
                		
        				String[] arrItem = item.split("_");
        				if (arrItem.length == 2 && arrItem[0].length() > 0) {
        					
        					log.debug("arrItem 0 : {}", arrItem[0]);
        					log.debug("arrItem 1 : {}", arrItem[1]);
        					
        					Date valueDate = null;
        					DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    				    	try {
    							valueDate = (deviceTransactionDatasDTO.getTMR() != null && deviceTransactionDatasDTO.getTMR().length() == 19) ? format.parse(deviceTransactionDatasDTO.getTMR().trim()) : null;
    						} catch (ParseException e) {
    							log.debug("Date format incorect : {}", arrItem[1]);
    						}
    				    	valueDate = (valueDate != null) ? valueDate : (new Date());
    				    	
    				    	DeviceTransaction itemDeviceTrans = new DeviceTransaction();
    				    	itemDeviceTrans.setDevicecode(deviceTransactionDatasDTO.getID());
    				    	
    				    	itemDeviceTrans.setValuetype(arrItem[0].trim());
    				    	itemDeviceTrans.setCurrentvalue(new BigDecimal(arrItem[1].trim()));
    				    	
    				    	itemDeviceTrans.setDeviceInfo(deviceInfo);
    				    	itemDeviceTrans.setActive(1);
    				    	itemDeviceTrans.setIsdel(0);
    				    	itemDeviceTrans.setTransdate(new Date());
    				    	itemDeviceTrans.setValuedate(valueDate);
    				    	itemDeviceTrans.setCreateby(SecurityUtils.getCurrentUserLogin());
    				    	itemDeviceTrans.setCreatedate(new Date());
    				    	
    				    	deviceTransactionRepository.save(itemDeviceTrans);
        				}
        			}
                }
        	}
        }
        
        return true;
    }
    
    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceTransactionDTO> getDeviceTransactionsByAreaIdTypeUser(Long cifAreaId, String valueType, Pageable pageable) {
        log.debug("Request to get all DeviceTransactions");
        return deviceTransactionRepository.getDeviceTransactionsByAreaIdTypeUser(cifAreaId, valueType, SecurityUtils.getCurrentUserLogin(), pageable)
            .map(deviceTransactionMapper::toDto);
    }
    
    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceTransactionDTO> getDeviceTransactionsByAreaIdUser(Long cifAreaId, Pageable pageable) {
        log.debug("Request to get all DeviceTransactions");
        return deviceTransactionRepository.getDeviceTransactionsByAreaIdUser(cifAreaId, SecurityUtils.getCurrentUserLogin(), pageable)
            .map(deviceTransactionMapper::toDto);
    }
    
    /**
     *  Get all the deviceTransactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceTransactionDTO> getDeviceTransactionsByAreaIdDateUser(Long cifAreaId, String fromDateStr, String toDateStr, Pageable pageable) {
        log.debug("Request to get all DeviceTransactions");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fromDate = null;
        Date toDate = null;
        
        if (fromDateStr != null) {
	    	try {
	    		fromDate = format.parse(fromDateStr.trim());
			} catch (ParseException e) {
				log.debug("Date format incorect : {}", fromDateStr);
			}
        	
        	fromDate = new Date(fromDate.getYear(), fromDate.getMonth(), fromDate.getDate(), 0, 0, 0);
        }
        
        if (toDateStr != null) {
        	try {
        		toDate = format.parse(toDateStr.trim());
			} catch (ParseException e) {
				log.debug("Date format incorect : {}", toDateStr);
			}
        	
        	toDate = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDate(), 23, 59, 59);
        }
        
        return deviceTransactionRepository.getDeviceTransactionsByAreaIdDateUser(cifAreaId, fromDate, toDate, SecurityUtils.getCurrentUserLogin(), pageable)
            .map(deviceTransactionMapper::toDto);
    }
}
