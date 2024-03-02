package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.assertEquals;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class BoredApiStepDefinitions {
    private Response response;
    private RequestSpecification request;

    @Given("the API endpoint is accessible")
    public void the_api_endpoint_is_accessible() {
        RestAssured.baseURI = "https://www.boredapi.com/api";
        request = RestAssured.given();
    }

    @When("I make a GET request to the API")
    public void i_make_a_get_request_to_the_api() {
        response = request.when().get("/activity");
        response.prettyPrint();
    }

    @Then("I should receive a response with code {int}")
    public void i_should_receive_a_response_with_code(Integer expectedStatusCode) {
        assertEquals((int)expectedStatusCode, response.getStatusCode());
    }

    @Then("the response should have the correct schema")
    public void the_response_should_have_the_correct_schema() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("activity_schema.json"));
    }

    @Then("the response should contain a non-empty activity")
    public void the_response_should_contain_a_non_empty_activity() {
        String activity = response.jsonPath().getString("activity");
        assertFalse("Activity should not be empty", activity.isEmpty());
    }

    @Then("the response should contain a valid number of participants")
    public void the_response_should_contain_a_valid_number_of_participants() {
        int participants = response.jsonPath().getInt("participants");
        assertTrue("Participants should be greater than 0", participants > 0);
    }

}
