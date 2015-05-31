Feature: Validate DataSet
  Background: Database and table
    Given An existing database 'Groovy'
    And its respective table 'SimpleOrders'

  Scenario: Test the dataset
    When creating a record with dataset
    Then validate the new record with dataset