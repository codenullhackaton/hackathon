package br.com.codenull.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Procedimento.
 */
@Entity
@Table(name = "procedimento")
public class Procedimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "duracao")
    private Integer duracao;

    @NotNull
    @Column(name = "valor", nullable = false)
    private String valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Procedimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public Procedimento duracao(Integer duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getValor() {
        return valor;
    }

    public Procedimento valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Procedimento procedimento = (Procedimento) o;
        if(procedimento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, procedimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Procedimento{" +
            "id=" + id +
            ", descricao='" + descricao + "'" +
            ", duracao='" + duracao + "'" +
            ", valor='" + valor + "'" +
            '}';
    }
}
