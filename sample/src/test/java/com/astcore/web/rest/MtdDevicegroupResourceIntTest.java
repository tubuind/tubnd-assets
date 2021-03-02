package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdDevicegroup;
import com.astcore.repository.MtdDevicegroupRepository;
import com.astcore.service.MtdDevicegroupService;
import com.astcore.repository.search.MtdDevicegroupSearchRepository;
import com.astcore.service.dto.MtdDevicegroupDTO;
import com.astcore.service.mapper.MtdDevicegroupMapper;
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
 * Test class for the MtdDevicegroupResource REST controller.
 *
 * @see MtdDevicegroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdDevicegroupResourceIntTest {

    private static final String DEFAULT_DEVICEGROUPNAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICEGROUPNAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNITCODE = "AAAAAAAAAA";
    private static final String UPDATED_UNITCODE = "BBBBBBBBBB";

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
    private MtdDevicegroupRepository mtdDevicegroupRepository;

    @Autowired
    private MtdDevicegroupMapper mtdDevicegroupMapper;

    @Autowired
    private MtdDevicegroupService mtdDevicegroupService;

    @Autowired
    private MtdDevicegroupSearchRepository mtdDevicegroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdDevicegroupMockMvc;

    private MtdDevicegroup mtdDevicegroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdDevicegroupResource mtdDevicegroupResource = new MtdDevicegroupResource(mtdDevicegroupService);
        this.restMtdDevicegroupMockMvc = MockMvcBuilders.standaloneSetup(mtdDevicegroupResource)
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
    public static MtdDevicegroup createEntity(EntityManager em) {
        MtdDevicegroup mtdDevicegroup = new MtdDevicegroup()
            .devicegroupname(DEFAULT_DEVICEGROUPNAME)
            .unitcode(DEFAULT_UNITCODE)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdDevicegroup;
    }

    @Before
    public void initTest() {
        mtdDevicegroupSearchRepository.deleteAll();
        mtdDevicegroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdDevicegroup() throws Exception {
        int databaseSizeBeforeCreate = mtdDevicegroupRepository.findAll().size();

        // Create the MtdDevicegroup
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);
        restMtdDevicegroupMockMvc.perform(post("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdDevicegroup in the database
        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeCreate + 1);
        MtdDevicegroup testMtdDevicegroup = mtdDevicegroupList.get(mtdDevicegroupList.size() - 1);
        assertThat(testMtdDevicegroup.getDevicegroupname()).isEqualTo(DEFAULT_DEVICEGROUPNAME);
        assertThat(testMtdDevicegroup.getUnitcode()).isEqualTo(DEFAULT_UNITCODE);
        assertThat(testMtdDevicegroup.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdDevicegroup.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdDevicegroup.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdDevicegroup.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdDevicegroup.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdDevicegroup.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdDevicegroup in Elasticsearch
        MtdDevicegroup mtdDevicegroupEs = mtdDevicegroupSearchRepository.findOne(testMtdDevicegroup.getId());
        assertThat(mtdDevicegroupEs).isEqualToComparingFieldByField(testMtdDevicegroup);
    }

    @Test
    @Transactional
    public void createMtdDevicegroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdDevicegroupRepository.findAll().size();

        // Create the MtdDevicegroup with an existing ID
        mtdDevicegroup.setId(1L);
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdDevicegroupMockMvc.perform(post("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDevicegroupnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicegroupRepository.findAll().size();
        // set the field null
        mtdDevicegroup.setDevicegroupname(null);

        // Create the MtdDevicegroup, which fails.
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);

        restMtdDevicegroupMockMvc.perform(post("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicegroupRepository.findAll().size();
        // set the field null
        mtdDevicegroup.setUnitcode(null);

        // Create the MtdDevicegroup, which fails.
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);

        restMtdDevicegroupMockMvc.perform(post("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicegroupRepository.findAll().size();
        // set the field null
        mtdDevicegroup.setActive(null);

        // Create the MtdDevicegroup, which fails.
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);

        restMtdDevicegroupMockMvc.perform(post("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicegroupRepository.findAll().size();
        // set the field null
        mtdDevicegroup.setIsdel(null);

        // Create the MtdDevicegroup, which fails.
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);

        restMtdDevicegroupMockMvc.perform(post("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdDevicegroups() throws Exception {
        // Initialize the database
        mtdDevicegroupRepository.saveAndFlush(mtdDevicegroup);

        // Get all the mtdDevicegroupList
        restMtdDevicegroupMockMvc.perform(get("/api/mtd-devicegroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdDevicegroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicegroupname").value(hasItem(DEFAULT_DEVICEGROUPNAME.toString())))
            .andExpect(jsonPath("$.[*].unitcode").value(hasItem(DEFAULT_UNITCODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdDevicegroup() throws Exception {
        // Initialize the database
        mtdDevicegroupRepository.saveAndFlush(mtdDevicegroup);

        // Get the mtdDevicegroup
        restMtdDevicegroupMockMvc.perform(get("/api/mtd-devicegroups/{id}", mtdDevicegroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdDevicegroup.getId().intValue()))
            .andExpect(jsonPath("$.devicegroupname").value(DEFAULT_DEVICEGROUPNAME.toString()))
            .andExpect(jsonPath("$.unitcode").value(DEFAULT_UNITCODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdDevicegroup() throws Exception {
        // Get the mtdDevicegroup
        restMtdDevicegroupMockMvc.perform(get("/api/mtd-devicegroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdDevicegroup() throws Exception {
        // Initialize the database
        mtdDevicegroupRepository.saveAndFlush(mtdDevicegroup);
        mtdDevicegroupSearchRepository.save(mtdDevicegroup);
        int databaseSizeBeforeUpdate = mtdDevicegroupRepository.findAll().size();

        // Update the mtdDevicegroup
        MtdDevicegroup updatedMtdDevicegroup = mtdDevicegroupRepository.findOne(mtdDevicegroup.getId());
        updatedMtdDevicegroup
            .devicegroupname(UPDATED_DEVICEGROUPNAME)
            .unitcode(UPDATED_UNITCODE)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(updatedMtdDevicegroup);

        restMtdDevicegroupMockMvc.perform(put("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isOk());

        // Validate the MtdDevicegroup in the database
        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeUpdate);
        MtdDevicegroup testMtdDevicegroup = mtdDevicegroupList.get(mtdDevicegroupList.size() - 1);
        assertThat(testMtdDevicegroup.getDevicegroupname()).isEqualTo(UPDATED_DEVICEGROUPNAME);
        assertThat(testMtdDevicegroup.getUnitcode()).isEqualTo(UPDATED_UNITCODE);
        assertThat(testMtdDevicegroup.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdDevicegroup.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdDevicegroup.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdDevicegroup.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdDevicegroup.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdDevicegroup.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdDevicegroup in Elasticsearch
        MtdDevicegroup mtdDevicegroupEs = mtdDevicegroupSearchRepository.findOne(testMtdDevicegroup.getId());
        assertThat(mtdDevicegroupEs).isEqualToComparingFieldByField(testMtdDevicegroup);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdDevicegroup() throws Exception {
        int databaseSizeBeforeUpdate = mtdDevicegroupRepository.findAll().size();

        // Create the MtdDevicegroup
        MtdDevicegroupDTO mtdDevicegroupDTO = mtdDevicegroupMapper.toDto(mtdDevicegroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdDevicegroupMockMvc.perform(put("/api/mtd-devicegroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicegroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdDevicegroup in the database
        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdDevicegroup() throws Exception {
        // Initialize the database
        mtdDevicegroupRepository.saveAndFlush(mtdDevicegroup);
        mtdDevicegroupSearchRepository.save(mtdDevicegroup);
        int databaseSizeBeforeDelete = mtdDevicegroupRepository.findAll().size();

        // Get the mtdDevicegroup
        restMtdDevicegroupMockMvc.perform(delete("/api/mtd-devicegroups/{id}", mtdDevicegroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdDevicegroupExistsInEs = mtdDevicegroupSearchRepository.exists(mtdDevicegroup.getId());
        assertThat(mtdDevicegroupExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdDevicegroup> mtdDevicegroupList = mtdDevicegroupRepository.findAll();
        assertThat(mtdDevicegroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdDevicegroup() throws Exception {
        // Initialize the database
        mtdDevicegroupRepository.saveAndFlush(mtdDevicegroup);
        mtdDevicegroupSearchRepository.save(mtdDevicegroup);

        // Search the mtdDevicegroup
        restMtdDevicegroupMockMvc.perform(get("/api/_search/mtd-devicegroups?query=id:" + mtdDevicegroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdDevicegroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicegroupname").value(hasItem(DEFAULT_DEVICEGROUPNAME.toString())))
            .andExpect(jsonPath("$.[*].unitcode").value(hasItem(DEFAULT_UNITCODE.toString())))
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
        TestUtil.equalsVerifier(MtdDevicegroup.class);
        MtdDevicegroup mtdDevicegroup1 = new MtdDevicegroup();
        mtdDevicegroup1.setId(1L);
        MtdDevicegroup mtdDevicegroup2 = new MtdDevicegroup();
        mtdDevicegroup2.setId(mtdDevicegroup1.getId());
        assertThat(mtdDevicegroup1).isEqualTo(mtdDevicegroup2);
        mtdDevicegroup2.setId(2L);
        assertThat(mtdDevicegroup1).isNotEqualTo(mtdDevicegroup2);
        mtdDevicegroup1.setId(null);
        assertThat(mtdDevicegroup1).isNotEqualTo(mtdDevicegroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdDevicegroupDTO.class);
        MtdDevicegroupDTO mtdDevicegroupDTO1 = new MtdDevicegroupDTO();
        mtdDevicegroupDTO1.setId(1L);
        MtdDevicegroupDTO mtdDevicegroupDTO2 = new MtdDevicegroupDTO();
        assertThat(mtdDevicegroupDTO1).isNotEqualTo(mtdDevicegroupDTO2);
        mtdDevicegroupDTO2.setId(mtdDevicegroupDTO1.getId());
        assertThat(mtdDevicegroupDTO1).isEqualTo(mtdDevicegroupDTO2);
        mtdDevicegroupDTO2.setId(2L);
        assertThat(mtdDevicegroupDTO1).isNotEqualTo(mtdDevicegroupDTO2);
        mtdDevicegroupDTO1.setId(null);
        assertThat(mtdDevicegroupDTO1).isNotEqualTo(mtdDevicegroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdDevicegroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdDevicegroupMapper.fromId(null)).isNull();
    }
}
