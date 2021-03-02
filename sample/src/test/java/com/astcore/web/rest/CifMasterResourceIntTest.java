package com.astcore.web.rest;

import com.astcore.AstcoreApp;

import com.astcore.domain.CifMaster;
import com.astcore.repository.CifMasterRepository;
import com.astcore.service.CifMasterService;
import com.astcore.repository.search.CifMasterSearchRepository;
import com.astcore.service.dto.CifMasterDTO;
import com.astcore.service.mapper.CifMasterMapper;
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
 * Test class for the CifMasterResource REST controller.
 *
 * @see CifMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AstcoreApp.class)
public class CifMasterResourceIntTest {

    private static final String DEFAULT_CUSTOMERCODE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEX = 1;
    private static final Integer UPDATED_SEX = 2;

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFYCODE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFYCODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IDENTIFYDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IDENTIFYDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILENUM = "AAAAAAAAAA";
    private static final String UPDATED_MOBILENUM = "BBBBBBBBBB";

    private static final String DEFAULT_PHONENUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUM = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERTYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTPARENTS = 1;
    private static final Integer UPDATED_CUSTPARENTS = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CUSTLATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTLATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CUSTLONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTLONGITUDE = new BigDecimal(2);

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
    private CifMasterRepository cifMasterRepository;

    @Autowired
    private CifMasterMapper cifMasterMapper;

    @Autowired
    private CifMasterService cifMasterService;

    @Autowired
    private CifMasterSearchRepository cifMasterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCifMasterMockMvc;

    private CifMaster cifMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CifMasterResource cifMasterResource = new CifMasterResource(cifMasterService);
        this.restCifMasterMockMvc = MockMvcBuilders.standaloneSetup(cifMasterResource)
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
    public static CifMaster createEntity(EntityManager em) {
        CifMaster cifMaster = new CifMaster()
            .customercode(DEFAULT_CUSTOMERCODE)
            .customername(DEFAULT_CUSTOMERNAME)
            .sex(DEFAULT_SEX)
            .birthday(DEFAULT_BIRTHDAY)
            .identifycode(DEFAULT_IDENTIFYCODE)
            .identifydate(DEFAULT_IDENTIFYDATE)
            .address(DEFAULT_ADDRESS)
            .mobilenum(DEFAULT_MOBILENUM)
            .phonenum(DEFAULT_PHONENUM)
            .customertype(DEFAULT_CUSTOMERTYPE)
            .custparents(DEFAULT_CUSTPARENTS)
            .note(DEFAULT_NOTE)
            .custlatitude(DEFAULT_CUSTLATITUDE)
            .custlongitude(DEFAULT_CUSTLONGITUDE)
            .active(DEFAULT_ACTIVE)
            .isdel(DEFAULT_ISDEL)
            .createby(DEFAULT_CREATEBY)
            .createdate(DEFAULT_CREATEDATE)
            .lastmodifyby(DEFAULT_LASTMODIFYBY)
            .lastmodifydate(DEFAULT_LASTMODIFYDATE);
        return cifMaster;
    }

    @Before
    public void initTest() {
        cifMasterSearchRepository.deleteAll();
        cifMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createCifMaster() throws Exception {
        int databaseSizeBeforeCreate = cifMasterRepository.findAll().size();

        // Create the CifMaster
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);
        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the CifMaster in the database
        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeCreate + 1);
        CifMaster testCifMaster = cifMasterList.get(cifMasterList.size() - 1);
        assertThat(testCifMaster.getCustomercode()).isEqualTo(DEFAULT_CUSTOMERCODE);
        assertThat(testCifMaster.getCustomername()).isEqualTo(DEFAULT_CUSTOMERNAME);
        assertThat(testCifMaster.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testCifMaster.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testCifMaster.getIdentifycode()).isEqualTo(DEFAULT_IDENTIFYCODE);
        assertThat(testCifMaster.getIdentifydate()).isEqualTo(DEFAULT_IDENTIFYDATE);
        assertThat(testCifMaster.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCifMaster.getMobilenum()).isEqualTo(DEFAULT_MOBILENUM);
        assertThat(testCifMaster.getPhonenum()).isEqualTo(DEFAULT_PHONENUM);
        assertThat(testCifMaster.getCustomertype()).isEqualTo(DEFAULT_CUSTOMERTYPE);
        assertThat(testCifMaster.getCustparents()).isEqualTo(DEFAULT_CUSTPARENTS);
        assertThat(testCifMaster.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testCifMaster.getCustlatitude()).isEqualTo(DEFAULT_CUSTLATITUDE);
        assertThat(testCifMaster.getCustlongitude()).isEqualTo(DEFAULT_CUSTLONGITUDE);
        assertThat(testCifMaster.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCifMaster.getIsdel()).isEqualTo(DEFAULT_ISDEL);
        assertThat(testCifMaster.getCreateby()).isEqualTo(DEFAULT_CREATEBY);
        assertThat(testCifMaster.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testCifMaster.getLastmodifyby()).isEqualTo(DEFAULT_LASTMODIFYBY);
        assertThat(testCifMaster.getLastmodifydate()).isEqualTo(DEFAULT_LASTMODIFYDATE);

