Feature: Check restrictions by CPF

  @WithoutRestriction
  Scenario: Check CPF without restriction
    Given a "<cpf>" to be checked with "getRestrictionAPI" http request
    When calls "getRestrictionAPI" with "GET" http request
    Then the API call got success with status "204"
    And message in response body is "Não possui restrição"


    @WithRestriction
    Scenario Outline: o: Check CPF with restriction
      Given a "<cpf>" to be checked with "getRestrictionAPI" http request
      When calls "getRestrictionAPI" with "GET" http request
      Then the API call got success with status "200"
      And message in response body is "O CPF <cpf> tem problema"
      Examples:
      | cpf |
      | 97093236014 |
      | 60094146012 |
      | 84809766080 |
      | 62648716050 |


