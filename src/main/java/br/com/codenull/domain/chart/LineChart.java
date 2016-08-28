package br.com.codenull.domain.chart;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LineChart implements Serializable {

    private List<String> labels;
    private List<BigDecimal> dados;


    public LineChart() {
        labels = Lists.newLinkedList();
        dados = Lists.newLinkedList();
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<BigDecimal> getDados() {
        return dados;
    }

    public void setDados(List<BigDecimal> dados) {
        this.dados = dados;
    }


    public void addLabel(String mes) {
        labels.add(mes);
    }


    public void addDados(BigDecimal valor) {
        dados.add(valor);
    }
}
