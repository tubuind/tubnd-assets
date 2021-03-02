package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.DeviceTransaction;
import com.astcore.repository.DeviceTransactionRepository;
import com.astcore.service.DeviceTransactionService;
import com.astcore.repository.search.DeviceTransactionSearchRepository;
import com.astcore.service.dto.DeviceTransactionDTO;
import com.astcore.service.mapper.DeviceTransactionMapper;
import com.astcore.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceTransactionResource REST controller.
 *
 * @see DeviceTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class DeviceTransactionResourceIntTest {

    private static final LocalDate DEFAULT_TRANSDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEVICECODE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICECODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CURRENTVALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENTVALUE = new BigDecimal(2);

    private static final LocalDate DEFAULT_VALUEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALUEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ACTIVE = 0;
    private static final Integer UPDATED_ACTIVE = 1;

    private static final Integer DEFAULT_ISDEL = 0;
    private static final Integer UPDATED_ISDEL = 1;

    private static final String DEFAULT_CREATEBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LASTMODIFYBY = "AAAAAAAAAA";
    private static final String UPDATED_LASTMODIFYBY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LASTMODIFYDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTMODIFYDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeviceTransactionRepository deviceTransactionRepository;

    @Autowired
    private DeviceTransactionMapper deviceTransactionMapper;

    @Autowired
    private DeviceTransactionService deviceTransactionService;

    @Autowired
    private DeviceTransactionSearchRepository deviceTransactionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceTransactionMockMvc;

    private DeviceTransaction deviceTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceTransactionResource deviceTransactionResource = new DeviceTransactionResource(deviceTransactionService);
        this.restDeviceTransactionMockMvc = MockMvcBuilders.standaloneSetup(deviceTransactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceTransaction createEntity(EntityManager em) {
        DeviceTransaction deviceTransaction = new DeviceTransaction()
            .transdate(DEFAULT_TRANSDATE)
            .devicecode(DEFAULT_DEVICECODE)
            .currentvalue(DEFAULT_CURRENTVALUE)
            .valuedate(DEFAULT_VALUEDATE)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return deviceTransaction;
    }

    @Before
    public void initTest() {
        deviceTransactionSearchRepository.deleteAll();
        deviceTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceTransaction() throws Exception {
        int databaseSizeBeforeCreate = deviceTransactionRepository.findAll().size();

        // Create the DeviceTransaction
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(deviceTransaction);
        restDeviceTransactionMockMvc.perform(post("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceTransaction in the database
        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceTransaction testDeviceTransaction = deviceTransactionList.get(deviceTransactionList.size() - 1);
        assertThat(testDeviceTransaction.getTransdate()).isEqualTo(DEFAULT_TRANSDATE);
        assertThat(testDeviceTransaction.getDevicecode()).isEqualTo(DEFAULT_DEVICECODE);
        assertThat(testDeviceTransaction.getCurrentvalue()).isEqualTo(DEFAULT_CURRENTVALUE);
        assertThat(testDeviceTransaction.getValuedate()).isEqualTo(DEFAULT_VALUEDATE);
        assertThat(testDeviceTransaction.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testDeviceTransaction.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testDeviceTransaction.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testDeviceTransaction.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testDeviceTransaction.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testDeviceTransaction.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the DeviceTransaction in Elasticsearch
        DeviceTransaction deviceTransactionEs = deviceTransactionSearchRepository.findOne(testDeviceTransaction.getId());
        assertThat(deviceTransactionEs).isEqualToComparingFieldByField(testDeviceTransaction);
    }

    @Test
    @Transactional
    public void createDeviceTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceTransactionRepository.findAll().size();

        // Create the DeviceTransaction with an existing ID
        deviceTransaction.setId(1L);
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(deviceTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceTransactionMockMvc.perform(post("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDevicecodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTransactionRepository.findAll().size();
        // set the field null
        deviceTransaction.setDevicecode(null);

        // Create the DeviceTransaction, which fails.
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(deviceTransaction);

        restDeviceTransactionMockMvc.perform(post("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTransactionRepository.findAll().size();
        // set the field null
        deviceTransaction.setActive(null);

        // Create the DeviceTransaction, which fails.
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(deviceTransaction);

        restDeviceTransactionMockMvc.perform(post("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTransactionRepository.findAll().size();
        // set the field null
        deviceTransaction.setIsdel(null);

        // Create the DeviceTransaction, which fails.
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(deviceTransaction);

        restDeviceTransactionMockMvc.perform(post("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceTransactions() throws Exception {
        // Initialize the database
        deviceTransactionRepository.saveAndFlush(deviceTransaction);

        // Get all the deviceTransactionList
        restDeviceTransactionMockMvc.perform(get("/api/device-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transdate").value(hasItem(DEFAULT_TRANSDATE.toString())))
            .andExpect(jsonPath("$.[*].devicecode").value(hasItem(DEFAULT_DEVICECODE.toString())))
            .andExpect(jsonPath("$.[*].currentvalue").value(hasItem(DEFAULT_CURRENTVALUE.intValue())))
            .andExpect(jsonPath("$.[*].valuedate").value(hasItem(DEFAULT_VALUEDATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getDeviceTransaction() throws Exception {
        // Initialize the database
        deviceTransactionRepository.saveAndFlush(deviceTransaction);

        // Get the deviceTransaction
        restDeviceTransactionMockMvc.perform(get("/api/device-transactions/{id}", deviceTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transdate").value(DEFAULT_TRANSDATE.toString()))
            .andExpect(jsonPath("$.devicecode").value(DEFAULT_DEVICECODE.toString()))
            .andExpect(jsonPath("$.currentvalue").value(DEFAULT_CURRENTVALUE.intValue()))
            .andExpect(jsonPath("$.valuedate").value(DEFAULT_VALUEDATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceTransaction() throws Exception {
        // Get the deviceTransaction
        restDeviceTransactionMockMvc.perform(get("/api/device-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceTransaction() throws Exception {
        // Initialize the database
        deviceTransactionRepository.saveAndFlush(deviceTransaction);
        deviceTransactionSearchRepository.save(deviceTransaction);
        int databaseSizeBeforeUpdate = deviceTransactionRepository.findAll().size();

        // Update the deviceTransaction
        DeviceTransaction updatedDeviceTransaction = deviceTransactionRepository.findOne(deviceTransaction.getId());
        updatedDeviceTransaction
            .transdate(UPDATED_TRANSDATE)
            .devicecode(UPDATED_DEVICECODE)
            .currentvalue(UPDATED_CURRENTVALUE)
            .valuedate(UPDATED_VALUEDATE)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(updatedDeviceTransaction);

        restDeviceTransactionMockMvc.perform(put("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceTransaction in the database
        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeUpdate);
        DeviceTransaction testDeviceTransaction = deviceTransactionList.get(deviceTransactionList.size() - 1);
        assertThat(testDeviceTransaction.getTransdate()).isEqualTo(UPDATED_TRANSDATE);
        assertThat(testDeviceTransaction.getDevicecode()).isEqualTo(UPDATED_DEVICECODE);
        assertThat(testDeviceTransaction.getCurrentvalue()).isEqualTo(UPDATED_CURRENTVALUE);
        assertThat(testDeviceTransaction.getValuedate()).isEqualTo(UPDATED_VALUEDATE);
        assertThat(testDeviceTransaction.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDeviceTransaction.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testDeviceTransaction.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testDeviceTransaction.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testDeviceTransaction.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testDeviceTransaction.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the DeviceTransaction in Elasticsearch
        DeviceTransaction deviceTransactionEs = deviceTransactionSearchRepository.findOne(testDeviceTransaction.getId());
        assertThat(deviceTransactionEs).isEqualToComparingFieldByField(testDeviceTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceTransaction() throws Exception {
        int databaseSizeBeforeUpdate = deviceTransactionRepository.findAll().size();

        // Create the DeviceTransaction
        DeviceTransactionDTO deviceTransactionDTO = deviceTransactionMapper.toDto(deviceTransaction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceTransactionMockMvc.perform(put("/api/device-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceTransaction in the database
        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeviceTransaction() throws Exception {
        // Initialize the database
        deviceTransactionRepository.saveAndFlush(deviceTransaction);
        deviceTransactionSearchRepository.save(deviceTransaction);
        int databaseSizeBeforeDelete = deviceTransactionRepository.findAll().size();

        // Get the deviceTransaction
        restDeviceTransactionMockMvc.perform(delete("/api/device-transactions/{id}", deviceTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean deviceTransactionExistsInEs = deviceTransactionSearchRepository.exists(deviceTransaction.getId());
        assertThat(deviceTransactionExistsInEs).isFalse();

        // Validate the database is empty
        List<DeviceTransaction> deviceTransactionList = deviceTransactionRepository.findAll();
        assertThat(deviceTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDeviceTransaction() throws Exception {
        // Initialize the database
        deviceTransactionRepository.saveAndFlush(deviceTransaction);
        deviceTransactionSearchRepository.save(deviceTransaction);

        // Search the deviceTransaction
        restDeviceTransactionMockMvc.perform(get("/api/_search/device-transactions?query=id:" + deviceTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transdate").value(hasItem(DEFAULT_TRANSDATE.toString())))
            .andExpect(jsonPath("$.[*].devicecode").value(hasItem(DEFAULT_DEVICECODE.toString())))
            .andExpect(jsonPath("$.[*].currentvalue").value(hasItem(DEFAULT_CURRENTVALUE.intValue())))
            .andExpect(jsonPath("$.[*].valuedate").value(hasItem(DEFAULT_VALUEDATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceTransaction.class);
        DeviceTransaction deviceTransaction1 = new DeviceTransaction();
        deviceTransaction1.setId(1L);
        DeviceTransaction deviceTransaction2 = new DeviceTransaction();
        deviceTransaction2.setId(deviceTransaction1.getId());
        assertThat(deviceTransaction1).isEqualTo(deviceTransaction2);
        deviceTransaction2.setId(2L);
        assertThat(deviceTransaction1).isNotEqualTo(deviceTransaction2);
        deviceTransaction1.setId(null);
        assertThat(deviceTransaction1).isNotEqualTo(deviceTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceTransactionDTO.class);
        DeviceTransactionDTO deviceTransactionDTO1 = new DeviceTransactionDTO();
        deviceTransactionDTO1.setId(1L);
        DeviceTransactionDTO deviceTransactionDTO2 = new DeviceTransactionDTO();
        assertThat(deviceTransactionDTO1).isNotEqualTo(deviceTransactionDTO2);
        deviceTransactionDTO2.setId(deviceTransactionDTO1.getId());
        assertThat(deviceTransactionDTO1).isEqualTo(deviceTransactionDTO2);
        deviceTransactionDTO2.setId(2L);
        assertThat(deviceTransactionDTO1).isNotEqualTo(deviceTransactionDTO2);
        deviceTransactionDTO1.setId(null);
        assertThat(deviceTransactionDTO1).isNotEqualTo(deviceTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deviceTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deviceTransactionMapper.fromId(null)).isNull();
    }
}
