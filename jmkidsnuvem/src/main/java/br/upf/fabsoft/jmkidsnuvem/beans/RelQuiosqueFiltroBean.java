package br.upf.fabsoft.jmkidsnuvem.beans;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.upf.fabsoft.jmkidsmodel.util.ExceptionHandler;
import br.upf.fabsoft.jmkidsnuvem.util.RelatorioUtil;;

@ManagedBean
@RequestScoped
public class RelQuiosqueFiltroBean implements Serializable{

	private String uf;

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public void print(){ 
		try {   
			HashMap parameters = new HashMap(); // par�metros para o relat�rio, se houver
			// passar o par�metro do relat�rio com a uf selecionada
			parameters.put("parUf", uf);
			
			RelatorioUtil.rodarRelatorioPDF("WEB-INF/relatorios/CidadeFiltroUf/CidadeFiltroUf.jasper", 
			              parameters); 
		} catch (Exception e) { 
			e.printStackTrace(); 
			FacesContext.getCurrentInstance().addMessage("Erro", 
					new FacesMessage(ExceptionHandler.exceptionHandler(e))); 
		} 
	}
	
	
}
