package br.com.codenull.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.codenull.domain.Procedimento;
import br.com.codenull.service.ProcedimentoService;
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
 * REST controller for managing Procedimento.
 */
@RestController
@RequestMapping("/api")
public class ProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoResource.class);
        
    @Inject
    private ProcedimentoService procedimentoService;

    /**
     * POST  /procedimentos : Create a new procedimento.
     *
     * @param procedimento the procedimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedimento, or with status 400 (Bad Request) if the procedimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/procedimentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Procedimento> createProcedimento(@Valid @RequestBody Procedimento procedimento) throws URISyntaxException {
        log.debug("REST request to save Procedimento : {}", procedimento);
        if (procedimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("procedimento", "idexists", "A new procedimento cannot already have an ID")).body(null);
        }
        Procedimento result = procedimentoService.save(procedimento);
        return ResponseEntity.created(new URI("/api/procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("procedimento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedimentos : Updates an existing procedimento.
     *
     * @param procedimento the procedimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedimento,
     * or with status 400 (Bad Request) if the procedimento is not valid,
     * or with status 500 (Internal Server Error) if the procedimento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/procedimentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Procedimento> updateProcedimento(@Valid @RequestBody Procedimento procedimento) throws URISyntaxException {
        log.debug("REST request to update Procedimento : {}", procedimento);
        if (procedimento.getId() == null) {
            return createProcedimento(procedimento);
        }
        Procedimento result = procedimentoService.save(procedimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("procedimento", procedimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedimentos : get all the procedimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedimentos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/procedimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Procedimento>> getAllProcedimentos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Procedimentos");
        Page<Procedimento> page = procedimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedimentos/:id : get the "id" procedimento.
     *
     * @param id the id of the procedimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedimento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/procedimentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Procedimento> getProcedimento(@PathVariable Long id) {
        log.debug("REST request to get Procedimento : {}", id);
        Procedimento procedimento = procedimentoService.findOne(id);
        return Optional.ofNullable(procedimento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /procedimentos/:id : delete the "id" procedimento.
     *
     * @param id the id of the procedimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/procedimentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimento : {}", id);
        procedimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("procedimento", id.toString())).build();
    }

}
