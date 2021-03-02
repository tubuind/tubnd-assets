package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.CifDevice;
import com.astcore.repository.CifDeviceRepository;
import com.astcore.service.CifDeviceService;
import com.astcore.repository.search.CifDeviceSearchRepository;
import com.astcore.service.dto.CifDeviceDTO;
import com.astcore.service.mapper.CifDeviceMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CifDeviceResource REST controller.
 *
 * @see CifDeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class CifDeviceResourceIntTest {

    private static final Integer DEFAULT_ACTIONTYPE = 0;
    private static final Integer UPDATED_ACTIONTYPE = 1;

    private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDDATE = LocalDate.now(ZoneId.systemDefault());

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
    private CifDeviceRepository cifDeviceRepository;

    @Autowired
    private CifDeviceMapper cifDeviceMapper;

    @Autowired
    private CifDeviceService cifDeviceService;

    @Autowired
    private CifDeviceSearchRepository cifDeviceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCifDeviceMockMvc;

    private CifDevice cifDevice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CifDeviceResource cifDeviceResource = new CifDeviceResource(cifDeviceService);
        this.restCifDeviceMockMvc = MockMvcBuilders.standaloneSetup(cifDeviceResource)
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
    public static CifDevice createEntity(EntityManager em) {
        CifDevice cifDevice = new CifDevice()
            .actiontype(DEFAULT_ACTIONTYPE)
            .startdate(DEFAULT_STARTDATE)
            .enddate(DEFAULT_ENDDATE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return cifDevice;
    }

    @Before
    public void initTest() {
        cifDeviceSearchRepository.deleteAll();
        cifDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCifDevice() throws Exception {
        int databaseSizeBeforeCreate = cifDeviceRepository.findAll().size();

        // Create the CifDevice
        CifDeviceDTO cifDeviceDTO = cifDeviceMapper.toDto(cifDevice);
        restCifDeviceMockMvc.perform(post("/api/cif-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the CifDevice in the database
        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        CifDevice testCifDevice = cifDeviceList.get(cifDeviceList.size() - 1);
        assertThat(testCifDevice.getActiontype()).isEqualTo(DEFAULT_ACTIONTYPE);
        assertThat(testCifDevice.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testCifDevice.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testCifDevice.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testCifDevice.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testCifDevice.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testCifDevice.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testCifDevice.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the CifDevice in Elasticsearch
        CifDevice cifDeviceEs = cifDeviceSearchRepository.findOne(testCifDevice.getId());
        assertThat(cifDeviceEs).isEqualToComparingFieldByField(testCifDevice);
    }

    @Test
    @Transactional
    public void createCifDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cifDeviceRepository.findAll().size();

        // Create the CifDevice with an existing ID
        cifDevice.setId(1L);
        CifDeviceDTO cifDeviceDTO = cifDeviceMapper.toDto(cifDevice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCifDeviceMockMvc.perform(post("/api/cif-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifDeviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActiontypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifDeviceRepository.findAll().size();
        // set the field null
        cifDevice.setActiontype(null);

        // Create the CifDevice, which fails.
        CifDeviceDTO cifDeviceDTO = cifDeviceMapper.toDto(cifDevice);

        restCifDeviceMockMvc.perform(post("/api/cif-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifDeviceDTO)))
            .andExpect(status().isBadRequest());

        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifDeviceRepository.findAll().size();
        // set the field null
        cifDevice.setIsdel(null);

        // Create the CifDevice, which fails.
        CifDeviceDTO cifDeviceDTO = cifDeviceMapper.toDto(cifDevice);

        restCifDeviceMockMvc.perform(post("/api/cif-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifDeviceDTO)))
            .andExpect(status().isBadRequest());

        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCifDevices() throws Exception {
        // Initialize the database
        cifDeviceRepository.saveAndFlush(cifDevice);

        // Get all the cifDeviceList
        restCifDeviceMockMvc.perform(get("/api/cif-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].actiontype").value(hasItem(DEFAULT_ACTIONTYPE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getCifDevice() throws Exception {
        // Initialize the database
        cifDeviceRepository.saveAndFlush(cifDevice);

        // Get the cifDevice
        restCifDeviceMockMvc.perform(get("/api/cif-devices/{id}", cifDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cifDevice.getId().intValue()))
            .andExpect(jsonPath("$.actiontype").value(DEFAULT_ACTIONTYPE))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCifDevice() throws Exception {
        // Get the cifDevice
        restCifDeviceMockMvc.perform(get("/api/cif-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCifDevice() throws Exception {
        // Initialize the database
        cifDeviceRepository.saveAndFlush(cifDevice);
        cifDeviceSearchRepository.save(cifDevice);
        int databaseSizeBeforeUpdate = cifDeviceRepository.findAll().size();

        // Update the cifDevice
        CifDevice updatedCifDevice = cifDeviceRepository.findOne(cifDevice.getId());
        updatedCifDevice
            .actiontype(UPDATED_ACTIONTYPE)
            .startdate(UPDATED_STARTDATE)
            .enddate(UPDATED_ENDDATE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        CifDeviceDTO cifDeviceDTO = cifDeviceMapper.toDto(updatedCifDevice);

        restCifDeviceMockMvc.perform(put("/api/cif-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifDeviceDTO)))
            .andExpect(status().isOk());

        // Validate the CifDevice in the database
        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeUpdate);
        CifDevice testCifDevice = cifDeviceList.get(cifDeviceList.size() - 1);
        assertThat(testCifDevice.getActiontype()).isEqualTo(UPDATED_ACTIONTYPE);
        assertThat(testCifDevice.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testCifDevice.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testCifDevice.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testCifDevice.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testCifDevice.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testCifDevice.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testCifDevice.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the CifDevice in Elasticsearch
        CifDevice cifDeviceEs = cifDeviceSearchRepository.findOne(testCifDevice.getId());
        assertThat(cifDeviceEs).isEqualToComparingFieldByField(testCifDevice);
    }

    @Test
    @Transactional
    public void updateNonExistingCifDevice() throws Exception {
        int databaseSizeBeforeUpdate = cifDeviceRepository.findAll().size();

        // Create the CifDevice
        CifDeviceDTO cifDeviceDTO = cifDeviceMapper.toDto(cifDevice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCifDeviceMockMvc.perform(put("/api/cif-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the CifDevice in the database
        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCifDevice() throws Exception {
        // Initialize the database
        cifDeviceRepository.saveAndFlush(cifDevice);
        cifDeviceSearchRepository.save(cifDevice);
        int databaseSizeBeforeDelete = cifDeviceRepository.findAll().size();

        // Get the cifDevice
        restCifDeviceMockMvc.perform(delete("/api/cif-devices/{id}", cifDevice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cifDeviceExistsInEs = cifDeviceSearchRepository.exists(cifDevice.getId());
        assertThat(cifDeviceExistsInEs).isFalse();

        // Validate the database is empty
        List<CifDevice> cifDeviceList = cifDeviceRepository.findAll();
        assertThat(cifDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCifDevice() throws Exception {
        // Initialize the database
        cifDeviceRepository.saveAndFlush(cifDevice);
        cifDeviceSearchRepository.save(cifDevice);

        // Search the cifDevice
        restCifDeviceMockMvc.perform(get("/api/_search/cif-devices?query=id:" + cifDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].actiontype").value(hasItem(DEFAULT_ACTIONTYPE)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CifDevice.class);
        CifDevice cifDevice1 = new CifDevice();
        cifDevice1.setId(1L);
        CifDevice cifDevice2 = new CifDevice();
        cifDevice2.setId(cifDevice1.getId());
        assertThat(cifDevice1).isEqualTo(cifDevice2);
        cifDevice2.setId(2L);
        assertThat(cifDevice1).isNotEqualTo(cifDevice2);
        cifDevice1.setId(null);
        assertThat(cifDevice1).isNotEqualTo(cifDevice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CifDeviceDTO.class);
        CifDeviceDTO cifDeviceDTO1 = new CifDeviceDTO();
        cifDeviceDTO1.setId(1L);
        CifDeviceDTO cifDeviceDTO2 = new CifDeviceDTO();
        assertThat(cifDeviceDTO1).isNotEqualTo(cifDeviceDTO2);
        cifDeviceDTO2.setId(cifDeviceDTO1.getId());
        assertThat(cifDeviceDTO1).isEqualTo(cifDeviceDTO2);
        cifDeviceDTO2.setId(2L);
        assertThat(cifDeviceDTO1).isNotEqualTo(cifDeviceDTO2);
        cifDeviceDTO1.setId(null);
        assertThat(cifDeviceDTO1).isNotEqualTo(cifDeviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cifDeviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cifDeviceMapper.fromId(null)).isNull();
    }
}
