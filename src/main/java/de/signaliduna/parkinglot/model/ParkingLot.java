package de.signaliduna.parkinglot.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Repräsentiert einen Parkplatz in dem Parkhaus.
 */
@Entity
@Getter
@Setter
@ToString
public class ParkingLot implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Min(1)
  @Max(9999)
  private int number;

  @Min(1)
  @Max(5)
  private int level;

  @Valid
  @OneToOne(cascade = CascadeType.ALL)
  private Rental rental;

  public static ParkingLotBuilder builder() {
    return new ParkingLotBuilder();
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
      if (this.rental !=  null) {
        parkingLot.rental = this.rental;
        parkingLot.rental.setParkingLot(parkingLot);
      }
      return parkingLot;
    }
  }
}
