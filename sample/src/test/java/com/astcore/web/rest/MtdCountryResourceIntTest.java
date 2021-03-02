package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdCountry;
import com.astcore.repository.MtdCountryRepository;
import com.astcore.service.MtdCountryService;
import com.astcore.repository.search.MtdCountrySearchRepository;
import com.astcore.service.dto.MtdCountryDTO;
import com.astcore.service.mapper.MtdCountryMapper;
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
 * Test class for the MtdCountryResource REST controller.
 *
 * @see MtdCountryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdCountryResourceIntTest {

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
    private MtdCountryRepository mtdCountryRepository;

    @Autowired
    private MtdCountryMapper mtdCountryMapper;

    @Autowired
    private MtdCountryService mtdCountryService;

    @Autowired
    private MtdCountrySearchRepository mtdCountrySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdCountryMockMvc;

    private MtdCountry mtdCountry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdCountryResource mtdCountryResource = new MtdCountryResource(mtdCountryService);
        this.restMtdCountryMockMvc = MockMvcBuilders.standaloneSetup(mtdCountryResource)
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
    public static MtdCountry createEntity(EntityManager em) {
        MtdCountry mtdCountry = new MtdCountry()
            .countryname(DEFAULT_COUNTRYNAME)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdCountry;
    }

    @Before
    public void initTest() {
        mtdCountrySearchRepository.deleteAll();
        mtdCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdCountry() throws Exception {
        int databaseSizeBeforeCreate = mtdCountryRepository.findAll().size();

        // Create the MtdCountry
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(mtdCountry);
        restMtdCountryMockMvc.perform(post("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdCountry in the database
        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeCreate + 1);
        MtdCountry testMtdCountry = mtdCountryList.get(mtdCountryList.size() - 1);
        assertThat(testMtdCountry.getCountryname()).isEqualTo(DEFAULT_COUNTRYNAME);
        assertThat(testMtdCountry.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdCountry.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdCountry.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdCountry.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdCountry.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdCountry.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdCountry in Elasticsearch
        MtdCountry mtdCountryEs = mtdCountrySearchRepository.findOne(testMtdCountry.getId());
        assertThat(mtdCountryEs).isEqualToComparingFieldByField(testMtdCountry);
    }

    @Test
    @Transactional
    public void createMtdCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdCountryRepository.findAll().size();

        // Create the MtdCountry with an existing ID
        mtdCountry.setId(1L);
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(mtdCountry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdCountryMockMvc.perform(post("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCountrynameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdCountryRepository.findAll().size();
        // set the field null
        mtdCountry.setCountryname(null);

        // Create the MtdCountry, which fails.
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(mtdCountry);

        restMtdCountryMockMvc.perform(post("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isBadRequest());

        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdCountryRepository.findAll().size();
        // set the field null
        mtdCountry.setActive(null);

        // Create the MtdCountry, which fails.
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(mtdCountry);

        restMtdCountryMockMvc.perform(post("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isBadRequest());

        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdCountryRepository.findAll().size();
        // set the field null
        mtdCountry.setIsdel(null);

        // Create the MtdCountry, which fails.
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(mtdCountry);

        restMtdCountryMockMvc.perform(post("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isBadRequest());

        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdCountries() throws Exception {
        // Initialize the database
        mtdCountryRepository.saveAndFlush(mtdCountry);

        // Get all the mtdCountryList
        restMtdCountryMockMvc.perform(get("/api/mtd-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdCountry.getId().intValue())))
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
    public void getMtdCountry() throws Exception {
        // Initialize the database
        mtdCountryRepository.saveAndFlush(mtdCountry);

        // Get the mtdCountry
        restMtdCountryMockMvc.perform(get("/api/mtd-countries/{id}", mtdCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdCountry.getId().intValue()))
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
    public void getNonExistingMtdCountry() throws Exception {
        // Get the mtdCountry
        restMtdCountryMockMvc.perform(get("/api/mtd-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdCountry() throws Exception {
        // Initialize the database
        mtdCountryRepository.saveAndFlush(mtdCountry);
        mtdCountrySearchRepository.save(mtdCountry);
        int databaseSizeBeforeUpdate = mtdCountryRepository.findAll().size();

        // Update the mtdCountry
        MtdCountry updatedMtdCountry = mtdCountryRepository.findOne(mtdCountry.getId());
        updatedMtdCountry
            .countryname(UPDATED_COUNTRYNAME)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(updatedMtdCountry);

        restMtdCountryMockMvc.perform(put("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isOk());

        // Validate the MtdCountry in the database
        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeUpdate);
        MtdCountry testMtdCountry = mtdCountryList.get(mtdCountryList.size() - 1);
        assertThat(testMtdCountry.getCountryname()).isEqualTo(UPDATED_COUNTRYNAME);
        assertThat(testMtdCountry.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdCountry.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdCountry.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdCountry.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdCountry.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdCountry.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdCountry in Elasticsearch
        MtdCountry mtdCountryEs = mtdCountrySearchRepository.findOne(testMtdCountry.getId());
        assertThat(mtdCountryEs).isEqualToComparingFieldByField(testMtdCountry);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdCountry() throws Exception {
        int databaseSizeBeforeUpdate = mtdCountryRepository.findAll().size();

        // Create the MtdCountry
        MtdCountryDTO mtdCountryDTO = mtdCountryMapper.toDto(mtdCountry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdCountryMockMvc.perform(put("/api/mtd-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdCountry in the database
        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdCountry() throws Exception {
        // Initialize the database
        mtdCountryRepository.saveAndFlush(mtdCountry);
        mtdCountrySearchRepository.save(mtdCountry);
        int databaseSizeBeforeDelete = mtdCountryRepository.findAll().size();

        // Get the mtdCountry
        restMtdCountryMockMvc.perform(delete("/api/mtd-countries/{id}", mtdCountry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdCountryExistsInEs = mtdCountrySearchRepository.exists(mtdCountry.getId());
        assertThat(mtdCountryExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdCountry> mtdCountryList = mtdCountryRepository.findAll();
        assertThat(mtdCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdCountry() throws Exception {
        // Initialize the database
        mtdCountryRepository.saveAndFlush(mtdCountry);
        mtdCountrySearchRepository.save(mtdCountry);

        // Search the mtdCountry
        restMtdCountryMockMvc.perform(get("/api/_search/mtd-countries?query=id:" + mtdCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdCountry.getId().intValue())))
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
        TestUtil.equalsVerifier(MtdCountry.class);
        MtdCountry mtdCountry1 = new MtdCountry();
        mtdCountry1.setId(1L);
        MtdCountry mtdCountry2 = new MtdCountry();
        mtdCountry2.setId(mtdCountry1.getId());
        assertThat(mtdCountry1).isEqualTo(mtdCountry2);
        mtdCountry2.setId(2L);
        assertThat(mtdCountry1).isNotEqualTo(mtdCountry2);
        mtdCountry1.setId(null);
        assertThat(mtdCountry1).isNotEqualTo(mtdCountry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdCountryDTO.class);
        MtdCountryDTO mtdCountryDTO1 = new MtdCountryDTO();
        mtdCountryDTO1.setId(1L);
        MtdCountryDTO mtdCountryDTO2 = new MtdCountryDTO();
        assertThat(mtdCountryDTO1).isNotEqualTo(mtdCountryDTO2);
        mtdCountryDTO2.setId(mtdCountryDTO1.getId());
        assertThat(mtdCountryDTO1).isEqualTo(mtdCountryDTO2);
        mtdCountryDTO2.setId(2L);
        assertThat(mtdCountryDTO1).isNotEqualTo(mtdCountryDTO2);
        mtdCountryDTO1.setId(null);
        assertThat(mtdCountryDTO1).isNotEqualTo(mtdCountryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdCountryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdCountryMapper.fromId(null)).isNull();
    }
}
