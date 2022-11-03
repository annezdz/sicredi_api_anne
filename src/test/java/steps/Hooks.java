package steps;

import io.cucumber.java.Before;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Utils;

import java.beans.AppletInitializer;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class Hooks extends Utils {

    private static SimulationsSteps newSimulation;

    @Before("@PutSimulation, @PutSimulationWithError,@GetByCPF,@TryGetCPFByIDWithoutSimulation, @DeleteSimulationSuccessful,@GetAllSimulations")
    public void beforeScenario() throws IOException {

        newSimulation =new SimulationsSteps();
        if(SimulationsSteps.duplicatedCPF==null) {
            newSimulation.iCreateANewSimulationWith("11", "1000.00", "true","POST");
            newSimulation.callsWithVerb("postSimulationAPI", "POST");
        }
    }

    @Before("@TryGetAllSimulations, @TryDeleteSimulation")
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
        while (simulations.size() > 0) {
//            List<Integer> numbers = simulations.stream().map(c -> Integer.parseInt(c.get("id"))).collect(Collectors.toList());
         if(simulations.size() == 0) {
                  break;
         }
           List<String> ids = JsonPath.from(response.asString()).get("id");
//           List<Integer> idsNumber = (List<Integer>) JsonPath.given(String.valueOf(response.then().extract().path("id")));
           for(int i = 0; i < ids.size(); i++) {
//               req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
//                       .setContentType(ContentType.JSON)
//                       .build();
//                   int value = Integer.parseInt(ids.get(i));
                   response = res.when()
                           .delete(APIResources.deleteSimulationAPI.getResource() + "40");
           }
        }

        }
    }

