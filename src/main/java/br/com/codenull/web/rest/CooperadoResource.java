package br.com.codenull.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.codenull.domain.Cooperado;
import br.com.codenull.service.CooperadoService;
import br.com.codenull.web.rest.util.HeaderUtil;
import br.com.codenull.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cooperado.
 */
@RestController
@RequestMapping("/api")
public class CooperadoResource {

    private final Logger log = LoggerFactory.getLogger(CooperadoResource.class);
        
    @Inject
    private CooperadoService cooperadoService;

    /**
     * POST  /cooperados : Create a new cooperado.
     *
     * @param cooperado the cooperado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cooperado, or with status 400 (Bad Request) if the cooperado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cooperados",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cooperado> createCooperado(@Valid @RequestBody Cooperado cooperado) throws URISyntaxException {
        log.debug("REST request to save Cooperado : {}", cooperado);
        if (cooperado.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cooperado", "idexists", "A new cooperado cannot already have an ID")).body(null);
        }
        Cooperado result = cooperadoService.save(cooperado);
        return ResponseEntity.created(new URI("/api/cooperados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cooperado", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cooperados : Updates an existing cooperado.
     *
     * @param cooperado the cooperado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cooperado,
     * or with status 400 (Bad Request) if the cooperado is not valid,
     * or with status 500 (Internal Server Error) if the cooperado couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cooperados",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cooperado> updateCooperado(@Valid @RequestBody Cooperado cooperado) throws URISyntaxException {
        log.debug("REST request to update Cooperado : {}", cooperado);
        if (cooperado.getId() == null) {
            return createCooperado(cooperado);
        }
        Cooperado result = cooperadoService.save(cooperado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cooperado", cooperado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cooperados : get all the cooperados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cooperados in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cooperados",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cooperado>> getAllCooperados(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cooperados");
        Page<Cooperado> page = cooperadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cooperados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cooperados/:id : get the "id" cooperado.
     *
     * @param id the id of the cooperado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cooperado, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cooperados/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cooperado> getCooperado(@PathVariable Long id) {
        log.debug("REST request to get Cooperado : {}", id);
        Cooperado cooperado = cooperadoService.findOne(id);
        return Optional.ofNullable(cooperado)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cooperados/:id : delete the "id" cooperado.
     *
     * @param id the id of the cooperado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cooperados/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCooperado(@PathVariable Long id) {
        log.debug("REST request to delete Cooperado : {}", id);
        cooperadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cooperado", id.toString())).build();
    }

}
