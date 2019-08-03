package br.upf.fabsoft.jmkidsquiosque.util;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

public class JsfUtil {

	public static void abrirPopup(String url, int largura, int altura, boolean modal) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", modal);
		options.put("width", largura);
		options.put("height", altura);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
		options.put("resizable", false);
		options.put("minimizable", true);
		options.put("maximizable", true);
		PrimeFaces.current().dialog().openDynamic(url, options, null); // a partir do Primefaces 6.2
	}
	/**
	 * Método que recupera um managedbean pelo nome do mesmo. 
	 * Exemplo de uso:
	 * ClasseBean bean = (ClasseBean) JSFUtil.getManagedBean("nomeBean"));
	 * @param nomeBean: Nome do bean conforme seu atrituto name @ManagedBean(name = "nomeBean")
	 * @return Instância do managed bean
	 */
	public static Object getManagedBean(String nomeBean) {    
	    FacesContext facesContext = FacesContext.getCurrentInstance();   
	    ELContext elContext = facesContext.getELContext();    
	    ExpressionFactory factory = facesContext.getApplication().getExpressionFactory();
	    return factory.createValueExpression(elContext, "#{" + nomeBean + "}", Object.class).getValue(elContext);
	}  	
	
	public static void addFacesMessageInfo(String message){
		FacesContext.getCurrentInstance().addMessage(
			null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
	}
	
	public static void addFacesMessageErro(String message){
		FacesContext.getCurrentInstance().addMessage(
			null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
	}		
}