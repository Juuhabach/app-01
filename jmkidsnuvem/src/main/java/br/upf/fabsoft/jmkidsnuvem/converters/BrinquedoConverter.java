package br.upf.fabsoft.jmkidsnuvem.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;

@FacesConverter(value = "brinquedoConverter")
public class BrinquedoConverter implements Converter {
	@Override
	public Brinquedo getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return new GenericDao<Brinquedo>().find(Long.parseLong(value), Brinquedo.class);
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversão do Brinquedo", "Brinquedo inválido."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Brinquedo) object).getId());
		} else
			return null;
	}
}