package br.upf.fabsoft.jmkidsnuvem.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@ManagedBean
@ViewScoped
public class BrinquedoBean {

	private List<Brinquedo> lista;
	private Brinquedo selecionado;
	private Boolean editando;



	public BrinquedoBean() {
		carregarLista();
		editando = false;
	}

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from Brinquedo", Brinquedo.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void incluir() {
		selecionado = new Brinquedo();
		setFotoSelecionada(null);
		editando = true;
	}

	public void editar() {
		editando = true;
		setFotoSelecionada(selecionado.getFoto());
	}

	
	private byte[] fotoSelecionada = null;
	public StreamedContent getFotoSelecionadaStream() {
		if (this.fotoSelecionada != null)
		    return new DefaultStreamedContent(new ByteArrayInputStream(this.fotoSelecionada));
		else
			return null;
	}
	
	// Antes de enviar foto para o banco, prepara a mesma...
	public void prepararFoto(FileUploadEvent event) { 
        try { 
            //Utilizado para atualizar o componente p:graphicImage, assim que a imagem for enviada 
            //selecionado.setFoto(new DefaultStreamedContent(event.getFile().getInputstream())); 
            //Antes de setar a imagem, converte de Inputstream para Array. 
            setFotoSelecionada(IOUtils.toByteArray(event.getFile().getInputstream()));
        } catch (IOException e) { 
        	e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
        } 
	}	
	
	public void salvar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			if (fotoSelecionada != null) {
				selecionado.setFoto(fotoSelecionada);
			}
			selecionado = em.merge(selecionado);
			em.getTransaction().commit();
			carregarLista();
			editando = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void cancelar() {
		selecionado = null;
		carregarLista();
		editando = false;
	}

	public void excluir() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(selecionado));
			em.getTransaction().commit();
			carregarLista();
			editando = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public List<Brinquedo> getLista() {
		return lista;
	}

	public void setLista(List<Brinquedo> lista) {
		this.lista = lista;
	}

	public Brinquedo getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Brinquedo selecionado) {
		this.selecionado = selecionado;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public byte[] getFotoSelecionada() {
		return fotoSelecionada;
	}

	public void setFotoSelecionada(byte[] fotoSelecionada) {
		this.fotoSelecionada = fotoSelecionada;
	}

}
