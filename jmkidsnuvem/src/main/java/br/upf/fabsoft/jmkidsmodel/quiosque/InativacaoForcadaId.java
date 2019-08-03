package br.upf.fabsoft.jmkidsmodel.quiosque;

import java.io.Serializable;


public class InativacaoForcadaId implements Serializable {

	private Long id;
	private BrinquedoOperacaoId brinquedoOperacao;
	
	public InativacaoForcadaId() {
		super();
	}

	public InativacaoForcadaId(Long id, BrinquedoOperacaoId brinquedoOperacao) {
		super();
		this.id = id;
		this.brinquedoOperacao = brinquedoOperacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BrinquedoOperacaoId getBrinquedoOperacao() {
		return brinquedoOperacao;
	}

	public void setBrinquedoOperacao(BrinquedoOperacaoId brinquedoOperacao) {
		this.brinquedoOperacao = brinquedoOperacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brinquedoOperacao == null) ? 0 : brinquedoOperacao.hashCode());
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
		InativacaoForcadaId other = (InativacaoForcadaId) obj;
		if (brinquedoOperacao == null) {
			if (other.brinquedoOperacao != null)
				return false;
		} else if (!brinquedoOperacao.equals(other.brinquedoOperacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
