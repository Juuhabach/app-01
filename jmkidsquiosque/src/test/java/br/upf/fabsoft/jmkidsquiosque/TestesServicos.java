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

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;
import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

public class TestesServicos {
   
	 private static final String URL_WEBSERVICE = "http://localhost:8080/jmkidsnuvem/rest";
	
	 @Test
		public void enviarClientes() throws Exception {	
	        try {
	        	String oql = "from Cliente o  ";
	        	List<Cliente> list = new GenericDao<Cliente>().createQuery(oql);
	        	ObjectMapper mapper = new ObjectMapper();
	        	String jsonString = mapper.writeValueAsString(list);
	            System.out.println("jsonString: "+jsonString); 
			} catch (Exception e) {
				throw new Exception(JpaUtil.exceptionHandler(e)); 
			} 
		}
}