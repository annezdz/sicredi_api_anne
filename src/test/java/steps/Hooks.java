package steps;

import io.cucumber.java.Before;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Hooks extends Utils {

    private static SimulationsSteps newSimulation;

    @Before("@PutSimulation, @PutSimulationWithError,@GetByCPF,@TryGetCPFByIDWithoutSimulation, @DeleteSimulationSuccessful,@GetAllSimulations")
    public void beforeScenario() throws IOException {

        newSimulation = new SimulationsSteps();
        if (SimulationsSteps.duplicatedCPF == null) {
            newSimulation.iCreateANewSimulationWith("11", "1000.00", "true", "POST");
            newSimulation.callsWithVerb("postSimulationAPI", "POST");
        }
    }

    @Before("@TryDeleteSimulation")
    public void deleteAllSimulations() throws IOException {
        RequestSpecification res = given()
                .spec(requestSpecification("GET"));
        ResponseSpecification resspec = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectContentType(ContentType.JSON)
                .build();
        Response response = res.when()
                .get(APIResources.getAllSimulationsAPI.getResource());

        List<Map<String, String>> simulations = JsonPath.from(response.asString()).get();

        boolean flag = true;
        while (simulations.size() > 0 && flag == true) {
            List<Integer> ids = JsonPath.from(response.asString()).get("id");
            if (ids.size() > 0) {
                for (int i = 0; i < ids.size(); i++) {
                    response = res.when()
                            .delete(APIResources.deleteSimulationAPI.getResource() + ids.get(0));
                    flag = false;
                    break;
                }

            }
        }
    }

    @Before("@TryGetAllSimulations")
    public void deleteAllRegisters() throws IOException {

            RequestSpecification res = given()
                    .spec(requestSpecification("GET"));
            ResponseSpecification resspec = new ResponseSpecBuilder()
                    .expectStatusCode(204)
                    .expectContentType(ContentType.JSON)
                    .build();
            Response response = res.when()
                    .get(APIResources.getAllSimulationsAPI.getResource());

            boolean flag = true;
            List<Integer> ids = JsonPath.from(response.asString()).get("id");
            int count = ids.size();
            while (count > -1 && flag == true) {
                while (count > 0 ) {
                    for(int i = 0; i <= ids.size() -1; i++) {
                        res.when()
                                .delete(APIResources.deleteSimulationAPI.getResource() + ids.get(i));
                        count--;
                    }
                }
                if(count <= 0) {
                    flag = false;
                }
            }
        }
    }


