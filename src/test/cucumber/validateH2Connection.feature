Feature: Validate connection HTwo
  Scenario: Connect to HTwo
    Given database groovy on HTwo
    When connect to HTwo
    Then connection is successful  to HTwo