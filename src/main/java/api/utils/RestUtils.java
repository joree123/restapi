package api.utils;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestUtils {

	public static Response loginPostReq1(HashMap<String,String> headersMap,HashMap<String,String> payload,String endPoint)
	{
		
		Response res=RestAssured.given().headers(headersMap).body(payload).when().post(endPoint);
		
		return res;
	}
	public static Response getDataReq(HashMap<String,String> headersMap,String endPoint)
	{
		
		Response res=RestAssured.given().headers(headersMap).when().get(endPoint);
		
		return res;
	}
	
	public static Response loginPostReq(HashMap<String,String> headersMap,String sPayload,String endPoint)
	{
		
		Response res=RestAssured.given().headers(headersMap).body(sPayload).when().post(endPoint);
		
		return res;
	}
	public static Response updatePostReq(HashMap<String,String> headersMap,String updatePayload,String endPoint)
	{
		
		Response res=RestAssured.given().headers(headersMap). body(updatePayload).when().put(endPoint);
		
		return res;
	}
	public static Response deletePostReq(HashMap<String,String> headersMap,HashMap<String,String> payload,String endPoint)
	{
		
		Response res=RestAssured.given().headers(headersMap).body(payload).when().delete(endPoint);
		
		return res;
	}
	
}
