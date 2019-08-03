package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.lang.Boolean;
import java.lang.Float;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;

@Entity
@Table(schema = "nuvem")
public class PromocaoQuiosque implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "PromocaoQuiosqueID")
	// @SequenceGenerator(name = "PromocaoQuiosqueID", sequenceName =
	// "PromocaoQuiosqueID", allocationSize = 1, schema = "nuvem")
	private Long id;
	@ManyToOne
	@NotNull
	private Quiosque quiosque;
	@ManyToOne
	@NotNull
	private Promocao promocao;
	// @Version
	private Integer versao;

	public PromocaoQuiosque() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public Quiosque getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Quiosque quiosque) {
		this.quiosque = quiosque;
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PromocaoQuiosque other = (PromocaoQuiosque) obj;
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