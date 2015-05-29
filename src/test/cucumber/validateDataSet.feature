Feature: Validate DataSet
  Background: Database and table
    Given An existing database 'Groovy'
    And its respective table 'SimpleOrders'

  Scenario: Test the dataset in SqlServer
    When creating a record with dataset in SqlServer
    Then validate the new record with dataset in SqlServer