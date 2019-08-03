package br.upf.fabsoft.jmkidsquiosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.PrimeFaces;

import br.upf.fabsoft.jmkidsmodel.dao.CaixaDao;
import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.BrinquedoNoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.FormaPagamento;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.GrupoCliente;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPrecoTempo;
import br.upf.fabsoft.jmkidsmodel.quiosque.BrinquedoOperacao;
import br.upf.fabsoft.jmkidsmodel.quiosque.Caixa;
import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;
import br.upf.fabsoft.jmkidsmodel.quiosque.ConfiguracaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.util.Datas;
import br.upf.fabsoft.jmkidsmodel.util.ExceptionHandler;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsmodel.util.ValidaCNPJ;
import br.upf.fabsoft.jmkidsmodel.util.ValidaCPF;
import br.upf.fabsoft.jmkidsquiosque.util.JsfUtil;
import br.upf.fabsoft.jmkidsquiosque.util.RelatorioUtil;

@ManagedBean
@SessionScoped
public class BrinquedoOperacaoBean implements Serializable {

	private BrinquedoOperacao selecionado;
	private List<BrinquedoNoQuiosque> listaBrinquedoNoQuiosque;
	private BrinquedoNoQuiosque brinquedoSelecionado;

	private Quiosque quiosqueLogado;
	private Funcionario funcionarioLogado;
	private Caixa caixaAberto;
	private Cliente clienteSelecionado;

	private BrinquedoOperacao inicializaBrinquedoOperacaoSelecionada() throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			String oql = "from BrinquedoOperacao bo where bo.brinquedo.id = "
					+ brinquedoSelecionado.getBrinquedo().getId() + " " + " and bo.dataHoraInativacao is null "
					+ " order by dataHoraContratado desc ";
			return (BrinquedoOperacao) em.createQuery(oql).getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao desativar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public BrinquedoOperacaoBean() {
		super();
	}

	public void onLoad() {
		Long id;
		try {
			funcionarioLogado = ((LoginBean) JsfUtil.getManagedBean("loginBean")).getUsuarioLogado();
			id = new GenericDao<ConfiguracaoQuiosque>().find(1L, ConfiguracaoQuiosque.class).getIdQuiosque();
			quiosqueLogado = new GenericDao<Quiosque>().find(id, Quiosque.class);
			caixaAberto = new CaixaDao().getCaixaAberto(quiosqueLogado);
			if (caixaAberto == null || caixaAberto.getDataHoraFechamento() != null) {
				JsfUtil.addFacesMessageErro("Para acessar as operações é preciso estar com um caixa aberto!");
				listaBrinquedoNoQuiosque = new ArrayList<BrinquedoNoQuiosque>();
				return;
			}
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro(
					"Erro ao carregar quiosque e funcionário. Detalhes: " + JpaUtil.exceptionHandler(e));
		}
	}

	public void carregarListas() {
		try {
			listaBrinquedoNoQuiosque = new GenericDao<BrinquedoNoQuiosque>()
					.createQuery("from BrinquedoNoQuiosque where quiosque.id = " + quiosqueLogado.getId()
							+ " order by brinquedo.descricao");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao carregar listas. Detalhes: " + JpaUtil.exceptionHandler(e));
		}
	}

	public List<GrupoCliente> completeGrupoCliente(String query) throws Exception {
		return new GenericDao<GrupoCliente>().createQuery("from GrupoCliente where upper(descricao) like " + "'"
				+ query.trim().toUpperCase() + "%' " + "order by descricao");
	}

