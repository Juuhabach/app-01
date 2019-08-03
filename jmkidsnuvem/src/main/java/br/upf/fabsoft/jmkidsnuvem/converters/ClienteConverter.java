package br.upf.fabsoft.jmkidsnuvem.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;

@FacesConverter(value = "clienteConverter")
public class ClienteConverter implements Converter {
	@Override
	public Cliente getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return new GenericDao<Cliente>().createQuery("select c from Cliente c where c.cpfOuCnpj = '"+value+"' ").get(0);
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversão do Cliente", "Cliente inválido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return ((Cliente) object).getCpfOuCnpj();
		} else
			return null;
	}
}