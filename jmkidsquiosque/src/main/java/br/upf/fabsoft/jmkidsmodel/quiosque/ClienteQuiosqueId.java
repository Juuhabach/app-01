package br.upf.fabsoft.jmkidsmodel.quiosque;

import java.io.Serializable;

public class ClienteQuiosqueId implements Serializable {

	private Long quiosque;
	private String cliente;

	public ClienteQuiosqueId() {
		super();
	}

	public ClienteQuiosqueId(String cliente, Long quiosque) {
		super();
		this.cliente = cliente;
		this.quiosque = quiosque;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Long getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Long quiosque) {
		this.quiosque = quiosque;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteQuiosqueId other = (ClienteQuiosqueId) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (quiosque == null) {
			if (other.quiosque != null)
				return false;
		} else if (!quiosque.equals(other.quiosque))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((quiosque == null) ? 0 : quiosque.hashCode());
		return result;
	}
}