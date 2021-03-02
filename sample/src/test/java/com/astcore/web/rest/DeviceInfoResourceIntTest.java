package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.DeviceInfo;
import com.astcore.repository.DeviceInfoRepository;
import com.astcore.service.DeviceInfoService;
import com.astcore.repository.search.DeviceInfoSearchRepository;
import com.astcore.service.dto.DeviceInfoDTO;
import com.astcore.service.mapper.DeviceInfoMapper;
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
 * Test class for the DeviceInfoResource REST controller.
 *
 * @see DeviceInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class DeviceInfoResourceIntTest {

    private static final String DEFAULT_DEVICECODE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICECODE = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICENAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICENAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MAKEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MAKEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

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
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private DeviceInfoSearchRepository deviceInfoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceInfoMockMvc;

    private DeviceInfo deviceInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceInfoResource deviceInfoResource = new DeviceInfoResource(deviceInfoService);
        this.restDeviceInfoMockMvc = MockMvcBuilders.standaloneSetup(deviceInfoResource)
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
    public static DeviceInfo createEntity(EntityManager em) {
        DeviceInfo deviceInfo = new DeviceInfo()
            .devicecode(DEFAULT_DEVICECODE)
            .devicename(DEFAULT_DEVICENAME)
            .makedate(DEFAULT_MAKEDATE)
            .note(DEFAULT_NOTE)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return deviceInfo;
    }

    @Before
    public void initTest() {
        deviceInfoSearchRepository.deleteAll();
        deviceInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceInfo() throws Exception {
        int databaseSizeBeforeCreate = deviceInfoRepository.findAll().size();

        // Create the DeviceInfo
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);
        restDeviceInfoMockMvc.perform(post("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceInfo in the database
        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceInfo testDeviceInfo = deviceInfoList.get(deviceInfoList.size() - 1);
        assertThat(testDeviceInfo.getDevicecode()).isEqualTo(DEFAULT_DEVICECODE);
        assertThat(testDeviceInfo.getDevicename()).isEqualTo(DEFAULT_DEVICENAME);
        assertThat(testDeviceInfo.getMakedate()).isEqualTo(DEFAULT_MAKEDATE);
        assertThat(testDeviceInfo.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testDeviceInfo.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testDeviceInfo.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testDeviceInfo.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testDeviceInfo.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testDeviceInfo.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testDeviceInfo.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the DeviceInfo in Elasticsearch
        DeviceInfo deviceInfoEs = deviceInfoSearchRepository.findOne(testDeviceInfo.getId());
        assertThat(deviceInfoEs).isEqualToComparingFieldByField(testDeviceInfo);
    }

    @Test
    @Transactional
    public void createDeviceInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceInfoRepository.findAll().size();

        // Create the DeviceInfo with an existing ID
        deviceInfo.setId(1L);
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceInfoMockMvc.perform(post("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDevicecodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceInfoRepository.findAll().size();
        // set the field null
        deviceInfo.setDevicecode(null);

        // Create the DeviceInfo, which fails.
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);

        restDeviceInfoMockMvc.perform(post("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDevicenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceInfoRepository.findAll().size();
        // set the field null
        deviceInfo.setDevicename(null);

        // Create the DeviceInfo, which fails.
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);

        restDeviceInfoMockMvc.perform(post("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceInfoRepository.findAll().size();
        // set the field null
        deviceInfo.setActive(null);

        // Create the DeviceInfo, which fails.
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);

        restDeviceInfoMockMvc.perform(post("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceInfoRepository.findAll().size();
        // set the field null
        deviceInfo.setIsdel(null);

        // Create the DeviceInfo, which fails.
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);

        restDeviceInfoMockMvc.perform(post("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceInfos() throws Exception {
        // Initialize the database
        deviceInfoRepository.saveAndFlush(deviceInfo);

        // Get all the deviceInfoList
        restDeviceInfoMockMvc.perform(get("/api/device-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicecode").value(hasItem(DEFAULT_DEVICECODE.toString())))
            .andExpect(jsonPath("$.[*].devicename").value(hasItem(DEFAULT_DEVICENAME.toString())))
            .andExpect(jsonPath("$.[*].makedate").value(hasItem(DEFAULT_MAKEDATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getDeviceInfo() throws Exception {
        // Initialize the database
        deviceInfoRepository.saveAndFlush(deviceInfo);

        // Get the deviceInfo
        restDeviceInfoMockMvc.perform(get("/api/device-infos/{id}", deviceInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceInfo.getId().intValue()))
            .andExpect(jsonPath("$.devicecode").value(DEFAULT_DEVICECODE.toString()))
            .andExpect(jsonPath("$.devicename").value(DEFAULT_DEVICENAME.toString()))
            .andExpect(jsonPath("$.makedate").value(DEFAULT_MAKEDATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceInfo() throws Exception {
        // Get the deviceInfo
        restDeviceInfoMockMvc.perform(get("/api/device-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceInfo() throws Exception {
        // Initialize the database
        deviceInfoRepository.saveAndFlush(deviceInfo);
        deviceInfoSearchRepository.save(deviceInfo);
        int databaseSizeBeforeUpdate = deviceInfoRepository.findAll().size();

        // Update the deviceInfo
        DeviceInfo updatedDeviceInfo = deviceInfoRepository.findOne(deviceInfo.getId());
        updatedDeviceInfo
            .devicecode(UPDATED_DEVICECODE)
            .devicename(UPDATED_DEVICENAME)
            .makedate(UPDATED_MAKEDATE)
            .note(UPDATED_NOTE)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(updatedDeviceInfo);

        restDeviceInfoMockMvc.perform(put("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceInfo in the database
        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeUpdate);
        DeviceInfo testDeviceInfo = deviceInfoList.get(deviceInfoList.size() - 1);
        assertThat(testDeviceInfo.getDevicecode()).isEqualTo(UPDATED_DEVICECODE);
        assertThat(testDeviceInfo.getDevicename()).isEqualTo(UPDATED_DEVICENAME);
        assertThat(testDeviceInfo.getMakedate()).isEqualTo(UPDATED_MAKEDATE);
        assertThat(testDeviceInfo.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testDeviceInfo.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDeviceInfo.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testDeviceInfo.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testDeviceInfo.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testDeviceInfo.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testDeviceInfo.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the DeviceInfo in Elasticsearch
        DeviceInfo deviceInfoEs = deviceInfoSearchRepository.findOne(testDeviceInfo.getId());
        assertThat(deviceInfoEs).isEqualToComparingFieldByField(testDeviceInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceInfo() throws Exception {
        int databaseSizeBeforeUpdate = deviceInfoRepository.findAll().size();

        // Create the DeviceInfo
        DeviceInfoDTO deviceInfoDTO = deviceInfoMapper.toDto(deviceInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceInfoMockMvc.perform(put("/api/device-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceInfo in the database
        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeviceInfo() throws Exception {
        // Initialize the database
        deviceInfoRepository.saveAndFlush(deviceInfo);
        deviceInfoSearchRepository.save(deviceInfo);
        int databaseSizeBeforeDelete = deviceInfoRepository.findAll().size();

        // Get the deviceInfo
        restDeviceInfoMockMvc.perform(delete("/api/device-infos/{id}", deviceInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean deviceInfoExistsInEs = deviceInfoSearchRepository.exists(deviceInfo.getId());
        assertThat(deviceInfoExistsInEs).isFalse();

        // Validate the database is empty
        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findAll();
        assertThat(deviceInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDeviceInfo() throws Exception {
        // Initialize the database
        deviceInfoRepository.saveAndFlush(deviceInfo);
        deviceInfoSearchRepository.save(deviceInfo);

        // Search the deviceInfo
        restDeviceInfoMockMvc.perform(get("/api/_search/device-infos?query=id:" + deviceInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicecode").value(hasItem(DEFAULT_DEVICECODE.toString())))
            .andExpect(jsonPath("$.[*].devicename").value(hasItem(DEFAULT_DEVICENAME.toString())))
            .andExpect(jsonPath("$.[*].makedate").value(hasItem(DEFAULT_MAKEDATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
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
        TestUtil.equalsVerifier(DeviceInfo.class);
        DeviceInfo deviceInfo1 = new DeviceInfo();
        deviceInfo1.setId(1L);
        DeviceInfo deviceInfo2 = new DeviceInfo();
        deviceInfo2.setId(deviceInfo1.getId());
        assertThat(deviceInfo1).isEqualTo(deviceInfo2);
        deviceInfo2.setId(2L);
        assertThat(deviceInfo1).isNotEqualTo(deviceInfo2);
        deviceInfo1.setId(null);
        assertThat(deviceInfo1).isNotEqualTo(deviceInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceInfoDTO.class);
        DeviceInfoDTO deviceInfoDTO1 = new DeviceInfoDTO();
        deviceInfoDTO1.setId(1L);
        DeviceInfoDTO deviceInfoDTO2 = new DeviceInfoDTO();
        assertThat(deviceInfoDTO1).isNotEqualTo(deviceInfoDTO2);
        deviceInfoDTO2.setId(deviceInfoDTO1.getId());
        assertThat(deviceInfoDTO1).isEqualTo(deviceInfoDTO2);
        deviceInfoDTO2.setId(2L);
        assertThat(deviceInfoDTO1).isNotEqualTo(deviceInfoDTO2);
        deviceInfoDTO1.setId(null);
        assertThat(deviceInfoDTO1).isNotEqualTo(deviceInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deviceInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deviceInfoMapper.fromId(null)).isNull();
    }
}
