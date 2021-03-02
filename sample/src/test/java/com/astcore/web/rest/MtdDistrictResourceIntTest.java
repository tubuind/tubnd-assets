package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.MtdDistrict;
import com.astcore.repository.MtdDistrictRepository;
import com.astcore.service.MtdDistrictService;
import com.astcore.repository.search.MtdDistrictSearchRepository;
import com.astcore.service.dto.MtdDistrictDTO;
import com.astcore.service.mapper.MtdDistrictMapper;
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
 * Test class for the MtdDistrictResource REST controller.
 *
 * @see MtdDistrictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class MtdDistrictResourceIntTest {

    private static final String DEFAULT_DISTRICTCODE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICTNAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICTNAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DISLATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISLATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISLONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISLONGITUDE = new BigDecimal(2);

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
    private MtdDistrictRepository mtdDistrictRepository;

    @Autowired
    private MtdDistrictMapper mtdDistrictMapper;

    @Autowired
    private MtdDistrictService mtdDistrictService;

    @Autowired
    private MtdDistrictSearchRepository mtdDistrictSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMtdDistrictMockMvc;

    private MtdDistrict mtdDistrict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MtdDistrictResource mtdDistrictResource = new MtdDistrictResource(mtdDistrictService);
        this.restMtdDistrictMockMvc = MockMvcBuilders.standaloneSetup(mtdDistrictResource)
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
    public static MtdDistrict createEntity(EntityManager em) {
        MtdDistrict mtdDistrict = new MtdDistrict()
            .districtcode(DEFAULT_DISTRICTCODE)
            .districtname(DEFAULT_DISTRICTNAME)
            .dislatitude(DEFAULT_DISLATITUDE)
            .dislongitude(DEFAULT_DISLONGITUDE)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return mtdDistrict;
    }

    @Before
    public void initTest() {
        mtdDistrictSearchRepository.deleteAll();
        mtdDistrict = createEntity(em);
    }

    @Test
    @Transactional
    public void createMtdDistrict() throws Exception {
        int databaseSizeBeforeCreate = mtdDistrictRepository.findAll().size();

        // Create the MtdDistrict
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);
        restMtdDistrictMockMvc.perform(post("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdDistrict in the database
        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeCreate + 1);
        MtdDistrict testMtdDistrict = mtdDistrictList.get(mtdDistrictList.size() - 1);
        assertThat(testMtdDistrict.getDistrictcode()).isEqualTo(DEFAULT_DISTRICTCODE);
        assertThat(testMtdDistrict.getDistrictname()).isEqualTo(DEFAULT_DISTRICTNAME);
        assertThat(testMtdDistrict.getDislatitude()).isEqualTo(DEFAULT_DISLATITUDE);
        assertThat(testMtdDistrict.getDislongitude()).isEqualTo(DEFAULT_DISLONGITUDE);
        assertThat(testMtdDistrict.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMtdDistrict.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testMtdDistrict.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testMtdDistrict.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testMtdDistrict.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testMtdDistrict.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the MtdDistrict in Elasticsearch
        MtdDistrict mtdDistrictEs = mtdDistrictSearchRepository.findOne(testMtdDistrict.getId());
        assertThat(mtdDistrictEs).isEqualToComparingFieldByField(testMtdDistrict);
    }

    @Test
    @Transactional
    public void createMtdDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mtdDistrictRepository.findAll().size();

        // Create the MtdDistrict with an existing ID
        mtdDistrict.setId(1L);
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtdDistrictMockMvc.perform(post("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDistrictcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDistrictRepository.findAll().size();
        // set the field null
        mtdDistrict.setDistrictcode(null);

        // Create the MtdDistrict, which fails.
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);

        restMtdDistrictMockMvc.perform(post("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistrictnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDistrictRepository.findAll().size();
        // set the field null
        mtdDistrict.setDistrictname(null);

        // Create the MtdDistrict, which fails.
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);

        restMtdDistrictMockMvc.perform(post("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDistrictRepository.findAll().size();
        // set the field null
        mtdDistrict.setActive(null);

        // Create the MtdDistrict, which fails.
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);

        restMtdDistrictMockMvc.perform(post("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = mtdDistrictRepository.findAll().size();
        // set the field null
        mtdDistrict.setIsdel(null);

        // Create the MtdDistrict, which fails.
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);

        restMtdDistrictMockMvc.perform(post("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isBadRequest());

        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMtdDistricts() throws Exception {
        // Initialize the database
        mtdDistrictRepository.saveAndFlush(mtdDistrict);

        // Get all the mtdDistrictList
        restMtdDistrictMockMvc.perform(get("/api/mtd-districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdDistrict.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtcode").value(hasItem(DEFAULT_DISTRICTCODE.toString())))
            .andExpect(jsonPath("$.[*].districtname").value(hasItem(DEFAULT_DISTRICTNAME.toString())))
            .andExpect(jsonPath("$.[*].dislatitude").value(hasItem(DEFAULT_DISLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].dislongitude").value(hasItem(DEFAULT_DISLONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getMtdDistrict() throws Exception {
        // Initialize the database
        mtdDistrictRepository.saveAndFlush(mtdDistrict);

        // Get the mtdDistrict
        restMtdDistrictMockMvc.perform(get("/api/mtd-districts/{id}", mtdDistrict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mtdDistrict.getId().intValue()))
            .andExpect(jsonPath("$.districtcode").value(DEFAULT_DISTRICTCODE.toString()))
            .andExpect(jsonPath("$.districtname").value(DEFAULT_DISTRICTNAME.toString()))
            .andExpect(jsonPath("$.dislatitude").value(DEFAULT_DISLATITUDE.intValue()))
            .andExpect(jsonPath("$.dislongitude").value(DEFAULT_DISLONGITUDE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMtdDistrict() throws Exception {
        // Get the mtdDistrict
        restMtdDistrictMockMvc.perform(get("/api/mtd-districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMtdDistrict() throws Exception {
        // Initialize the database
        mtdDistrictRepository.saveAndFlush(mtdDistrict);
        mtdDistrictSearchRepository.save(mtdDistrict);
        int databaseSizeBeforeUpdate = mtdDistrictRepository.findAll().size();

        // Update the mtdDistrict
        MtdDistrict updatedMtdDistrict = mtdDistrictRepository.findOne(mtdDistrict.getId());
        updatedMtdDistrict
            .districtcode(UPDATED_DISTRICTCODE)
            .districtname(UPDATED_DISTRICTNAME)
            .dislatitude(UPDATED_DISLATITUDE)
            .dislongitude(UPDATED_DISLONGITUDE)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(updatedMtdDistrict);

        restMtdDistrictMockMvc.perform(put("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isOk());

        // Validate the MtdDistrict in the database
        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeUpdate);
        MtdDistrict testMtdDistrict = mtdDistrictList.get(mtdDistrictList.size() - 1);
        assertThat(testMtdDistrict.getDistrictcode()).isEqualTo(UPDATED_DISTRICTCODE);
        assertThat(testMtdDistrict.getDistrictname()).isEqualTo(UPDATED_DISTRICTNAME);
        assertThat(testMtdDistrict.getDislatitude()).isEqualTo(UPDATED_DISLATITUDE);
        assertThat(testMtdDistrict.getDislongitude()).isEqualTo(UPDATED_DISLONGITUDE);
        assertThat(testMtdDistrict.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMtdDistrict.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testMtdDistrict.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testMtdDistrict.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testMtdDistrict.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testMtdDistrict.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the MtdDistrict in Elasticsearch
        MtdDistrict mtdDistrictEs = mtdDistrictSearchRepository.findOne(testMtdDistrict.getId());
        assertThat(mtdDistrictEs).isEqualToComparingFieldByField(testMtdDistrict);
    }

    @Test
    @Transactional
    public void updateNonExistingMtdDistrict() throws Exception {
        int databaseSizeBeforeUpdate = mtdDistrictRepository.findAll().size();

        // Create the MtdDistrict
        MtdDistrictDTO mtdDistrictDTO = mtdDistrictMapper.toDto(mtdDistrict);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMtdDistrictMockMvc.perform(put("/api/mtd-districts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mtdDistrictDTO)))
            .andExpect(status().isCreated());

        // Validate the MtdDistrict in the database
        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMtdDistrict() throws Exception {
        // Initialize the database
        mtdDistrictRepository.saveAndFlush(mtdDistrict);
        mtdDistrictSearchRepository.save(mtdDistrict);
        int databaseSizeBeforeDelete = mtdDistrictRepository.findAll().size();

        // Get the mtdDistrict
        restMtdDistrictMockMvc.perform(delete("/api/mtd-districts/{id}", mtdDistrict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mtdDistrictExistsInEs = mtdDistrictSearchRepository.exists(mtdDistrict.getId());
        assertThat(mtdDistrictExistsInEs).isFalse();

        // Validate the database is empty
        List<MtdDistrict> mtdDistrictList = mtdDistrictRepository.findAll();
        assertThat(mtdDistrictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMtdDistrict() throws Exception {
        // Initialize the database
        mtdDistrictRepository.saveAndFlush(mtdDistrict);
        mtdDistrictSearchRepository.save(mtdDistrict);

        // Search the mtdDistrict
        restMtdDistrictMockMvc.perform(get("/api/_search/mtd-districts?query=id:" + mtdDistrict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtdDistrict.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtcode").value(hasItem(DEFAULT_DISTRICTCODE.toString())))
            .andExpect(jsonPath("$.[*].districtname").value(hasItem(DEFAULT_DISTRICTNAME.toString())))
            .andExpect(jsonPath("$.[*].dislatitude").value(hasItem(DEFAULT_DISLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].dislongitude").value(hasItem(DEFAULT_DISLONGITUDE.intValue())))
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
        TestUtil.equalsVerifier(MtdDistrict.class);
        MtdDistrict mtdDistrict1 = new MtdDistrict();
        mtdDistrict1.setId(1L);
        MtdDistrict mtdDistrict2 = new MtdDistrict();
        mtdDistrict2.setId(mtdDistrict1.getId());
        assertThat(mtdDistrict1).isEqualTo(mtdDistrict2);
        mtdDistrict2.setId(2L);
        assertThat(mtdDistrict1).isNotEqualTo(mtdDistrict2);
        mtdDistrict1.setId(null);
        assertThat(mtdDistrict1).isNotEqualTo(mtdDistrict2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtdDistrictDTO.class);
        MtdDistrictDTO mtdDistrictDTO1 = new MtdDistrictDTO();
        mtdDistrictDTO1.setId(1L);
        MtdDistrictDTO mtdDistrictDTO2 = new MtdDistrictDTO();
        assertThat(mtdDistrictDTO1).isNotEqualTo(mtdDistrictDTO2);
        mtdDistrictDTO2.setId(mtdDistrictDTO1.getId());
        assertThat(mtdDistrictDTO1).isEqualTo(mtdDistrictDTO2);
        mtdDistrictDTO2.setId(2L);
        assertThat(mtdDistrictDTO1).isNotEqualTo(mtdDistrictDTO2);
        mtdDistrictDTO1.setId(null);
        assertThat(mtdDistrictDTO1).isNotEqualTo(mtdDistrictDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mtdDistrictMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mtdDistrictMapper.fromId(null)).isNull();
    }
}
