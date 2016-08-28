package br.com.codenull.web.rest;

import br.com.codenull.domain.Consulta;
import br.com.codenull.domain.chart.LineChart;
import br.com.codenull.service.ConsultaService;
import br.com.codenull.service.GraficoService;
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
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Consulta.
 */
@RestController
@RequestMapping("/api")
public class ConsultaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultaResource.class);

    @Inject
    private ConsultaService consultaService;

    @Inject
    private GraficoService graficoService;

    /**
     * POST  /consultas : Create a new consulta.
     *
     * @param consulta the consulta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consulta, or with status 400 (Bad Request) if the consulta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/consultas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Consulta> createConsulta(@Valid @RequestBody Consulta consulta) throws URISyntaxException {
        log.debug("REST request to save Consulta : {}", consulta);
        if (consulta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("consulta", "idexists", "A new consulta cannot already have an ID")).body(null);
        }
        Consulta result = consultaService.save(consulta);
        return ResponseEntity.created(new URI("/api/consultas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("consulta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultas : Updates an existing consulta.
     *
     * @param consulta the consulta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consulta,
     * or with status 400 (Bad Request) if the consulta is not valid,
     * or with status 500 (Internal Server Error) if the consulta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/consultas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Consulta> updateConsulta(@Valid @RequestBody Consulta consulta) throws URISyntaxException {
        log.debug("REST request to update Consulta : {}", consulta);
        if (consulta.getId() == null) {
            return createConsulta(consulta);
        }
        Consulta result = consultaService.save(consulta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("consulta", consulta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultas : get all the consultas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/consultas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Consulta>> getAllConsultas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Consultas");
        Page<Consulta> page = consultaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/consultas/agenda/cooperado/{idCooperado}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Consulta>> getConsultasPorCooperado(@PathVariable Long idCooperado) {
        log.debug("REST request to get Consultas por Cooperado: {}", idCooperado);

        List<Consulta> consultas = consultaService.findConsultasByCooperadoId(idCooperado);
        return Optional.ofNullable(consultas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
        value = "/consultas/agenda/beneficiario/{idBeneficiario}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Consulta>> getConsultasPorBeneficiario(@PathVariable Long idBeneficiario) {
        log.debug("REST request to get Consultas por Beneficiario: {}", idBeneficiario);

        List<Consulta> consultas = consultaService.findConsultasByBeneficiarioId(idBeneficiario);
        return Optional.ofNullable(consultas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /consultas/:id : get the "id" consulta.
     *
     * @param id the id of the consulta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consulta, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/consultas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Consulta> getConsulta(@PathVariable Long id) {
        log.debug("REST request to get Consulta : {}", id);
        Consulta consulta = consultaService.findOne(id);
        return Optional.ofNullable(consulta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /consultas/:id : delete the "id" consulta.
     *
     * @param id the id of the consulta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/consultas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        log.debug("REST request to delete Consulta : {}", id);
        consultaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("consulta", id.toString())).build();
    }


    @RequestMapping(value = "/consulta/cooperado/{idCooperado}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LineChart> lineChartCooperado(@PathVariable Long idCooperado) throws URISyntaxException {
        log.debug("REST request to line chart cooperados Cooperado : {} " + idCooperado);

        //LineChart lineChart = criarLineChartFake();
        LineChart lineChart = graficoService.consultasPorMes(idCooperado, null, null);

        /*Cooperado result = cooperadoService.save(cooperado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cooperado", cooperado.getId().toString()))
            .body(result);*/
        return ResponseEntity.ok(lineChart);
    }

    private LineChart criarLineChartFake() {
        LineChart line = new LineChart();
        criarLabelsAndDados(line);
        return line;
    }

    private void criarLabelsAndDados(LineChart line) {
        YearMonth mesAno = YearMonth.of(2016, 1);
        YearMonth hoje = YearMonth.of(2016, 8);
        while (mesAno.isBefore(hoje)) {
            line.addLabel(mesAno.getMonth().name());
            line.addDados(BigDecimal.valueOf(mesAno.getMonthValue() + 50));
            mesAno = mesAno.plusMonths(1);
        }

    }


}
