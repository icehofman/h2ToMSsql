Feature: Validate connection SQL
  Scenario: Connect to SQLServer
    Given database groovy on SqlServer
    When connect to SqlServer
    Then connection is successful to SqlServer