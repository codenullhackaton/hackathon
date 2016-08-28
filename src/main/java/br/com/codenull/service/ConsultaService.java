package br.com.codenull.service;

import br.com.codenull.domain.Consulta;
import br.com.codenull.domain.Cooperado;
import br.com.codenull.domain.ResumoCooperado;
import br.com.codenull.repository.ConsultaRepository;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Service Implementation for managing Consulta.
 */
@Service
@Transactional
public class ConsultaService {

    private final Logger log = LoggerFactory.getLogger(ConsultaService.class);

    @Inject
    private ConsultaRepository consultaRepository;
    @Inject
    private CooperadoService cooperadoService;

    /**
     * Save a consulta.
     *
     * @param consulta the entity to save
     * @return the persisted entity
     */
    public Consulta save(Consulta consulta) {
        log.debug("Request to save Consulta : {}", consulta);
        Consulta result = consultaRepository.save(consulta);
        return result;
    }

    /**
     * Get all the consultas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Consulta> findAll(Pageable pageable) {
        log.debug("Request to get all Consultas");
        Page<Consulta> result = consultaRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one consulta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Consulta findOne(Long id) {
        log.debug("Request to get Consulta : {}", id);
        Consulta consulta = consultaRepository.findOne(id);
        return consulta;
    }

    /**
     * Delete the  consulta by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Consulta : {}", id);
        consultaRepository.delete(id);
    }

    public List<Consulta> consultaByCooperadoId(Cooperado cooperado) {
        log.debug("Consultando as Consultas do cooperado: {}", cooperado);
        return consultaRepository.findByCooperadoId(cooperado.getId());
    }

    public List<Consulta> findConsultasByCooperadoId(Long idCooperado) {
        log.debug("REST request to get Consultas por Cooperado: {}", idCooperado);
        return consultaRepository.findByCooperadoId(idCooperado);
    }

    public List<Consulta> findConsultasByBeneficiarioId(Long idBeneficiario) {
        log.debug("REST request to get Consultas por Beneficiario: {}", idBeneficiario);
        return consultaRepository.findByBeneficiarioId(idBeneficiario);
    }

    public ResumoCooperado getResumoCooperado(Long cooperadoId){

        ResumoCooperado resumoCooperado = new ResumoCooperado();
        Map<String, BigDecimal> mapa = Maps.newLinkedHashMap();
        Stream<Consulta> consultas = consultaRepository.findByCooperadoId(cooperadoId).stream();
        LocalDate hoje = LocalDate.now();
        consultas  = consultas .filter(p -> p.getDataConsulta().getYear() == hoje.getYear() || p.getDataConsulta().toLocalDate().isAfter(hoje));

        consultas
            .forEach(p -> {
                if(p.getDataConsulta().getYear()== hoje.getYear() && p.getDataConsulta().getMonthValue() == hoje.getMonthValue()) {
                    log.info("data consulta: {}", p.getDataConsulta());
                    resumoCooperado.setTotalConsultas(resumoCooperado.getTotalConsultas() + 1);
                    resumoCooperado.somarValor(p.getProcedimento().getValor());
                    TemporalField semanaAno = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
                    if (p.getDataConsulta().get(semanaAno) == hoje.get(semanaAno)) {
                        resumoCooperado.setTotalSemana(resumoCooperado.getTotalSemana() + 1);
                    }
                }
                if(p.getDataConsulta().toLocalDate().isBefore(hoje)){
                    resumoCooperado.somarValorArrecadado(p.getProcedimento().getValor());
                }
                if(p.getDataConsulta().toLocalDate().isAfter(hoje)){
                    resumoCooperado.setProximasConsultas(resumoCooperado.getProximasConsultas()+1);
                }

            });

/*

        consultas  = consultas.filter(p -> p.getDataConsulta().getYear()== hoje.getYear() && p.getDataConsulta().toLocalDate().isBefore(hoje));
        consultas
            .forEach(p -> {
                resumoCooperado.somarValorArrecadado(p.getProcedimento().getValor());
            });

        consultas  = consultas .filter(p -> p.getDataConsulta().toLocalDate().isAfter(hoje));
        consultas
            .forEach(p -> {
                resumoCooperado.setProximasConsultas(resumoCooperado.getProximasConsultas()+1);
            });
*/

        Cooperado cooperado = cooperadoService.findOne(cooperadoId);
        resumoCooperado.setValorCotaOriginal(cooperado.getValorCota());
        resumoCooperado.setValorCotaReajustado(cooperado.getValorCota());
        return resumoCooperado;
    }



}
