package br.com.codenull.web.rest;

import br.com.codenull.domain.Beneficiario;
import br.com.codenull.service.BeneficiarioService;
import br.com.codenull.web.rest.util.HeaderUtil;
import br.com.codenull.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Beneficiario.
 */
@RestController
@RequestMapping("/api")
public class BeneficiarioResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiarioResource.class);

    @Inject
    private BeneficiarioService beneficiarioService;

    /**
     * POST  /beneficiarios : Create a new beneficiario.
     *
     * @param beneficiario the beneficiario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beneficiario, or with status 400 (Bad Request) if the beneficiario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beneficiarios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Beneficiario> createBeneficiario(@RequestBody Beneficiario beneficiario) throws URISyntaxException {
        log.debug("REST request to save Beneficiario : {}", beneficiario);
        if (beneficiario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("beneficiario", "idexists", "A new beneficiario cannot already have an ID")).body(null);
        }
        Beneficiario result = beneficiarioService.save(beneficiario);
        return ResponseEntity.created(new URI("/api/beneficiarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("beneficiario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beneficiarios : Updates an existing beneficiario.
     *
     * @param beneficiario the beneficiario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beneficiario,
     * or with status 400 (Bad Request) if the beneficiario is not valid,
     * or with status 500 (Internal Server Error) if the beneficiario couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beneficiarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Beneficiario> updateBeneficiario(@RequestBody Beneficiario beneficiario) throws URISyntaxException {
        log.debug("REST request to update Beneficiario : {}", beneficiario);
        if (beneficiario.getId() == null) {
            return createBeneficiario(beneficiario);
        }
        Beneficiario result = beneficiarioService.save(beneficiario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("beneficiario", beneficiario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beneficiarios : get all the beneficiarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of beneficiarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/beneficiarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Beneficiario>> getAllBeneficiarios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Beneficiarios");
        Page<Beneficiario> page = beneficiarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/beneficiarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /beneficiarios/:id : get the "id" beneficiario.
     *
     * @param id the id of the beneficiario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beneficiario, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/beneficiarios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Beneficiario> getBeneficiario(@PathVariable Long id) {
        log.debug("REST request to get Beneficiario : {}", id);
        Beneficiario beneficiario = beneficiarioService.findOne(id);
        return Optional.ofNullable(beneficiario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/beneficiarios-por-cooperado/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Beneficiario> getBeneficiarioPorCooperado(@PathVariable Long id) {
        log.debug("REST request to get Beneficiario : {}", id);
        Beneficiario beneficiario = beneficiarioService.getBeneficiarioPorCooperado(id);
        return Optional.ofNullable(beneficiario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /beneficiarios/:id : delete the "id" beneficiario.
     *
     * @param id the id of the beneficiario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/beneficiarios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBeneficiario(@PathVariable Long id) {
        log.debug("REST request to delete Beneficiario : {}", id);
        beneficiarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("beneficiario", id.toString())).build();
    }

}
