package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.domain.Symptome;
import io.cogitech.gooddoctor.service.SymptomeService;
import io.cogitech.gooddoctor.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.cogitech.gooddoctor.domain.Symptome}.
 */
@RestController
@RequestMapping("/api")
public class SymptomeResource {

    private final Logger log = LoggerFactory.getLogger(SymptomeResource.class);

    private static final String ENTITY_NAME = "symptome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SymptomeService symptomeService;

    public SymptomeResource(SymptomeService symptomeService) {
        this.symptomeService = symptomeService;
    }

    /**
     * {@code POST  /symptomes} : Create a new symptome.
     *
     * @param symptome the symptome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new symptome, or with status {@code 400 (Bad Request)} if the symptome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/symptomes")
    public ResponseEntity<Symptome> createSymptome(@RequestBody Symptome symptome) throws URISyntaxException {
        log.debug("REST request to save Symptome : {}", symptome);
        if (symptome.getId() != null) {
            throw new BadRequestAlertException("A new symptome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Symptome result = symptomeService.save(symptome);
        return ResponseEntity.created(new URI("/api/symptomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /symptomes} : Updates an existing symptome.
     *
     * @param symptome the symptome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated symptome,
     * or with status {@code 400 (Bad Request)} if the symptome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the symptome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/symptomes")
    public ResponseEntity<Symptome> updateSymptome(@RequestBody Symptome symptome) throws URISyntaxException {
        log.debug("REST request to update Symptome : {}", symptome);
        if (symptome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Symptome result = symptomeService.save(symptome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, symptome.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /symptomes} : get all the symptomes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of symptomes in body.
     */
    @GetMapping("/symptomes")
    public ResponseEntity<List<Symptome>> getAllSymptomes(Pageable pageable) {
        log.debug("REST request to get a page of Symptomes");
        Page<Symptome> page = symptomeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /symptomes/:id} : get the "id" symptome.
     *
     * @param id the id of the symptome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the symptome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/symptomes/{id}")
    public ResponseEntity<Symptome> getSymptome(@PathVariable Long id) {
        log.debug("REST request to get Symptome : {}", id);
        Optional<Symptome> symptome = symptomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(symptome);
    }

    /**
     * {@code DELETE  /symptomes/:id} : delete the "id" symptome.
     *
     * @param id the id of the symptome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/symptomes/{id}")
    public ResponseEntity<Void> deleteSymptome(@PathVariable Long id) {
        log.debug("REST request to delete Symptome : {}", id);
        symptomeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
