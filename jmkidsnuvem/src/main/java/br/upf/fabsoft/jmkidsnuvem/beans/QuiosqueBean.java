package br.upf.fabsoft.jmkidsnuvem.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.nuvem.BrinquedoNoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.FuncionarioQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.Promocao;
import br.upf.fabsoft.jmkidsmodel.nuvem.PromocaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsnuvem.util.JsfUtil;

@ManagedBean
@SessionScoped
public class QuiosqueBean {

	private List<Quiosque> lista;
	private Quiosque selecionado;
	private Boolean editando;
	private List<FuncionarioQuiosque> listaFuncionariosQuiosque;
	private FuncionarioQuiosque funcionarioQuiosqueSelecionado;
	private List<Funcionario> listaFuncionariosAtivos;
	
	private List<BrinquedoNoQuiosque> listaBrinquedosQuiosque;
	private BrinquedoNoQuiosque brinquedoQuiosqueSelecionado;
	private List<Brinquedo> listaBrinquedosAtivos;	
	
	private List<PromocaoQuiosque> listaPromocoesQuiosque;
	private PromocaoQuiosque promocaoQuiosqueSelecionado;
	private List<Promocao> listaPromocoesAtivas;		
	
	public QuiosqueBean() {
		carregarLista();
		editando = false;
	}
	
	
	// PromocaoQuiosqueList -----------------------------------------------------------------------
	public void formPromocoes() {
		carregarListaPromocoesQuiosque();
		try {
			listaPromocoesAtivas = new GenericDao<Promocao>().createQuery("from Promocao where dataHoraFim is null");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao carregar promoções ativas!");
		}
		JsfUtil.abrirPopup("PromocaoQuiosqueList.xhtml", 800, 600, true);
	}

	public void carregarListaPromocoesQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			listaPromocoesQuiosque = em
					.createQuery("from PromocaoQuiosque f where f.quiosque.id = " + selecionado.getId(),
							PromocaoQuiosque.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	public void incluirPromocaoQuiosque() {
		promocaoQuiosqueSelecionado = new PromocaoQuiosque();
		promocaoQuiosqueSelecionado.setQuiosque(selecionado);
		editando = true;
	}
	
	public void salvarPromocaoQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			promocaoQuiosqueSelecionado = em.merge(promocaoQuiosqueSelecionado);
			em.getTransaction().commit();
			carregarListaPromocoesQuiosque();
			editando = false;
			promocaoQuiosqueSelecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	public void cancelarPromocaoQuiosque() {
		promocaoQuiosqueSelecionado = null;
		carregarListaPromocoesQuiosque();
		editando = false;
	}
	
	public void excluirPromocaoQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(promocaoQuiosqueSelecionado));
			em.getTransaction().commit();
			carregarListaPromocoesQuiosque();
			editando = false;
			promocaoQuiosqueSelecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	
	// BrinquedosQuiosqueList -----------------------------------------------------------------------
	public void formBrinquedos() {
		carregarListaBrinquedosQuiosque();
		try {
			listaBrinquedosAtivos = new GenericDao<Brinquedo>().createQuery("from Brinquedo where dataHoraDesabilitado is null");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao carregar brinquedos ativos!");
		}
		JsfUtil.abrirPopup("BrinquedosQuiosqueList.xhtml", 800, 600, true);
	}

