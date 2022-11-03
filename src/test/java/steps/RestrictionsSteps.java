package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class RestrictionsSteps extends Utils {

    protected RequestSpecification res;
    protected ResponseSpecification resspec;
    public static String cpfWithoutRestriction;
    protected Response response;
    protected  int statusCodeReturned;

    @Given("a {string} to be checked with {string} http request")
    public void i_check_a(String cpf, String http) throws IOException {
        if(!cpf.matches("\\d{11}")) {
            cpfWithoutRestriction = generateValidCPF();
        } else {
            cpfWithoutRestriction = cpf;
        }
        res = given()
                .spec(requestSpecification(http));
    }

    @When("calls {string} with {string} http request")
    public void calls_with_http_request(String resource, String method) {
        APIResources resourceAPI = APIResources.valueOf(resource);
        resspec = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectContentType(ContentType.JSON)
                .build();

        if(method.equalsIgnoreCase("GET")) {
            response = res.when()
                    .get(resourceAPI.getResource() + cpfWithoutRestriction);
        }
    }

    @Then("the API call got success with status {int}")
    public void the_API_call_got_success_with_status(int statusCode) {
        Assert.assertEquals(response.getStatusCode(),statusCode);
    }

    @And("{string} in response body is {string}")
    public void messageInResponseBodyIs(String key, String expectedMessage) {
        Assert.assertEquals(getJsonPath(response,key), expectedMessage);

    }
}
