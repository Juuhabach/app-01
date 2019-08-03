package br.upf.fabsoft.jmkidsquiosque.integracao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.nuvem.BrinquedoNoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.FormaPagamento;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.GrupoCliente;
import br.upf.fabsoft.jmkidsmodel.nuvem.PromocaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPrecoTempo;
import br.upf.fabsoft.jmkidsmodel.quiosque.ConfiguracaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.util.Datas;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

public class IntegracaoNuvemToQuiosque {

	private String URLBASE = null;
	private EntityManager em;
	private ConfiguracaoQuiosque configuracaoQuiosque;

	public IntegracaoNuvemToQuiosque() {
		super();
		try {
			configuracaoQuiosque = new GenericDao<ConfiguracaoQuiosque>().find(1L, ConfiguracaoQuiosque.class);
			URLBASE = configuracaoQuiosque.getUrlIntegracao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pegarTodosOsDadosDaNuvem() throws Exception {
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		importaQuiosque();
		importaFuncionario();
		importaFormaPagamento();
		importaTabelaPreco();
		importaGrupoCliente();
		importaPromocaoQuiosque();
		importaBrinquedos();
		em.getTransaction().commit();
	}

	public void importaQuiosque() throws Exception {
		String endpoint = URLBASE + "/nuvemtoquiosque/getQuiosque/" + configuracaoQuiosque.getIdQuiosque();
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		String linha, json = "";
		while ((linha = br.readLine()) != null) {
			json += linha;
		}
		Gson gson = new Gson(); // Or use new GsonBuilder().create();
		Quiosque q = gson.fromJson(json, Quiosque.class);
		em.merge(q);
	}

	public void importaFormaPagamento() throws Exception {
		String endpoint = URLBASE + "/nuvemtoquiosque/getFormaPagamento";
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream is = con.getInputStream();
			JsonReader rdr = Json.createReader(is);
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				// JsonObject jsonObj = result.getJsonObject("formaPagamento");
				// FormaPagamento o = objectMapper.convertValue(result.toString(),
				// FormaPagamento.class);
				FormaPagamento o = new Gson().fromJson(result.toString(), FormaPagamento.class);
				em.merge(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void importaGrupoCliente() throws Exception {
		String endpoint = URLBASE + "/nuvemtoquiosque/getGrupoCliente";
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream is = con.getInputStream();
			JsonReader rdr = Json.createReader(is);
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				// JsonObject jsonObj = result.getJsonObject("formaPagamento");
				// GrupoCliente o = objectMapper.convertValue(result.toString(),
				// GrupoCliente.class);
				GrupoCliente o = new Gson().fromJson(result.toString(), GrupoCliente.class);
				em.merge(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void importaTabelaPreco() throws Exception {
		String endpoint = URLBASE + "/nuvemtoquiosque/getTabelaPreco/" + configuracaoQuiosque.getIdQuiosque();
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream is = con.getInputStream();
			JsonReader rdr = Json.createReader(is);
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				// JsonObject jsonObj = result.getJsonObject("formaPagamento");
				TabelaPreco o = objectMapper.reader().forType(TabelaPreco.class).readValue(result.toString());
				for (TabelaPrecoTempo pt : o.getTempos()) {
					pt.setTabelaPreco(o);
				}
				em.merge(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void importaPromocaoQuiosque() throws Exception {
		String endpoint = URLBASE + "/nuvemtoquiosque/getPromocaoQuiosque/" + configuracaoQuiosque.getIdQuiosque();
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream is = con.getInputStream();
			JsonReader rdr = Json.createReader(is);
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			int cont = 0;
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				// JsonObject jsonObj = result.getJsonObject("formaPagamento");
				// TabelaPreco o = objectMapper.convertValue(result.toString(),
				// TabelaPreco.class);
				PromocaoQuiosque o = new Gson().fromJson(result.toString(), PromocaoQuiosque.class);
				if (cont == 0) {
					em.merge(o.getPromocao());
				}
				cont++;
				em.merge(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void importaFuncionario() throws Exception {
		String endpoint = URLBASE + "/nuvemtoquiosque/getFuncionarios/" + configuracaoQuiosque.getIdQuiosque();
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream is = con.getInputStream();
			JsonReader rdr = Json.createReader(is);
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				System.out.println("result: " + result);
				JsonObject jsonObj = result.getJsonObject("funcionario");
				System.out.println("jsonObj: " + jsonObj);
				System.out.println("brinquedoJsqon: " + jsonObj.toString());
				// Funcionario o = objectMapper.convertValue(jsonObj.toString(),
				// Funcionario.class);
				Funcionario o = new Funcionario(jsonObj.getJsonNumber("id").longValue(),
						JsonUtil.getString(jsonObj, "nome"), JsonUtil.getString(jsonObj, "email"),
						JsonUtil.getString(jsonObj, "senha"), JsonUtil.getString(jsonObj, "endereco"),
						JsonUtil.getString(jsonObj, "numero"), JsonUtil.getString(jsonObj, "complemento"),
						JsonUtil.getString(jsonObj, "bairro"), JsonUtil.getString(jsonObj, "cep"),
						JsonUtil.getString(jsonObj, "nomeCidade"), JsonUtil.getString(jsonObj, "ufCidade"),
						JsonUtil.getString(jsonObj, "rg"), JsonUtil.getString(jsonObj, "cpf"),
						JsonUtil.getString(jsonObj, "ctpsNumero"), JsonUtil.getString(jsonObj, "ctpsSerie"),
						JsonUtil.getString(jsonObj, "foneUm"), JsonUtil.getString(jsonObj, "foneDois"),
						JsonUtil.getDate(jsonObj, "dataAdmissao"), JsonUtil.getDate(jsonObj, "dataDemissao"),
						JsonUtil.getByteVet(jsonObj, "foto"), jsonObj.getJsonNumber("versao").intValue());
				em.merge(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void importaBrinquedos() throws Exception {
		URL url = new URL(URLBASE + "/nuvemtoquiosque/getBrinquedoNoQuiosque/" + configuracaoQuiosque.getIdQuiosque());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		GenericDao<BrinquedoNoQuiosque> daobQ = new GenericDao<BrinquedoNoQuiosque>();
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			InputStream is = con.getInputStream();
			JsonReader rdr = Json.createReader(is);
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				BrinquedoNoQuiosque bq = objectMapper.reader().forType(BrinquedoNoQuiosque.class)
						.readValue(result.toString());
				// System.out.println("result: "+result);
				JsonObject jsonObj = result.getJsonObject("brinquedo");
				// System.out.println("jsonObj: "+jsonObj);
				// System.out.println("brinquedoJsqon: "+jsonObj.toString());
				// Brinquedo b = objectMapper.convertValue(jsonObj.toString(), Brinquedo.class);
				byte[] foto = null;
				try {
					foto = Base64.getDecoder().decode(jsonObj.getString("foto"));
				} catch (Exception e) {
				}
				Brinquedo b = new Brinquedo(jsonObj.getJsonNumber("id").longValue(),
						JsonUtil.getString(jsonObj, "chave"), JsonUtil.getString(jsonObj, "descricao"),
						JsonUtil.getString(jsonObj, "infTecnicas"), null, 0d, null,
						JsonUtil.getString(jsonObj, "motivoDesabilitado"), foto,
						jsonObj.getJsonNumber("tempoMinimo").intValue(), jsonObj.getJsonNumber("versao").intValue());
				em.merge(b);
				BrinquedoNoQuiosque bqLocal = daobQ.find(bq.getId(), BrinquedoNoQuiosque.class);
				if (bqLocal == null) {
					em.merge(bq);
				} else {
					if (bq.getDataHoraAtivacao() != null) {
						String sql = "update nuvem.brinquedonoquiosque "
								+ "      set datahoraativacao = :datahoraativacao " + "    where id = :id";
						Query q = em.createNativeQuery(sql);
						q.setParameter("datahoraativacao", bq.getDataHoraAtivacao());
						q.setParameter("id", bq.getId());
						q.executeUpdate();
					} else {
						String sql = "update nuvem.brinquedonoquiosque " + "      set datahoraativacao = null "
								+ "    where id = :id";
						Query q = em.createNativeQuery(sql);
						q.setParameter("id", bq.getId());
						q.executeUpdate();
					}
					if (bq.getDataHoraInativacao() != null) {
						String sql = "update nuvem.brinquedonoquiosque "
								+ "      set datahorainativacao = :datahorainativacao " + "    where id = :id";
						Query q = em.createNativeQuery(sql);
						q.setParameter("datahorainativacao", bq.getDataHoraInativacao());
						q.setParameter("id", bq.getId());
						q.executeUpdate();
					} else {
						String sql = "update nuvem.brinquedonoquiosque " + "      set datahorainativacao = null "
								+ "    where id = :id";
						Query q = em.createNativeQuery(sql);
						q.setParameter("id", bq.getId());
						q.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/*
	 * public void importaBrinquedoNoQuiosque() throws Exception { String endpoint =
	 * URLBASE +
	 * "/nuvemtoquiosque/getBrinquedoNoQuiosque/"+configuracaoQuiosque.getIdQuiosque
	 * (); URL url = new URL(endpoint); HttpURLConnection con = (HttpURLConnection)
	 * url.openConnection(); if (con.getResponseCode() != 200) { throw new
	 * RuntimeException("HTTP error code : "+ con.getResponseCode()); } ObjectMapper
	 * objectMapper = new ObjectMapper(); try (InputStream is =
	 * con.getInputStream(); JsonReader rdr = Json.createReader(is)) { //JsonObject
	 * obj = rdr.readObject(); JsonArray results = rdr.readArray(); for (JsonObject
	 * result : results.getValuesAs(JsonObject.class)) { //JsonObject jsonObj =
	 * result.getJsonObject("formaPagamento"); BrinquedoNoQuiosque o =
	 * objectMapper.reader().forType(BrinquedoNoQuiosque.class).readValue(result.
	 * toString()); em.merge(o); } } }
	 */
}