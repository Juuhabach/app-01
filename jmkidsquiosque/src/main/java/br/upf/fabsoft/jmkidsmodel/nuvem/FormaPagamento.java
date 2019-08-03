package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

@Entity
@Table(schema = "nuvem")
public class FormaPagamento implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "FormaPagamentoID")
	// @SequenceGenerator(name = "FormaPagamentoID", sequenceName =
	// "FormaPagamentoID", allocationSize = 1, schema = "nuvem")
	private Long id;
	@NotEmpty
	@Length(min = 2, max = 60)
	@Column(length = 60)
	private String descricao;
	// @Version
	private Integer versao;

	public FormaPagamento() {
		super();
	}

	public FormaPagamento(Long id, @NotEmpty @Length(min = 2, max = 60) String descricao, Integer versao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.versao = versao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormaPagamento other = (FormaPagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
