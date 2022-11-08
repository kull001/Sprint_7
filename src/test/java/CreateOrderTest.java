import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final Order testOrder ;
    Order order;
    String trackId;

    public CreateOrderTest(Order testOrder) {
        this.testOrder = testOrder;
    }
    @Parameterized.Parameters
    public static Object[][]SetOrders() {
        return new Object[][]{
                {new Order("name1", "lastName1", "Moscow, 125", "1", "+79171111111", 1, "2022-11-01")},
                {new Order("name1", "lastName1", "Moscow, 125", "1", "+79171111111", 1, "2022-11-01", new String[]{"GREY"})},
                {new Order("name1", "lastName1", "Moscow, 125", "1", "+79171111111", 1, "2022-11-01", new String[]{"BLACK"})},



        };
    }

    @Before
        public void setUp() {
            RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
            order = testOrder;

        }

    @Test
    public void SuccessOrderTest(){

        GetTrack track = given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .body().as(GetTrack.class);


        trackId = track.getTrack();
        MatcherAssert.assertThat(track.getTrack(), notNullValue());

    }
@After
    public void DelTrack(){
    given()
            .header("Content-type", "application/json")
            .put("/api/v1/orders/cancel?track="+ trackId);
}
}
