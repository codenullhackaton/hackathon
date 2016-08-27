package br.com.codenull.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cooperado.
 */
@Entity
@Table(name = "cooperado")
public class Cooperado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "crm", nullable = false)
    private String crm;

    @NotNull
    @Column(name = "valor_cota", precision=10, scale=2, nullable = false)
    private BigDecimal valorCota;

    @NotNull
    @Column(name = "adesao", nullable = false)
    private LocalDate adesao;

    @ManyToMany
    @JoinTable(name = "cooperado_especialidades",
               joinColumns = @JoinColumn(name="cooperados_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="especialidades_id", referencedColumnName="ID"))
    private Set<Especialidade> especialidades = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Cooperado nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public Cooperado crm(String crm) {
        this.crm = crm;
        return this;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public BigDecimal getValorCota() {
        return valorCota;
    }

    public Cooperado valorCota(BigDecimal valorCota) {
        this.valorCota = valorCota;
        return this;
    }

    public void setValorCota(BigDecimal valorCota) {
        this.valorCota = valorCota;
    }

    public LocalDate getAdesao() {
        return adesao;
    }

    public Cooperado adesao(LocalDate adesao) {
        this.adesao = adesao;
        return this;
    }

    public void setAdesao(LocalDate adesao) {
        this.adesao = adesao;
    }

    public Set<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public Cooperado especialidades(Set<Especialidade> especialidades) {
        this.especialidades = especialidades;
        return this;
    }

    public Cooperado addEspecialidade(Especialidade especialidade) {
        especialidades.add(especialidade);
        /*especialidade.getCooperados().add(this);*/
        return this;
    }

    public Cooperado removeEspecialidade(Especialidade especialidade) {
        especialidades.remove(especialidade);
        /*especialidade.getCooperados().remove(this);*/
        return this;
    }

    public void setEspecialidades(Set<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cooperado cooperado = (Cooperado) o;
        if(cooperado.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cooperado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cooperado{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", crm='" + crm + "'" +
            ", valorCota='" + valorCota + "'" +
            ", adesao='" + adesao + "'" +
            '}';
    }
}
