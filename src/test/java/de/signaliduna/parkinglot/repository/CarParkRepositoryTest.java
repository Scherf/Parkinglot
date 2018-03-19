package de.signaliduna.parkinglot.repository;

import org.junit.Before;
import org.junit.Test;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

/**
 * @author U094915
 */
public class CarParkRepositoryTest {

  private CarParkRepository repository;

  @Before
  public void setUp() {
    repository = new CarParkRepository();
  }

  @Test(expected = NullPointerException.class)
  public void persist_empty_name() {
    repository.persist(new CarPark(null));
  }

  @Test(expected = BusinessException.class)
  public void persist_with_same_name() {
    repository.persist(new CarPark("JUnit"));
    repository.persist(new CarPark("Junit"));
  }

  @Test
  public void persist_find() {
    String carParkName = "JUnit";
    repository.persist(new CarPark(carParkName));
    CarPark carPark = repository.find(carParkName);
    assertThat(carPark, is(notNullValue()));
    assertThat(carPark.getName(), is("JUnit"));
  }

}