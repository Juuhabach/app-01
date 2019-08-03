package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema = "nuvem")
public class BrinquedoNoQuiosque implements Serializable {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BrinquedoNoQuiosqueID")
	@SequenceGenerator(name = "BrinquedoNoQuiosqueID", sequenceName = "BrinquedoNoQuiosqueID", allocationSize = 1, schema = "nuvem")
	private Long id;
	 
	@Version
	private Integer versao;
	
	@ManyToOne
	@NotNull
	private Quiosque quiosque;
	
	@ManyToOne
	@NotNull
	private Brinquedo brinquedo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraAtivacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInativacao;
	
	@NotNull
	private Boolean emUso;
	@NotNull
	private Boolean emPausa;	
	@NotNull
	private Boolean contratado;		
	
	public BrinquedoNoQuiosque() {
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

	public Brinquedo getBrinquedo() {
		return brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}

	public Date getDataHoraAtivacao() {
		return dataHoraAtivacao;
	}

	public void setDataHoraAtivacao(Date dataHoraAtivacao) {
		this.dataHoraAtivacao = dataHoraAtivacao;
	}

	public Date getDataHoraInativacao() {
		return dataHoraInativacao;
	}

	public void setDataHoraInativacao(Date dataHoraInativacao) {
		this.dataHoraInativacao = dataHoraInativacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrinquedoNoQuiosque other = (BrinquedoNoQuiosque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Boolean getEmUso() {
		return emUso;
	}

	public void setEmUso(Boolean emUso) {
		this.emUso = emUso;
	}

	public Boolean getEmPausa() {
		return emPausa;
	}

	public void setEmPausa(Boolean emPausa) {
		this.emPausa = emPausa;
	}

	public Boolean getContratado() {
		return contratado;
	}

	public void setContratado(Boolean contratado) {
		this.contratado = contratado;
	}

}
