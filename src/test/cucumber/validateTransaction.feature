Feature: Validate Transaction
  Background: Database and table
    Given An existing database
    And its respective table

    Scenario: Test the transaction in SqlServer
      When creating a record in SqlServer
      Then use transaction in SqlServer