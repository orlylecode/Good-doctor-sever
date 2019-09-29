package io.cogitech.gooddoctor.service;

import io.cogitech.gooddoctor.domain.Maladie;
import io.cogitech.gooddoctor.repository.MaladieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Maladie}.
 */
@Service
@Transactional
public class MaladieService {

    private final Logger log = LoggerFactory.getLogger(MaladieService.class);

    private final MaladieRepository maladieRepository;

    public MaladieService(MaladieRepository maladieRepository) {
        this.maladieRepository = maladieRepository;
    }

    /**
     * Save a maladie.
     *
     * @param maladie the entity to save.
     * @return the persisted entity.
     */
    public Maladie save(Maladie maladie) {
        log.debug("Request to save Maladie : {}", maladie);
        return maladieRepository.save(maladie);
    }

    /**
     * Get all the maladies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Maladie> findAll(Pageable pageable) {
        log.debug("Request to get all Maladies");
        return maladieRepository.findAll(pageable);
    }

    /**
     * Get all the maladies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Maladie> findAllWithEagerRelationships(Pageable pageable) {
        return maladieRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one maladie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Maladie> findOne(Long id) {
        log.debug("Request to get Maladie : {}", id);
        return maladieRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the maladie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Maladie : {}", id);
        maladieRepository.deleteById(id);
    }
}
