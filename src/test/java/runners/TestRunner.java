package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features/cpfValidations.feature",
        plugin ="json:target/jsonReports/cucumber-report.json",
        tags = "@WithoutRestriction",
        glue= {"steps"})
public class TestRunner {
}