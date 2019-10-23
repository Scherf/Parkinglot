package de.signaliduna.parkinglot.parkinglot.boundary;

import de.signaliduna.parkinglot.model.ParkingLot;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


/**
 * @author u094915
 */
@QuarkusTest
class ParkingLotResourceIT {

  private final Client client = ClientBuilder.newClient();

  private String getBaseUrl() {
    return "http://localhost:8081/rest/";
  }


  @Test
  @Ignore
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


  @Test
  void create() {
    String parkingLotAsJson = "{\"level\":1,\"number\":666,\"rental\":{" +
                              "\"rentee\":{" +
                              "\"name\":\"JUnit\"}}}";
    Response response = given()
                            .contentType(ContentType.JSON)
                            .body(parkingLotAsJson)
                            .post("/rest/parkinglot")
                            .then()
                            .statusCode(201)
                            .extract()
                            .response();

    assertThat(response.getHeaders().getValue("Location"), is("http://localhost:8081/rest/parkinglot/1"));

    ParkingLot result = given()
                        .get("/rest/parkinglot/1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(ParkingLot.class);

    assertThat(result, notNullValue());
    assertThat(result.getRental().getRentee().getName(), is("JUnit"));
  }
}