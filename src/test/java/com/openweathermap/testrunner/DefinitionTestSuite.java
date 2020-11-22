package com.openweathermap.testrunner;

import com.openweathermap.pages.BaseClass;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.junit.AfterClass;
import org.junit.BeforeClass;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        tags = {"@sample"},
        glue = {"com.openweathermap.steps"},
        plugin = {"pretty", "json:target/surefire-reports/cucumber.json"},
        strict = true
)
public class DefinitionTestSuite extends BaseClass {
    @BeforeClass
    public static void beforeSuite() {
    }

    @AfterClass
    public static void afterSuite() {
        driver.close();
    }

}
