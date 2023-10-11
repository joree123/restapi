package apitests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import api.constants.FileConstants;
import api.pojo.AddUser;
import api.utils.CommonUtilities;
import api.utils.RestUtils;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseApiTest{
  protected String token;
  private String id;
  private String userid;
	@Test(priority=1)
	public void loginApi() throws IOException
	{
		HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		
		HashMap<String,String> payload=new HashMap<String,String>();
		payload.put("username", "joree@tekarch.com");
		payload.put("password", "Admin123");
		
		String loginEndPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.endpoints.login");
		
		Response response=RestUtils.loginPostReq1(headers, payload,loginEndPoint );
		String res=response.then().assertThat().statusCode(201).body(matchesJsonSchema(new File(FileConstants.LOGINSCHEMA_PATH)))
		.extract().response().asString();
		token=JsonPath.read(res, "$[0].token");
		System.out.println(token);
			
		
	}
	
	@Test(priority=2,enabled=true)
	public void addUserApiTest() throws IOException
	{
		AddUser user=new AddUser("TA-1101101","2","68768","123432");
		HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		headers.put("Token", token);
		String sUser=CommonUtilities.serializedObjectToString(user);
		String addUserEndPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.endpoints.adddata");
		
		Response res=RestUtils.loginPostReq(headers, sUser, addUserEndPoint);
		String message=res.then().assertThat().statusCode(201).extract().response().asString();
		System.out.println(message);
		Assert.assertEquals("{\"status\":\"success\"}", message);
		
	}
	
	@Test(priority=3,enabled=true)
	public void getUserApiTest() throws IOException
	{
		HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		headers.put("Token", token);
		String getUserEndPoint= JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.endpoints.getdata");
		Response response=RestUtils.getDataReq(headers, getUserEndPoint);
		Response res1=response.then().assertThat().statusCode(200).body(matchesJsonSchema(new File(FileConstants.GETDATASCHEMA_PATH))).extract().response();
		String sResponse=res1.asString();
		List<String> accounts=JsonPath.read(sResponse, "$[*].accountno");
		System.out.println(accounts);
		String addedAccount=JsonPath.read(sResponse, "$[0].accountno");
		if(addedAccount.equalsIgnoreCase("TA-1101101"))
		{
		id=JsonPath.read(sResponse, "$[0].id");
		userid=JsonPath.read(sResponse, "$[0].userid");
		System.out.println(id);
		}
	}
	
	
	@Test(priority=4,enabled=true)
	public void updateUserApiTest() throws IOException
	{
		HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		headers.put("Token", token);
		String updatePayload=CommonUtilities.readJsonFileToString(FileConstants.UPDATEUSER_FILEPATH);
		String updateUserEndPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH),"$.login.endpoints.updatedata");
	Response res=RestUtils.updatePostReq(headers, updatePayload, updateUserEndPoint);
	String sResponse=res.then().assertThat().statusCode(200).extract().response().asString();
	System.out.println(sResponse);
	Assert.assertEquals("{\"status\":\"success\"}", sResponse);
	}
	
	@Test(priority=5, enabled=true)
	public void deleteUserApiTest() throws IOException
	{
		HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		headers.put("Token", token);
		HashMap<String,String> payload=new HashMap<String,String>();
		payload.put("id", id);
		payload.put("userid", userid);
		String deleteUserEndPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH),"$.login.endpoints.deletedata");
	Response res=RestUtils.deletePostReq(headers, payload, deleteUserEndPoint);
	String sResponse=res.then().assertThat().statusCode(200).extract().response().asString();
	Assert.assertEquals("{\"status\":\"success\"}", sResponse);
	System.out.println(sResponse);
	}
}

