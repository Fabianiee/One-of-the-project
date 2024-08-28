import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

public class OrderListTest {
    private static final String ORDER_URL = "https://qa-scooter.praktikum-services.ru";

    @Test
    public void getOrdersTest() {
                given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_URL)
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
