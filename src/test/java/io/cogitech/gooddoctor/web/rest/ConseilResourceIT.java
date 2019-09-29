package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.GoodDoctorSeverApp;
import io.cogitech.gooddoctor.domain.Conseil;
import io.cogitech.gooddoctor.repository.ConseilRepository;
import io.cogitech.gooddoctor.service.ConseilService;
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
 * Integration tests for the {@link ConseilResource} REST controller.
 */
@SpringBootTest(classes = GoodDoctorSeverApp.class)
public class ConseilResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_AUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_CONSEIL = "AAAAAAAAAA";
    private static final String UPDATED_CONSEIL = "BBBBBBBBBB";

    @Autowired
    private ConseilRepository conseilRepository;

    @Autowired
    private ConseilService conseilService;

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

    private MockMvc restConseilMockMvc;

    private Conseil conseil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConseilResource conseilResource = new ConseilResource(conseilService);
        this.restConseilMockMvc = MockMvcBuilders.standaloneSetup(conseilResource)
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
    public static Conseil createEntity(EntityManager em) {
        Conseil conseil = new Conseil()
            .nom(DEFAULT_NOM)
            .auteur(DEFAULT_AUTEUR)
            .conseil(DEFAULT_CONSEIL);
        return conseil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conseil createUpdatedEntity(EntityManager em) {
        Conseil conseil = new Conseil()
            .nom(UPDATED_NOM)
            .auteur(UPDATED_AUTEUR)
            .conseil(UPDATED_CONSEIL);
        return conseil;
    }

    @BeforeEach
    public void initTest() {
        conseil = createEntity(em);
    }

    @Test
    @Transactional
    public void createConseil() throws Exception {
        int databaseSizeBeforeCreate = conseilRepository.findAll().size();

        // Create the Conseil
        restConseilMockMvc.perform(post("/api/conseils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conseil)))
            .andExpect(status().isCreated());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeCreate + 1);
        Conseil testConseil = conseilList.get(conseilList.size() - 1);
        assertThat(testConseil.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testConseil.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testConseil.getConseil()).isEqualTo(DEFAULT_CONSEIL);
    }

    @Test
    @Transactional
    public void createConseilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conseilRepository.findAll().size();

        // Create the Conseil with an existing ID
        conseil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConseilMockMvc.perform(post("/api/conseils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conseil)))
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConseils() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        // Get all the conseilList
        restConseilMockMvc.perform(get("/api/conseils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conseil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR.toString())))
            .andExpect(jsonPath("$.[*].conseil").value(hasItem(DEFAULT_CONSEIL.toString())));
    }
    
    @Test
    @Transactional
    public void getConseil() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        // Get the conseil
        restConseilMockMvc.perform(get("/api/conseils/{id}", conseil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conseil.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR.toString()))
            .andExpect(jsonPath("$.conseil").value(DEFAULT_CONSEIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConseil() throws Exception {
        // Get the conseil
        restConseilMockMvc.perform(get("/api/conseils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConseil() throws Exception {
        // Initialize the database
        conseilService.save(conseil);

        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();

        // Update the conseil
        Conseil updatedConseil = conseilRepository.findById(conseil.getId()).get();
        // Disconnect from session so that the updates on updatedConseil are not directly saved in db
        em.detach(updatedConseil);
        updatedConseil
            .nom(UPDATED_NOM)
            .auteur(UPDATED_AUTEUR)
            .conseil(UPDATED_CONSEIL);

        restConseilMockMvc.perform(put("/api/conseils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConseil)))
            .andExpect(status().isOk());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
        Conseil testConseil = conseilList.get(conseilList.size() - 1);
        assertThat(testConseil.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testConseil.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testConseil.getConseil()).isEqualTo(UPDATED_CONSEIL);
    }

    @Test
    @Transactional
    public void updateNonExistingConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();

        // Create the Conseil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConseilMockMvc.perform(put("/api/conseils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conseil)))
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConseil() throws Exception {
        // Initialize the database
        conseilService.save(conseil);

        int databaseSizeBeforeDelete = conseilRepository.findAll().size();

        // Delete the conseil
        restConseilMockMvc.perform(delete("/api/conseils/{id}", conseil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conseil.class);
        Conseil conseil1 = new Conseil();
        conseil1.setId(1L);
        Conseil conseil2 = new Conseil();
        conseil2.setId(conseil1.getId());
        assertThat(conseil1).isEqualTo(conseil2);
        conseil2.setId(2L);
        assertThat(conseil1).isNotEqualTo(conseil2);
        conseil1.setId(null);
        assertThat(conseil1).isNotEqualTo(conseil2);
    }
}
