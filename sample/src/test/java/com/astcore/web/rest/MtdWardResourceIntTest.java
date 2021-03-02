package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdWard;
import com.astcore.repository.MtdWardRepository;
import com.astcore.service.MtdWardService;
import com.astcore.repository.search.MtdWardSearchRepository;
import com.astcore.service.dto.MtdWardDTO;
import com.astcore.service.mapper.MtdWardMapper;
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
 * Test class for the MtdWardResource REST controller.
 *
 * @see MtdWardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdWardResourceIntTest {

    private static final String DEFAULT_WARDCODE = "AAAAAAAAAA";
    private static final String UPDATED_WARDCODE = "BBBBBBBBBB";

    private static final String DEFAULT_WARDNAME = "AAAAAAAAAA";
    private static final String UPDATED_WARDNAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WRDLATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WRDLATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WRDLONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WRDLONGITUDE = new BigDecimal(2);

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
    private MtdWardRepository mtdWardRepository;

    @Autowired
    private MtdWardMapper mtdWardMapper;

    @Autowired
    private MtdWardService mtdWardService;

    @Autowired
    private MtdWardSearchRepository mtdWardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdWardMockMvc;

    private MtdWard mtdWard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdWardResource mtdWardResource = new MtdWardResource(mtdWardService);
        this.restMtdWardMockMvc = MockMvcBuilders.standaloneSetup(mtdWardResource)
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
    public static MtdWard createEntity(EntityManager em) {
        MtdWard mtdWard = new MtdWard()
            .wardcode(DEFAULT_WARDCODE)
            .wardname(DEFAULT_WARDNAME)
            .wrdlatitude(DEFAULT_WRDLATITUDE)
            .wrdlongitude(DEFAULT_WRDLONGITUDE)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdWard;
    }

    @Before
    public void initTest() {
        mtdWardSearchRepository.deleteAll();
        mtdWard = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdWard() throws Exception {
        int databaseSizeBeforeCreate = mtdWardRepository.findAll().size();

        // Create the MtdWard
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);
        restMtdWardMockMvc.perform(post("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdWard in the database
        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeCreate + 1);
        MtdWard testMtdWard = mtdWardList.get(mtdWardList.size() - 1);
        assertThat(testMtdWard.getWardcode()).isEqualTo(DEFAULT_WARDCODE);
        assertThat(testMtdWard.getWardname()).isEqualTo(DEFAULT_WARDNAME);
        assertThat(testMtdWard.getWrdlatitude()).isEqualTo(DEFAULT_WRDLATITUDE);
        assertThat(testMtdWard.getWrdlongitude()).isEqualTo(DEFAULT_WRDLONGITUDE);
        assertThat(testMtdWard.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdWard.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdWard.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdWard.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdWard.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdWard.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdWard in Elasticsearch
        MtdWard mtdWardEs = mtdWardSearchRepository.findOne(testMtdWard.getId());
        assertThat(mtdWardEs).isEqualToComparingFieldByField(testMtdWard);
    }

    @Test
    @Transactional
    public void createMtdWardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdWardRepository.findAll().size();

        // Create the MtdWard with an existing ID
        mtdWard.setId(1L);
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdWardMockMvc.perform(post("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWardcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdWardRepository.findAll().size();
        // set the field null
        mtdWard.setWardcode(null);

        // Create the MtdWard, which fails.
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);

        restMtdWardMockMvc.perform(post("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isBadRequest());

        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWardnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdWardRepository.findAll().size();
        // set the field null
        mtdWard.setWardname(null);

        // Create the MtdWard, which fails.
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);

        restMtdWardMockMvc.perform(post("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isBadRequest());

        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdWardRepository.findAll().size();
        // set the field null
        mtdWard.setActive(null);

        // Create the MtdWard, which fails.
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);

        restMtdWardMockMvc.perform(post("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isBadRequest());

        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdWardRepository.findAll().size();
        // set the field null
        mtdWard.setIsdel(null);

        // Create the MtdWard, which fails.
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);

        restMtdWardMockMvc.perform(post("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isBadRequest());

        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdWards() throws Exception {
        // Initialize the database
        mtdWardRepository.saveAndFlush(mtdWard);

        // Get all the mtdWardList
        restMtdWardMockMvc.perform(get("/api/mtd-wards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdWard.getId().intValue())))
            .andExpect(jsonPath("$.[*].wardcode").value(hasItem(DEFAULT_WARDCODE.toString())))
            .andExpect(jsonPath("$.[*].wardname").value(hasItem(DEFAULT_WARDNAME.toString())))
            .andExpect(jsonPath("$.[*].wrdlatitude").value(hasItem(DEFAULT_WRDLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].wrdlongitude").value(hasItem(DEFAULT_WRDLONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdWard() throws Exception {
        // Initialize the database
        mtdWardRepository.saveAndFlush(mtdWard);

        // Get the mtdWard
        restMtdWardMockMvc.perform(get("/api/mtd-wards/{id}", mtdWard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdWard.getId().intValue()))
            .andExpect(jsonPath("$.wardcode").value(DEFAULT_WARDCODE.toString()))
            .andExpect(jsonPath("$.wardname").value(DEFAULT_WARDNAME.toString()))
            .andExpect(jsonPath("$.wrdlatitude").value(DEFAULT_WRDLATITUDE.intValue()))
            .andExpect(jsonPath("$.wrdlongitude").value(DEFAULT_WRDLONGITUDE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdWard() throws Exception {
        // Get the mtdWard
        restMtdWardMockMvc.perform(get("/api/mtd-wards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdWard() throws Exception {
        // Initialize the database
        mtdWardRepository.saveAndFlush(mtdWard);
        mtdWardSearchRepository.save(mtdWard);
        int databaseSizeBeforeUpdate = mtdWardRepository.findAll().size();

        // Update the mtdWard
        MtdWard updatedMtdWard = mtdWardRepository.findOne(mtdWard.getId());
        updatedMtdWard
            .wardcode(UPDATED_WARDCODE)
            .wardname(UPDATED_WARDNAME)
            .wrdlatitude(UPDATED_WRDLATITUDE)
            .wrdlongitude(UPDATED_WRDLONGITUDE)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(updatedMtdWard);

        restMtdWardMockMvc.perform(put("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isOk());

        // Validate the MtdWard in the database
        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeUpdate);
        MtdWard testMtdWard = mtdWardList.get(mtdWardList.size() - 1);
        assertThat(testMtdWard.getWardcode()).isEqualTo(UPDATED_WARDCODE);
        assertThat(testMtdWard.getWardname()).isEqualTo(UPDATED_WARDNAME);
        assertThat(testMtdWard.getWrdlatitude()).isEqualTo(UPDATED_WRDLATITUDE);
        assertThat(testMtdWard.getWrdlongitude()).isEqualTo(UPDATED_WRDLONGITUDE);
        assertThat(testMtdWard.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdWard.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdWard.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdWard.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdWard.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdWard.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdWard in Elasticsearch
        MtdWard mtdWardEs = mtdWardSearchRepository.findOne(testMtdWard.getId());
        assertThat(mtdWardEs).isEqualToComparingFieldByField(testMtdWard);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdWard() throws Exception {
        int databaseSizeBeforeUpdate = mtdWardRepository.findAll().size();

        // Create the MtdWard
        MtdWardDTO mtdWardDTO = mtdWardMapper.toDto(mtdWard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdWardMockMvc.perform(put("/api/mtd-wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdWardDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdWard in the database
        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdWard() throws Exception {
        // Initialize the database
        mtdWardRepository.saveAndFlush(mtdWard);
        mtdWardSearchRepository.save(mtdWard);
        int databaseSizeBeforeDelete = mtdWardRepository.findAll().size();

        // Get the mtdWard
        restMtdWardMockMvc.perform(delete("/api/mtd-wards/{id}", mtdWard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdWardExistsInEs = mtdWardSearchRepository.exists(mtdWard.getId());
        assertThat(mtdWardExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdWard> mtdWardList = mtdWardRepository.findAll();
        assertThat(mtdWardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdWard() throws Exception {
        // Initialize the database
        mtdWardRepository.saveAndFlush(mtdWard);
        mtdWardSearchRepository.save(mtdWard);

        // Search the mtdWard
        restMtdWardMockMvc.perform(get("/api/_search/mtd-wards?query=id:" + mtdWard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdWard.getId().intValue())))
            .andExpect(jsonPath("$.[*].wardcode").value(hasItem(DEFAULT_WARDCODE.toString())))
            .andExpect(jsonPath("$.[*].wardname").value(hasItem(DEFAULT_WARDNAME.toString())))
            .andExpect(jsonPath("$.[*].wrdlatitude").value(hasItem(DEFAULT_WRDLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].wrdlongitude").value(hasItem(DEFAULT_WRDLONGITUDE.intValue())))
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
        TestUtil.equalsVerifier(MtdWard.class);
        MtdWard mtdWard1 = new MtdWard();
        mtdWard1.setId(1L);
        MtdWard mtdWard2 = new MtdWard();
        mtdWard2.setId(mtdWard1.getId());
        assertThat(mtdWard1).isEqualTo(mtdWard2);
        mtdWard2.setId(2L);
        assertThat(mtdWard1).isNotEqualTo(mtdWard2);
        mtdWard1.setId(null);
        assertThat(mtdWard1).isNotEqualTo(mtdWard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdWardDTO.class);
        MtdWardDTO mtdWardDTO1 = new MtdWardDTO();
        mtdWardDTO1.setId(1L);
        MtdWardDTO mtdWardDTO2 = new MtdWardDTO();
        assertThat(mtdWardDTO1).isNotEqualTo(mtdWardDTO2);
        mtdWardDTO2.setId(mtdWardDTO1.getId());
        assertThat(mtdWardDTO1).isEqualTo(mtdWardDTO2);
        mtdWardDTO2.setId(2L);
        assertThat(mtdWardDTO1).isNotEqualTo(mtdWardDTO2);
        mtdWardDTO1.setId(null);
        assertThat(mtdWardDTO1).isNotEqualTo(mtdWardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdWardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdWardMapper.fromId(null)).isNull();
    }
}
