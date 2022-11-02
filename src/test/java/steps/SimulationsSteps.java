package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import pojo.Simulation;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class SimulationsSteps extends Utils {

    protected RequestSpecification res;
    protected ResponseSpecification resspec;
    protected Response response;

    static Simulation duplicatedCPF;

//    static TestDataBuild teste ;

    protected static int statusCodeReturned;
    @Given("I create a new simulation with {string} {string} {string} with {string} verb")
    public void iCreateANewSimulationWith(String instalments, String value, String insurance, String http)  throws IOException {
       duplicatedCPF = TestDataBuild.addSimulation(Double.parseDouble(value),Integer.parseInt(instalments),Boolean.parseBoolean(insurance));
        res = given()
                .spec(requestSpecification(http))
                .body(duplicatedCPF);
    }

    @And("returned the new simulation")
    public void returnedTheNewSimulation() {
       Assert.assertFalse(getBody(response).isEmpty());
       response
               .then()
               .assertThat()
               .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/resources/schema.json")));
    }

    @When("calls {string} with {string} verb")
    public void callsWithVerb(String resource, String http) {
        APIResources resourceAPI = APIResources.valueOf(resource);

        resspec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();

        if(http.equalsIgnoreCase("POST")) {
            response = res.when()
                    .post(resourceAPI.getResource());
            statusCodeReturned = response.getStatusCode();
        } else if(http.equalsIgnoreCase("PUT")) {
            response = res.when()
                    .put(resourceAPI.getResource() + duplicatedCPF.getCpf());
            statusCodeReturned = response.getStatusCode();
        }
    }

    @Then("the API Simulation return with status {int}")
    public void theAPISimulationCallGotSuccessWithStatus(int statusCodeExpected) {
        Assert.assertEquals(response.getStatusCode(),statusCodeExpected);
    }

    @And("returned the list with errors")
    public void returnedTheListWithErrors() {
        Assert.assertFalse(getBody(response).isEmpty());
        response.then().assertThat().body("size()", Matchers.is(1));
    }

    @Given("I insert a existent CPF with {string} {string} {string} with {string} verb")
    public void iInsertAExistentCPFWithWithVerb(String instalments, String value, String insurance, String http) throws IOException {

        duplicatedCPF = TestDataBuild.addSimulation(Double.parseDouble(value),Integer.parseInt(instalments),Boolean.parseBoolean(insurance));
        duplicatedCPF.setCpf("97093236014");
        res = given()
                .spec(requestSpecification(http))
                .body(duplicatedCPF);
    }

    @And("returned the {string}")
    public void returnedThe(String message) {
        response.then()
                .body("mensagem",Matchers.equalTo("CPF duplicado"));
    }

    @And("returned the modified simulation")
    public void returnedTheModifiedSimulation() {
        Assert.assertFalse(getBody(response).isEmpty());
        response
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/resources/schema.json")));
    }

    @Given("I insert a CPF and {string} simulation with {string} {string} {string} with {string} verb")
    public void iInsertAExistentCPFAndModifiedSimulationWithWithVerb(String action, String instalments, String value, String insurance, String http) throws IOException {
        if(duplicatedCPF.getCpf() == null) {
            duplicatedCPF = TestDataBuild.addSimulation(Double.parseDouble(value),Integer.parseInt(instalments),Boolean.parseBoolean(insurance));
            duplicatedCPF.setCpf("97093236014");

        } else  if(action.equalsIgnoreCase("modified with error")){
            duplicatedCPF.setParcelas(Integer.valueOf(instalments));

            duplicatedCPF.setEmail("emailErradoTeste");
        } else if(action.equalsIgnoreCase("modified")) {
            duplicatedCPF.setNome(generateFakerName());
            duplicatedCPF.setParcelas(Integer.valueOf(instalments));
        } else {
            duplicatedCPF.setNome(generateFakerName());
        }
        res = given()
                .spec(requestSpecification(http))
                .body(duplicatedCPF);
    }
}

