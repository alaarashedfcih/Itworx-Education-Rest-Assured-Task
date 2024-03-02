Feature: Bored API Automation

  Scenario: Verify API response code and schema
    Given the API endpoint is accessible
    When I make a GET request to the API
    Then I should receive a response with code 200
    And the response should have the correct schema
    And the response should contain a non-empty activity
    And the response should contain a valid number of participants