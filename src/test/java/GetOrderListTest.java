import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    final static String ORDERS_URI = "/api/v1/orders";
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getListOrdersReturnListOrdersTest(){

        given()
                .header("Content-type", "application/json")
                .get(ORDERS_URI)
                .then().assertThat().body("orders", notNullValue());
    }



}
