package br.com.codenull.service;

import br.com.codenull.domain.Consulta;
import br.com.codenull.domain.Cooperado;
import br.com.codenull.domain.chart.LineChart;
import br.com.codenull.repository.ConsultaRepository;
import br.com.codenull.repository.CooperadoRepository;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Daniel Franco
 * @since 28/08/2016 01:03
 */
@Service
public class GraficoService {

    private static final Logger logger = LoggerFactory.getLogger(GraficoService.class);
    private static final BigDecimal MIL = new BigDecimal("1000");
    private static final BigDecimal CEM = new BigDecimal("100");

    @Inject
    private ConsultaRepository consultaRepository;
    @Inject
    private CooperadoRepository cooperadoRepository;

    public LineChart consultasPorMes(Long cooperado, ZonedDateTime dataBase, Integer offsetMeses) {
        LineChart retorno = new LineChart();
        Map<String, BigDecimal> mapa = Maps.newLinkedHashMap();
        Stream<Consulta> stream = consultaRepository.findByCooperadoId(cooperado).stream();
        int offset = offsetMeses == null ? 3 : offsetMeses;
        if (dataBase != null) {
            stream = stream.filter(p -> p.getDataConsulta().minusMonths(offset).compareTo(dataBase) < 0 && p.getDataConsulta().plusMonths(offset).compareTo(dataBase) > 0);
        } else {
            stream = stream.filter(p -> p.getDataConsulta().minusMonths(offset).compareTo(ZonedDateTime.now()) < 0 && p.getDataConsulta().plusMonths(offset).compareTo(ZonedDateTime.now()) > 0);
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

    public LineChart arrecadacaoGeral(Integer ano) {
        LineChart retorno = new LineChart();
        Stream<Consulta> consultas = consultaRepository.findAll().stream();
        int anoConsiderado = ano == null ? LocalDate.now().getYear() : ano;
        Map<String, BigDecimal[]> mapa = Maps.newLinkedHashMap();
        consultas
            .filter( p -> p.getDataConsulta().getYear() == anoConsiderado)
            .sorted((c1, c2) -> c1.getDataConsulta().compareTo(c2.getDataConsulta()))
            .forEachOrdered( p -> {
                final String chave = p.getDataConsulta().getMonthValue() + "/" + p.getDataConsulta().getYear();
                BigDecimal[] valoresDoMes = mapa.get(chave);
                if (valoresDoMes == null) {
                    valoresDoMes = new BigDecimal[2];
                    valoresDoMes[0] = BigDecimal.ZERO;
                    valoresDoMes[1] = BigDecimal.ZERO;
                }
                valoresDoMes[0] = valoresDoMes[0].add(p.getProcedimento().getValor());
                valoresDoMes[1] = valoresDoMes[1].add(BigDecimal.ONE);
                mapa.put(chave, valoresDoMes);
            });
        for (String chave : mapa.keySet()) {
            retorno.addLabel(chave);
            retorno.addDados(mapa.get(chave)[0]);
            retorno.addDadosSecundarios(mapa.get(chave)[1]);
        }
        return retorno;
    }

    public LineChart valorCotasDoCooperado(Long cooperadoID) {
        final Cooperado cooperado = cooperadoRepository.findOne(cooperadoID);
        if (cooperado == null) {
            throw new RuntimeException("Nenhum Cooperado encontrado com ID " + cooperadoID);
        }
        LineChart retorno = new LineChart();
        LocalDate mesSendoCalculado = cooperado.getAdesao().withDayOfMonth(1);
        LocalDate esseMes = LocalDate.now().withDayOfMonth(1);
        BigDecimal cota = cooperado.getValorCota();
        while (mesSendoCalculado.compareTo(esseMes) < 0) {
            BigDecimal percentual = randomPercentual();
            logger.info("percentual {} ", percentual);
            BigDecimal acrescimo = cota.multiply(percentual);
            acrescimo = acrescimo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            cota = cota.add(acrescimo);
            cota = cota.setScale(2);

            logger.info("Mês sendo calculado {}", mesSendoCalculado);
            long qdeMeses = Period.between(mesSendoCalculado, esseMes).toTotalMonths();
            logger.info("qtde meses {}", qdeMeses);
            if(qdeMeses <= 12) {
                retorno.addLabel(mesSendoCalculado.getMonthValue() + "/" + mesSendoCalculado.getYear());
                retorno.addDados(cota);
                retorno.addDadosSecundarios(percentual);
            }
            mesSendoCalculado = mesSendoCalculado.plusMonths(1);
        }
        return retorno;
    }

    private BigDecimal randomPercentual() {
        BigDecimal perc = new BigDecimal(Integer.toString(new Random().nextInt(5200) + 500)).divide(MIL, BigDecimal.ROUND_HALF_EVEN);
        logger.info("perce {}", perc);
        return perc.divide(CEM);
    }
}
