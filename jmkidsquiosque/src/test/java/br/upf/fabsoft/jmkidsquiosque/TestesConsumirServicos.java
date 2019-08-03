package br.upf.fabsoft.jmkidsquiosque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;

public class TestesConsumirServicos {

	private static final String URL_WEBSERVICE = "http://localhost:8080/jmkidsnuvem/rest";

	private BufferedReader getRecurso(String endpoint) throws Exception {
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		return br;
	}

	private String getRecursoJSon(String endpoint) throws Exception {
		
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		// System.out.println(br.readLine());
		String linha, json = "";
		while ((linha = br.readLine()) != null) {
			json += linha;
		}
		return json;
	}

	@Test
	public void testeQuiosque() throws Exception {
		
		String endpoint = URL_WEBSERVICE + "/nuvemtoquiosque/getQuiosque/1";
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		// System.out.println(br.readLine());
		String linha, json = "";
		while ((linha = br.readLine()) != null) {
			json += linha;
		}
		// System.out.println(json);
		Gson gson = new Gson(); // Or use new GsonBuilder().create();
		// MyType target = new MyType();
		// String json = gson.toJson(target); // serializes target to Json
		Quiosque q = gson.fromJson(json, Quiosque.class);
		System.out.println("Quiosque: " + q);
	}

	@Test
	public void testeBrinquedoNoQuiosque() throws Exception {

		URL url = new URL(URL_WEBSERVICE + "/nuvemtoquiosque/getBrinquedoNoQuiosque/1");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code : " + con.getResponseCode());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try (InputStream is = con.getInputStream(); JsonReader rdr = Json.createReader(is)) {
			// JsonObject obj = rdr.readObject();
			// System.out.println("obj = "+obj);
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				System.out.println("result: " + result);
				JsonObject jsonObj = result.getJsonObject("brinquedo");
				System.out.println("jsonObj: " + jsonObj);
				System.out.println("brinquedoJsqon: " + jsonObj.toString());
				// Brinquedo b = objectMapper.convertValue(jsonObj.toString(), Brinquedo.class);
				Brinquedo b = new Brinquedo(jsonObj.getJsonNumber("id").longValue(), jsonObj.getString("chave"),
						jsonObj.getString("descricao"), jsonObj.getString("infTecnicas"), null, 0d, null,
						jsonObj.getString("motivoDesabilitado"), Base64.getDecoder().decode(jsonObj.getString("foto")),
						jsonObj.getJsonNumber("tempoMinimo").intValue(), jsonObj.getJsonNumber("versao").intValue());
				System.out.println("Brinquedo: " + b.getId() + " | " + b.getDescricao() + " | " + b.getFoto() + " | ");
			}
		}
	}

	@Test
	public void testeTabelaPreco() throws Exception {
		
		String endpoint = URL_WEBSERVICE + "/nuvemtoquiosque/getTabelaPreco/1";
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream is = con.getInputStream(); JsonReader rdr = Json.createReader(is)) {
			// JsonObject obj = rdr.readObject();
			JsonArray results = rdr.readArray();
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				// JsonObject jsonObj = result.getJsonObject("formaPagamento");
				TabelaPreco o = objectMapper.reader().forType(TabelaPreco.class).readValue(result.toString());
				// TabelaPreco o = new Gson().fromJson(result.toString(), TabelaPreco.class);
			}
		}
	}

	/*
	 * @Test public void testeBrinquedoNoQuiosque() throws Exception { String json =
	 * getRecursoJSon(URL_WEBSERVICE + "/nuvemtoquiosque/getBrinquedoNoQuiosque/1");
	 * System.out.println(json);
	 * 
	 * ObjectMapper om = new ObjectMapper(); JsonNode rootNode = om.readTree(json);
	 * System.out.println("rootNode = "+rootNode);
	 * 
	 * JsonNode nodes = rootNode.path("brinquedo");
	 * System.out.println("nodes = "+nodes);
	 * 
	 * Iterator<JsonNode> elements = nodes.elements(); while(elements.hasNext()){
	 * JsonNode nodeBr = elements.next(); System.out.println("NodeBr = "+nodeBr); }
	 * 
	 * }
	 */
}