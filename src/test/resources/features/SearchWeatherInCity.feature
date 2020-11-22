@sample
Feature: Search Weather In City

  Background:
    Given User opens browser
    And Wait until page loads completely

  Scenario Outline: Verify UI when input valid city
    When User inputs '<city>' into search box
    And User clicks button Search
    And User selects the first result return from search box
    Then Verify 'General Panel' displays property
    And Verify 'Hourly Forecast' displays property
    And Verify '8-day Forecast' displays property

    When User clicks the '4th' day in 8-day forecast
    Then Verify details for '4th' day in 8-day forecast displays property
    And User closes browser
    Examples:
      | Discription                                    | city                 |
      | Search weather for city 'Ho Chi Minh City, VN' | Ho Chi Minh City, VN |
      | Search weather for city 'Chennai, IN'          | Chennai, IN          |

  Scenario: Verify UI displays not found message when input invalid city
    When User inputs 'ABC123' into search box
    And User clicks button Search
    Then Verify error message is displayed under searchbox with content 'Not found. To make search more precise put the city's name, comma, 2-letter country code (ISO3166). You will get all proper cities in chosen country.'
    And User closes browser
