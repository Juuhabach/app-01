package br.upf.fabsoft.jmkidsquiosque.integracao;

import java.util.List;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;
import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;
import br.upf.fabsoft.jmkidsmodel.quiosque.ConfiguracaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

public class IntegracaoQuiosqueToNuvem {

	private String URLBASE = null;
	private EntityManager em;
	private ConfiguracaoQuiosque configuracaoQuiosque;

	/**
	 * TRABALHAR NISTO...
	 */

	public IntegracaoQuiosqueToNuvem() {
		super();
		try {
			configuracaoQuiosque = new GenericDao<ConfiguracaoQuiosque>().find(1L, ConfiguracaoQuiosque.class);
			URLBASE = configuracaoQuiosque.getUrlIntegracao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarTodosOsDadosParaNuvem() throws Exception {
		em = JpaUtil.getEntityManager();
		enviarClientes();
		em.close();
	}

	// Criar o JSON e enviar a requisição para o QuiosqueToNuvem
	public void enviarClientes() throws Exception {
		try {
			String oql = "from Cliente o  ";
			List<Cliente> list = new GenericDao<Cliente>().createQuery(oql);
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(list);
			// return jsonString;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e));
		}
	}
}