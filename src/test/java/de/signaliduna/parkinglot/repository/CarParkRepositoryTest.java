package de.signaliduna.parkinglot.repository;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author U094915
 *     <p>
 *     s. https://stackoverflow.com/questions/55063778/how-can-use-an-in-memory-h2-database-when-testing-my-quarkus-application
 */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class CarParkRepositoryTest {

  @Inject
  private CarParkRepository repository;


  @Test
  void persist_empty_name() {
    Assertions.assertThrows(NullPointerException.class, () -> repository.persist(new CarPark(null)));
  }

  @Test
  void persist_with_same_name() {
    Assertions.assertThrows(BusinessException.class, () -> {
      repository.persist(new CarPark("JUnit"));
      repository.persist(new CarPark("Junit"));
    });

  }

  @Test
  void persist_find() {
    String carParkName = "JUnit";
    CarPark persisted =
        repository.persist(
            CarPark
                .builder()
                .withName(carParkName)
                .withOwner(
                    Person
                        .builder()
                        .withName("owner")
                        .build())
                .build());
    assertThat(persisted, notNullValue());

    CarPark carPark = repository.find(carParkName);
    assertThat(carPark, is(notNullValue()));
    assertThat(carPark.getName(), is("JUnit"));
  }

  @Test
  void delete() {
    String name = "ToDelete";
    CarPark persisted =
        repository.persist(
            CarPark
                .builder()
                .withName(name)
                .withOwner(
                    Person
                        .builder()
                        .withName("owner")
                        .build())
                .build());
    assertThat(persisted, notNullValue());

    int removed = repository.remove(name);

    assertThat(removed, is(1));
  }
}