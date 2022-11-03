package steps;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@PutSimulation, @PutSimulationWithError,@GetByCPF")
    public void beforeScenario() throws IOException {

        SimulationsSteps newSimulation =new SimulationsSteps();
        if(SimulationsSteps.duplicatedCPF==null)
        {
            newSimulation.iCreateANewSimulationWith("11", "1000.00", "true","POST");
            newSimulation.callsWithVerb("postSimulationAPI", "POST");
        }
    }

    @Before("@TryGetAllSimulations")
    public void deleteAllSimulations() {

    }
}
