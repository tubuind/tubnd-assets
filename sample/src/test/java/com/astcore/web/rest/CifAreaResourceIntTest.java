package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.CifArea;
import com.astcore.repository.CifAreaRepository;
import com.astcore.service.CifAreaService;
import com.astcore.repository.search.CifAreaSearchRepository;
import com.astcore.service.dto.CifAreaDTO;
import com.astcore.service.mapper.CifAreaMapper;
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
 * Test class for the CifAreaResource REST controller.
 *
 * @see CifAreaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class CifAreaResourceIntTest {

    private static final Integer DEFAULT_CIFAREAPARENT = 1;
    private static final Integer UPDATED_CIFAREAPARENT = 2;

    private static final String DEFAULT_CIFAREACODE = "AAAAAAAAAA";
    private static final String UPDATED_CIFAREACODE = "BBBBBBBBBB";

    private static final String DEFAULT_CIFAREANAME = "AAAAAAAAAA";
    private static final String UPDATED_CIFAREANAME = "BBBBBBBBBB";

    private static final String DEFAULT_CIFAREADESC = "AAAAAAAAAA";
    private static final String UPDATED_CIFAREADESC = "BBBBBBBBBB";

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
    private CifAreaRepository cifAreaRepository;

    @Autowired
    private CifAreaMapper cifAreaMapper;

    @Autowired
    private CifAreaService cifAreaService;

    @Autowired
    private CifAreaSearchRepository cifAreaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCifAreaMockMvc;

    private CifArea cifArea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CifAreaResource cifAreaResource = new CifAreaResource(cifAreaService);
        this.restCifAreaMockMvc = MockMvcBuilders.standaloneSetup(cifAreaResource)
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
    public static CifArea createEntity(EntityManager em) {
        CifArea cifArea = new CifArea()
            .cifareaparent(DEFAULT_CIFAREAPARENT)
            .cifareacode(DEFAULT_CIFAREACODE)
            .cifareaname(DEFAULT_CIFAREANAME)
            .cifareadesc(DEFAULT_CIFAREADESC)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return cifArea;
    }

    @Before
    public void initTest() {
        cifAreaSearchRepository.deleteAll();
        cifArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createCifArea() throws Exception {
        int databaseSizeBeforeCreate = cifAreaRepository.findAll().size();

        // Create the CifArea
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);
        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the CifArea in the database
        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeCreate + 1);
        CifArea testCifArea = cifAreaList.get(cifAreaList.size() - 1);
        assertThat(testCifArea.getCifareaparent()).isEqualTo(DEFAULT_CIFAREAPARENT);
        assertThat(testCifArea.getCifareacode()).isEqualTo(DEFAULT_CIFAREACODE);
        assertThat(testCifArea.getCifareaname()).isEqualTo(DEFAULT_CIFAREANAME);
        assertThat(testCifArea.getCifareadesc()).isEqualTo(DEFAULT_CIFAREADESC);
        assertThat(testCifArea.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCifArea.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testCifArea.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testCifArea.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testCifArea.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testCifArea.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the CifArea in Elasticsearch
        CifArea cifAreaEs = cifAreaSearchRepository.findOne(testCifArea.getId());
        assertThat(cifAreaEs).isEqualToComparingFieldByField(testCifArea);
    }

    @Test
    @Transactional
    public void createCifAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cifAreaRepository.findAll().size();

        // Create the CifArea with an existing ID
        cifArea.setId(1L);
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCifareacodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifAreaRepository.findAll().size();
        // set the field null
        cifArea.setCifareacode(null);

        // Create the CifArea, which fails.
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isBadRequest());

        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCifareanameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifAreaRepository.findAll().size();
        // set the field null
        cifArea.setCifareaname(null);

        // Create the CifArea, which fails.
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isBadRequest());

        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCifareadescIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifAreaRepository.findAll().size();
        // set the field null
        cifArea.setCifareadesc(null);

        // Create the CifArea, which fails.
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isBadRequest());

        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifAreaRepository.findAll().size();
        // set the field null
        cifArea.setActive(null);

        // Create the CifArea, which fails.
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isBadRequest());

        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifAreaRepository.findAll().size();
        // set the field null
        cifArea.setIsdel(null);

        // Create the CifArea, which fails.
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        restCifAreaMockMvc.perform(post("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isBadRequest());

        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCifAreas() throws Exception {
        // Initialize the database
        cifAreaRepository.saveAndFlush(cifArea);

        // Get all the cifAreaList
        restCifAreaMockMvc.perform(get("/api/cif-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].cifareaparent").value(hasItem(DEFAULT_CIFAREAPARENT)))
            .andExpect(jsonPath("$.[*].cifareacode").value(hasItem(DEFAULT_CIFAREACODE.toString())))
            .andExpect(jsonPath("$.[*].cifareaname").value(hasItem(DEFAULT_CIFAREANAME.toString())))
            .andExpect(jsonPath("$.[*].cifareadesc").value(hasItem(DEFAULT_CIFAREADESC.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getCifArea() throws Exception {
        // Initialize the database
        cifAreaRepository.saveAndFlush(cifArea);

        // Get the cifArea
        restCifAreaMockMvc.perform(get("/api/cif-areas/{id}", cifArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cifArea.getId().intValue()))
            .andExpect(jsonPath("$.cifareaparent").value(DEFAULT_CIFAREAPARENT))
            .andExpect(jsonPath("$.cifareacode").value(DEFAULT_CIFAREACODE.toString()))
            .andExpect(jsonPath("$.cifareaname").value(DEFAULT_CIFAREANAME.toString()))
            .andExpect(jsonPath("$.cifareadesc").value(DEFAULT_CIFAREADESC.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCifArea() throws Exception {
        // Get the cifArea
        restCifAreaMockMvc.perform(get("/api/cif-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCifArea() throws Exception {
        // Initialize the database
        cifAreaRepository.saveAndFlush(cifArea);
        cifAreaSearchRepository.save(cifArea);
        int databaseSizeBeforeUpdate = cifAreaRepository.findAll().size();

        // Update the cifArea
        CifArea updatedCifArea = cifAreaRepository.findOne(cifArea.getId());
        updatedCifArea
            .cifareaparent(UPDATED_CIFAREAPARENT)
            .cifareacode(UPDATED_CIFAREACODE)
            .cifareaname(UPDATED_CIFAREANAME)
            .cifareadesc(UPDATED_CIFAREADESC)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(updatedCifArea);

        restCifAreaMockMvc.perform(put("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isOk());

        // Validate the CifArea in the database
        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeUpdate);
        CifArea testCifArea = cifAreaList.get(cifAreaList.size() - 1);
        assertThat(testCifArea.getCifareaparent()).isEqualTo(UPDATED_CIFAREAPARENT);
        assertThat(testCifArea.getCifareacode()).isEqualTo(UPDATED_CIFAREACODE);
        assertThat(testCifArea.getCifareaname()).isEqualTo(UPDATED_CIFAREANAME);
        assertThat(testCifArea.getCifareadesc()).isEqualTo(UPDATED_CIFAREADESC);
        assertThat(testCifArea.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCifArea.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testCifArea.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testCifArea.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testCifArea.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testCifArea.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the CifArea in Elasticsearch
        CifArea cifAreaEs = cifAreaSearchRepository.findOne(testCifArea.getId());
        assertThat(cifAreaEs).isEqualToComparingFieldByField(testCifArea);
    }

    @Test
    @Transactional
    public void updateNonExistingCifArea() throws Exception {
        int databaseSizeBeforeUpdate = cifAreaRepository.findAll().size();

        // Create the CifArea
        CifAreaDTO cifAreaDTO = cifAreaMapper.toDto(cifArea);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCifAreaMockMvc.perform(put("/api/cif-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the CifArea in the database
        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCifArea() throws Exception {
        // Initialize the database
        cifAreaRepository.saveAndFlush(cifArea);
        cifAreaSearchRepository.save(cifArea);
        int databaseSizeBeforeDelete = cifAreaRepository.findAll().size();

        // Get the cifArea
        restCifAreaMockMvc.perform(delete("/api/cif-areas/{id}", cifArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cifAreaExistsInEs = cifAreaSearchRepository.exists(cifArea.getId());
        assertThat(cifAreaExistsInEs).isFalse();

        // Validate the database is empty
        List<CifArea> cifAreaList = cifAreaRepository.findAll();
        assertThat(cifAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCifArea() throws Exception {
        // Initialize the database
        cifAreaRepository.saveAndFlush(cifArea);
        cifAreaSearchRepository.save(cifArea);

        // Search the cifArea
        restCifAreaMockMvc.perform(get("/api/_search/cif-areas?query=id:" + cifArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].cifareaparent").value(hasItem(DEFAULT_CIFAREAPARENT)))
            .andExpect(jsonPath("$.[*].cifareacode").value(hasItem(DEFAULT_CIFAREACODE.toString())))
            .andExpect(jsonPath("$.[*].cifareaname").value(hasItem(DEFAULT_CIFAREANAME.toString())))
            .andExpect(jsonPath("$.[*].cifareadesc").value(hasItem(DEFAULT_CIFAREADESC.toString())))
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
        TestUtil.equalsVerifier(CifArea.class);
        CifArea cifArea1 = new CifArea();
        cifArea1.setId(1L);
        CifArea cifArea2 = new CifArea();
        cifArea2.setId(cifArea1.getId());
        assertThat(cifArea1).isEqualTo(cifArea2);
        cifArea2.setId(2L);
        assertThat(cifArea1).isNotEqualTo(cifArea2);
        cifArea1.setId(null);
        assertThat(cifArea1).isNotEqualTo(cifArea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CifAreaDTO.class);
        CifAreaDTO cifAreaDTO1 = new CifAreaDTO();
        cifAreaDTO1.setId(1L);
        CifAreaDTO cifAreaDTO2 = new CifAreaDTO();
        assertThat(cifAreaDTO1).isNotEqualTo(cifAreaDTO2);
        cifAreaDTO2.setId(cifAreaDTO1.getId());
        assertThat(cifAreaDTO1).isEqualTo(cifAreaDTO2);
        cifAreaDTO2.setId(2L);
        assertThat(cifAreaDTO1).isNotEqualTo(cifAreaDTO2);
        cifAreaDTO1.setId(null);
        assertThat(cifAreaDTO1).isNotEqualTo(cifAreaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cifAreaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cifAreaMapper.fromId(null)).isNull();
    }
}
