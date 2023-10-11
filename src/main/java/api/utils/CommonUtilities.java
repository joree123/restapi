package api.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.common.io.Files;

public class CommonUtilities {

	public static String readJsonFileToString(String filePath) throws IOException
	{
		byte[] jsonBytes=Files.toByteArray(new File(filePath));
		String jsonContent=new String(jsonBytes, StandardCharsets.UTF_8);
		return jsonContent;
	}
	
	public static String serializedObjectToString(Object jsonObject) throws JsonProcessingException
	{
		ObjectMapper obj=new ObjectMapper();
		//to prevent by default camel case conversion of the json keys and values
		obj.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
		String jsonContent=obj.writeValueAsString(jsonObject);
		
		System.out.println(jsonContent);
		return jsonContent;
		
	}
	

	
}
