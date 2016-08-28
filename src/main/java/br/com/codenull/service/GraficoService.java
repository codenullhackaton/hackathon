package br.com.codenull.service;

import br.com.codenull.domain.Consulta;
import br.com.codenull.domain.chart.LineChart;
import br.com.codenull.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Daniel Franco
 * @since 28/08/2016 01:03
 */
@Service
public class GraficoService {

    private static final Logger logger = LoggerFactory.getLogger(GraficoService.class);

    @Inject
    private ConsultaRepository consultaRepository;

    public LineChart consultasPorMes(Long cooperado, ZonedDateTime dataBase, Integer offsetMeses) {
        LineChart retorno = new LineChart();
        Map<String, BigDecimal> mapa = new HashMap<>();
        Stream<Consulta> stream = consultaRepository.findByCooperadoId(cooperado).stream();
        int offset = offsetMeses == null ? 3 : offsetMeses;
        if (dataBase != null) {
            stream = stream.filter(p -> p.getDataConsulta().minusMonths(offset).compareTo(dataBase) > 0 || p.getDataConsulta().plusMonths(offset).compareTo(dataBase) < 0);
        } else {
            stream = stream.filter(p -> p.getDataConsulta().minusMonths(offset).compareTo(ZonedDateTime.now()) > 0 || p.getDataConsulta().plusMonths(offset).compareTo(ZonedDateTime.now()) < 0);
        }
        stream.sorted((c1, c2) -> c1.getDataConsulta().compareTo(c2.getDataConsulta()))
            .forEachOrdered( p-> {
                final String chave = p.getDataConsulta().getMonthValue() + "/" + p.getDataConsulta().getYear();
                BigDecimal acumuladoDoMes = mapa.get(chave);
                mapa.put(chave, acumuladoDoMes == null ? BigDecimal.ONE : acumuladoDoMes.add(BigDecimal.ONE));
            });
        for (String chave : mapa.keySet()) {
            retorno.addLabel(chave);
            retorno.addDados(mapa.get(chave));
        }
        logger.debug("ConsultasPorMes[cooperado={}, dataBase={}, offsetMeses={}] = {}", cooperado, dataBase, offset, retorno);
        return retorno;
    }
}
