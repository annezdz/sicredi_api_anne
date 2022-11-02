package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features/simulations.feature",
        plugin ="json:target/jsonReports/cucumber-report.json",
        tags = " @PutSimulationWithError",
        glue= {"steps"})
public class TestRunnerSimulations {
}