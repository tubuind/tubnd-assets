package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdProvince;
import com.astcore.repository.MtdProvinceRepository;
import com.astcore.service.MtdProvinceService;
import com.astcore.repository.search.MtdProvinceSearchRepository;
import com.astcore.service.dto.MtdProvinceDTO;
import com.astcore.service.mapper.MtdProvinceMapper;
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
 * Test class for the MtdProvinceResource REST controller.
 *
 * @see MtdProvinceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdProvinceResourceIntTest {

    private static final String DEFAULT_PROVINCECODE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCECODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCENAME = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCENAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PROLATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROLATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROLONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROLONGITUDE = new BigDecimal(2);

    private static final String DEFAULT_COUNTRYNAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRYNAME = "BBBBBBBBBB";

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
    private MtdProvinceRepository mtdProvinceRepository;

    @Autowired
    private MtdProvinceMapper mtdProvinceMapper;

    @Autowired
    private MtdProvinceService mtdProvinceService;

    @Autowired
    private MtdProvinceSearchRepository mtdProvinceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdProvinceMockMvc;

    private MtdProvince mtdProvince;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdProvinceResource mtdProvinceResource = new MtdProvinceResource(mtdProvinceService);
        this.restMtdProvinceMockMvc = MockMvcBuilders.standaloneSetup(mtdProvinceResource)
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
    public static MtdProvince createEntity(EntityManager em) {
        MtdProvince mtdProvince = new MtdProvince()
            .provincecode(DEFAULT_PROVINCECODE)
            .provincename(DEFAULT_PROVINCENAME)
            .prolatitude(DEFAULT_PROLATITUDE)
            .prolongitude(DEFAULT_PROLONGITUDE)
            .countryname(DEFAULT_COUNTRYNAME)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdProvince;
    }

    @Before
    public void initTest() {
        mtdProvinceSearchRepository.deleteAll();
        mtdProvince = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdProvince() throws Exception {
        int databaseSizeBeforeCreate = mtdProvinceRepository.findAll().size();

        // Create the MtdProvince
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);
        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdProvince in the database
        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeCreate + 1);
        MtdProvince testMtdProvince = mtdProvinceList.get(mtdProvinceList.size() - 1);
        assertThat(testMtdProvince.getProvincecode()).isEqualTo(DEFAULT_PROVINCECODE);
        assertThat(testMtdProvince.getProvincename()).isEqualTo(DEFAULT_PROVINCENAME);
        assertThat(testMtdProvince.getProlatitude()).isEqualTo(DEFAULT_PROLATITUDE);
        assertThat(testMtdProvince.getProlongitude()).isEqualTo(DEFAULT_PROLONGITUDE);
        assertThat(testMtdProvince.getCountryname()).isEqualTo(DEFAULT_COUNTRYNAME);
        assertThat(testMtdProvince.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdProvince.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdProvince.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdProvince.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdProvince.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdProvince.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdProvince in Elasticsearch
        MtdProvince mtdProvinceEs = mtdProvinceSearchRepository.findOne(testMtdProvince.getId());
        assertThat(mtdProvinceEs).isEqualToComparingFieldByField(testMtdProvince);
    }

    @Test
    @Transactional
    public void createMtdProvinceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdProvinceRepository.findAll().size();

        // Create the MtdProvince with an existing ID
        mtdProvince.setId(1L);
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProvincecodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdProvinceRepository.findAll().size();
        // set the field null
        mtdProvince.setProvincecode(null);

        // Create the MtdProvince, which fails.
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isBadRequest());

        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvincenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdProvinceRepository.findAll().size();
        // set the field null
        mtdProvince.setProvincename(null);

        // Create the MtdProvince, which fails.
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isBadRequest());

        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountrynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdProvinceRepository.findAll().size();
        // set the field null
        mtdProvince.setCountryname(null);

        // Create the MtdProvince, which fails.
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isBadRequest());

        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdProvinceRepository.findAll().size();
        // set the field null
        mtdProvince.setActive(null);

        // Create the MtdProvince, which fails.
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isBadRequest());

        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdProvinceRepository.findAll().size();
        // set the field null
        mtdProvince.setIsdel(null);

        // Create the MtdProvince, which fails.
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        restMtdProvinceMockMvc.perform(post("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isBadRequest());

        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdProvinces() throws Exception {
        // Initialize the database
        mtdProvinceRepository.saveAndFlush(mtdProvince);

        // Get all the mtdProvinceList
        restMtdProvinceMockMvc.perform(get("/api/mtd-provinces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdProvince.getId().intValue())))
            .andExpect(jsonPath("$.[*].provincecode").value(hasItem(DEFAULT_PROVINCECODE.toString())))
            .andExpect(jsonPath("$.[*].provincename").value(hasItem(DEFAULT_PROVINCENAME.toString())))
            .andExpect(jsonPath("$.[*].prolatitude").value(hasItem(DEFAULT_PROLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].prolongitude").value(hasItem(DEFAULT_PROLONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].countryname").value(hasItem(DEFAULT_COUNTRYNAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdProvince() throws Exception {
        // Initialize the database
        mtdProvinceRepository.saveAndFlush(mtdProvince);

        // Get the mtdProvince
        restMtdProvinceMockMvc.perform(get("/api/mtd-provinces/{id}", mtdProvince.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdProvince.getId().intValue()))
            .andExpect(jsonPath("$.provincecode").value(DEFAULT_PROVINCECODE.toString()))
            .andExpect(jsonPath("$.provincename").value(DEFAULT_PROVINCENAME.toString()))
            .andExpect(jsonPath("$.prolatitude").value(DEFAULT_PROLATITUDE.intValue()))
            .andExpect(jsonPath("$.prolongitude").value(DEFAULT_PROLONGITUDE.intValue()))
            .andExpect(jsonPath("$.countryname").value(DEFAULT_COUNTRYNAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdProvince() throws Exception {
        // Get the mtdProvince
        restMtdProvinceMockMvc.perform(get("/api/mtd-provinces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdProvince() throws Exception {
        // Initialize the database
        mtdProvinceRepository.saveAndFlush(mtdProvince);
        mtdProvinceSearchRepository.save(mtdProvince);
        int databaseSizeBeforeUpdate = mtdProvinceRepository.findAll().size();

        // Update the mtdProvince
        MtdProvince updatedMtdProvince = mtdProvinceRepository.findOne(mtdProvince.getId());
        updatedMtdProvince
            .provincecode(UPDATED_PROVINCECODE)
            .provincename(UPDATED_PROVINCENAME)
            .prolatitude(UPDATED_PROLATITUDE)
            .prolongitude(UPDATED_PROLONGITUDE)
            .countryname(UPDATED_COUNTRYNAME)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(updatedMtdProvince);

        restMtdProvinceMockMvc.perform(put("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isOk());

        // Validate the MtdProvince in the database
        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeUpdate);
        MtdProvince testMtdProvince = mtdProvinceList.get(mtdProvinceList.size() - 1);
        assertThat(testMtdProvince.getProvincecode()).isEqualTo(UPDATED_PROVINCECODE);
        assertThat(testMtdProvince.getProvincename()).isEqualTo(UPDATED_PROVINCENAME);
        assertThat(testMtdProvince.getProlatitude()).isEqualTo(UPDATED_PROLATITUDE);
        assertThat(testMtdProvince.getProlongitude()).isEqualTo(UPDATED_PROLONGITUDE);
        assertThat(testMtdProvince.getCountryname()).isEqualTo(UPDATED_COUNTRYNAME);
        assertThat(testMtdProvince.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdProvince.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdProvince.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdProvince.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdProvince.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdProvince.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdProvince in Elasticsearch
        MtdProvince mtdProvinceEs = mtdProvinceSearchRepository.findOne(testMtdProvince.getId());
        assertThat(mtdProvinceEs).isEqualToComparingFieldByField(testMtdProvince);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdProvince() throws Exception {
        int databaseSizeBeforeUpdate = mtdProvinceRepository.findAll().size();

        // Create the MtdProvince
        MtdProvinceDTO mtdProvinceDTO = mtdProvinceMapper.toDto(mtdProvince);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdProvinceMockMvc.perform(put("/api/mtd-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdProvinceDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdProvince in the database
        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdProvince() throws Exception {
        // Initialize the database
        mtdProvinceRepository.saveAndFlush(mtdProvince);
        mtdProvinceSearchRepository.save(mtdProvince);
        int databaseSizeBeforeDelete = mtdProvinceRepository.findAll().size();

        // Get the mtdProvince
        restMtdProvinceMockMvc.perform(delete("/api/mtd-provinces/{id}", mtdProvince.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdProvinceExistsInEs = mtdProvinceSearchRepository.exists(mtdProvince.getId());
        assertThat(mtdProvinceExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdProvince> mtdProvinceList = mtdProvinceRepository.findAll();
        assertThat(mtdProvinceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdProvince() throws Exception {
        // Initialize the database
        mtdProvinceRepository.saveAndFlush(mtdProvince);
        mtdProvinceSearchRepository.save(mtdProvince);

        // Search the mtdProvince
        restMtdProvinceMockMvc.perform(get("/api/_search/mtd-provinces?query=id:" + mtdProvince.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdProvince.getId().intValue())))
            .andExpect(jsonPath("$.[*].provincecode").value(hasItem(DEFAULT_PROVINCECODE.toString())))
            .andExpect(jsonPath("$.[*].provincename").value(hasItem(DEFAULT_PROVINCENAME.toString())))
            .andExpect(jsonPath("$.[*].prolatitude").value(hasItem(DEFAULT_PROLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].prolongitude").value(hasItem(DEFAULT_PROLONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].countryname").value(hasItem(DEFAULT_COUNTRYNAME.toString())))
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
        TestUtil.equalsVerifier(MtdProvince.class);
        MtdProvince mtdProvince1 = new MtdProvince();
        mtdProvince1.setId(1L);
        MtdProvince mtdProvince2 = new MtdProvince();
        mtdProvince2.setId(mtdProvince1.getId());
        assertThat(mtdProvince1).isEqualTo(mtdProvince2);
        mtdProvince2.setId(2L);
        assertThat(mtdProvince1).isNotEqualTo(mtdProvince2);
        mtdProvince1.setId(null);
        assertThat(mtdProvince1).isNotEqualTo(mtdProvince2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdProvinceDTO.class);
        MtdProvinceDTO mtdProvinceDTO1 = new MtdProvinceDTO();
        mtdProvinceDTO1.setId(1L);
        MtdProvinceDTO mtdProvinceDTO2 = new MtdProvinceDTO();
        assertThat(mtdProvinceDTO1).isNotEqualTo(mtdProvinceDTO2);
        mtdProvinceDTO2.setId(mtdProvinceDTO1.getId());
        assertThat(mtdProvinceDTO1).isEqualTo(mtdProvinceDTO2);
        mtdProvinceDTO2.setId(2L);
        assertThat(mtdProvinceDTO1).isNotEqualTo(mtdProvinceDTO2);
        mtdProvinceDTO1.setId(null);
        assertThat(mtdProvinceDTO1).isNotEqualTo(mtdProvinceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdProvinceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdProvinceMapper.fromId(null)).isNull();
    }
}
