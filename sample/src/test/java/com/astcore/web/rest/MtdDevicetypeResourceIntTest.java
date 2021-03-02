package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdDevicetype;
import com.astcore.repository.MtdDevicetypeRepository;
import com.astcore.service.MtdDevicetypeService;
import com.astcore.repository.search.MtdDevicetypeSearchRepository;
import com.astcore.service.dto.MtdDevicetypeDTO;
import com.astcore.service.mapper.MtdDevicetypeMapper;
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
 * Test class for the MtdDevicetypeResource REST controller.
 *
 * @see MtdDevicetypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdDevicetypeResourceIntTest {

    private static final String DEFAULT_DEVICETYPENAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICETYPENAME = "BBBBBBBBBB";

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
    private MtdDevicetypeRepository mtdDevicetypeRepository;

    @Autowired
    private MtdDevicetypeMapper mtdDevicetypeMapper;

    @Autowired
    private MtdDevicetypeService mtdDevicetypeService;

    @Autowired
    private MtdDevicetypeSearchRepository mtdDevicetypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdDevicetypeMockMvc;

    private MtdDevicetype mtdDevicetype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdDevicetypeResource mtdDevicetypeResource = new MtdDevicetypeResource(mtdDevicetypeService);
        this.restMtdDevicetypeMockMvc = MockMvcBuilders.standaloneSetup(mtdDevicetypeResource)
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
    public static MtdDevicetype createEntity(EntityManager em) {
        MtdDevicetype mtdDevicetype = new MtdDevicetype()
            .devicetypename(DEFAULT_DEVICETYPENAME)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdDevicetype;
    }

    @Before
    public void initTest() {
        mtdDevicetypeSearchRepository.deleteAll();
        mtdDevicetype = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdDevicetype() throws Exception {
        int databaseSizeBeforeCreate = mtdDevicetypeRepository.findAll().size();

        // Create the MtdDevicetype
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(mtdDevicetype);
        restMtdDevicetypeMockMvc.perform(post("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdDevicetype in the database
        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeCreate + 1);
        MtdDevicetype testMtdDevicetype = mtdDevicetypeList.get(mtdDevicetypeList.size() - 1);
        assertThat(testMtdDevicetype.getDevicetypename()).isEqualTo(DEFAULT_DEVICETYPENAME);
        assertThat(testMtdDevicetype.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdDevicetype.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdDevicetype.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdDevicetype.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdDevicetype.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdDevicetype.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdDevicetype in Elasticsearch
        MtdDevicetype mtdDevicetypeEs = mtdDevicetypeSearchRepository.findOne(testMtdDevicetype.getId());
        assertThat(mtdDevicetypeEs).isEqualToComparingFieldByField(testMtdDevicetype);
    }

    @Test
    @Transactional
    public void createMtdDevicetypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdDevicetypeRepository.findAll().size();

        // Create the MtdDevicetype with an existing ID
        mtdDevicetype.setId(1L);
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(mtdDevicetype);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdDevicetypeMockMvc.perform(post("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDevicetypenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicetypeRepository.findAll().size();
        // set the field null
        mtdDevicetype.setDevicetypename(null);

        // Create the MtdDevicetype, which fails.
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(mtdDevicetype);

        restMtdDevicetypeMockMvc.perform(post("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicetypeRepository.findAll().size();
        // set the field null
        mtdDevicetype.setActive(null);

        // Create the MtdDevicetype, which fails.
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(mtdDevicetype);

        restMtdDevicetypeMockMvc.perform(post("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDevicetypeRepository.findAll().size();
        // set the field null
        mtdDevicetype.setIsdel(null);

        // Create the MtdDevicetype, which fails.
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(mtdDevicetype);

        restMtdDevicetypeMockMvc.perform(post("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdDevicetypes() throws Exception {
        // Initialize the database
        mtdDevicetypeRepository.saveAndFlush(mtdDevicetype);

        // Get all the mtdDevicetypeList
        restMtdDevicetypeMockMvc.perform(get("/api/mtd-devicetypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdDevicetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicetypename").value(hasItem(DEFAULT_DEVICETYPENAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdDevicetype() throws Exception {
        // Initialize the database
        mtdDevicetypeRepository.saveAndFlush(mtdDevicetype);

        // Get the mtdDevicetype
        restMtdDevicetypeMockMvc.perform(get("/api/mtd-devicetypes/{id}", mtdDevicetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdDevicetype.getId().intValue()))
            .andExpect(jsonPath("$.devicetypename").value(DEFAULT_DEVICETYPENAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdDevicetype() throws Exception {
        // Get the mtdDevicetype
        restMtdDevicetypeMockMvc.perform(get("/api/mtd-devicetypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdDevicetype() throws Exception {
        // Initialize the database
        mtdDevicetypeRepository.saveAndFlush(mtdDevicetype);
        mtdDevicetypeSearchRepository.save(mtdDevicetype);
        int databaseSizeBeforeUpdate = mtdDevicetypeRepository.findAll().size();

        // Update the mtdDevicetype
        MtdDevicetype updatedMtdDevicetype = mtdDevicetypeRepository.findOne(mtdDevicetype.getId());
        updatedMtdDevicetype
            .devicetypename(UPDATED_DEVICETYPENAME)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(updatedMtdDevicetype);

        restMtdDevicetypeMockMvc.perform(put("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isOk());

        // Validate the MtdDevicetype in the database
        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeUpdate);
        MtdDevicetype testMtdDevicetype = mtdDevicetypeList.get(mtdDevicetypeList.size() - 1);
        assertThat(testMtdDevicetype.getDevicetypename()).isEqualTo(UPDATED_DEVICETYPENAME);
        assertThat(testMtdDevicetype.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdDevicetype.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdDevicetype.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdDevicetype.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdDevicetype.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdDevicetype.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdDevicetype in Elasticsearch
        MtdDevicetype mtdDevicetypeEs = mtdDevicetypeSearchRepository.findOne(testMtdDevicetype.getId());
        assertThat(mtdDevicetypeEs).isEqualToComparingFieldByField(testMtdDevicetype);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdDevicetype() throws Exception {
        int databaseSizeBeforeUpdate = mtdDevicetypeRepository.findAll().size();

        // Create the MtdDevicetype
        MtdDevicetypeDTO mtdDevicetypeDTO = mtdDevicetypeMapper.toDto(mtdDevicetype);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdDevicetypeMockMvc.perform(put("/api/mtd-devicetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDevicetypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdDevicetype in the database
        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdDevicetype() throws Exception {
        // Initialize the database
        mtdDevicetypeRepository.saveAndFlush(mtdDevicetype);
        mtdDevicetypeSearchRepository.save(mtdDevicetype);
        int databaseSizeBeforeDelete = mtdDevicetypeRepository.findAll().size();

        // Get the mtdDevicetype
        restMtdDevicetypeMockMvc.perform(delete("/api/mtd-devicetypes/{id}", mtdDevicetype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdDevicetypeExistsInEs = mtdDevicetypeSearchRepository.exists(mtdDevicetype.getId());
        assertThat(mtdDevicetypeExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdDevicetype> mtdDevicetypeList = mtdDevicetypeRepository.findAll();
        assertThat(mtdDevicetypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdDevicetype() throws Exception {
        // Initialize the database
        mtdDevicetypeRepository.saveAndFlush(mtdDevicetype);
        mtdDevicetypeSearchRepository.save(mtdDevicetype);

        // Search the mtdDevicetype
        restMtdDevicetypeMockMvc.perform(get("/api/_search/mtd-devicetypes?query=id:" + mtdDevicetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdDevicetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].devicetypename").value(hasItem(DEFAULT_DEVICETYPENAME.toString())))
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
        TestUtil.equalsVerifier(MtdDevicetype.class);
        MtdDevicetype mtdDevicetype1 = new MtdDevicetype();
        mtdDevicetype1.setId(1L);
        MtdDevicetype mtdDevicetype2 = new MtdDevicetype();
        mtdDevicetype2.setId(mtdDevicetype1.getId());
        assertThat(mtdDevicetype1).isEqualTo(mtdDevicetype2);
        mtdDevicetype2.setId(2L);
        assertThat(mtdDevicetype1).isNotEqualTo(mtdDevicetype2);
        mtdDevicetype1.setId(null);
        assertThat(mtdDevicetype1).isNotEqualTo(mtdDevicetype2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdDevicetypeDTO.class);
        MtdDevicetypeDTO mtdDevicetypeDTO1 = new MtdDevicetypeDTO();
        mtdDevicetypeDTO1.setId(1L);
        MtdDevicetypeDTO mtdDevicetypeDTO2 = new MtdDevicetypeDTO();
        assertThat(mtdDevicetypeDTO1).isNotEqualTo(mtdDevicetypeDTO2);
        mtdDevicetypeDTO2.setId(mtdDevicetypeDTO1.getId());
        assertThat(mtdDevicetypeDTO1).isEqualTo(mtdDevicetypeDTO2);
        mtdDevicetypeDTO2.setId(2L);
        assertThat(mtdDevicetypeDTO1).isNotEqualTo(mtdDevicetypeDTO2);
        mtdDevicetypeDTO1.setId(null);
        assertThat(mtdDevicetypeDTO1).isNotEqualTo(mtdDevicetypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdDevicetypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdDevicetypeMapper.fromId(null)).isNull();
    }
}
