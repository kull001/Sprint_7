import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void GetListOrdersReturnListOrdersTest(){

        given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then().assertThat().body("orders", notNullValue());
    }



}
