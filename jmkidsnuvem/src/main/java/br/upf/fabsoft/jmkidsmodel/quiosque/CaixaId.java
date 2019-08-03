package br.upf.fabsoft.jmkidsmodel.quiosque;

import java.io.Serializable;

public class CaixaId implements Serializable{

	private Long id;
	private Long quiosque;
	
	public CaixaId() {
		super();
	}
	
	public CaixaId(Long id, Long quiosque) {
		super();
		this.id = id;
		this.quiosque = quiosque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((quiosque == null) ? 0 : quiosque.hashCode());
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
		CaixaId other = (CaixaId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (quiosque == null) {
			if (other.quiosque != null)
				return false;
		} else if (!quiosque.equals(other.quiosque))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Long quiosque) {
		this.quiosque = quiosque;
	}
	
	
	
}
