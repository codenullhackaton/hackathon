package br.com.codenull.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.codenull.domain.Especialidade;
import br.com.codenull.service.EspecialidadeService;
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
 * REST controller for managing Especialidade.
 */
@RestController
@RequestMapping("/api")
public class EspecialidadeResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeResource.class);
        
    @Inject
    private EspecialidadeService especialidadeService;

    /**
     * POST  /especialidades : Create a new especialidade.
     *
     * @param especialidade the especialidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especialidade, or with status 400 (Bad Request) if the especialidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/especialidades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Especialidade> createEspecialidade(@Valid @RequestBody Especialidade especialidade) throws URISyntaxException {
        log.debug("REST request to save Especialidade : {}", especialidade);
        if (especialidade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("especialidade", "idexists", "A new especialidade cannot already have an ID")).body(null);
        }
        Especialidade result = especialidadeService.save(especialidade);
        return ResponseEntity.created(new URI("/api/especialidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("especialidade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especialidades : Updates an existing especialidade.
     *
     * @param especialidade the especialidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especialidade,
     * or with status 400 (Bad Request) if the especialidade is not valid,
     * or with status 500 (Internal Server Error) if the especialidade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/especialidades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Especialidade> updateEspecialidade(@Valid @RequestBody Especialidade especialidade) throws URISyntaxException {
        log.debug("REST request to update Especialidade : {}", especialidade);
        if (especialidade.getId() == null) {
            return createEspecialidade(especialidade);
        }
        Especialidade result = especialidadeService.save(especialidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("especialidade", especialidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especialidades : get all the especialidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of especialidades in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/especialidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Especialidade>> getAllEspecialidades(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Especialidades");
        Page<Especialidade> page = especialidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especialidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /especialidades/:id : get the "id" especialidade.
     *
     * @param id the id of the especialidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especialidade, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/especialidades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Especialidade> getEspecialidade(@PathVariable Long id) {
        log.debug("REST request to get Especialidade : {}", id);
        Especialidade especialidade = especialidadeService.findOne(id);
        return Optional.ofNullable(especialidade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /especialidades/:id : delete the "id" especialidade.
     *
     * @param id the id of the especialidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/especialidades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEspecialidade(@PathVariable Long id) {
        log.debug("REST request to delete Especialidade : {}", id);
        especialidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("especialidade", id.toString())).build();
    }

}
