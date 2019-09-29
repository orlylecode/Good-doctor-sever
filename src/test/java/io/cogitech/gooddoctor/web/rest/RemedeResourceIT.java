package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.GoodDoctorSeverApp;
import io.cogitech.gooddoctor.domain.Remede;
import io.cogitech.gooddoctor.repository.RemedeRepository;
import io.cogitech.gooddoctor.service.RemedeService;
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
 * Integration tests for the {@link RemedeResource} REST controller.
 */
@SpringBootTest(classes = GoodDoctorSeverApp.class)
public class RemedeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_COMPOSITION = "AAAAAAAAAA";
    private static final String UPDATED_COMPOSITION = "BBBBBBBBBB";

    private static final String DEFAULT_POSSOLOGIE = "AAAAAAAAAA";
    private static final String UPDATED_POSSOLOGIE = "BBBBBBBBBB";

    private static final String DEFAULT_PREVENTION = "AAAAAAAAAA";
    private static final String UPDATED_PREVENTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX = "AAAAAAAAAA";
    private static final String UPDATED_PRIX = "BBBBBBBBBB";

    @Autowired
    private RemedeRepository remedeRepository;

    @Autowired
    private RemedeService remedeService;

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

    private MockMvc restRemedeMockMvc;

    private Remede remede;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RemedeResource remedeResource = new RemedeResource(remedeService);
        this.restRemedeMockMvc = MockMvcBuilders.standaloneSetup(remedeResource)
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
    public static Remede createEntity(EntityManager em) {
        Remede remede = new Remede()
            .nom(DEFAULT_NOM)
            .composition(DEFAULT_COMPOSITION)
            .possologie(DEFAULT_POSSOLOGIE)
            .prevention(DEFAULT_PREVENTION)
            .prix(DEFAULT_PRIX);
        return remede;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remede createUpdatedEntity(EntityManager em) {
        Remede remede = new Remede()
            .nom(UPDATED_NOM)
            .composition(UPDATED_COMPOSITION)
            .possologie(UPDATED_POSSOLOGIE)
            .prevention(UPDATED_PREVENTION)
            .prix(UPDATED_PRIX);
        return remede;
    }

    @BeforeEach
    public void initTest() {
        remede = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemede() throws Exception {
        int databaseSizeBeforeCreate = remedeRepository.findAll().size();

        // Create the Remede
        restRemedeMockMvc.perform(post("/api/remedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remede)))
            .andExpect(status().isCreated());

        // Validate the Remede in the database
        List<Remede> remedeList = remedeRepository.findAll();
        assertThat(remedeList).hasSize(databaseSizeBeforeCreate + 1);
        Remede testRemede = remedeList.get(remedeList.size() - 1);
        assertThat(testRemede.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRemede.getComposition()).isEqualTo(DEFAULT_COMPOSITION);
        assertThat(testRemede.getPossologie()).isEqualTo(DEFAULT_POSSOLOGIE);
        assertThat(testRemede.getPrevention()).isEqualTo(DEFAULT_PREVENTION);
        assertThat(testRemede.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createRemedeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remedeRepository.findAll().size();

        // Create the Remede with an existing ID
        remede.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemedeMockMvc.perform(post("/api/remedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remede)))
            .andExpect(status().isBadRequest());

        // Validate the Remede in the database
        List<Remede> remedeList = remedeRepository.findAll();
        assertThat(remedeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRemedes() throws Exception {
        // Initialize the database
        remedeRepository.saveAndFlush(remede);

        // Get all the remedeList
        restRemedeMockMvc.perform(get("/api/remedes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remede.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].composition").value(hasItem(DEFAULT_COMPOSITION.toString())))
            .andExpect(jsonPath("$.[*].possologie").value(hasItem(DEFAULT_POSSOLOGIE.toString())))
            .andExpect(jsonPath("$.[*].prevention").value(hasItem(DEFAULT_PREVENTION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.toString())));
    }
    
    @Test
    @Transactional
    public void getRemede() throws Exception {
        // Initialize the database
        remedeRepository.saveAndFlush(remede);

        // Get the remede
        restRemedeMockMvc.perform(get("/api/remedes/{id}", remede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(remede.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.composition").value(DEFAULT_COMPOSITION.toString()))
            .andExpect(jsonPath("$.possologie").value(DEFAULT_POSSOLOGIE.toString()))
            .andExpect(jsonPath("$.prevention").value(DEFAULT_PREVENTION.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRemede() throws Exception {
        // Get the remede
        restRemedeMockMvc.perform(get("/api/remedes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemede() throws Exception {
        // Initialize the database
        remedeService.save(remede);

        int databaseSizeBeforeUpdate = remedeRepository.findAll().size();

        // Update the remede
        Remede updatedRemede = remedeRepository.findById(remede.getId()).get();
        // Disconnect from session so that the updates on updatedRemede are not directly saved in db
        em.detach(updatedRemede);
        updatedRemede
            .nom(UPDATED_NOM)
            .composition(UPDATED_COMPOSITION)
            .possologie(UPDATED_POSSOLOGIE)
            .prevention(UPDATED_PREVENTION)
            .prix(UPDATED_PRIX);

        restRemedeMockMvc.perform(put("/api/remedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRemede)))
            .andExpect(status().isOk());

        // Validate the Remede in the database
        List<Remede> remedeList = remedeRepository.findAll();
        assertThat(remedeList).hasSize(databaseSizeBeforeUpdate);
        Remede testRemede = remedeList.get(remedeList.size() - 1);
        assertThat(testRemede.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRemede.getComposition()).isEqualTo(UPDATED_COMPOSITION);
        assertThat(testRemede.getPossologie()).isEqualTo(UPDATED_POSSOLOGIE);
        assertThat(testRemede.getPrevention()).isEqualTo(UPDATED_PREVENTION);
        assertThat(testRemede.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingRemede() throws Exception {
        int databaseSizeBeforeUpdate = remedeRepository.findAll().size();

        // Create the Remede

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemedeMockMvc.perform(put("/api/remedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remede)))
            .andExpect(status().isBadRequest());

        // Validate the Remede in the database
        List<Remede> remedeList = remedeRepository.findAll();
        assertThat(remedeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRemede() throws Exception {
        // Initialize the database
        remedeService.save(remede);

        int databaseSizeBeforeDelete = remedeRepository.findAll().size();

        // Delete the remede
        restRemedeMockMvc.perform(delete("/api/remedes/{id}", remede.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remede> remedeList = remedeRepository.findAll();
        assertThat(remedeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remede.class);
        Remede remede1 = new Remede();
        remede1.setId(1L);
        Remede remede2 = new Remede();
        remede2.setId(remede1.getId());
        assertThat(remede1).isEqualTo(remede2);
        remede2.setId(2L);
        assertThat(remede1).isNotEqualTo(remede2);
        remede1.setId(null);
        assertThat(remede1).isNotEqualTo(remede2);
    }
}
