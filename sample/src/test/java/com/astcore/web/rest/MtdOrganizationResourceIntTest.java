package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdOrganization;
import com.astcore.repository.MtdOrganizationRepository;
import com.astcore.service.MtdOrganizationService;
import com.astcore.repository.search.MtdOrganizationSearchRepository;
import com.astcore.service.dto.MtdOrganizationDTO;
import com.astcore.service.mapper.MtdOrganizationMapper;
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
 * Test class for the MtdOrganizationResource REST controller.
 *
 * @see MtdOrganizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdOrganizationResourceIntTest {

    private static final String DEFAULT_ORGANIZATIONCODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATIONCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATIONNAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATIONNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILENUM = "AAAAAAAAAA";
    private static final String UPDATED_MOBILENUM = "BBBBBBBBBB";

    private static final String DEFAULT_PHONENUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUM = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARENTS = 1;
    private static final Integer UPDATED_PARENTS = 2;

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
    private MtdOrganizationRepository mtdOrganizationRepository;

    @Autowired
    private MtdOrganizationMapper mtdOrganizationMapper;

    @Autowired
    private MtdOrganizationService mtdOrganizationService;

    @Autowired
    private MtdOrganizationSearchRepository mtdOrganizationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdOrganizationMockMvc;

    private MtdOrganization mtdOrganization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdOrganizationResource mtdOrganizationResource = new MtdOrganizationResource(mtdOrganizationService);
        this.restMtdOrganizationMockMvc = MockMvcBuilders.standaloneSetup(mtdOrganizationResource)
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
    public static MtdOrganization createEntity(EntityManager em) {
        MtdOrganization mtdOrganization = new MtdOrganization()
            .organizationcode(DEFAULT_ORGANIZATIONCODE)
            .organizationname(DEFAULT_ORGANIZATIONNAME)
            .address(DEFAULT_ADDRESS)
            .mobilenum(DEFAULT_MOBILENUM)
            .phonenum(DEFAULT_PHONENUM)
            .parents(DEFAULT_PARENTS)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdOrganization;
    }

    @Before
    public void initTest() {
        mtdOrganizationSearchRepository.deleteAll();
        mtdOrganization = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdOrganization() throws Exception {
        int databaseSizeBeforeCreate = mtdOrganizationRepository.findAll().size();

        // Create the MtdOrganization
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);
        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdOrganization in the database
        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        MtdOrganization testMtdOrganization = mtdOrganizationList.get(mtdOrganizationList.size() - 1);
        assertThat(testMtdOrganization.getOrganizationcode()).isEqualTo(DEFAULT_ORGANIZATIONCODE);
        assertThat(testMtdOrganization.getOrganizationname()).isEqualTo(DEFAULT_ORGANIZATIONNAME);
        assertThat(testMtdOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMtdOrganization.getMobilenum()).isEqualTo(DEFAULT_MOBILENUM);
        assertThat(testMtdOrganization.getPhonenum()).isEqualTo(DEFAULT_PHONENUM);
        assertThat(testMtdOrganization.getParents()).isEqualTo(DEFAULT_PARENTS);
        assertThat(testMtdOrganization.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdOrganization.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdOrganization.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdOrganization.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdOrganization.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdOrganization.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdOrganization in Elasticsearch
        MtdOrganization mtdOrganizationEs = mtdOrganizationSearchRepository.findOne(testMtdOrganization.getId());
        assertThat(mtdOrganizationEs).isEqualToComparingFieldByField(testMtdOrganization);
    }

    @Test
    @Transactional
    public void createMtdOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdOrganizationRepository.findAll().size();

        // Create the MtdOrganization with an existing ID
        mtdOrganization.setId(1L);
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrganizationcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setOrganizationcode(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganizationnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setOrganizationname(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setAddress(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobilenumIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setMobilenum(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhonenumIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setPhonenum(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setActive(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdOrganizationRepository.findAll().size();
        // set the field null
        mtdOrganization.setIsdel(null);

        // Create the MtdOrganization, which fails.
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        restMtdOrganizationMockMvc.perform(post("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isBadRequest());

        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdOrganizations() throws Exception {
        // Initialize the database
        mtdOrganizationRepository.saveAndFlush(mtdOrganization);

        // Get all the mtdOrganizationList
        restMtdOrganizationMockMvc.perform(get("/api/mtd-organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].organizationcode").value(hasItem(DEFAULT_ORGANIZATIONCODE.toString())))
            .andExpect(jsonPath("$.[*].organizationname").value(hasItem(DEFAULT_ORGANIZATIONNAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mobilenum").value(hasItem(DEFAULT_MOBILENUM.toString())))
            .andExpect(jsonPath("$.[*].phonenum").value(hasItem(DEFAULT_PHONENUM.toString())))
            .andExpect(jsonPath("$.[*].parents").value(hasItem(DEFAULT_PARENTS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdOrganization() throws Exception {
        // Initialize the database
        mtdOrganizationRepository.saveAndFlush(mtdOrganization);

        // Get the mtdOrganization
        restMtdOrganizationMockMvc.perform(get("/api/mtd-organizations/{id}", mtdOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdOrganization.getId().intValue()))
            .andExpect(jsonPath("$.organizationcode").value(DEFAULT_ORGANIZATIONCODE.toString()))
            .andExpect(jsonPath("$.organizationname").value(DEFAULT_ORGANIZATIONNAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.mobilenum").value(DEFAULT_MOBILENUM.toString()))
            .andExpect(jsonPath("$.phonenum").value(DEFAULT_PHONENUM.toString()))
            .andExpect(jsonPath("$.parents").value(DEFAULT_PARENTS))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdOrganization() throws Exception {
        // Get the mtdOrganization
        restMtdOrganizationMockMvc.perform(get("/api/mtd-organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdOrganization() throws Exception {
        // Initialize the database
        mtdOrganizationRepository.saveAndFlush(mtdOrganization);
        mtdOrganizationSearchRepository.save(mtdOrganization);
        int databaseSizeBeforeUpdate = mtdOrganizationRepository.findAll().size();

        // Update the mtdOrganization
        MtdOrganization updatedMtdOrganization = mtdOrganizationRepository.findOne(mtdOrganization.getId());
        updatedMtdOrganization
            .organizationcode(UPDATED_ORGANIZATIONCODE)
            .organizationname(UPDATED_ORGANIZATIONNAME)
            .address(UPDATED_ADDRESS)
            .mobilenum(UPDATED_MOBILENUM)
            .phonenum(UPDATED_PHONENUM)
            .parents(UPDATED_PARENTS)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(updatedMtdOrganization);

        restMtdOrganizationMockMvc.perform(put("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isOk());

        // Validate the MtdOrganization in the database
        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeUpdate);
        MtdOrganization testMtdOrganization = mtdOrganizationList.get(mtdOrganizationList.size() - 1);
        assertThat(testMtdOrganization.getOrganizationcode()).isEqualTo(UPDATED_ORGANIZATIONCODE);
        assertThat(testMtdOrganization.getOrganizationname()).isEqualTo(UPDATED_ORGANIZATIONNAME);
        assertThat(testMtdOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMtdOrganization.getMobilenum()).isEqualTo(UPDATED_MOBILENUM);
        assertThat(testMtdOrganization.getPhonenum()).isEqualTo(UPDATED_PHONENUM);
        assertThat(testMtdOrganization.getParents()).isEqualTo(UPDATED_PARENTS);
        assertThat(testMtdOrganization.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdOrganization.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdOrganization.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdOrganization.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdOrganization.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdOrganization.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdOrganization in Elasticsearch
        MtdOrganization mtdOrganizationEs = mtdOrganizationSearchRepository.findOne(testMtdOrganization.getId());
        assertThat(mtdOrganizationEs).isEqualToComparingFieldByField(testMtdOrganization);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdOrganization() throws Exception {
        int databaseSizeBeforeUpdate = mtdOrganizationRepository.findAll().size();

        // Create the MtdOrganization
        MtdOrganizationDTO mtdOrganizationDTO = mtdOrganizationMapper.toDto(mtdOrganization);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdOrganizationMockMvc.perform(put("/api/mtd-organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdOrganizationDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdOrganization in the database
        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdOrganization() throws Exception {
        // Initialize the database
        mtdOrganizationRepository.saveAndFlush(mtdOrganization);
        mtdOrganizationSearchRepository.save(mtdOrganization);
        int databaseSizeBeforeDelete = mtdOrganizationRepository.findAll().size();

        // Get the mtdOrganization
        restMtdOrganizationMockMvc.perform(delete("/api/mtd-organizations/{id}", mtdOrganization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdOrganizationExistsInEs = mtdOrganizationSearchRepository.exists(mtdOrganization.getId());
        assertThat(mtdOrganizationExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdOrganization> mtdOrganizationList = mtdOrganizationRepository.findAll();
        assertThat(mtdOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdOrganization() throws Exception {
        // Initialize the database
        mtdOrganizationRepository.saveAndFlush(mtdOrganization);
        mtdOrganizationSearchRepository.save(mtdOrganization);

        // Search the mtdOrganization
        restMtdOrganizationMockMvc.perform(get("/api/_search/mtd-organizations?query=id:" + mtdOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].organizationcode").value(hasItem(DEFAULT_ORGANIZATIONCODE.toString())))
            .andExpect(jsonPath("$.[*].organizationname").value(hasItem(DEFAULT_ORGANIZATIONNAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mobilenum").value(hasItem(DEFAULT_MOBILENUM.toString())))
            .andExpect(jsonPath("$.[*].phonenum").value(hasItem(DEFAULT_PHONENUM.toString())))
            .andExpect(jsonPath("$.[*].parents").value(hasItem(DEFAULT_PARENTS)))
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
        TestUtil.equalsVerifier(MtdOrganization.class);
        MtdOrganization mtdOrganization1 = new MtdOrganization();
        mtdOrganization1.setId(1L);
        MtdOrganization mtdOrganization2 = new MtdOrganization();
        mtdOrganization2.setId(mtdOrganization1.getId());
        assertThat(mtdOrganization1).isEqualTo(mtdOrganization2);
        mtdOrganization2.setId(2L);
        assertThat(mtdOrganization1).isNotEqualTo(mtdOrganization2);
        mtdOrganization1.setId(null);
        assertThat(mtdOrganization1).isNotEqualTo(mtdOrganization2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdOrganizationDTO.class);
        MtdOrganizationDTO mtdOrganizationDTO1 = new MtdOrganizationDTO();
        mtdOrganizationDTO1.setId(1L);
        MtdOrganizationDTO mtdOrganizationDTO2 = new MtdOrganizationDTO();
        assertThat(mtdOrganizationDTO1).isNotEqualTo(mtdOrganizationDTO2);
        mtdOrganizationDTO2.setId(mtdOrganizationDTO1.getId());
        assertThat(mtdOrganizationDTO1).isEqualTo(mtdOrganizationDTO2);
        mtdOrganizationDTO2.setId(2L);
        assertThat(mtdOrganizationDTO1).isNotEqualTo(mtdOrganizationDTO2);
        mtdOrganizationDTO1.setId(null);
        assertThat(mtdOrganizationDTO1).isNotEqualTo(mtdOrganizationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdOrganizationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdOrganizationMapper.fromId(null)).isNull();
    }
}