        // Validate the CifMaster in Elasticsearch
        CifMaster cifMasterEs = cifMasterSearchRepository.findOne(testCifMaster.getId());
        assertThat(cifMasterEs).isEqualToComparingFieldByField(testCifMaster);
    }

    @Test
    @Transactional
    public void createCifMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cifMasterRepository.findAll().size();

        // Create the CifMaster with an existing ID
        cifMaster.setId(1L);
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustomercodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setCustomercode(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setCustomername(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifycodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setIdentifycode(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobilenumIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setMobilenum(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhonenumIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setPhonenum(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomertypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setCustomertype(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setActive(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsdelIsRequired() throws Exception {
        int databaseSizeBeforeTest = cifMasterRepository.findAll().size();
        // set the field null
        cifMaster.setIsdel(null);

        // Create the CifMaster, which fails.
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        restCifMasterMockMvc.perform(post("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isBadRequest());

        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCifMasters() throws Exception {
        // Initialize the database
        cifMasterRepository.saveAndFlush(cifMaster);

        // Get all the cifMasterList
        restCifMasterMockMvc.perform(get("/api/cif-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].customercode").value(hasItem(DEFAULT_CUSTOMERCODE.toString())))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].identifycode").value(hasItem(DEFAULT_IDENTIFYCODE.toString())))
            .andExpect(jsonPath("$.[*].identifydate").value(hasItem(DEFAULT_IDENTIFYDATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mobilenum").value(hasItem(DEFAULT_MOBILENUM.toString())))
            .andExpect(jsonPath("$.[*].phonenum").value(hasItem(DEFAULT_PHONENUM.toString())))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE.toString())))
            .andExpect(jsonPath("$.[*].custparents").value(hasItem(DEFAULT_CUSTPARENTS)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].custlatitude").value(hasItem(DEFAULT_CUSTLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].custlongitude").value(hasItem(DEFAULT_CUSTLONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].isdel").value(hasItem(DEFAULT_ISDEL)))
            .andExpect(jsonPath("$.[*].createby").value(hasItem(DEFAULT_CREATEBY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].lastmodifyby").value(hasItem(DEFAULT_LASTMODIFYBY.toString())))
            .andExpect(jsonPath("$.[*].lastmodifydate").value(hasItem(DEFAULT_LASTMODIFYDATE.toString())));
    }

    @Test
    @Transactional
    public void getCifMaster() throws Exception {
        // Initialize the database
        cifMasterRepository.saveAndFlush(cifMaster);

        // Get the cifMaster
        restCifMasterMockMvc.perform(get("/api/cif-masters/{id}", cifMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cifMaster.getId().intValue()))
            .andExpect(jsonPath("$.customercode").value(DEFAULT_CUSTOMERCODE.toString()))
            .andExpect(jsonPath("$.customername").value(DEFAULT_CUSTOMERNAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.identifycode").value(DEFAULT_IDENTIFYCODE.toString()))
            .andExpect(jsonPath("$.identifydate").value(DEFAULT_IDENTIFYDATE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.mobilenum").value(DEFAULT_MOBILENUM.toString()))
            .andExpect(jsonPath("$.phonenum").value(DEFAULT_PHONENUM.toString()))
            .andExpect(jsonPath("$.customertype").value(DEFAULT_CUSTOMERTYPE.toString()))
            .andExpect(jsonPath("$.custparents").value(DEFAULT_CUSTPARENTS))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.custlatitude").value(DEFAULT_CUSTLATITUDE.intValue()))
            .andExpect(jsonPath("$.custlongitude").value(DEFAULT_CUSTLONGITUDE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.isdel").value(DEFAULT_ISDEL))
            .andExpect(jsonPath("$.createby").value(DEFAULT_CREATEBY.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.lastmodifyby").value(DEFAULT_LASTMODIFYBY.toString()))
            .andExpect(jsonPath("$.lastmodifydate").value(DEFAULT_LASTMODIFYDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCifMaster() throws Exception {
        // Get the cifMaster
        restCifMasterMockMvc.perform(get("/api/cif-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCifMaster() throws Exception {
        // Initialize the database
        cifMasterRepository.saveAndFlush(cifMaster);
        cifMasterSearchRepository.save(cifMaster);
        int databaseSizeBeforeUpdate = cifMasterRepository.findAll().size();

        // Update the cifMaster
        CifMaster updatedCifMaster = cifMasterRepository.findOne(cifMaster.getId());
        updatedCifMaster
            .customercode(UPDATED_CUSTOMERCODE)
            .customername(UPDATED_CUSTOMERNAME)
            .sex(UPDATED_SEX)
            .birthday(UPDATED_BIRTHDAY)
            .identifycode(UPDATED_IDENTIFYCODE)
            .identifydate(UPDATED_IDENTIFYDATE)
            .address(UPDATED_ADDRESS)
            .mobilenum(UPDATED_MOBILENUM)
            .phonenum(UPDATED_PHONENUM)
            .customertype(UPDATED_CUSTOMERTYPE)
            .custparents(UPDATED_CUSTPARENTS)
            .note(UPDATED_NOTE)
            .custlatitude(UPDATED_CUSTLATITUDE)
            .custlongitude(UPDATED_CUSTLONGITUDE)
            .active(UPDATED_ACTIVE)
            .isdel(UPDATED_ISDEL)
            .createby(UPDATED_CREATEBY)
            .createdate(UPDATED_CREATEDATE)
            .lastmodifyby(UPDATED_LASTMODIFYBY)
            .lastmodifydate(UPDATED_LASTMODIFYDATE);
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(updatedCifMaster);

        restCifMasterMockMvc.perform(put("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isOk());

        // Validate the CifMaster in the database
        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeUpdate);
        CifMaster testCifMaster = cifMasterList.get(cifMasterList.size() - 1);
        assertThat(testCifMaster.getCustomercode()).isEqualTo(UPDATED_CUSTOMERCODE);
        assertThat(testCifMaster.getCustomername()).isEqualTo(UPDATED_CUSTOMERNAME);
        assertThat(testCifMaster.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCifMaster.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCifMaster.getIdentifycode()).isEqualTo(UPDATED_IDENTIFYCODE);
        assertThat(testCifMaster.getIdentifydate()).isEqualTo(UPDATED_IDENTIFYDATE);
        assertThat(testCifMaster.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCifMaster.getMobilenum()).isEqualTo(UPDATED_MOBILENUM);
        assertThat(testCifMaster.getPhonenum()).isEqualTo(UPDATED_PHONENUM);
        assertThat(testCifMaster.getCustomertype()).isEqualTo(UPDATED_CUSTOMERTYPE);
        assertThat(testCifMaster.getCustparents()).isEqualTo(UPDATED_CUSTPARENTS);
        assertThat(testCifMaster.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testCifMaster.getCustlatitude()).isEqualTo(UPDATED_CUSTLATITUDE);
        assertThat(testCifMaster.getCustlongitude()).isEqualTo(UPDATED_CUSTLONGITUDE);
        assertThat(testCifMaster.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCifMaster.getIsdel()).isEqualTo(UPDATED_ISDEL);
        assertThat(testCifMaster.getCreateby()).isEqualTo(UPDATED_CREATEBY);
        assertThat(testCifMaster.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testCifMaster.getLastmodifyby()).isEqualTo(UPDATED_LASTMODIFYBY);
        assertThat(testCifMaster.getLastmodifydate()).isEqualTo(UPDATED_LASTMODIFYDATE);

        // Validate the CifMaster in Elasticsearch
        CifMaster cifMasterEs = cifMasterSearchRepository.findOne(testCifMaster.getId());
        assertThat(cifMasterEs).isEqualToComparingFieldByField(testCifMaster);
    }

    @Test
    @Transactional
    public void updateNonExistingCifMaster() throws Exception {
        int databaseSizeBeforeUpdate = cifMasterRepository.findAll().size();

        // Create the CifMaster
        CifMasterDTO cifMasterDTO = cifMasterMapper.toDto(cifMaster);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCifMasterMockMvc.perform(put("/api/cif-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cifMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the CifMaster in the database
        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCifMaster() throws Exception {
        // Initialize the database
        cifMasterRepository.saveAndFlush(cifMaster);
        cifMasterSearchRepository.save(cifMaster);
        int databaseSizeBeforeDelete = cifMasterRepository.findAll().size();

        // Get the cifMaster
        restCifMasterMockMvc.perform(delete("/api/cif-masters/{id}", cifMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cifMasterExistsInEs = cifMasterSearchRepository.exists(cifMaster.getId());
        assertThat(cifMasterExistsInEs).isFalse();

        // Validate the database is empty
        List<CifMaster> cifMasterList = cifMasterRepository.findAll();
        assertThat(cifMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCifMaster() throws Exception {
        // Initialize the database
        cifMasterRepository.saveAndFlush(cifMaster);
        cifMasterSearchRepository.save(cifMaster);

        // Search the cifMaster
        restCifMasterMockMvc.perform(get("/api/_search/cif-masters?query=id:" + cifMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cifMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].customercode").value(hasItem(DEFAULT_CUSTOMERCODE.toString())))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].identifycode").value(hasItem(DEFAULT_IDENTIFYCODE.toString())))
            .andExpect(jsonPath("$.[*].identifydate").value(hasItem(DEFAULT_IDENTIFYDATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mobilenum").value(hasItem(DEFAULT_MOBILENUM.toString())))
            .andExpect(jsonPath("$.[*].phonenum").value(hasItem(DEFAULT_PHONENUM.toString())))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE.toString())))
            .andExpect(jsonPath("$.[*].custparents").value(hasItem(DEFAULT_CUSTPARENTS)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].custlatitude").value(hasItem(DEFAULT_CUSTLATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].custlongitude").value(hasItem(DEFAULT_CUSTLONGITUDE.intValue())))
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
        TestUtil.equalsVerifier(CifMaster.class);
        CifMaster cifMaster1 = new CifMaster();
        cifMaster1.setId(1L);
        CifMaster cifMaster2 = new CifMaster();
        cifMaster2.setId(cifMaster1.getId());
        assertThat(cifMaster1).isEqualTo(cifMaster2);
        cifMaster2.setId(2L);
        assertThat(cifMaster1).isNotEqualTo(cifMaster2);
        cifMaster1.setId(null);
        assertThat(cifMaster1).isNotEqualTo(cifMaster2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CifMasterDTO.class);
        CifMasterDTO cifMasterDTO1 = new CifMasterDTO();
        cifMasterDTO1.setId(1L);
        CifMasterDTO cifMasterDTO2 = new CifMasterDTO();
        assertThat(cifMasterDTO1).isNotEqualTo(cifMasterDTO2);
        cifMasterDTO2.setId(cifMasterDTO1.getId());
        assertThat(cifMasterDTO1).isEqualTo(cifMasterDTO2);
        cifMasterDTO2.setId(2L);
        assertThat(cifMasterDTO1).isNotEqualTo(cifMasterDTO2);
        cifMasterDTO1.setId(null);
        assertThat(cifMasterDTO1).isNotEqualTo(cifMasterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cifMasterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cifMasterMapper.fromId(null)).isNull();
    }
}
