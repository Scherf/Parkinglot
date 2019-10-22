package de.signaliduna.parkinglot.parkinglot.boundary;

import de.signaliduna.parkinglot.model.ParkingLot;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;


/**
 * @author u094915
 */
@QuarkusTest
class ParkingLotResourceIT {

  private final Client client = ClientBuilder.newClient();

  private String getBaseUrl() {
    return "http://localhost:8080/rest/";
  }


  @Test
  void testIt() {
    WebTarget resource = client.target(getBaseUrl()).path("parkinglot/all");
    List<ParkingLot> parkingLots = resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<ParkingLot>>() {
    });
    assertThat(parkingLots.size(), greaterThan(0));
    assertThat(parkingLots.get(0).getLevel(), is(1));
  }

  @Test
  void name() {
    given()
        .when()
          .get("/rest/parkinglot/all")
        .then()
          .statusCode(200);
  }
}