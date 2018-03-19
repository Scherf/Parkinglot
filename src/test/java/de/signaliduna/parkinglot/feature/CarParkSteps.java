package de.signaliduna.parkinglot.feature;

import javax.inject.Singleton;

import org.picocontainer.annotations.Inject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.service.CarParkService;

/**
 * @author u094915
 */
public class CarParkSteps {

  @Inject
  private CarParkService carParkService;

  @Given("Create carpark ('.+') with initial capacity of '(\\d+)' and owner name '(.+)'")
  public void initCarpark(String carparkName, int capacity, String ownerName) {
    Person owner = Person.builder().withName(ownerName).build();
    carParkService.createCarpark(carparkName, owner, capacity);
  }

  @When("Rent a parking lot for rentee '(.*)' in Carpark (.+)")
  public void rentPsrkingLot(String renteeName, String carparkName) {
    Person rentee = Person.builder().withName(renteeName).build();
    CarPark carPark = carParkService.findByName(carparkName);
    carParkService.rentParkingLot(carPark, rentee,1,1);
  }

  @Then("Carpark '(.*)' has (\\d+) free rentable parking lots")
  public void verifyFreeRentableParkingLots(String carParkName, int amount){
    carParkService.getAmountOfRentableParkingLots(carParkName);

  }


}
