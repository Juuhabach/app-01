package br.upf.fabsoft.jmkidsquiosque.beans;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.dao.CaixaDao;
import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.FormaPagamento;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.quiosque.Caixa;
import br.upf.fabsoft.jmkidsmodel.quiosque.CaixaMovimento;
import br.upf.fabsoft.jmkidsmodel.quiosque.CaixaMovimentoDois;
import br.upf.fabsoft.jmkidsmodel.quiosque.ConfiguracaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsquiosque.util.JsfUtil;
import br.upf.fabsoft.jmkidsquiosque.util.RelatorioUtil;

@ManagedBean
@SessionScoped
public class CaixaBean {

	private List<Caixa> lista;
	private Caixa selecionado;
	private String flag;
	private IllegalArgumentException erroMaisDeUmCaixa;
	private IllegalArgumentException listasNaoVazias;
	private Quiosque quiosqueLogado;
	private Funcionario funcionarioLogado;
	private CaixaDao dao;
	private List<CaixaMovimento> movimentos;
	private List<CaixaMovimentoDois> movimentosDois;
	private CaixaMovimentoDois caixaMovimentoDois;

	public CaixaBean() {
		dao = new CaixaDao();
		flag = "Abertos";
		carregarListas();
		// Puxar o quiosque logado... Arrumar
		try {
			quiosqueLogado = new GenericDao<Quiosque>().find(1L, Quiosque.class);
			funcionarioLogado = new GenericDao<Funcionario>().find(1L, Funcionario.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onLoad() {
		Long id;
		try {
			funcionarioLogado = ((LoginBean) JsfUtil.getManagedBean("loginBean")).getUsuarioLogado();
			id = new GenericDao<ConfiguracaoQuiosque>().find(1L, ConfiguracaoQuiosque.class).getIdQuiosque();
			quiosqueLogado = new GenericDao<Quiosque>().find(id, Quiosque.class);
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro(
					"Erro ao carregar quiosque e funcionário. Detalhes: " + JpaUtil.exceptionHandler(e));
		}
	}

	public void relCaixa() {
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("ParCaixaID", selecionado.getId());
		String pathRelatorio = "WEB-INF/relatorios/CaixaRel/CaixaReportID.jasper";
		try {
			RelatorioUtil.rodarRelatorioPDF(pathRelatorio, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao gerar o relatório do caixa. Detalhes: " + JpaUtil.exceptionHandler(e));
		}
	}

	public void incluirMovimento() {
		caixaMovimentoDois = new CaixaMovimentoDois();
		caixaMovimentoDois.setCaixa(selecionado);
		caixaMovimentoDois.setDataHora(new Date());
	}

	public void excluirMovimento(Integer index) {
		caixaMovimentoDois = movimentosDois.get(index);
	}

	public void excluirMovimentoConfirmar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(caixaMovimentoDois));
			em.getTransaction().commit();
			verMovimentos();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void incluirMovimentoSalvar() {
		caixaMovimentoDois.setCaixa(selecionado);
		caixaMovimentoDois.setFuncionario(funcionarioLogado);
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			caixaMovimentoDois = em.merge(caixaMovimentoDois);
			em.getTransaction().commit();
			verMovimentos();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void incluirMovimentoCancelar() {
		caixaMovimentoDois.setCaixa(null);
		caixaMovimentoDois.setFuncionario(null);

	}

	public List<FormaPagamento> completeFormaPagamento(String query) throws Exception {
		return new GenericDao<FormaPagamento>().createQuery("from FormaPagamento where upper(descricao) like " + "'"
				+ query.trim().toUpperCase() + "%' " + "order by descricao");
	}

	public void verMovimentos() {
		String oql = "select cm from CaixaMovimento cm where cm.caixa.id = " + selecionado.getId()
				+ " order by cm.dataHora ";
		// " and cm.caixa.quiosque.id = "+selecionado.getQuiosque().getId()+
		// " order by cm.dataHora ";
		try {
			movimentos = new GenericDao<CaixaMovimento>().createQuery(oql);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		}
		String oql2 = "select cm from CaixaMovimentoDois cm where cm.caixa.id = " + selecionado.getId()
				+ " order by cm.dataHora ";
		// " and cm.caixa.quiosque.id = "+selecionado.getQuiosque().getId()+
		// " order by cm.dataHora ";
		try {
			movimentosDois = new GenericDao<CaixaMovimentoDois>().createQuery(oql2);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		}
	}

	public void carregarListas() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			switch (flag) {
			case "Abertos":
				lista = em.createNamedQuery(flag, Caixa.class).getResultList();
				if (lista.size() > 1)
					JsfUtil.addFacesMessageInfo(
							"Mais de um caixa aberto. Encerrar os caixas anteriores e manter somente um caixa aberto!");
				break;

			case "Fechados":
				lista = em.createNamedQuery(flag, Caixa.class).getResultList();
				break;

			case "Todos":
				lista = em.createNamedQuery(flag, Caixa.class).getResultList();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		selecionado = null;
	}

	public Integer getCaixaAbertosQtd() {
		try {
			return dao.getCaixaAbertosQtd(quiosqueLogado);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		}
		return 0;
	}

	public Integer getCaixaMovimentosQtd() {
		try {
			return dao.getCaixaMovimentoList(selecionado).size();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		}
		return 0;
	}

	public void abrirCaixa() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			int abertos = em.createQuery("from Caixa c where c.dataHoraFechamento is null").getResultList().size();
			if (abertos > 0) {
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Já existe caixa aberto! "));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		selecionado = new Caixa();
		selecionado.setFuncionarioAbriu(funcionarioLogado);
	}

	public void cancelar() {
		selecionado = null;
		carregarListas();
	}

	public void abrirCaixaGravar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			selecionado.setQuiosque(quiosqueLogado);
			// selecionado.setFuncionario(funcionarioLogado);
			selecionado = em.merge(selecionado);
			em.getTransaction().commit();
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void abrirCaixaCancelar() {
		selecionado = null;
		carregarListas();
	}

	public Boolean getPodeFecharCaixa() {
		if (dao.temBrinquedoAtivo(quiosqueLogado)) {
			return false;
		} else {
			return true;
		}
	}

	public void fecharCaixa() {
		if (!getPodeFecharCaixa()) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(
					"Existem brinquedos em operação. Não é possível fechar o caixa até inativar todos os brinquedos."));
			return;
		}
		selecionado.setDataHoraFechamento(new Date());
		selecionado.setFuncionarioFechou(funcionarioLogado);
		verMovimentos();
		try {
			List<CaixaMovimento> movs = movimentos;
			Double vlrFecha = selecionado.getValorAbertura();
			for (CaixaMovimento cm : movs) {
				if (cm.getOperacao().equals("Recebimento") || cm.getOperacao().equals("Entrada")) {
					vlrFecha += cm.getValor();
				} else if (cm.getOperacao().equals("Pagamento") || cm.getOperacao().equals("Saida")) {
					vlrFecha -= cm.getValor();
				} else {
					JsfUtil.addFacesMessageErro("Tipo de movimento do caixa não reconhecido!");
				}
			}
			List<CaixaMovimentoDois> movs2 = movimentosDois;
			for (CaixaMovimentoDois cm : movs2) {
				if (cm.getOperacao().equals("Recebimento") || cm.getOperacao().equals("Entrada")) {
					vlrFecha += cm.getValor();
				} else if (cm.getOperacao().equals("Pagamento") || cm.getOperacao().equals("Saida")) {
					vlrFecha -= cm.getValor();
				} else {
					JsfUtil.addFacesMessageErro("Tipo de movimento do caixa não reconhecido!");
				}
			}
			selecionado.setValorFechamento(vlrFecha);
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Não carregou os movimentos. Detalhe do erro: " + JpaUtil.exceptionHandler(e));
		}
	}

	public void fecharCaixaGravar() {
		if (!getPodeFecharCaixa()) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(
					"Existem brinquedos em operação. Não é possível fechar o caixa até inativar todos os brinquedos."));
			return;
		}
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			selecionado.setFuncionarioFechou(funcionarioLogado);
			selecionado = em.merge(selecionado);
			em.getTransaction().commit();
			carregarListas();
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void fecharCaixaCancelar() {
		selecionado = null;
		carregarListas();
	}

	public void excluirCaixa() {
		try {
			// if (selecionado.getMovimentos().size() == 0 &&
			// selecionado.getOperacoes().size() == 0) {
			EntityManager em = null;
			try {
				em = JpaUtil.getEntityManager();
				em.getTransaction().begin();
				em.remove(em.merge(selecionado));
				em.getTransaction().commit();
				carregarListas();
				selecionado = null;
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
			} finally {
				if (em != null)
					em.close();
			}
			/*
			 * } else { throw listasNaoVazias = new IllegalArgumentException(
			 * "Para poder excluir um caixa é necessário que ele não possua nenhum movimento ou operação e que ele esteja aberto."
			 * ); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————

	public Caixa getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Caixa selecionado) {
		this.selecionado = selecionado;
	}

	public List<Caixa> getLista() {
		return lista;
	}

	public void setLista(List<Caixa> lista) {
		this.lista = lista;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public IllegalArgumentException getErroMaisDeUmCaixa() {
		return erroMaisDeUmCaixa;
	}

	public void setErroMaisDeUmCaixa(IllegalArgumentException erroMaisDeUmCaixa) {
		this.erroMaisDeUmCaixa = erroMaisDeUmCaixa;
	}

	public IllegalArgumentException getListasNaoVazias() {
		return listasNaoVazias;
	}

	public void setListasNaoVazias(IllegalArgumentException listasNaoVazias) {
		this.listasNaoVazias = listasNaoVazias;
	}

	public Quiosque getQuiosqueLogado() {
		return quiosqueLogado;
	}

	public void setQuiosqueLogado(Quiosque quiosqueLogado) {
		this.quiosqueLogado = quiosqueLogado;
	}

	public Funcionario getFuncionarioLogado() {
		return funcionarioLogado;
	}

	public void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}

	public CaixaDao getDao() {
		return dao;
	}

	public void setDao(CaixaDao dao) {
		this.dao = dao;
	}

	public List<CaixaMovimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<CaixaMovimento> movimentos) {
		this.movimentos = movimentos;
	}

	public CaixaMovimentoDois getCaixaMovimentoDois() {
		return caixaMovimentoDois;
	}

	public void setCaixaMovimentoDois(CaixaMovimentoDois caixaMovimentoDois) {
		this.caixaMovimentoDois = caixaMovimentoDois;
	}

	public List<CaixaMovimentoDois> getMovimentosDois() {
		return movimentosDois;
	}

	public void setMovimentosDois(List<CaixaMovimentoDois> movimentosDois) {
		this.movimentosDois = movimentosDois;
	}
}