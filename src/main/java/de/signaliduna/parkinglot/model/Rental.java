package de.signaliduna.parkinglot.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * @author u094915
 */
public class Rental extends Entity {

  @NotNull
  private ParkingLot parkingLot;
  @NotNull
  private Person rentee;

  public ParkingLot getParkingLot() {
    return parkingLot;
  }

  public Person getRentee() {
    return rentee;
  }

  public void setParkingLot(ParkingLot parkingLot) {
    this.parkingLot = parkingLot;
  }

  public void setRentee(Person rentee) {
    this.rentee = rentee;
  }

  public static RentalBuilder builder() {
    return new RentalBuilder();
  }

  /**
   * Internal builder.
   */
  public static class RentalBuilder {
    private ParkingLot parkingLot;
    private Person rentee;

    public RentalBuilder withParkingLot(ParkingLot parkingLot) {
      this.parkingLot = parkingLot;
      return this;
    }

    public RentalBuilder withRentee(Person rentee) {
      this.rentee = rentee;
      return this;
    }

    public Rental build() {
      Rental rental = new Rental();
      rental.rentee = this.rentee;
      rental.rentee.setRental(rental);
      rental.parkingLot = this.parkingLot;
      rental.parkingLot.setRental(rental);
      return rental;
    }
  }

}
