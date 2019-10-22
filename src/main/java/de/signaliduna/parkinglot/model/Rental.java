package de.signaliduna.parkinglot.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author u094915
 */
@Entity
@Getter
@Setter
public class Rental implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @OneToOne
  private ParkingLot parkingLot;

  @NotNull
  @ManyToOne(cascade = CascadeType.ALL)
  private Person rentee;

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
      Objects.requireNonNull(rentee, "Cannot create rental without rentee");
      Objects.requireNonNull(parkingLot, "Cannot create rental without parkinglot");
      Rental rental = new Rental();
      rental.rentee = this.rentee;
      rental.rentee.addRental(rental);
      rental.parkingLot = this.parkingLot;
      rental.parkingLot.setRental(rental);
      return rental;
    }
  }
}
