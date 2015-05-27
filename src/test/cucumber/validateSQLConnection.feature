Feature: Validate connection SQL
  Scenario: Connect to SQLServer
    Given database groovy on SqlServer
    When connect to SqlServer
    Then create table 'things' on SqlServer
    And close connection