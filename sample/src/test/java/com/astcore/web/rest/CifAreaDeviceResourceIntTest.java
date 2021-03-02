package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.CifAreaDevice;
import com.astcore.repository.CifAreaDeviceRepository;
import com.astcore.service.CifAreaDeviceService;
import com.astcore.repository.search.CifAreaDeviceSearchRepository;
import com.astcore.service.dto.CifAreaDeviceDTO;
import com.astcore.service.mapper.CifAreaDeviceMapper;
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
 * Test class for the CifAreaDeviceResource REST controller.
 *
 * @see CifAreaDeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class CifAreaDeviceResourceIntTest {

    private static final String DEFAULT_DEVICECODE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICECODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CifAreaDeviceRepository cifAreaDeviceRepository;

    @Autowired
    private CifAreaDeviceMapper cifAreaDeviceMapper;

    @Autowired
    private CifAreaDeviceService cifAreaDeviceService;

    @Autowired
    private CifAreaDeviceSearchRepository cifAreaDeviceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCifAreaDeviceMockMvc;

    private CifAreaDevice cifAreaDevice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CifAreaDeviceResource cifAreaDeviceResource = new CifAreaDeviceResource(cifAreaDeviceService);
        this.restCifAreaDeviceMockMvc = MockMvcBuilders.standaloneSetup(cifAreaDeviceResource)
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
    public static CifAreaDevice createEntity(EntityManager em) {
        CifAreaDevice cifAreaDevice = new CifAreaDevice()
            .devicecode(DEFAULT_DEVICECODE)
            .startdate(DEFAULT_STARTDATE);
        return cifAreaDevice;
    }

    @Before
    public void initTest() {
        cifAreaDeviceSearchRepository.deleteAll();
        cifAreaDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCifAreaDevice() throws Exception {
        int databaseSizeBeforeCreate = cifAreaDeviceRepository.findAll().size();

        // Create the CifAreaDevice
        CifAreaDeviceDTO cifAreaDeviceDTO = cifAreaDeviceMapper.toDto(cifAreaDevice);
        restCifAreaDeviceMockMvc.perform(post("/api/cif-area-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the CifAreaDevice in the database
        List<CifAreaDevice> cifAreaDeviceList = cifAreaDeviceRepository.findAll();
        assertThat(cifAreaDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        CifAreaDevice testCifAreaDevice = cifAreaDeviceList.get(cifAreaDeviceList.size() - 1);
        assertThat(testCifAreaDevice.getDevicecode()).isEqualTo(DEFAULT_DEVICECODE);
        assertThat(testCifAreaDevice.getStartdate()).isEqualTo(DEFAULT_STARTDATE);

        // Validate the CifAreaDevice in Elasticsearch
        CifAreaDevice cifAreaDeviceEs = cifAreaDeviceSearchRepository.findOne(testCifAreaDevice.getId());
        assertThat(cifAreaDeviceEs).isEqualToComparingFieldByField(testCifAreaDevice);
    }

    @Test
    @Transactional
    public void createCifAreaDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cifAreaDeviceRepository.findAll().size();

        // Create the CifAreaDevice with an existing ID
        cifAreaDevice.setId(1L);
        CifAreaDeviceDTO cifAreaDeviceDTO = cifAreaDeviceMapper.toDto(cifAreaDevice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCifAreaDeviceMockMvc.perform(post("/api/cif-area-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDeviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CifAreaDevice> cifAreaDeviceList = cifAreaDeviceRepository.findAll();
        assertThat(cifAreaDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDevicecodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifAreaDeviceRepository.findAll().size();
        // set the field null
        cifAreaDevice.setDevicecode(null);

        // Create the CifAreaDevice, which fails.
        CifAreaDeviceDTO cifAreaDeviceDTO = cifAreaDeviceMapper.toDto(cifAreaDevice);

        restCifAreaDeviceMockMvc.perform(post("/api/cif-area-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDeviceDTO)))
            .andExpect(status().isBadRequest());

        List<CifAreaDevice> cifAreaDeviceList = cifAreaDeviceRepository.findAll();
        assertThat(cifAreaDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCifAreaDevices() throws Exception {
        // Initialize the database
        cifAreaDeviceRepository.saveAndFlush(cifAreaDevice);

        // Get all the cifAreaDeviceList
        restCifAreaDeviceMockMvc.perform(get("/api/cif-area-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifAreaDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicecode").value(hasItem(DEFAULT_DEVICECODE.toString())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())));
    }

    @Test
    @Transactional
    public void getCifAreaDevice() throws Exception {
        // Initialize the database
        cifAreaDeviceRepository.saveAndFlush(cifAreaDevice);

        // Get the cifAreaDevice
        restCifAreaDeviceMockMvc.perform(get("/api/cif-area-devices/{id}", cifAreaDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cifAreaDevice.getId().intValue()))
            .andExpect(jsonPath("$.devicecode").value(DEFAULT_DEVICECODE.toString()))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCifAreaDevice() throws Exception {
        // Get the cifAreaDevice
        restCifAreaDeviceMockMvc.perform(get("/api/cif-area-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCifAreaDevice() throws Exception {
        // Initialize the database
        cifAreaDeviceRepository.saveAndFlush(cifAreaDevice);
        cifAreaDeviceSearchRepository.save(cifAreaDevice);
        int databaseSizeBeforeUpdate = cifAreaDeviceRepository.findAll().size();

        // Update the cifAreaDevice
        CifAreaDevice updatedCifAreaDevice = cifAreaDeviceRepository.findOne(cifAreaDevice.getId());
        updatedCifAreaDevice
            .devicecode(UPDATED_DEVICECODE)
            .startdate(UPDATED_STARTDATE);
        CifAreaDeviceDTO cifAreaDeviceDTO = cifAreaDeviceMapper.toDto(updatedCifAreaDevice);

        restCifAreaDeviceMockMvc.perform(put("/api/cif-area-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDeviceDTO)))
            .andExpect(status().isOk());

        // Validate the CifAreaDevice in the database
        List<CifAreaDevice> cifAreaDeviceList = cifAreaDeviceRepository.findAll();
        assertThat(cifAreaDeviceList).hasSize(databaseSizeBeforeUpdate);
        CifAreaDevice testCifAreaDevice = cifAreaDeviceList.get(cifAreaDeviceList.size() - 1);
        assertThat(testCifAreaDevice.getDevicecode()).isEqualTo(UPDATED_DEVICECODE);
        assertThat(testCifAreaDevice.getStartdate()).isEqualTo(UPDATED_STARTDATE);

        // Validate the CifAreaDevice in Elasticsearch
        CifAreaDevice cifAreaDeviceEs = cifAreaDeviceSearchRepository.findOne(testCifAreaDevice.getId());
        assertThat(cifAreaDeviceEs).isEqualToComparingFieldByField(testCifAreaDevice);
    }

    @Test
    @Transactional
    public void updateNonExistingCifAreaDevice() throws Exception {
        int databaseSizeBeforeUpdate = cifAreaDeviceRepository.findAll().size();

        // Create the CifAreaDevice
        CifAreaDeviceDTO cifAreaDeviceDTO = cifAreaDeviceMapper.toDto(cifAreaDevice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCifAreaDeviceMockMvc.perform(put("/api/cif-area-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the CifAreaDevice in the database
        List<CifAreaDevice> cifAreaDeviceList = cifAreaDeviceRepository.findAll();
        assertThat(cifAreaDeviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCifAreaDevice() throws Exception {
        // Initialize the database
        cifAreaDeviceRepository.saveAndFlush(cifAreaDevice);
        cifAreaDeviceSearchRepository.save(cifAreaDevice);
        int databaseSizeBeforeDelete = cifAreaDeviceRepository.findAll().size();

        // Get the cifAreaDevice
        restCifAreaDeviceMockMvc.perform(delete("/api/cif-area-devices/{id}", cifAreaDevice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cifAreaDeviceExistsInEs = cifAreaDeviceSearchRepository.exists(cifAreaDevice.getId());
        assertThat(cifAreaDeviceExistsInEs).isFalse();

        // Validate the database is empty
        List<CifAreaDevice> cifAreaDeviceList = cifAreaDeviceRepository.findAll();
        assertThat(cifAreaDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCifAreaDevice() throws Exception {
        // Initialize the database
        cifAreaDeviceRepository.saveAndFlush(cifAreaDevice);
        cifAreaDeviceSearchRepository.save(cifAreaDevice);

        // Search the cifAreaDevice
        restCifAreaDeviceMockMvc.perform(get("/api/_search/cif-area-devices?query=id:" + cifAreaDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifAreaDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicecode").value(hasItem(DEFAULT_DEVICECODE.toString())))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CifAreaDevice.class);
        CifAreaDevice cifAreaDevice1 = new CifAreaDevice();
        cifAreaDevice1.setId(1L);
        CifAreaDevice cifAreaDevice2 = new CifAreaDevice();
        cifAreaDevice2.setId(cifAreaDevice1.getId());
        assertThat(cifAreaDevice1).isEqualTo(cifAreaDevice2);
        cifAreaDevice2.setId(2L);
        assertThat(cifAreaDevice1).isNotEqualTo(cifAreaDevice2);
        cifAreaDevice1.setId(null);
        assertThat(cifAreaDevice1).isNotEqualTo(cifAreaDevice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CifAreaDeviceDTO.class);
        CifAreaDeviceDTO cifAreaDeviceDTO1 = new CifAreaDeviceDTO();
        cifAreaDeviceDTO1.setId(1L);
        CifAreaDeviceDTO cifAreaDeviceDTO2 = new CifAreaDeviceDTO();
        assertThat(cifAreaDeviceDTO1).isNotEqualTo(cifAreaDeviceDTO2);
        cifAreaDeviceDTO2.setId(cifAreaDeviceDTO1.getId());
        assertThat(cifAreaDeviceDTO1).isEqualTo(cifAreaDeviceDTO2);
        cifAreaDeviceDTO2.setId(2L);
        assertThat(cifAreaDeviceDTO1).isNotEqualTo(cifAreaDeviceDTO2);
        cifAreaDeviceDTO1.setId(null);
        assertThat(cifAreaDeviceDTO1).isNotEqualTo(cifAreaDeviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cifAreaDeviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cifAreaDeviceMapper.fromId(null)).isNull();
    }
}
