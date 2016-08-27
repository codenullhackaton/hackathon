package br.com.codenull.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Consulta.
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "data_consulta", nullable = false)
    private LocalDate dataConsulta;

    @NotNull
    @Column(name = "localidade", nullable = false)
    private String localidade;

    @Column(name = "criado_em")
    private LocalDate criadoEm;

    @ManyToOne
    @NotNull
    private Procedimento procedimento;

    @ManyToOne
    @NotNull
    private Cooperado cooperado;

    @ManyToOne
    @NotNull
    private Beneficiario beneficiario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public Consulta dataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
        return this;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getLocalidade() {
        return localidade;
    }

    public Consulta localidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }

    public Consulta criadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
        return this;
    }

    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Consulta procedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
        return this;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Cooperado getCooperado() {
        return cooperado;
    }

    public Consulta cooperado(Cooperado cooperado) {
        this.cooperado = cooperado;
        return this;
    }

    public void setCooperado(Cooperado cooperado) {
        this.cooperado = cooperado;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public Consulta beneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
        return this;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consulta consulta = (Consulta) o;
        if(consulta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, consulta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Consulta{" +
            "id=" + id +
            ", dataConsulta='" + dataConsulta + "'" +
            ", localidade='" + localidade + "'" +
            ", criadoEm='" + criadoEm + "'" +
            '}';
    }
}
