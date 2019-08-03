package br.upf.fabsoft.jmkidsmodel.quiosque;

import java.io.Serializable;

public class BrinquedoOperacaoId implements Serializable {

	private Long id;
	private CaixaId caixa;
	
	public BrinquedoOperacaoId() { 
	}

	public BrinquedoOperacaoId(Long id, CaixaId caixa) {
		super();
		this.id = id;
		this.caixa = caixa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CaixaId getCaixa() {
		return caixa;
	}

	public void setCaixa(CaixaId caixa) {
		this.caixa = caixa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caixa == null) ? 0 : caixa.hashCode());
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
		BrinquedoOperacaoId other = (BrinquedoOperacaoId) obj;
		if (caixa == null) {
			if (other.caixa != null)
				return false;
		} else if (!caixa.equals(other.caixa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	
}