	public void incluirCliente() {
		clienteSelecionado = new Cliente();
		clienteSelecionado.setPfOuPj("PF");
		try {
			clienteSelecionado.setGrupoCliente(new GenericDao<GrupoCliente>().find(1L, GrupoCliente.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void salvarCliente() {
		if (clienteSelecionado.getPfOuPj().equals("PJ")) {
			String cnpj = ValidaCNPJ.removeMascara(clienteSelecionado.getCpfOuCnpj());
			if (!ValidaCNPJ.isCNPJ(cnpj)) {
				JsfUtil.addFacesMessageErro("CNPJ inválido");
				clienteSelecionado.setCpfOuCnpj("");
				return;
			}
			clienteSelecionado.setCpfOuCnpj(ValidaCNPJ.imprimeCNPJ(cnpj));

		} else if (clienteSelecionado.getPfOuPj().equals("PF")) {
			String cpf = ValidaCPF.removeMascara(clienteSelecionado.getCpfOuCnpj());
			if (!ValidaCPF.isCPF(cpf)) {
				clienteSelecionado.setCpfOuCnpj("");
				JsfUtil.addFacesMessageErro("CPF inválido");
				return;
			}
			clienteSelecionado.setCpfOuCnpj(ValidaCPF.imprimeCPF(cpf));
		}

		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			clienteSelecionado.setDataHoraModificado(new Date());
			clienteSelecionado = em.merge(clienteSelecionado);
			em.getTransaction().commit();
			selecionado.setCliente(clienteSelecionado);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void printTermo(Integer rowIndex) {
		brinquedoSelecionado = listaBrinquedoNoQuiosque.get(rowIndex);
		try {
			selecionado = inicializaBrinquedoOperacaoSelecionada();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao imprimir o termo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
		}

		try {
			HashMap parameters = new HashMap(); // par�metros para o relat�rio, se houver
			parameters.put("local", quiosqueLogado.getLocal());
			parameters.put("nomecidade", quiosqueLogado.getNomeCidade());
			if (selecionado.getCliente() != null) {
				parameters.put("nomeCliente", selecionado.getCliente().getNome());
				parameters.put("cpfCliente", selecionado.getCliente().getCpfOuCnpj());
			} else {
				parameters.put("nomeCliente", selecionado.getDadosSemCadastro());
				parameters.put("cpfCliente", "");
			}
			if (brinquedoSelecionado.getEmUso() == false) {
				// Se ainda não ativou, manda para a impressora direto o termo
				RelatorioUtil.imprimirRelatorio(
						"WEB-INF/relatorios/TermoResponsabilidade/TermoResponsabilidadeQuiosque.jasper", parameters);
			} else {
				// Se já ativou, gera PDF do termo
				RelatorioUtil.rodarRelatorioPDF(
						"WEB-INF/relatorios/TermoResponsabilidade/TermoResponsabilidadeQuiosque.jasper", parameters);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro",
					new FacesMessage(ExceptionHandler.exceptionHandler(e)));
		}
	}

	public void selecionouTabelaPrecoTempo() {
		selecionado.setMinutosContratados(selecionado.getTabelaPrecoTempo().getMinutos());
		selecionado.setValorPrePago(selecionado.getTabelaPrecoTempo().getValor());
	}

	public List<TabelaPrecoTempo> completeTabelaPrecoTempo(String query) throws Exception {
		return new GenericDao<TabelaPrecoTempo>().createQuery("from TabelaPrecoTempo order by minutos");
	}

	public List<FormaPagamento> completeFormaPagamento(String query) throws Exception {
		return new GenericDao<FormaPagamento>().createQuery("from FormaPagamento where upper(descricao) like " + "'"
				+ query.trim().toUpperCase() + "%' " + "order by descricao");
	}

	public List<Cliente> completeCliente(String query) throws Exception {
		return new GenericDao<Cliente>()
				.createQuery("from Cliente where upper(nome) like " + "'" + query.trim().toUpperCase() + "%' "
						+ " or upper(cpfOuCnpj) like " + "'" + query.trim().toUpperCase() + "%' " + "order by nome");
	}

	public void contratarBrinquedo(Integer rowIndex) {
		brinquedoSelecionado = listaBrinquedoNoQuiosque.get(rowIndex);
		selecionado = new BrinquedoOperacao();
	}

	public void contratarBrinquedoGravar() {
		if (brinquedoSelecionado.getContratado() == true) {
			JsfUtil.addFacesMessageErro("Brinquedo já contratado!");
			FacesContext.getCurrentInstance().validationFailed();
			return;
		}
		if (selecionado.getMinutosContratados() <= 0) {
			JsfUtil.addFacesMessageErro("Não há minutos contratados!");
			FacesContext.getCurrentInstance().validationFailed();
			return;
		}

		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			// Ver dados e regras ao ativar brinquedo
			selecionado.setBrinquedo(brinquedoSelecionado.getBrinquedo());
			selecionado.setCaixa(caixaAberto);
			selecionado.setFuncionarioAtivou(funcionarioLogado);
			em.merge(selecionado);
			String sql = "UPDATE nuvem.BrinquedoNoQuiosque SET contratado = true " + " WHERE id = "
					+ brinquedoSelecionado.getId();
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao contratar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void ativarBrinquedo(Integer rowIndex) {
		brinquedoSelecionado = listaBrinquedoNoQuiosque.get(rowIndex);
		try {
			selecionado = inicializaBrinquedoOperacaoSelecionada();
			selecionado.setDataHoraAtivacao(new Date());
			selecionado.setFuncionarioAtivou(funcionarioLogado);
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao ativar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
		}
	}

	public void ativarBrinquedoGravar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			String sql = "UPDATE nuvem.BrinquedoNoQuiosque SET emUso = true " + " WHERE id = "
					+ brinquedoSelecionado.getId();
			em.createNativeQuery(sql).executeUpdate();
			em.merge(selecionado);
			em.getTransaction().commit();
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao ativar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void desativarBrinquedo(Integer rowIndex) {
		try {
			brinquedoSelecionado = listaBrinquedoNoQuiosque.get(rowIndex);
			selecionado = inicializaBrinquedoOperacaoSelecionada();
			selecionado.setFuncionarioInativou(funcionarioLogado);
			selecionado.setDataHoraInativacao(new Date());
			selecionado.setMinutosDecorridos(
					Datas.getDiferencaMinutos(selecionado.getDataHoraAtivacao(), selecionado.getDataHoraInativacao()));
			selecionado.setMinutosDiferenca(selecionado.getMinutosDecorridos() - selecionado.getMinutosContratados());
			if (selecionado.getMinutosDecorridos() > selecionado.getMinutosContratados()) {
				Integer minutosAMais = selecionado.getMinutosDecorridos() - selecionado.getMinutosContratados();
				if (minutosAMais > selecionado.getTabelaPrecoTempo().getTabelaPreco().getTempoFlexibilizacao()) {
					Float vlrPorMin = selecionado.getTabelaPrecoTempo().getTabelaPreco().getValorMinutoAdicional();
					Float vlrAdicional = minutosAMais + vlrPorMin;
					if (vlrAdicional > 0)
						selecionado.setValorPosPago(vlrAdicional.doubleValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao desativar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
		}
	}

	public void desativarBrinquedoGravar() {
		if (selecionado.getValorPosPago() > 0.0 && selecionado.getFormaPosPago() == null) {
			JsfUtil.addFacesMessageErro("Informe a forma de pagamento para o valor pós-pago!");
			FacesContext.getCurrentInstance().validationFailed();
			return;
		}

		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			String sql = "UPDATE nuvem.BrinquedoNoQuiosque SET emUso = false, contratado = false, emPausa = false "
					+ " WHERE id = " + brinquedoSelecionado.getId();
			em.createNativeQuery(sql).executeUpdate();
			// Ver dados e regras ao desativar brinquedo

			em.merge(selecionado);
			em.getTransaction().commit();
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao desativar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void cancelarBrinquedo(Integer rowIndex) {
		try {
			brinquedoSelecionado = listaBrinquedoNoQuiosque.get(rowIndex);
			selecionado = inicializaBrinquedoOperacaoSelecionada();
			selecionado.setFuncionarioCancelou(funcionarioLogado);
			selecionado.setDataHoraCancelamento(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao cancelar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
		}
	}

	public void cancelarBrinquedoGravar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			String sql = "UPDATE nuvem.BrinquedoNoQuiosque SET emUso = false, contratado = false, emPausa = false  "
					+ " WHERE id = " + brinquedoSelecionado.getId();
			em.createNativeQuery(sql).executeUpdate();
			// Ver dados e regras ao cancelar brinquedo

			em.merge(selecionado);
			em.getTransaction().commit();
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao pausar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void pausarBrinquedo(Integer rowIndex) {
		try {
			brinquedoSelecionado = listaBrinquedoNoQuiosque.get(rowIndex);
			selecionado = inicializaBrinquedoOperacaoSelecionada();
			if (selecionado.getDataHoraEmPausa() == null) {
				selecionado.setDataHoraEmPausa(new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao pausar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
		}
	}

	public void pausarBrinquedoGravar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			String sql = "";
			if (brinquedoSelecionado.getEmPausa() == false) {
				selecionado.setDataHoraEmPausa(new Date());
				selecionado.setFuncionarioPausou(funcionarioLogado);
				sql = "UPDATE nuvem.brinquedonoquiosque SET empausa = true " + " WHERE brinquedo_id = "
						+ selecionado.getBrinquedo().getId();
			} else {
				Date agora = new Date();
				Integer minutosPausados = Datas.getDiferencaMinutos(selecionado.getDataHoraEmPausa(), agora);
				selecionado.setTempoEmPausa(selecionado.getTempoEmPausa() + minutosPausados);
				selecionado.setDataHoraEmPausa(null);
				sql = "UPDATE nuvem.brinquedonoquiosque SET empausa = false " + " WHERE brinquedo_id = "
						+ selecionado.getBrinquedo().getId();
			}
			em.getTransaction().begin();
			em.merge(selecionado);
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
			carregarListas();
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao pausar o brinquedo. Detalhes: " + JpaUtil.exceptionHandler(e));
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	// ————————————————————————————————————————————————————————————————————————————————————————————————————————————

	public BrinquedoOperacao getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(BrinquedoOperacao selecionado) {
		this.selecionado = selecionado;
	}

	public List<BrinquedoNoQuiosque> getListaBrinquedoNoQuiosque() {
		return listaBrinquedoNoQuiosque;
	}

	public void setListaBrinquedoNoQuiosque(List<BrinquedoNoQuiosque> listaBrinquedoNoQuiosque) {
		this.listaBrinquedoNoQuiosque = listaBrinquedoNoQuiosque;
	}

	public BrinquedoNoQuiosque getBrinquedoSelecionado() {
		return brinquedoSelecionado;
	}

	public void setBrinquedoSelecionado(BrinquedoNoQuiosque brinquedoSelecionado) {
		this.brinquedoSelecionado = brinquedoSelecionado;
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

	public Caixa getCaixaAberto() {
		return caixaAberto;
	}

	public void setCaixaAberto(Caixa caixaAberto) {
		this.caixaAberto = caixaAberto;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
}