package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.GoodDoctorSeverApp;
import io.cogitech.gooddoctor.domain.Traitement;
import io.cogitech.gooddoctor.repository.TraitementRepository;
import io.cogitech.gooddoctor.service.TraitementService;
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
 * Integration tests for the {@link TraitementResource} REST controller.
 */
@SpringBootTest(classes = GoodDoctorSeverApp.class)
public class TraitementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_AUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TraitementRepository traitementRepository;

    @Mock
    private TraitementRepository traitementRepositoryMock;

    @Mock
    private TraitementService traitementServiceMock;

    @Autowired
    private TraitementService traitementService;

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

    private MockMvc restTraitementMockMvc;

    private Traitement traitement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TraitementResource traitementResource = new TraitementResource(traitementService);
        this.restTraitementMockMvc = MockMvcBuilders.standaloneSetup(traitementResource)
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
    public static Traitement createEntity(EntityManager em) {
        Traitement traitement = new Traitement()
            .nom(DEFAULT_NOM)
            .auteur(DEFAULT_AUTEUR)
            .description(DEFAULT_DESCRIPTION);
        return traitement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traitement createUpdatedEntity(EntityManager em) {
        Traitement traitement = new Traitement()
            .nom(UPDATED_NOM)
            .auteur(UPDATED_AUTEUR)
            .description(UPDATED_DESCRIPTION);
        return traitement;
    }

    @BeforeEach
    public void initTest() {
        traitement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraitement() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isCreated());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate + 1);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTraitement.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testTraitement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraitementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement with an existing ID
        traitement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isBadRequest());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTraitements() throws Exception {
        // Initialize the database
        traitementRepository.saveAndFlush(traitement);

        // Get all the traitementList
        restTraitementMockMvc.perform(get("/api/traitements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traitement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTraitementsWithEagerRelationshipsIsEnabled() throws Exception {
        TraitementResource traitementResource = new TraitementResource(traitementServiceMock);
        when(traitementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTraitementMockMvc = MockMvcBuilders.standaloneSetup(traitementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTraitementMockMvc.perform(get("/api/traitements?eagerload=true"))
        .andExpect(status().isOk());

        verify(traitementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTraitementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        TraitementResource traitementResource = new TraitementResource(traitementServiceMock);
            when(traitementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTraitementMockMvc = MockMvcBuilders.standaloneSetup(traitementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTraitementMockMvc.perform(get("/api/traitements?eagerload=true"))
        .andExpect(status().isOk());

            verify(traitementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTraitement() throws Exception {
        // Initialize the database
        traitementRepository.saveAndFlush(traitement);

        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", traitement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traitement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraitement() throws Exception {
        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraitement() throws Exception {
        // Initialize the database
        traitementService.save(traitement);

        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Update the traitement
        Traitement updatedTraitement = traitementRepository.findById(traitement.getId()).get();
        // Disconnect from session so that the updates on updatedTraitement are not directly saved in db
        em.detach(updatedTraitement);
        updatedTraitement
            .nom(UPDATED_NOM)
            .auteur(UPDATED_AUTEUR)
            .description(UPDATED_DESCRIPTION);

        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraitement)))
            .andExpect(status().isOk());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTraitement.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testTraitement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraitement() throws Exception {
        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Create the Traitement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isBadRequest());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTraitement() throws Exception {
        // Initialize the database
        traitementService.save(traitement);

        int databaseSizeBeforeDelete = traitementRepository.findAll().size();

        // Delete the traitement
        restTraitementMockMvc.perform(delete("/api/traitements/{id}", traitement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traitement.class);
        Traitement traitement1 = new Traitement();
        traitement1.setId(1L);
        Traitement traitement2 = new Traitement();
        traitement2.setId(traitement1.getId());
        assertThat(traitement1).isEqualTo(traitement2);
        traitement2.setId(2L);
        assertThat(traitement1).isNotEqualTo(traitement2);
        traitement1.setId(null);
        assertThat(traitement1).isNotEqualTo(traitement2);
    }
}
