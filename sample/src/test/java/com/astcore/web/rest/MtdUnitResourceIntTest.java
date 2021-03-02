package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdUnit;
import com.astcore.repository.MtdUnitRepository;
import com.astcore.service.MtdUnitService;
import com.astcore.repository.search.MtdUnitSearchRepository;
import com.astcore.service.dto.MtdUnitDTO;
import com.astcore.service.mapper.MtdUnitMapper;
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
 * Test class for the MtdUnitResource REST controller.
 *
 * @see MtdUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdUnitResourceIntTest {

    private static final String DEFAULT_UNITCODE = "AAAAAAAAAA";
    private static final String UPDATED_UNITCODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNITNAME = "AAAAAAAAAA";
    private static final String UPDATED_UNITNAME = "BBBBBBBBBB";

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
    private MtdUnitRepository mtdUnitRepository;

    @Autowired
    private MtdUnitMapper mtdUnitMapper;

    @Autowired
    private MtdUnitService mtdUnitService;

    @Autowired
    private MtdUnitSearchRepository mtdUnitSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdUnitMockMvc;

    private MtdUnit mtdUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdUnitResource mtdUnitResource = new MtdUnitResource(mtdUnitService);
        this.restMtdUnitMockMvc = MockMvcBuilders.standaloneSetup(mtdUnitResource)
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
    public static MtdUnit createEntity(EntityManager em) {
        MtdUnit mtdUnit = new MtdUnit()
            .unitcode(DEFAULT_UNITCODE)
            .unitname(DEFAULT_UNITNAME)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdUnit;
    }

    @Before
    public void initTest() {
        mtdUnitSearchRepository.deleteAll();
        mtdUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdUnit() throws Exception {
        int databaseSizeBeforeCreate = mtdUnitRepository.findAll().size();

        // Create the MtdUnit
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);
        restMtdUnitMockMvc.perform(post("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdUnit in the database
        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeCreate + 1);
        MtdUnit testMtdUnit = mtdUnitList.get(mtdUnitList.size() - 1);
        assertThat(testMtdUnit.getUnitcode()).isEqualTo(DEFAULT_UNITCODE);
        assertThat(testMtdUnit.getUnitname()).isEqualTo(DEFAULT_UNITNAME);
        assertThat(testMtdUnit.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdUnit.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdUnit.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdUnit.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdUnit.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdUnit.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdUnit in Elasticsearch
        MtdUnit mtdUnitEs = mtdUnitSearchRepository.findOne(testMtdUnit.getId());
        assertThat(mtdUnitEs).isEqualToComparingFieldByField(testMtdUnit);
    }

    @Test
    @Transactional
    public void createMtdUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdUnitRepository.findAll().size();

        // Create the MtdUnit with an existing ID
        mtdUnit.setId(1L);
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdUnitMockMvc.perform(post("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUnitcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdUnitRepository.findAll().size();
        // set the field null
        mtdUnit.setUnitcode(null);

        // Create the MtdUnit, which fails.
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);

        restMtdUnitMockMvc.perform(post("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isBadRequest());

        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdUnitRepository.findAll().size();
        // set the field null
        mtdUnit.setUnitname(null);

        // Create the MtdUnit, which fails.
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);

        restMtdUnitMockMvc.perform(post("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isBadRequest());

        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdUnitRepository.findAll().size();
        // set the field null
        mtdUnit.setActive(null);

        // Create the MtdUnit, which fails.
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);

        restMtdUnitMockMvc.perform(post("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isBadRequest());

        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdUnitRepository.findAll().size();
        // set the field null
        mtdUnit.setIsdel(null);

        // Create the MtdUnit, which fails.
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);

        restMtdUnitMockMvc.perform(post("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isBadRequest());

        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdUnits() throws Exception {
        // Initialize the database
        mtdUnitRepository.saveAndFlush(mtdUnit);

        // Get all the mtdUnitList
        restMtdUnitMockMvc.perform(get("/api/mtd-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitcode").value(hasItem(DEFAULT_UNITCODE.toString())))
            .andExpect(jsonPath("$.[*].unitname").value(hasItem(DEFAULT_UNITNAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdUnit() throws Exception {
        // Initialize the database
        mtdUnitRepository.saveAndFlush(mtdUnit);

        // Get the mtdUnit
        restMtdUnitMockMvc.perform(get("/api/mtd-units/{id}", mtdUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdUnit.getId().intValue()))
            .andExpect(jsonPath("$.unitcode").value(DEFAULT_UNITCODE.toString()))
            .andExpect(jsonPath("$.unitname").value(DEFAULT_UNITNAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdUnit() throws Exception {
        // Get the mtdUnit
        restMtdUnitMockMvc.perform(get("/api/mtd-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdUnit() throws Exception {
        // Initialize the database
        mtdUnitRepository.saveAndFlush(mtdUnit);
        mtdUnitSearchRepository.save(mtdUnit);
        int databaseSizeBeforeUpdate = mtdUnitRepository.findAll().size();

        // Update the mtdUnit
        MtdUnit updatedMtdUnit = mtdUnitRepository.findOne(mtdUnit.getId());
        updatedMtdUnit
            .unitcode(UPDATED_UNITCODE)
            .unitname(UPDATED_UNITNAME)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(updatedMtdUnit);

        restMtdUnitMockMvc.perform(put("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isOk());

        // Validate the MtdUnit in the database
        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeUpdate);
        MtdUnit testMtdUnit = mtdUnitList.get(mtdUnitList.size() - 1);
        assertThat(testMtdUnit.getUnitcode()).isEqualTo(UPDATED_UNITCODE);
        assertThat(testMtdUnit.getUnitname()).isEqualTo(UPDATED_UNITNAME);
        assertThat(testMtdUnit.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdUnit.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdUnit.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdUnit.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdUnit.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdUnit.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdUnit in Elasticsearch
        MtdUnit mtdUnitEs = mtdUnitSearchRepository.findOne(testMtdUnit.getId());
        assertThat(mtdUnitEs).isEqualToComparingFieldByField(testMtdUnit);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdUnit() throws Exception {
        int databaseSizeBeforeUpdate = mtdUnitRepository.findAll().size();

        // Create the MtdUnit
        MtdUnitDTO mtdUnitDTO = mtdUnitMapper.toDto(mtdUnit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdUnitMockMvc.perform(put("/api/mtd-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdUnit in the database
        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdUnit() throws Exception {
        // Initialize the database
        mtdUnitRepository.saveAndFlush(mtdUnit);
        mtdUnitSearchRepository.save(mtdUnit);
        int databaseSizeBeforeDelete = mtdUnitRepository.findAll().size();

        // Get the mtdUnit
        restMtdUnitMockMvc.perform(delete("/api/mtd-units/{id}", mtdUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdUnitExistsInEs = mtdUnitSearchRepository.exists(mtdUnit.getId());
        assertThat(mtdUnitExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdUnit> mtdUnitList = mtdUnitRepository.findAll();
        assertThat(mtdUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdUnit() throws Exception {
        // Initialize the database
        mtdUnitRepository.saveAndFlush(mtdUnit);
        mtdUnitSearchRepository.save(mtdUnit);

        // Search the mtdUnit
        restMtdUnitMockMvc.perform(get("/api/_search/mtd-units?query=id:" + mtdUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitcode").value(hasItem(DEFAULT_UNITCODE.toString())))
            .andExpect(jsonPath("$.[*].unitname").value(hasItem(DEFAULT_UNITNAME.toString())))
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
        TestUtil.equalsVerifier(MtdUnit.class);
        MtdUnit mtdUnit1 = new MtdUnit();
        mtdUnit1.setId(1L);
        MtdUnit mtdUnit2 = new MtdUnit();
        mtdUnit2.setId(mtdUnit1.getId());
        assertThat(mtdUnit1).isEqualTo(mtdUnit2);
        mtdUnit2.setId(2L);
        assertThat(mtdUnit1).isNotEqualTo(mtdUnit2);
        mtdUnit1.setId(null);
        assertThat(mtdUnit1).isNotEqualTo(mtdUnit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdUnitDTO.class);
        MtdUnitDTO mtdUnitDTO1 = new MtdUnitDTO();
        mtdUnitDTO1.setId(1L);
        MtdUnitDTO mtdUnitDTO2 = new MtdUnitDTO();
        assertThat(mtdUnitDTO1).isNotEqualTo(mtdUnitDTO2);
        mtdUnitDTO2.setId(mtdUnitDTO1.getId());
        assertThat(mtdUnitDTO1).isEqualTo(mtdUnitDTO2);
        mtdUnitDTO2.setId(2L);
        assertThat(mtdUnitDTO1).isNotEqualTo(mtdUnitDTO2);
        mtdUnitDTO1.setId(null);
        assertThat(mtdUnitDTO1).isNotEqualTo(mtdUnitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdUnitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdUnitMapper.fromId(null)).isNull();
    }
}
