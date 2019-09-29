package io.cogitech.gooddoctor.service;

import io.cogitech.gooddoctor.domain.Conseil;
import io.cogitech.gooddoctor.repository.ConseilRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Conseil}.
 */
@Service
@Transactional
public class ConseilService {

    private final Logger log = LoggerFactory.getLogger(ConseilService.class);

    private final ConseilRepository conseilRepository;

    public ConseilService(ConseilRepository conseilRepository) {
        this.conseilRepository = conseilRepository;
    }

    /**
     * Save a conseil.
     *
     * @param conseil the entity to save.
     * @return the persisted entity.
     */
    public Conseil save(Conseil conseil) {
        log.debug("Request to save Conseil : {}", conseil);
        return conseilRepository.save(conseil);
    }

    /**
     * Get all the conseils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Conseil> findAll(Pageable pageable) {
        log.debug("Request to get all Conseils");
        return conseilRepository.findAll(pageable);
    }


    /**
     * Get one conseil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Conseil> findOne(Long id) {
        log.debug("Request to get Conseil : {}", id);
        return conseilRepository.findById(id);
    }

    /**
     * Delete the conseil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conseil : {}", id);
        conseilRepository.deleteById(id);
    }
}
