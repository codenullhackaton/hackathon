package br.com.codenull.domain;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Mailson on 28/08/2016.
 */
public class ResumoCooperado implements Serializable {

    private Map<String,Integer> consultasSemanas;

    private Integer totalSemana = 0;

    private Integer totalConsultas = 0;
    private BigDecimal totalValor = BigDecimal.ZERO;

    private BigDecimal valorCotaOriginal = BigDecimal.ZERO;

    private BigDecimal valorCotaReajustado = BigDecimal.ZERO;


    public ResumoCooperado() {
        consultasSemanas = Maps.newLinkedHashMap();
    }

    public Map<String, Integer> getConsultasSemanas() {
        return consultasSemanas;
    }

    public void setConsultasSemanas(Map<String, Integer> consultasSemanas) {
        this.consultasSemanas = consultasSemanas;
    }

    public Integer getTotalConsultas() {
        return totalConsultas;
    }

    public void setTotalConsultas(Integer totalConsultas) {
        this.totalConsultas = totalConsultas;
    }

    public BigDecimal getTotalValor() {
        return totalValor;
    }

    public void setTotalValor(BigDecimal totalValor) {
        this.totalValor = totalValor;
    }

    public Integer getTotalSemana() {
        return totalSemana;
    }

    public void setTotalSemana(Integer totalSemana) {
        this.totalSemana = totalSemana;
    }

    public BigDecimal somarValor(BigDecimal valor){
        totalValor = totalValor.add(valor);
        return totalValor;
    }

    public BigDecimal getValorCotaOriginal() {
        return valorCotaOriginal;
    }

    public void setValorCotaOriginal(BigDecimal valorCotaOriginal) {
        this.valorCotaOriginal = valorCotaOriginal;
    }

    public BigDecimal getValorCotaReajustado() {
        return valorCotaReajustado;
    }

    public void setValorCotaReajustado(BigDecimal valorCotaReajustado) {
        this.valorCotaReajustado = valorCotaReajustado;
    }
}
