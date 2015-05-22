Feature: Validate connection SQL
  Scenario: Connect to SQLServer
    Given database
    When connect
    Then connection is sucessful