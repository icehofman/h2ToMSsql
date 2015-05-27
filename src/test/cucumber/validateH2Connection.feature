Feature: Validate connection HTwo
  Scenario: Connect to HTwo
    Given database groovy on HTwo
    When connect to HTwo
    Then create table 'things' on HTwo
    And close connection to HTwo