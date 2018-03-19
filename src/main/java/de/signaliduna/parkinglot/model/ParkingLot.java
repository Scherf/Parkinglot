package de.signaliduna.parkinglot.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Repräsentiert einen Parkplatz in dem Parkhaus.
 */
public class ParkingLot extends Entity implements Cloneable {

  @Min(1)
  @Max(9999)
  private int number;

  @Min(1)
  @Max(5)
  private int level;

  @Valid
  private Rental rental;

  ParkingLot() {
    //
  }

  public int getNumber() {
    return number;
  }

  public int getLevel() {
    return level;
  }

  public Rental getRental() {
    return rental;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setRental(Rental rental) {
    this.rental = rental;
  }

  public static ParkingLotBuilder builder() {
    return new ParkingLotBuilder();
  }

  @Override
  public String toString() {
    return super.toString() + "\n"
            + "ParkingLot{" +
            "number=" + number +
            ", level=" + level +
            ", rental=" + rental +
            '}';
  }

  public static class ParkingLotBuilder {

    private int number;
    private int level;
    private Rental rental;

    ParkingLotBuilder() {
      //
    }


    public ParkingLotBuilder withNumber(int number) {
      this.number = number;
      return this;
    }

    public ParkingLotBuilder withLevel(int level) {
      this.level = level;
      return this;
    }

    public ParkingLotBuilder withRental(Rental rental) {
      this.rental = rental;
      return this;
    }

    @NotNull
    public ParkingLot build() {
      ParkingLot parkingLot = new ParkingLot();
      parkingLot.level = this.level;
      parkingLot.number = this.number;
      parkingLot.rental = this.rental;
      return parkingLot;
    }
  }
}
