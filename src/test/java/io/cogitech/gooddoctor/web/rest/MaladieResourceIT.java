package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.GoodDoctorSeverApp;
import io.cogitech.gooddoctor.domain.Maladie;
import io.cogitech.gooddoctor.repository.MaladieRepository;
import io.cogitech.gooddoctor.service.MaladieService;
import io.cogitech.gooddoctor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static io.cogitech.gooddoctor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MaladieResource} REST controller.
 */
@SpringBootTest(classes = GoodDoctorSeverApp.class)
public class MaladieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private MaladieRepository maladieRepository;

    @Mock
    private MaladieRepository maladieRepositoryMock;

    @Mock
    private MaladieService maladieServiceMock;

    @Autowired
    private MaladieService maladieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMaladieMockMvc;

    private Maladie maladie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaladieResource maladieResource = new MaladieResource(maladieService);
        this.restMaladieMockMvc = MockMvcBuilders.standaloneSetup(maladieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maladie createEntity(EntityManager em) {
        Maladie maladie = new Maladie()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return maladie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maladie createUpdatedEntity(EntityManager em) {
        Maladie maladie = new Maladie()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        return maladie;
    }

    @BeforeEach
    public void initTest() {
        maladie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaladie() throws Exception {
        int databaseSizeBeforeCreate = maladieRepository.findAll().size();

        // Create the Maladie
        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isCreated());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeCreate + 1);
        Maladie testMaladie = maladieList.get(maladieList.size() - 1);
        assertThat(testMaladie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMaladie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMaladie.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createMaladieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maladieRepository.findAll().size();

        // Create the Maladie with an existing ID
        maladie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMaladies() throws Exception {
        // Initialize the database
        maladieRepository.saveAndFlush(maladie);

        // Get all the maladieList
        restMaladieMockMvc.perform(get("/api/maladies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maladie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMaladiesWithEagerRelationshipsIsEnabled() throws Exception {
        MaladieResource maladieResource = new MaladieResource(maladieServiceMock);
        when(maladieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMaladieMockMvc = MockMvcBuilders.standaloneSetup(maladieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMaladieMockMvc.perform(get("/api/maladies?eagerload=true"))
        .andExpect(status().isOk());

        verify(maladieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMaladiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MaladieResource maladieResource = new MaladieResource(maladieServiceMock);
            when(maladieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMaladieMockMvc = MockMvcBuilders.standaloneSetup(maladieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMaladieMockMvc.perform(get("/api/maladies?eagerload=true"))
        .andExpect(status().isOk());

            verify(maladieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMaladie() throws Exception {
        // Initialize the database
        maladieRepository.saveAndFlush(maladie);

        // Get the maladie
        restMaladieMockMvc.perform(get("/api/maladies/{id}", maladie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maladie.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaladie() throws Exception {
        // Get the maladie
        restMaladieMockMvc.perform(get("/api/maladies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaladie() throws Exception {
        // Initialize the database
        maladieService.save(maladie);

        int databaseSizeBeforeUpdate = maladieRepository.findAll().size();

        // Update the maladie
        Maladie updatedMaladie = maladieRepository.findById(maladie.getId()).get();
        // Disconnect from session so that the updates on updatedMaladie are not directly saved in db
        em.detach(updatedMaladie);
        updatedMaladie
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);

        restMaladieMockMvc.perform(put("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaladie)))
            .andExpect(status().isOk());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeUpdate);
        Maladie testMaladie = maladieList.get(maladieList.size() - 1);
        assertThat(testMaladie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMaladie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMaladie.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMaladie() throws Exception {
        int databaseSizeBeforeUpdate = maladieRepository.findAll().size();

        // Create the Maladie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaladieMockMvc.perform(put("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaladie() throws Exception {
        // Initialize the database
        maladieService.save(maladie);

        int databaseSizeBeforeDelete = maladieRepository.findAll().size();

        // Delete the maladie
        restMaladieMockMvc.perform(delete("/api/maladies/{id}", maladie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maladie.class);
        Maladie maladie1 = new Maladie();
        maladie1.setId(1L);
        Maladie maladie2 = new Maladie();
        maladie2.setId(maladie1.getId());
        assertThat(maladie1).isEqualTo(maladie2);
        maladie2.setId(2L);
        assertThat(maladie1).isNotEqualTo(maladie2);
        maladie1.setId(null);
        assertThat(maladie1).isNotEqualTo(maladie2);
    }
}
