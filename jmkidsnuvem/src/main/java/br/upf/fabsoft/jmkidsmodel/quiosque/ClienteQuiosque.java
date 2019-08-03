package br.upf.fabsoft.jmkidsmodel.quiosque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;

/**
 * Entity implementation class for Entity: ClienteQuiosque
 *
 */
@Entity
@IdClass(ClienteQuiosqueId.class)
@Table(schema = "quiosque")
public class ClienteQuiosque implements Serializable {

	@Id
	@ManyToOne
	@NotNull
	private Quiosque quiosque;
	
	@Id
	@ManyToOne
	@NotNull
	private Cliente cliente;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraModificado;
	
			
	private static final long serialVersionUID = 1L;
	
	public ClienteQuiosque() {
		super();
	}

	public Quiosque getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Quiosque quiosque) {
		this.quiosque = quiosque;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		ClienteQuiosque other = (ClienteQuiosque) obj;
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

	public Date getDataHoraModificado() {
		return dataHoraModificado;
	}

	public void setDataHoraModificado(Date dataHoraModificado) {
		this.dataHoraModificado = dataHoraModificado;
	}   


   
}
