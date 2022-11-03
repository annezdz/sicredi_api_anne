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
  Scenario: try add simulation with existent CPF
    Given I insert a existent CPF with "12" "2000.00" "true" with "POST" verb
    When calls "postSimulationAPI" with "POST" verb
    Then the API Simulation return with status 400
    And returned the "mensagem" "POST"

  @PutSimulation
  Scenario: modified existent simulation
    Given I insert a CPF and "modified" simulation with "20" "2000.00" "false" with "PUT" verb
    When calls "putSimulationAPI" with "PUT" verb
    Then the API Simulation return with status 200
    And returned the modified simulation

  @PutSimulationWithError
  Scenario: try modified existent simulation
    Given I insert a CPF and "modified with error" simulation with "1" "2000.00" "true" with "PUT" verb
    When calls "putSimulationAPI" with "PUT" verb
    Then the API Simulation return with status 400
    And returned the list with errors


  @PutWithoutSimulation
  Scenario: try modified a inexistent simulation
    Given I insert a CPF without simulation
    When calls "putSimulationAPI" with "PUT" verb
    Then the API Simulation return with status 404
    And returned the "mensagem" "PUT"

  @GetAllSimulations
  Scenario: get all simulations
    When calls "getAllSimulationsAPI" with "GET ALL" verb and then get all registers
    Then search all simulations


  @TryGetAllSimulations
  Scenario: try get all simulations
    When calls "getAllSimulationsAPI" with "GET ALL" verb and then get all registers
    Then returned status 204 because lis is empty

  @GetByCPF
  Scenario: Get a simulation by CPF
    Given I search a CPF
    When calls "getSimulationAPI" with "GET BY CPF"
    Then the API Simulation return with status 200
    And validate Schema



