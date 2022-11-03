package steps;

import cucumber.api.java.it.Ma;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import pojo.Simulation;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SimulationsSteps extends Utils {

    protected RequestSpecification res;
    protected ResponseSpecification resspec;
    protected Response response;

    static Simulation duplicatedCPF;

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

    @And("returned the {string} {string}")
    public void returnedThe(String message, String http) {
        if(http.equalsIgnoreCase("POST")) {
            response.then()
                    .body("mensagem",Matchers.equalTo("CPF duplicado"));
        } else if(http.equalsIgnoreCase("PUT")) {
            response.then()
                    .body("mensagem",Matchers.equalTo("CPF " + duplicatedCPF.getCpf() +" não encontrado"));
        }
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

        } else if(action.equalsIgnoreCase("without")) {
            duplicatedCPF.setCpf(generateValidCPF());
        } else {
            duplicatedCPF.setNome(generateFakerName());
        }
        res = given()
                .spec(requestSpecification(http))
                .body(duplicatedCPF);
    }

    @Given("I insert a CPF without simulation")
    public void iInsertACPFWithoutSimulation() throws IOException {
        duplicatedCPF = TestDataBuild.addSimulation(200.00,12,false);
        res = given()
                .spec(requestSpecification("PUT"))
                .body(duplicatedCPF);
    }

    @When("calls {string} with {string} verb and then get all registers")
    public void callsWithVerbAndGetAllRegisters(String resource, String http) throws IOException {

        res = given()
                .spec(requestSpecification(resource));
        APIResources resourceAPI = APIResources.valueOf(resource);
        resspec = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectContentType(ContentType.JSON)
                .build();

        if(http.equalsIgnoreCase("GET ALL")) {
            response = res.when()
                    .get(resourceAPI.getResource());
        }
    }

    @When("calls {string} with {string} and {string}")
    public void callsWithAnd(String resource, String http, String cpf) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        resspec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
        if(http.equalsIgnoreCase("GET")) {
            response = res.when()
                    .get(resourceAPI.getResource() + duplicatedCPF.getCpf());
        }

    }

    @When("calls {string} with {string}")
    public void callsWith(String resource, String verb) throws IOException {

        res = given()
                .spec(requestSpecification(resource));
        APIResources resourceAPI = APIResources.valueOf(resource);
        resspec = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectContentType(ContentType.JSON)
                .build();

        if (verb.equalsIgnoreCase("GET BY CPF")) {
            response = res.when()
                    .get(resourceAPI.getResource() + duplicatedCPF.getCpf());
        }
    }

    @Then("returned status {int} because lis is empty")
    public void returnedStatusBecauseLisIsEmpty(int status) {
        List<String> list = Collections.singletonList(response.asString());
        List<Map<String, String>> simulations = JsonPath.from(response.asString()).get();
        Assert.assertTrue(simulations.size() == 0);
        Assert.assertEquals(response.getStatusCode(), status);
    }

    @Then("search all simulations")
    public void searchAllSimulations() throws IOException {
        List<Map<String, String>> simulations = JsonPath.from(response.asString()).get();
        Assert.assertTrue(simulations.size() > 0);
        int size = simulations.size();
        response.then().assertThat().body("size()", Matchers.is(size));
    }

    @Given("I search a CPF")
    public void iSearchACPF() {
        if(duplicatedCPF.getCpf() == null) {
            duplicatedCPF = TestDataBuild.addSimulation(899.00,50, false);
            duplicatedCPF.setCpf("97093236014");
        }
    }

    @And("validate Schema")
    public void validateSchema() {
        Assert.assertFalse(getBody(response).isEmpty());
        response
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/resources/schema.json")));
    }
}

