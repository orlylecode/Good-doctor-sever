package io.cogitech.gooddoctor.service;

import io.cogitech.gooddoctor.domain.Symptome;
import io.cogitech.gooddoctor.repository.SymptomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Symptome}.
 */
@Service
@Transactional
public class SymptomeService {

    private final Logger log = LoggerFactory.getLogger(SymptomeService.class);

    private final SymptomeRepository symptomeRepository;

    public SymptomeService(SymptomeRepository symptomeRepository) {
        this.symptomeRepository = symptomeRepository;
    }

    /**
     * Save a symptome.
     *
     * @param symptome the entity to save.
     * @return the persisted entity.
     */
    public Symptome save(Symptome symptome) {
        log.debug("Request to save Symptome : {}", symptome);
        return symptomeRepository.save(symptome);
    }

    /**
     * Get all the symptomes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Symptome> findAll(Pageable pageable) {
        log.debug("Request to get all Symptomes");
        return symptomeRepository.findAll(pageable);
    }


    /**
     * Get one symptome by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Symptome> findOne(Long id) {
        log.debug("Request to get Symptome : {}", id);
        return symptomeRepository.findById(id);
    }

    /**
     * Delete the symptome by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Symptome : {}", id);
        symptomeRepository.deleteById(id);
    }
}
