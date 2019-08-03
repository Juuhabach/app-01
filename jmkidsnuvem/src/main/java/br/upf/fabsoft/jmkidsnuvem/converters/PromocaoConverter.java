package br.upf.fabsoft.jmkidsnuvem.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Promocao;

@FacesConverter(value = "promocaoConverter")
public class PromocaoConverter implements Converter {
	@Override
	public Promocao getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return new GenericDao<Promocao>().find(Long.parseLong(value), Promocao.class);
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Conversão da Promocao", "Promocao inválida."));
			}
		} else
			return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Promocao) object).getId());
		} else
			return null;
	}
}