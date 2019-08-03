package br.upf.fabsoft.jmkidsnuvem.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPrecoTempo;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@ManagedBean
@ViewScoped
public class TabelaPrecoBean {
	
	private List<TabelaPreco> lista;
	private TabelaPreco selecionado;
	private Boolean editando;
	
	public TabelaPrecoBean() {
		carregarLista();
		editando = false;
	}
	
	// --------------------------------------------------------
	
	private TabelaPrecoTempo item; // item em edição, vinculado ao formulário
	private Integer rowIndex = null; // índice do item selecionado - alterar e
										// excluir

	public void incluirItem() {
		rowIndex = null;
		item = new TabelaPrecoTempo();
	}

	public void alterarItem(Integer rowIndex) {
		this.rowIndex = rowIndex;
		item = selecionado.getTempos().get(rowIndex); // pega item da coleção
	}

	public void excluirItem(Integer rowIndex) {
		selecionado.getTempos().remove(rowIndex.intValue()); // exclui item da coleção
	}

	public void gravarItem() {
		if (this.rowIndex == null) {
			item.setTabelaPreco(selecionado);
			selecionado.getTempos().add(item); // adiciona item na coleção
		} else {
			selecionado.getTempos().set(rowIndex, item); // altera na coleção
		}
		rowIndex = null;
		item = null;
	}

	public void cancelarItem() {
		rowIndex = null;
		item = null;
	}

	
	
	

	// ----------------------------------------------------------
	 public List<Quiosque> completeQuiosque(String query) throws Exception {
		return new GenericDao<Quiosque>().createQuery(
		  "from Quiosque where upper(descricao) like "+
				"'"+query.trim().toUpperCase()+"%' "+
				"order by descricao");
	 }		

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from TabelaPreco", TabelaPreco.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Erro: "+JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
			   em.close();
		}
	}

	public void incluir() {
		selecionado = new TabelaPreco();
		editando = true;
	}
	
	public void editar() {
		editando = true;
	}
	
	public void salvar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			selecionado = em.merge(selecionado);
			em.getTransaction().commit();
			carregarLista();
			editando = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Erro: "+JpaUtil.exceptionHandler(e)));
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
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Erro: "+JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
			   em.close();
		}		
	}
	
	public List<TabelaPreco> getLista() {
		return lista;
	}
	public void setLista(List<TabelaPreco> lista) {
		this.lista = lista;
	}
	public TabelaPreco getSelecionado() {
		return selecionado;
	}
	public void setSelecionado(TabelaPreco selecionado) {
		this.selecionado = selecionado;
	}
	public Boolean getEditando() {
		return editando;
	}
	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public TabelaPrecoTempo getItem() {
		return item;
	}

	public void setItem(TabelaPrecoTempo item) {
		this.item = item;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
}
