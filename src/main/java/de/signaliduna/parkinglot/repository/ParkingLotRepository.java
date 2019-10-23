package de.signaliduna.parkinglot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Rental;

/**
 * Gemäß DDD Zugriff auf die {@link de.signaliduna.parkinglot.model.ParkingLot} Instanzen.
 */
@ApplicationScoped
@Transactional
public class ParkingLotRepository implements Repository {

  @Inject
  EntityManager entityManager;

  public ParkingLotRepository() {
    //
  }

  @NotNull
  public List<ParkingLot> findAllParkingLots() {
    return entityManager
        .createQuery("SELECT pl FROM ParkingLot pl", ParkingLot.class)
        .getResultList();
  }

  @NotNull
  public ParkingLot persist(ParkingLot parkingLot) {
    return entityManager.merge(parkingLot);
  }

  public ParkingLot findById(long id) {
    return entityManager.find(ParkingLot.class, id);
  }
}
