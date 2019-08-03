package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema = "nuvem")
public class FuncionarioQuiosque implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "FuncionarioQuiosqueID")
	// @SequenceGenerator(name = "FuncionarioQuiosqueID", sequenceName =
	// "FuncionarioQuiosqueID", allocationSize = 1, schema = "nuvem")
	private Long id;
	@ManyToOne(optional = false)
	@NotNull
	private Quiosque quiosque;
	@ManyToOne(optional = false)
	@NotNull
	private Funcionario funcionario;
	// @Version
	private Integer versao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quiosque getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Quiosque quiosque) {
		this.quiosque = quiosque;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
		FuncionarioQuiosque other = (FuncionarioQuiosque) obj;
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