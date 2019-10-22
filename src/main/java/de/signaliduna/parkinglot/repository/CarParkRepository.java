package de.signaliduna.parkinglot.repository;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import de.signaliduna.parkinglot.model.exception.ErrorCode;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * @author U094915
 */
@ApplicationScoped
public class CarParkRepository implements Repository {

  private static final Logger LOGGER = LogManager.getLogger(CarParkRepository.class);

  /**
   * Using package-private visibility. See https://quarkus.io/guides/cdi-reference (2.)
   */
  @Inject
  EntityManager entityManager;

  public CarPark find(String name) {
    TypedQuery<CarPark> query = entityManager
                                    .createQuery("SELECT c FROM CarPark c WHERE c.name = :name", CarPark.class);
    query.setParameter("name", name);
    CarPark result = null;
    try {
      result = query.getSingleResult();
    } catch (NoResultException e) {
      LOGGER.info("No carpark found for {}", name);
    }
    return result;
  }

  @Transactional
  public CarPark persist(CarPark carPark) {
    Objects.requireNonNull(carPark.getName(), "Carpark without name cannot be persisted");
    if (find(carPark.getName()) != null) {
      throw new BusinessException(ErrorCode.F1001);
    }
    return entityManager.merge(carPark);
  }

  @Transactional
  public int remove(String name) {
    Query query = entityManager.createQuery("DELETE FROM CarPark c WHERE c.name = :name");
    query.setParameter("name", name);
    return query.executeUpdate();
  }
}
