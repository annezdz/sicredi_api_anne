Feature: Check restrictions by CPF

  @WithoutRestriction
  Scenario: Check CPF without restriction
    Given a "<cpf>" to be checked with "getRestrictionAPI" http request
    When calls "getRestrictionAPI" with "GET" http request
    Then the API call got success with status 204

    @WithRestriction
    Scenario Outline: o: Check CPF with restriction
      Given a "<cpf>" to be checked with "getRestrictionAPI" http request
      When calls "getRestrictionAPI" with "GET" http request
      Then the API call got success with status 200
      And "mensagem" in response body is "O CPF <cpf> possui restrição"
      Examples:
      | cpf |
      | 97093236014 |
      | 60094146012 |
      | 84809766080 |
      | 62648716050 |
      | 26276298085 |
      | 01317496094 |
      | 55856777050 |
      | 19626829001 |
      | 24094592008 |
      | 58063164083 |



