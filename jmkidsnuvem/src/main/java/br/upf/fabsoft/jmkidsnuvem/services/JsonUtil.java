package br.upf.fabsoft.jmkidsnuvem.services;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.json.JsonObject;

public class JsonUtil {
	public static Date getDate(JsonObject jsonObj, String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
           return sdf.parse(getString(jsonObj, name));
        }catch(Exception e){
            return null;
        }
    }
	
	public static byte[] getByteVet(JsonObject jsonObj, String name) {
        try {
           return Base64.getDecoder().decode(jsonObj.getString(name));
        }catch(Exception e){
            return null;
        }
    }	
	
    public static String getString(JsonObject jsonObj, String name) {
    	try {
    	  return jsonObj.getJsonString(name).getString();
    	} catch (Exception e) {
    		return "";
    	}
    }
}
