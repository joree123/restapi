package apitests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.jayway.jsonpath.JsonPath;

import api.constants.FileConstants;
import io.restassured.RestAssured;

public class BaseApiTest {

	protected static ExtentReports extent = new ExtentReports();
	protected static ExtentSparkReporter spark = null;
	public static ExtentTest test = null;
	
	public static Logger logger = LogManager.getLogger("BASETEST");
	
	@BeforeTest
	public static void getBaseURI() throws IOException
	{
		String uri=JsonPath.read(new File(FileConstants.URI_FILEPATH), "$.login.prod");
		RestAssured.baseURI=uri;
		logger.info("BaseApiTest:getBaseuri(): "+ RestAssured.baseURI+" is set.");
		spark = new ExtentSparkReporter(new File(FileConstants.REPORT_PATH));
		extent.attachReporter(spark);

	}
	@BeforeMethod
	public static void getTest(Method name)
	{
		
		test=extent.createTest(name.getName());
	}
}
