package io.cogitech.gooddoctor.service;

import io.cogitech.gooddoctor.domain.Remede;
import io.cogitech.gooddoctor.repository.RemedeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Remede}.
 */
@Service
@Transactional
public class RemedeService {

    private final Logger log = LoggerFactory.getLogger(RemedeService.class);

    private final RemedeRepository remedeRepository;

    public RemedeService(RemedeRepository remedeRepository) {
        this.remedeRepository = remedeRepository;
    }

    /**
     * Save a remede.
     *
     * @param remede the entity to save.
     * @return the persisted entity.
     */
    public Remede save(Remede remede) {
        log.debug("Request to save Remede : {}", remede);
        return remedeRepository.save(remede);
    }

    /**
     * Get all the remedes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Remede> findAll(Pageable pageable) {
        log.debug("Request to get all Remedes");
        return remedeRepository.findAll(pageable);
    }


    /**
     * Get one remede by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Remede> findOne(Long id) {
        log.debug("Request to get Remede : {}", id);
        return remedeRepository.findById(id);
    }

    /**
     * Delete the remede by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Remede : {}", id);
        remedeRepository.deleteById(id);
    }
}
