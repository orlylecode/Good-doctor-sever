package io.cogitech.gooddoctor.service;

import io.cogitech.gooddoctor.domain.Traitement;
import io.cogitech.gooddoctor.repository.TraitementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Traitement}.
 */
@Service
@Transactional
public class TraitementService {

    private final Logger log = LoggerFactory.getLogger(TraitementService.class);

    private final TraitementRepository traitementRepository;

    public TraitementService(TraitementRepository traitementRepository) {
        this.traitementRepository = traitementRepository;
    }

    /**
     * Save a traitement.
     *
     * @param traitement the entity to save.
     * @return the persisted entity.
     */
    public Traitement save(Traitement traitement) {
        log.debug("Request to save Traitement : {}", traitement);
        return traitementRepository.save(traitement);
    }

    /**
     * Get all the traitements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Traitement> findAll(Pageable pageable) {
        log.debug("Request to get all Traitements");
        return traitementRepository.findAll(pageable);
    }

    /**
     * Get all the traitements with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Traitement> findAllWithEagerRelationships(Pageable pageable) {
        return traitementRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one traitement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Traitement> findOne(Long id) {
        log.debug("Request to get Traitement : {}", id);
        return traitementRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the traitement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Traitement : {}", id);
        traitementRepository.deleteById(id);
    }
}
