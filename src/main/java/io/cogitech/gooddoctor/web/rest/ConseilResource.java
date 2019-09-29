package io.cogitech.gooddoctor.web.rest;

import io.cogitech.gooddoctor.domain.Conseil;
import io.cogitech.gooddoctor.service.ConseilService;
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
 * REST controller for managing {@link io.cogitech.gooddoctor.domain.Conseil}.
 */
@RestController
@RequestMapping("/api")
public class ConseilResource {

    private final Logger log = LoggerFactory.getLogger(ConseilResource.class);

    private static final String ENTITY_NAME = "conseil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConseilService conseilService;

    public ConseilResource(ConseilService conseilService) {
        this.conseilService = conseilService;
    }

    /**
     * {@code POST  /conseils} : Create a new conseil.
     *
     * @param conseil the conseil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conseil, or with status {@code 400 (Bad Request)} if the conseil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conseils")
    public ResponseEntity<Conseil> createConseil(@RequestBody Conseil conseil) throws URISyntaxException {
        log.debug("REST request to save Conseil : {}", conseil);
        if (conseil.getId() != null) {
            throw new BadRequestAlertException("A new conseil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conseil result = conseilService.save(conseil);
        return ResponseEntity.created(new URI("/api/conseils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conseils} : Updates an existing conseil.
     *
     * @param conseil the conseil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conseil,
     * or with status {@code 400 (Bad Request)} if the conseil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conseil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conseils")
    public ResponseEntity<Conseil> updateConseil(@RequestBody Conseil conseil) throws URISyntaxException {
        log.debug("REST request to update Conseil : {}", conseil);
        if (conseil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Conseil result = conseilService.save(conseil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conseil.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conseils} : get all the conseils.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conseils in body.
     */
    @GetMapping("/conseils")
    public ResponseEntity<List<Conseil>> getAllConseils(Pageable pageable) {
        log.debug("REST request to get a page of Conseils");
        Page<Conseil> page = conseilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conseils/:id} : get the "id" conseil.
     *
     * @param id the id of the conseil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conseil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conseils/{id}")
    public ResponseEntity<Conseil> getConseil(@PathVariable Long id) {
        log.debug("REST request to get Conseil : {}", id);
        Optional<Conseil> conseil = conseilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conseil);
    }

    /**
     * {@code DELETE  /conseils/:id} : delete the "id" conseil.
     *
     * @param id the id of the conseil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conseils/{id}")
    public ResponseEntity<Void> deleteConseil(@PathVariable Long id) {
        log.debug("REST request to delete Conseil : {}", id);
        conseilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
