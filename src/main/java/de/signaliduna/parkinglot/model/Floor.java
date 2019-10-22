package de.signaliduna.parkinglot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Jeder {@link CarPark} besteht aus mehreren {@link Floor}s, welche {@link ParkingLot}s enthalten.
 * @author U094915
 */
@Entity
@Getter
@Setter
@ToString
public class Floor implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private int level;
  private int capacity;

  @OneToMany
  private List<ParkingLot> parkingLots;

  public List<ParkingLot> addParkingLot(ParkingLot parkingLot) {
    parkingLots.add(parkingLot);
    return parkingLots;
  }

  public static FloorBuilder builder() {
    return new FloorBuilder();
  }

  public static final class FloorBuilder {
    private int level;
    private int capacity;
    private List<ParkingLot> parkingLots;

    private FloorBuilder() {
    }

    public FloorBuilder withLevel(int level) {
      this.level = level;
      return this;
    }

    public FloorBuilder withCapacity(int capacity) {
      this.capacity = capacity;
      return this;
    }


    public FloorBuilder withParkingLots(List<ParkingLot> parkingLots) {
      this.parkingLots = parkingLots;
      return this;
    }

    public Floor build() {
      Floor floor = new Floor();
      floor.level = this.level;
      floor.capacity = this.capacity;
      floor.parkingLots = this.parkingLots;
      return floor;
    }
  }
}
