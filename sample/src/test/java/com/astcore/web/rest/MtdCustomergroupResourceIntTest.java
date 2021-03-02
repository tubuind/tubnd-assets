package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdCustomergroup;
import com.astcore.repository.MtdCustomergroupRepository;
import com.astcore.service.MtdCustomergroupService;
import com.astcore.repository.search.MtdCustomergroupSearchRepository;
import com.astcore.service.dto.MtdCustomergroupDTO;
import com.astcore.service.mapper.MtdCustomergroupMapper;
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
 * Test class for the MtdCustomergroupResource REST controller.
 *
 * @see MtdCustomergroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdCustomergroupResourceIntTest {

    private static final String DEFAULT_CUSTGROUPNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTGROUPNAME = "BBBBBBBBBB";

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
    private MtdCustomergroupRepository mtdCustomergroupRepository;

    @Autowired
    private MtdCustomergroupMapper mtdCustomergroupMapper;

    @Autowired
    private MtdCustomergroupService mtdCustomergroupService;

    @Autowired
    private MtdCustomergroupSearchRepository mtdCustomergroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdCustomergroupMockMvc;

    private MtdCustomergroup mtdCustomergroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdCustomergroupResource mtdCustomergroupResource = new MtdCustomergroupResource(mtdCustomergroupService);
        this.restMtdCustomergroupMockMvc = MockMvcBuilders.standaloneSetup(mtdCustomergroupResource)
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
    public static MtdCustomergroup createEntity(EntityManager em) {
        MtdCustomergroup mtdCustomergroup = new MtdCustomergroup()
            .custgroupname(DEFAULT_CUSTGROUPNAME)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdCustomergroup;
    }

    @Before
    public void initTest() {
        mtdCustomergroupSearchRepository.deleteAll();
        mtdCustomergroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdCustomergroup() throws Exception {
        int databaseSizeBeforeCreate = mtdCustomergroupRepository.findAll().size();

        // Create the MtdCustomergroup
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(mtdCustomergroup);
        restMtdCustomergroupMockMvc.perform(post("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdCustomergroup in the database
        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeCreate + 1);
        MtdCustomergroup testMtdCustomergroup = mtdCustomergroupList.get(mtdCustomergroupList.size() - 1);
        assertThat(testMtdCustomergroup.getCustgroupname()).isEqualTo(DEFAULT_CUSTGROUPNAME);
        assertThat(testMtdCustomergroup.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdCustomergroup.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdCustomergroup.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdCustomergroup.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdCustomergroup.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdCustomergroup.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdCustomergroup in Elasticsearch
        MtdCustomergroup mtdCustomergroupEs = mtdCustomergroupSearchRepository.findOne(testMtdCustomergroup.getId());
        assertThat(mtdCustomergroupEs).isEqualToComparingFieldByField(testMtdCustomergroup);
    }

    @Test
    @Transactional
    public void createMtdCustomergroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdCustomergroupRepository.findAll().size();

        // Create the MtdCustomergroup with an existing ID
        mtdCustomergroup.setId(1L);
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(mtdCustomergroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdCustomergroupMockMvc.perform(post("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustgroupnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdCustomergroupRepository.findAll().size();
        // set the field null
        mtdCustomergroup.setCustgroupname(null);

        // Create the MtdCustomergroup, which fails.
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(mtdCustomergroup);

        restMtdCustomergroupMockMvc.perform(post("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdCustomergroupRepository.findAll().size();
        // set the field null
        mtdCustomergroup.setActive(null);

        // Create the MtdCustomergroup, which fails.
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(mtdCustomergroup);

        restMtdCustomergroupMockMvc.perform(post("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdCustomergroupRepository.findAll().size();
        // set the field null
        mtdCustomergroup.setIsdel(null);

        // Create the MtdCustomergroup, which fails.
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(mtdCustomergroup);

        restMtdCustomergroupMockMvc.perform(post("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isBadRequest());

        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdCustomergroups() throws Exception {
        // Initialize the database
        mtdCustomergroupRepository.saveAndFlush(mtdCustomergroup);

        // Get all the mtdCustomergroupList
        restMtdCustomergroupMockMvc.perform(get("/api/mtd-customergroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdCustomergroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].custgroupname").value(hasItem(DEFAULT_CUSTGROUPNAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdCustomergroup() throws Exception {
        // Initialize the database
        mtdCustomergroupRepository.saveAndFlush(mtdCustomergroup);

        // Get the mtdCustomergroup
        restMtdCustomergroupMockMvc.perform(get("/api/mtd-customergroups/{id}", mtdCustomergroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdCustomergroup.getId().intValue()))
            .andExpect(jsonPath("$.custgroupname").value(DEFAULT_CUSTGROUPNAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdCustomergroup() throws Exception {
        // Get the mtdCustomergroup
        restMtdCustomergroupMockMvc.perform(get("/api/mtd-customergroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdCustomergroup() throws Exception {
        // Initialize the database
        mtdCustomergroupRepository.saveAndFlush(mtdCustomergroup);
        mtdCustomergroupSearchRepository.save(mtdCustomergroup);
        int databaseSizeBeforeUpdate = mtdCustomergroupRepository.findAll().size();

        // Update the mtdCustomergroup
        MtdCustomergroup updatedMtdCustomergroup = mtdCustomergroupRepository.findOne(mtdCustomergroup.getId());
        updatedMtdCustomergroup
            .custgroupname(UPDATED_CUSTGROUPNAME)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(updatedMtdCustomergroup);

        restMtdCustomergroupMockMvc.perform(put("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isOk());

        // Validate the MtdCustomergroup in the database
        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeUpdate);
        MtdCustomergroup testMtdCustomergroup = mtdCustomergroupList.get(mtdCustomergroupList.size() - 1);
        assertThat(testMtdCustomergroup.getCustgroupname()).isEqualTo(UPDATED_CUSTGROUPNAME);
        assertThat(testMtdCustomergroup.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdCustomergroup.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdCustomergroup.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdCustomergroup.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdCustomergroup.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdCustomergroup.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdCustomergroup in Elasticsearch
        MtdCustomergroup mtdCustomergroupEs = mtdCustomergroupSearchRepository.findOne(testMtdCustomergroup.getId());
        assertThat(mtdCustomergroupEs).isEqualToComparingFieldByField(testMtdCustomergroup);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdCustomergroup() throws Exception {
        int databaseSizeBeforeUpdate = mtdCustomergroupRepository.findAll().size();

        // Create the MtdCustomergroup
        MtdCustomergroupDTO mtdCustomergroupDTO = mtdCustomergroupMapper.toDto(mtdCustomergroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdCustomergroupMockMvc.perform(put("/api/mtd-customergroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCustomergroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdCustomergroup in the database
        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdCustomergroup() throws Exception {
        // Initialize the database
        mtdCustomergroupRepository.saveAndFlush(mtdCustomergroup);
        mtdCustomergroupSearchRepository.save(mtdCustomergroup);
        int databaseSizeBeforeDelete = mtdCustomergroupRepository.findAll().size();

        // Get the mtdCustomergroup
        restMtdCustomergroupMockMvc.perform(delete("/api/mtd-customergroups/{id}", mtdCustomergroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdCustomergroupExistsInEs = mtdCustomergroupSearchRepository.exists(mtdCustomergroup.getId());
        assertThat(mtdCustomergroupExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdCustomergroup> mtdCustomergroupList = mtdCustomergroupRepository.findAll();
        assertThat(mtdCustomergroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdCustomergroup() throws Exception {
        // Initialize the database
        mtdCustomergroupRepository.saveAndFlush(mtdCustomergroup);
        mtdCustomergroupSearchRepository.save(mtdCustomergroup);

        // Search the mtdCustomergroup
        restMtdCustomergroupMockMvc.perform(get("/api/_search/mtd-customergroups?query=id:" + mtdCustomergroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdCustomergroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].custgroupname").value(hasItem(DEFAULT_CUSTGROUPNAME.toString())))
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
        TestUtil.equalsVerifier(MtdCustomergroup.class);
        MtdCustomergroup mtdCustomergroup1 = new MtdCustomergroup();
        mtdCustomergroup1.setId(1L);
        MtdCustomergroup mtdCustomergroup2 = new MtdCustomergroup();
        mtdCustomergroup2.setId(mtdCustomergroup1.getId());
        assertThat(mtdCustomergroup1).isEqualTo(mtdCustomergroup2);
        mtdCustomergroup2.setId(2L);
        assertThat(mtdCustomergroup1).isNotEqualTo(mtdCustomergroup2);
        mtdCustomergroup1.setId(null);
        assertThat(mtdCustomergroup1).isNotEqualTo(mtdCustomergroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdCustomergroupDTO.class);
        MtdCustomergroupDTO mtdCustomergroupDTO1 = new MtdCustomergroupDTO();
        mtdCustomergroupDTO1.setId(1L);
        MtdCustomergroupDTO mtdCustomergroupDTO2 = new MtdCustomergroupDTO();
        assertThat(mtdCustomergroupDTO1).isNotEqualTo(mtdCustomergroupDTO2);
        mtdCustomergroupDTO2.setId(mtdCustomergroupDTO1.getId());
        assertThat(mtdCustomergroupDTO1).isEqualTo(mtdCustomergroupDTO2);
        mtdCustomergroupDTO2.setId(2L);
        assertThat(mtdCustomergroupDTO1).isNotEqualTo(mtdCustomergroupDTO2);
        mtdCustomergroupDTO1.setId(null);
        assertThat(mtdCustomergroupDTO1).isNotEqualTo(mtdCustomergroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdCustomergroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdCustomergroupMapper.fromId(null)).isNull();
    }
}
