Feature: Simulations

  @AddSimulationSuccessful
  Scenario: add a new simulation successfully
    Given I create a new simulation with "10" "1000.00" "true" with "POST" verb
    When calls "postSimulationAPI" with "POST" verb
    Then the API Simulation return with status 201
    And returned the new simulation


  @AddSimulationError
  Scenario: add a new simulation with error
    Given I create a new simulation with "1" "1000.00" "false" with "POST" verb
    When calls "postSimulationAPI" with "POST" verb
    Then the API Simulation return with status 400
    And returned the list with errors


  @AddSimulationExistent
  Scenario: add a new simulation with error
    Given I insert a existent CPF with "12" "2000.00" "true" with "POST" verb
    When calls "postSimulationAPI" with "POST" verb
    Then the API Simulation return with status 400
    And returned the "mensagem"