	public void carregarListaBrinquedosQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			listaBrinquedosQuiosque = em
					.createQuery("from BrinquedoNoQuiosque f where f.quiosque.id = " + selecionado.getId(),
							BrinquedoNoQuiosque.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	public void incluirBrinquedoQuiosque() {
		brinquedoQuiosqueSelecionado = new BrinquedoNoQuiosque();
		brinquedoQuiosqueSelecionado.setQuiosque(selecionado);
		brinquedoQuiosqueSelecionado.setEmUso(false);
		brinquedoQuiosqueSelecionado.setEmPausa(false);
		brinquedoQuiosqueSelecionado.setContratado(false);
		editando = true;
	}
	
	public void salvarBrinquedoQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			brinquedoQuiosqueSelecionado = em.merge(brinquedoQuiosqueSelecionado);
			em.getTransaction().commit();
			carregarListaBrinquedosQuiosque();
			editando = false;
			brinquedoQuiosqueSelecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	public void cancelarBrinquedoQuiosque() {
		brinquedoQuiosqueSelecionado = null;
		carregarListaBrinquedosQuiosque();
		editando = false;
	}
	
	public void excluirBrinquedoQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(brinquedoQuiosqueSelecionado));
			em.getTransaction().commit();
			carregarListaBrinquedosQuiosque();
			editando = false;
			brinquedoQuiosqueSelecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}


	
	
	
	
	
	// FuncionarioQuiosqueList -----------------------------------------------------------------------
	public void formFuncionarios() {
		carregarListaFuncionariosQuiosque();
		try {
			listaFuncionariosAtivos = new GenericDao<Funcionario>().createQuery("from Funcionario where dataDemissao is null");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao carregar funcionários ativos!");
		}
		JsfUtil.abrirPopup("FuncionarioQuiosqueList.xhtml", 800, 600, true);
	}

	public void carregarListaFuncionariosQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			listaFuncionariosQuiosque = em
					.createQuery("from FuncionarioQuiosque f where f.quiosque.id = " + selecionado.getId(),
							FuncionarioQuiosque.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	public void incluirFuncionarioQuiosque() {
		funcionarioQuiosqueSelecionado = new FuncionarioQuiosque();
		funcionarioQuiosqueSelecionado.setQuiosque(selecionado);
		editando = true;
	}
	
	public void salvarFuncionarioQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			funcionarioQuiosqueSelecionado = em.merge(funcionarioQuiosqueSelecionado);
			em.getTransaction().commit();
			carregarListaFuncionariosQuiosque();
			editando = false;
			funcionarioQuiosqueSelecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}
	
	public void cancelarFuncionarioQuiosque() {
		funcionarioQuiosqueSelecionado = null;
		carregarListaFuncionariosQuiosque();
		editando = false;
	}
	
	public void excluirFuncionarioQuiosque() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(funcionarioQuiosqueSelecionado));
			em.getTransaction().commit();
			carregarListaFuncionariosQuiosque();
			editando = false;
			funcionarioQuiosqueSelecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	// QuisqueList ----------------------------------------------------------

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from Quiosque", Quiosque.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void incluir() {
		selecionado = new Quiosque();
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

	// —————————————————Getters—&—Setters—————————————————————————————————————————————————————————————————————————————————————

	public List<Quiosque> getLista() {
		return lista;
	}

	public void setLista(List<Quiosque> lista) {
		this.lista = lista;
	}

	public Quiosque getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Quiosque selecionado) {
		this.selecionado = selecionado;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public List<FuncionarioQuiosque> getListaFuncionariosQuiosque() {
		return listaFuncionariosQuiosque;
	}

	public void setListaFuncionariosQuiosque(List<FuncionarioQuiosque> listaFuncionariosQuiosque) {
		this.listaFuncionariosQuiosque = listaFuncionariosQuiosque;
	}

	public FuncionarioQuiosque getFuncionarioQuiosqueSelecionado() {
		return funcionarioQuiosqueSelecionado;
	}

	public void setFuncionarioQuiosqueSelecionado(FuncionarioQuiosque funcionarioQuiosqueSelecionado) {
		this.funcionarioQuiosqueSelecionado = funcionarioQuiosqueSelecionado;
	}

	public List<Funcionario> getListaFuncionariosAtivos() {
		return listaFuncionariosAtivos;
	}

	public void setListaFuncionariosAtivos(List<Funcionario> listaFuncionariosAtivos) {
		this.listaFuncionariosAtivos = listaFuncionariosAtivos;
	}


	public List<BrinquedoNoQuiosque> getListaBrinquedosQuiosque() {
		return listaBrinquedosQuiosque;
	}


	public void setListaBrinquedosQuiosque(List<BrinquedoNoQuiosque> listaBrinquedosQuiosque) {
		this.listaBrinquedosQuiosque = listaBrinquedosQuiosque;
	}


	public BrinquedoNoQuiosque getBrinquedoQuiosqueSelecionado() {
		return brinquedoQuiosqueSelecionado;
	}


	public void setBrinquedoQuiosqueSelecionado(BrinquedoNoQuiosque brinquedoQuiosqueSelecionado) {
		this.brinquedoQuiosqueSelecionado = brinquedoQuiosqueSelecionado;
	}


	public List<Brinquedo> getListaBrinquedosAtivos() {
		return listaBrinquedosAtivos;
	}


	public void setListaBrinquedosAtivos(List<Brinquedo> listaBrinquedosAtivos) {
		this.listaBrinquedosAtivos = listaBrinquedosAtivos;
	}


	public List<PromocaoQuiosque> getListaPromocoesQuiosque() {
		return listaPromocoesQuiosque;
	}


	public void setListaPromocoesQuiosque(List<PromocaoQuiosque> listaPromocoesQuiosque) {
		this.listaPromocoesQuiosque = listaPromocoesQuiosque;
	}


	public PromocaoQuiosque getPromocaoQuiosqueSelecionado() {
		return promocaoQuiosqueSelecionado;
	}


	public void setPromocaoQuiosqueSelecionado(PromocaoQuiosque promocaoQuiosqueSelecionado) {
		this.promocaoQuiosqueSelecionado = promocaoQuiosqueSelecionado;
	}


	public List<Promocao> getListaPromocoesAtivas() {
		return listaPromocoesAtivas;
	}


	public void setListaPromocoesAtivas(List<Promocao> listaPromocoesAtivas) {
		this.listaPromocoesAtivas = listaPromocoesAtivas;
	}

}
