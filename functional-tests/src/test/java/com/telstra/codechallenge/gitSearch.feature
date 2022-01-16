Feature: Getting Last week's Hottest Repo

  Background: 
    * url 'http://localhost:8087/hottestRepo'
    * header Accept = 'application/json'

  Scenario: Get top 3 last weeks Hottest Repo
    Given param size = 3
    When method GET
    Then status 200
    And print response
    And assert response.length == 3

  Scenario: check Response
    Given param size = 1
    When method GET
    Then status 200
    And print response
    * def schema =
      """
          {
              "html_url": "##string",
              "watchers_count":"#number",
              "language": "##string",
              "description": "##string",
              "name": "##string"
          }

      """
    * match each response == schema

  Scenario: check Response with 0 as size
    Given param size = 0
    When method GET
    Then status 200
    And print response
    And assert response.length==0

  Scenario: check Response with large number
    Given param size = 31
    When method GET
    Then status 400
    And print response
    And assert response.error=="Bad Request"
    
  Scenario: check Response check order
    Given param size = 4
    When method GET
    Then status 200
    And print response
    And assert response[0].watchers_count >= response[1].watchers_count