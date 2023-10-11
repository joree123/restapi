package stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

import com.jayway.jsonpath.JsonPath;

import api.constants.FileConstants;
import api.pojo.AddUser;
import api.pojo.UpdateUser;
import api.utils.CommonUtilities;
import api.utils.RestUtils;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.*;
public class userStepDefn {
	private HashMap<String,String> loginHeaders;
	private static HashMap<String,String> headers;
	private HashMap<String,String> loginPayload;
	private HashMap<String,String> deletePayload;
	private String updatePayload;
	private String endPoint;
	private Response response;
	private String sUserData;
	
	private static String token;
	private String addUserPayLoad;
	private String message;
	private static String id;
	private static String userid;
	
	//@Given ("Setting up base uri")
	@BeforeAll
	public static void set_up_baseURI() throws IOException
	{
		String uri=JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.prod");
		RestAssured.baseURI=uri;
	}
	@Given("Login payload with username and password")
	public void login_payload_with_username_and_password() throws IOException {
		
		
		loginHeaders=new HashMap<String,String>();
		loginHeaders.put("Content-Type","application/json");
		
		loginPayload=new HashMap<String,String>();
		loginPayload.put("username", "joree@tekarch.com");
		loginPayload.put("password", "Admin123");
		
		endPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.endpoints.login");
		
	}
	
	@When("User call LoginApi with Post http request")
	public void user_call_with_loginApi() throws IOException {
	  
		response=RestUtils.loginPostReq1(loginHeaders, loginPayload,endPoint );
	   String res=response.then().assertThat().statusCode(201).body(matchesJsonSchema(new File(FileConstants.LOGINSCHEMA_PATH)))
				.extract().response().asString();
	   token=JsonPath.read(res, "$[0].token");
	   System.out.println(token);
	   }
	
	@When ("User call AddUserApi with Post http request")
	public void user_call_with_addUserApi()
	{
		    response=RestUtils.loginPostReq(headers, addUserPayLoad, endPoint);
			message=response.then().assertThat().statusCode(201).extract().response().asString();
			System.out.println(message);
	   }
	@When("User call GetUserApi with Get http request")
	public void user_call_with_getUserApi() throws IOException
	  
	   {
		  
			endPoint= JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.endpoints.getdata");
			response=RestUtils.getDataReq(headers, endPoint);
			Response res1=response.then().assertThat().statusCode(200).body(matchesJsonSchema(new File(FileConstants.GETDATASCHEMA_PATH))).extract().response();
			sUserData=res1.asString();
			List<String> accounts=JsonPath.read(sUserData, "$[*].accountno");
			System.out.println(accounts);
			
			String addedAccount=JsonPath.read(sUserData, "$[0].accountno");
			Assert.assertEquals(addedAccount,"TA-3425231");
			if(addedAccount.equalsIgnoreCase("TA-3425231"))
			{
			id=JsonPath.read(sUserData, "$[0].id");
			userid=JsonPath.read(sUserData, "$[0].userid");
			
			}
			System.out.println(id);
			
	   }
	@When("User call UpdateUserApi with Put http request")
	public void user_call_with_updateApi()
	{
	  
		 response=RestUtils.updatePostReq(headers, updatePayload, endPoint);
		message=response.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(message);
		
	   }
		   
	 @When("User call DeleteUserApi with Delete http request")  
	 public void user_call_with_deleteApi()
	 {
		   response=RestUtils.deletePostReq(headers, deletePayload, endPoint);
	message=response.then().assertThat().statusCode(200).extract().response().asString();
			System.out.println(message);
	   	   
	   
	}

	@Then("the api call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		Assert.assertEquals(response.statusCode(),int1);
	}
	 
	@Then("And the autorization token is generated")
	public void verify_generated_auth_token()
	{
		Assert.assertTrue(!token.isEmpty());
		
	}
	

	

  @Given("Add user Payload with {string} {string} {string} {string}")
	public void add_user_payload_with(String string, String string2, String string3, String string4) throws IOException {
	  AddUser user=new AddUser(string,string2,string3,string4);
	  headers=new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		headers.put("Token", token);
		addUserPayLoad=CommonUtilities.serializedObjectToString(user);
		endPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.endpoints.adddata");
	}

	@Then("the success message is displayed")
	public void the_success_message_is_displayed() {
		Assert.assertEquals("{\"status\":\"success\"}", message);
	}

	
	@Given("Delete user payload")
	public void delete_user_payload() throws IOException {
		
		deletePayload=new HashMap<String,String>();
		deletePayload.put("id", id);
		deletePayload.put("userid", userid);
		endPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH),"$.login.endpoints.deletedata");
	}

	@Then("The users info is displayed")
	public void the_users_info_is_displayed() {
		List<String> accounts=JsonPath.read(sUserData, "$[*].accountno");
		System.out.println(accounts);
		String addedAccount=JsonPath.read(sUserData, "$[0].accountno");
		if(addedAccount.equalsIgnoreCase("TA-1101101"))
		{
		id=JsonPath.read(sUserData, "$[0].id");
		userid=JsonPath.read(sUserData, "$[0].userid");
		System.out.println(id);
		}
	}

	@Given("Update user payload with {string} {string} {string} {string}")
	public void update_user_payload(String string, String string2, String string3, String string4) throws IOException {
		
		UpdateUser user=new UpdateUser(string,string2,string3,string4,id,userid);
        updatePayload=CommonUtilities.serializedObjectToString(user);
		endPoint=JsonPath.read(new File(FileConstants.URI_FILEPATH),"$.login.endpoints.updatedata");
	}



}
