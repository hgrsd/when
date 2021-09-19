package nl.hgrsd.when;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OccurrencesResourceTest {

    @Test
    public void getOccurrencesTest() {
        given()
                .queryParam("rrule", "FREQ=DAILY;BYHOUR=8")
                .queryParam("from", "20210401T080000")
                .queryParam("to", "20210403T075959")
                .when().get("/occurrences")
                .then()
                .statusCode(200)
                .body(is("[\"20210401T080000\",\"20210402T080000\"]"));
    }

}