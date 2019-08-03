package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "nuvem")
public class GrupoCliente implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "GrupoClienteID")
	// @SequenceGenerator(name = "GrupoClienteID", sequenceName = "GrupoClienteID",
	// allocationSize = 1, schema = "nuvem")
	private Long id;
	@NotEmpty(message = "O nome deve ser informado!")
	@Length(min = 2, max = 60, message = "A descrição deve ter entre {min} e {max} caracteres!")
	@Column(length = 60)
	private String descricao;
	// @Version
	private Integer versao;

	public GrupoCliente() {
		super();
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
		GrupoCliente other = (GrupoCliente) obj;
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