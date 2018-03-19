Feature: Rent a parkinglot
  Scenario: Rent a parkinglot
    Given Create carpark '.+' with initial capacity of '100' and owner name 'JUnit'
    When Rent a parking lot for rentee 'Test' in Carpark Junit
    Then Carpark 'JUnit' has 100 free rentable parking lots
    