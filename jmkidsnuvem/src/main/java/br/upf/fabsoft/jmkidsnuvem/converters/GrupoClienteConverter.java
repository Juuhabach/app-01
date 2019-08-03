package br.upf.fabsoft.jmkidsnuvem.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.GrupoCliente;

@FacesConverter(value = "grupoClienteConverter")
public class GrupoClienteConverter implements Converter {
	@Override
	public GrupoCliente getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return new GenericDao<GrupoCliente>().find(Long.parseLong(value), GrupoCliente.class);
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversão do Grupo Cliente", "Grupo Cliente inválido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((GrupoCliente) object).getId());
		} else
			return null;
	}
}