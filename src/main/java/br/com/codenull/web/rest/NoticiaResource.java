package br.com.codenull.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.codenull.domain.Noticia;
import br.com.codenull.service.NoticiaService;
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
 * REST controller for managing Noticia.
 */
@RestController
@RequestMapping("/api")
public class NoticiaResource {

    private final Logger log = LoggerFactory.getLogger(NoticiaResource.class);
        
    @Inject
    private NoticiaService noticiaService;

    /**
     * POST  /noticias : Create a new noticia.
     *
     * @param noticia the noticia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noticia, or with status 400 (Bad Request) if the noticia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/noticias",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noticia> createNoticia(@Valid @RequestBody Noticia noticia) throws URISyntaxException {
        log.debug("REST request to save Noticia : {}", noticia);
        if (noticia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("noticia", "idexists", "A new noticia cannot already have an ID")).body(null);
        }
        Noticia result = noticiaService.save(noticia);
        return ResponseEntity.created(new URI("/api/noticias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("noticia", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noticias : Updates an existing noticia.
     *
     * @param noticia the noticia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noticia,
     * or with status 400 (Bad Request) if the noticia is not valid,
     * or with status 500 (Internal Server Error) if the noticia couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/noticias",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noticia> updateNoticia(@Valid @RequestBody Noticia noticia) throws URISyntaxException {
        log.debug("REST request to update Noticia : {}", noticia);
        if (noticia.getId() == null) {
            return createNoticia(noticia);
        }
        Noticia result = noticiaService.save(noticia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("noticia", noticia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noticias : get all the noticias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of noticias in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/noticias",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Noticia>> getAllNoticias(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Noticias");
        Page<Noticia> page = noticiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/noticias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /noticias/:id : get the "id" noticia.
     *
     * @param id the id of the noticia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noticia, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/noticias/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noticia> getNoticia(@PathVariable Long id) {
        log.debug("REST request to get Noticia : {}", id);
        Noticia noticia = noticiaService.findOne(id);
        return Optional.ofNullable(noticia)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /noticias/:id : delete the "id" noticia.
     *
     * @param id the id of the noticia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/noticias/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNoticia(@PathVariable Long id) {
        log.debug("REST request to delete Noticia : {}", id);
        noticiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("noticia", id.toString())).build();
    }

}
