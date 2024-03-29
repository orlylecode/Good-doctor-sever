package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.domain.Remede;
import io.cogitech.gooddoctor.service.RemedeService;
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
 * REST controller for managing {@link io.cogitech.gooddoctor.domain.Remede}.
 */
@RestController
@RequestMapping("/api")
public class RemedeResource {

    private final Logger log = LoggerFactory.getLogger(RemedeResource.class);

    private static final String ENTITY_NAME = "remede";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemedeService remedeService;

    public RemedeResource(RemedeService remedeService) {
        this.remedeService = remedeService;
    }

    /**
     * {@code POST  /remedes} : Create a new remede.
     *
     * @param remede the remede to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remede, or with status {@code 400 (Bad Request)} if the remede has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remedes")
    public ResponseEntity<Remede> createRemede(@RequestBody Remede remede) throws URISyntaxException {
        log.debug("REST request to save Remede : {}", remede);
        if (remede.getId() != null) {
            throw new BadRequestAlertException("A new remede cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Remede result = remedeService.save(remede);
        return ResponseEntity.created(new URI("/api/remedes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remedes} : Updates an existing remede.
     *
     * @param remede the remede to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remede,
     * or with status {@code 400 (Bad Request)} if the remede is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remede couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remedes")
    public ResponseEntity<Remede> updateRemede(@RequestBody Remede remede) throws URISyntaxException {
        log.debug("REST request to update Remede : {}", remede);
        if (remede.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Remede result = remedeService.save(remede);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remede.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /remedes} : get all the remedes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remedes in body.
     */
    @GetMapping("/remedes")
    public ResponseEntity<List<Remede>> getAllRemedes(Pageable pageable) {
        log.debug("REST request to get a page of Remedes");
        Page<Remede> page = remedeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remedes/:id} : get the "id" remede.
     *
     * @param id the id of the remede to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remede, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remedes/{id}")
    public ResponseEntity<Remede> getRemede(@PathVariable Long id) {
        log.debug("REST request to get Remede : {}", id);
        Optional<Remede> remede = remedeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remede);
    }

    /**
     * {@code DELETE  /remedes/:id} : delete the "id" remede.
     *
     * @param id the id of the remede to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remedes/{id}")
    public ResponseEntity<Void> deleteRemede(@PathVariable Long id) {
        log.debug("REST request to delete Remede : {}", id);
        remedeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
