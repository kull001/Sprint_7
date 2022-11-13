import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    final static String ORDERS_URI = "/api/v1/orders";
    public Response createOrder(Order order){
        return  given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS_URI);
    }
}
