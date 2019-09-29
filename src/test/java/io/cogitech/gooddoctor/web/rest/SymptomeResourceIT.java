package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.GoodDoctorSeverApp;
import io.cogitech.gooddoctor.domain.Symptome;
import io.cogitech.gooddoctor.repository.SymptomeRepository;
import io.cogitech.gooddoctor.service.SymptomeService;
import io.cogitech.gooddoctor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.cogitech.gooddoctor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SymptomeResource} REST controller.
 */
@SpringBootTest(classes = GoodDoctorSeverApp.class)
public class SymptomeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EFFET = "AAAAAAAAAA";
    private static final String UPDATED_EFFET = "BBBBBBBBBB";

    @Autowired
    private SymptomeRepository symptomeRepository;

    @Autowired
    private SymptomeService symptomeService;

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

    private MockMvc restSymptomeMockMvc;

    private Symptome symptome;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SymptomeResource symptomeResource = new SymptomeResource(symptomeService);
        this.restSymptomeMockMvc = MockMvcBuilders.standaloneSetup(symptomeResource)
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
    public static Symptome createEntity(EntityManager em) {
        Symptome symptome = new Symptome()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .effet(DEFAULT_EFFET);
        return symptome;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Symptome createUpdatedEntity(EntityManager em) {
        Symptome symptome = new Symptome()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .effet(UPDATED_EFFET);
        return symptome;
    }

    @BeforeEach
    public void initTest() {
        symptome = createEntity(em);
    }

    @Test
    @Transactional
    public void createSymptome() throws Exception {
        int databaseSizeBeforeCreate = symptomeRepository.findAll().size();

        // Create the Symptome
        restSymptomeMockMvc.perform(post("/api/symptomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(symptome)))
            .andExpect(status().isCreated());

        // Validate the Symptome in the database
        List<Symptome> symptomeList = symptomeRepository.findAll();
        assertThat(symptomeList).hasSize(databaseSizeBeforeCreate + 1);
        Symptome testSymptome = symptomeList.get(symptomeList.size() - 1);
        assertThat(testSymptome.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSymptome.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSymptome.getEffet()).isEqualTo(DEFAULT_EFFET);
    }

    @Test
    @Transactional
    public void createSymptomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = symptomeRepository.findAll().size();

        // Create the Symptome with an existing ID
        symptome.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomeMockMvc.perform(post("/api/symptomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(symptome)))
            .andExpect(status().isBadRequest());

        // Validate the Symptome in the database
        List<Symptome> symptomeList = symptomeRepository.findAll();
        assertThat(symptomeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSymptomes() throws Exception {
        // Initialize the database
        symptomeRepository.saveAndFlush(symptome);

        // Get all the symptomeList
        restSymptomeMockMvc.perform(get("/api/symptomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptome.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].effet").value(hasItem(DEFAULT_EFFET.toString())));
    }
    
    @Test
    @Transactional
    public void getSymptome() throws Exception {
        // Initialize the database
        symptomeRepository.saveAndFlush(symptome);

        // Get the symptome
        restSymptomeMockMvc.perform(get("/api/symptomes/{id}", symptome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(symptome.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.effet").value(DEFAULT_EFFET.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSymptome() throws Exception {
        // Get the symptome
        restSymptomeMockMvc.perform(get("/api/symptomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSymptome() throws Exception {
        // Initialize the database
        symptomeService.save(symptome);

        int databaseSizeBeforeUpdate = symptomeRepository.findAll().size();

        // Update the symptome
        Symptome updatedSymptome = symptomeRepository.findById(symptome.getId()).get();
        // Disconnect from session so that the updates on updatedSymptome are not directly saved in db
        em.detach(updatedSymptome);
        updatedSymptome
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .effet(UPDATED_EFFET);

        restSymptomeMockMvc.perform(put("/api/symptomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSymptome)))
            .andExpect(status().isOk());

        // Validate the Symptome in the database
        List<Symptome> symptomeList = symptomeRepository.findAll();
        assertThat(symptomeList).hasSize(databaseSizeBeforeUpdate);
        Symptome testSymptome = symptomeList.get(symptomeList.size() - 1);
        assertThat(testSymptome.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSymptome.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSymptome.getEffet()).isEqualTo(UPDATED_EFFET);
    }

    @Test
    @Transactional
    public void updateNonExistingSymptome() throws Exception {
        int databaseSizeBeforeUpdate = symptomeRepository.findAll().size();

        // Create the Symptome

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomeMockMvc.perform(put("/api/symptomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(symptome)))
            .andExpect(status().isBadRequest());

        // Validate the Symptome in the database
        List<Symptome> symptomeList = symptomeRepository.findAll();
        assertThat(symptomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSymptome() throws Exception {
        // Initialize the database
        symptomeService.save(symptome);

        int databaseSizeBeforeDelete = symptomeRepository.findAll().size();

        // Delete the symptome
        restSymptomeMockMvc.perform(delete("/api/symptomes/{id}", symptome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Symptome> symptomeList = symptomeRepository.findAll();
        assertThat(symptomeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Symptome.class);
        Symptome symptome1 = new Symptome();
        symptome1.setId(1L);
        Symptome symptome2 = new Symptome();
        symptome2.setId(symptome1.getId());
        assertThat(symptome1).isEqualTo(symptome2);
        symptome2.setId(2L);
        assertThat(symptome1).isNotEqualTo(symptome2);
        symptome1.setId(null);
        assertThat(symptome1).isNotEqualTo(symptome2);
    }
}
