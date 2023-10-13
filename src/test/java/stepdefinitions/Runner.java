package stepdefinitions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src\\test\\java\\featurefiles",
glue="stepdefinitions",plugin= {"pretty","json:target/jsonReports/cucumber-report.json"},tags= "@Regression ")


public class Runner extends AbstractTestNGCucumberTests {

}
