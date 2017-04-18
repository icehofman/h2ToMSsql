Feature: Validate Gstorm
Background: Class
  Given the class 'Person'

  Scenario: Gstorm persistence Htwo
    When using the Gstorm on Htwo
    Then must create a new record on Htwo