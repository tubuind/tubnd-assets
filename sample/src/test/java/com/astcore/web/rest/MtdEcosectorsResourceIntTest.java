package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdEcosectors;
import com.astcore.repository.MtdEcosectorsRepository;
import com.astcore.service.MtdEcosectorsService;
import com.astcore.repository.search.MtdEcosectorsSearchRepository;
import com.astcore.service.dto.MtdEcosectorsDTO;
import com.astcore.service.mapper.MtdEcosectorsMapper;
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
 * Test class for the MtdEcosectorsResource REST controller.
 *
 * @see MtdEcosectorsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdEcosectorsResourceIntTest {

    private static final String DEFAULT_ECONAME = "AAAAAAAAAA";
    private static final String UPDATED_ECONAME = "BBBBBBBBBB";

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
    private MtdEcosectorsRepository mtdEcosectorsRepository;

    @Autowired
    private MtdEcosectorsMapper mtdEcosectorsMapper;

    @Autowired
    private MtdEcosectorsService mtdEcosectorsService;

    @Autowired
    private MtdEcosectorsSearchRepository mtdEcosectorsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdEcosectorsMockMvc;

    private MtdEcosectors mtdEcosectors;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdEcosectorsResource mtdEcosectorsResource = new MtdEcosectorsResource(mtdEcosectorsService);
        this.restMtdEcosectorsMockMvc = MockMvcBuilders.standaloneSetup(mtdEcosectorsResource)
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
    public static MtdEcosectors createEntity(EntityManager em) {
        MtdEcosectors mtdEcosectors = new MtdEcosectors()
            .econame(DEFAULT_ECONAME)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdEcosectors;
    }

    @Before
    public void initTest() {
        mtdEcosectorsSearchRepository.deleteAll();
        mtdEcosectors = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdEcosectors() throws Exception {
        int databaseSizeBeforeCreate = mtdEcosectorsRepository.findAll().size();

        // Create the MtdEcosectors
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(mtdEcosectors);
        restMtdEcosectorsMockMvc.perform(post("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdEcosectors in the database
        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeCreate + 1);
        MtdEcosectors testMtdEcosectors = mtdEcosectorsList.get(mtdEcosectorsList.size() - 1);
        assertThat(testMtdEcosectors.getEconame()).isEqualTo(DEFAULT_ECONAME);
        assertThat(testMtdEcosectors.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdEcosectors.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdEcosectors.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdEcosectors.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdEcosectors.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdEcosectors.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdEcosectors in Elasticsearch
        MtdEcosectors mtdEcosectorsEs = mtdEcosectorsSearchRepository.findOne(testMtdEcosectors.getId());
        assertThat(mtdEcosectorsEs).isEqualToComparingFieldByField(testMtdEcosectors);
    }

    @Test
    @Transactional
    public void createMtdEcosectorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdEcosectorsRepository.findAll().size();

        // Create the MtdEcosectors with an existing ID
        mtdEcosectors.setId(1L);
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(mtdEcosectors);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdEcosectorsMockMvc.perform(post("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEconameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdEcosectorsRepository.findAll().size();
        // set the field null
        mtdEcosectors.setEconame(null);

        // Create the MtdEcosectors, which fails.
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(mtdEcosectors);

        restMtdEcosectorsMockMvc.perform(post("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isBadRequest());

        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdEcosectorsRepository.findAll().size();
        // set the field null
        mtdEcosectors.setActive(null);

        // Create the MtdEcosectors, which fails.
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(mtdEcosectors);

        restMtdEcosectorsMockMvc.perform(post("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isBadRequest());

        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdEcosectorsRepository.findAll().size();
        // set the field null
        mtdEcosectors.setIsdel(null);

        // Create the MtdEcosectors, which fails.
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(mtdEcosectors);

        restMtdEcosectorsMockMvc.perform(post("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isBadRequest());

        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdEcosectors() throws Exception {
        // Initialize the database
        mtdEcosectorsRepository.saveAndFlush(mtdEcosectors);

        // Get all the mtdEcosectorsList
        restMtdEcosectorsMockMvc.perform(get("/api/mtd-ecosectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdEcosectors.getId().intValue())))
            .andExpect(jsonPath("$.[*].econame").value(hasItem(DEFAULT_ECONAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdEcosectors() throws Exception {
        // Initialize the database
        mtdEcosectorsRepository.saveAndFlush(mtdEcosectors);

        // Get the mtdEcosectors
        restMtdEcosectorsMockMvc.perform(get("/api/mtd-ecosectors/{id}", mtdEcosectors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdEcosectors.getId().intValue()))
            .andExpect(jsonPath("$.econame").value(DEFAULT_ECONAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdEcosectors() throws Exception {
        // Get the mtdEcosectors
        restMtdEcosectorsMockMvc.perform(get("/api/mtd-ecosectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdEcosectors() throws Exception {
        // Initialize the database
        mtdEcosectorsRepository.saveAndFlush(mtdEcosectors);
        mtdEcosectorsSearchRepository.save(mtdEcosectors);
        int databaseSizeBeforeUpdate = mtdEcosectorsRepository.findAll().size();

        // Update the mtdEcosectors
        MtdEcosectors updatedMtdEcosectors = mtdEcosectorsRepository.findOne(mtdEcosectors.getId());
        updatedMtdEcosectors
            .econame(UPDATED_ECONAME)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(updatedMtdEcosectors);

        restMtdEcosectorsMockMvc.perform(put("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isOk());

        // Validate the MtdEcosectors in the database
        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeUpdate);
        MtdEcosectors testMtdEcosectors = mtdEcosectorsList.get(mtdEcosectorsList.size() - 1);
        assertThat(testMtdEcosectors.getEconame()).isEqualTo(UPDATED_ECONAME);
        assertThat(testMtdEcosectors.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdEcosectors.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdEcosectors.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdEcosectors.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdEcosectors.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdEcosectors.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdEcosectors in Elasticsearch
        MtdEcosectors mtdEcosectorsEs = mtdEcosectorsSearchRepository.findOne(testMtdEcosectors.getId());
        assertThat(mtdEcosectorsEs).isEqualToComparingFieldByField(testMtdEcosectors);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdEcosectors() throws Exception {
        int databaseSizeBeforeUpdate = mtdEcosectorsRepository.findAll().size();

        // Create the MtdEcosectors
        MtdEcosectorsDTO mtdEcosectorsDTO = mtdEcosectorsMapper.toDto(mtdEcosectors);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdEcosectorsMockMvc.perform(put("/api/mtd-ecosectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdEcosectorsDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdEcosectors in the database
        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdEcosectors() throws Exception {
        // Initialize the database
        mtdEcosectorsRepository.saveAndFlush(mtdEcosectors);
        mtdEcosectorsSearchRepository.save(mtdEcosectors);
        int databaseSizeBeforeDelete = mtdEcosectorsRepository.findAll().size();

        // Get the mtdEcosectors
        restMtdEcosectorsMockMvc.perform(delete("/api/mtd-ecosectors/{id}", mtdEcosectors.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdEcosectorsExistsInEs = mtdEcosectorsSearchRepository.exists(mtdEcosectors.getId());
        assertThat(mtdEcosectorsExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdEcosectors> mtdEcosectorsList = mtdEcosectorsRepository.findAll();
        assertThat(mtdEcosectorsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdEcosectors() throws Exception {
        // Initialize the database
        mtdEcosectorsRepository.saveAndFlush(mtdEcosectors);
        mtdEcosectorsSearchRepository.save(mtdEcosectors);

        // Search the mtdEcosectors
        restMtdEcosectorsMockMvc.perform(get("/api/_search/mtd-ecosectors?query=id:" + mtdEcosectors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdEcosectors.getId().intValue())))
            .andExpect(jsonPath("$.[*].econame").value(hasItem(DEFAULT_ECONAME.toString())))
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
        TestUtil.equalsVerifier(MtdEcosectors.class);
        MtdEcosectors mtdEcosectors1 = new MtdEcosectors();
        mtdEcosectors1.setId(1L);
        MtdEcosectors mtdEcosectors2 = new MtdEcosectors();
        mtdEcosectors2.setId(mtdEcosectors1.getId());
        assertThat(mtdEcosectors1).isEqualTo(mtdEcosectors2);
        mtdEcosectors2.setId(2L);
        assertThat(mtdEcosectors1).isNotEqualTo(mtdEcosectors2);
        mtdEcosectors1.setId(null);
        assertThat(mtdEcosectors1).isNotEqualTo(mtdEcosectors2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdEcosectorsDTO.class);
        MtdEcosectorsDTO mtdEcosectorsDTO1 = new MtdEcosectorsDTO();
        mtdEcosectorsDTO1.setId(1L);
        MtdEcosectorsDTO mtdEcosectorsDTO2 = new MtdEcosectorsDTO();
        assertThat(mtdEcosectorsDTO1).isNotEqualTo(mtdEcosectorsDTO2);
        mtdEcosectorsDTO2.setId(mtdEcosectorsDTO1.getId());
        assertThat(mtdEcosectorsDTO1).isEqualTo(mtdEcosectorsDTO2);
        mtdEcosectorsDTO2.setId(2L);
        assertThat(mtdEcosectorsDTO1).isNotEqualTo(mtdEcosectorsDTO2);
        mtdEcosectorsDTO1.setId(null);
        assertThat(mtdEcosectorsDTO1).isNotEqualTo(mtdEcosectorsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdEcosectorsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdEcosectorsMapper.fromId(null)).isNull();
    }
}
