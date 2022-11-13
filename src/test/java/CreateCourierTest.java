import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import org.mockito.internal.matchers.NotNull;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class CreateCourierTest {
    //
    String message = "Ќедостаточно данных дл€ создани€ учетной записи";
    CourierApi courierApi = new CourierApi();
    CreateUser firstUser = new CreateUser("kull001","12345","firstUser");
    CreateUser userWithoutLogin = new CreateUser("", "12345","firstUser");
    CreateUser userWithoutPassword = new CreateUser("kull001", "","firstUser");
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }


    @Test
    public void possibleCreateCourierTest(){
    courierApi.createCourier(firstUser)
                .then().statusCode(SC_CREATED);


    }

    @Test
    public void notPossibleCreateExistingCourierTest(){
        courierApi.createCourier(firstUser);
        courierApi.createCourier(firstUser)
                .then().statusCode(SC_CONFLICT);
    }

    @Test
    public void createUserWithoutFieldLoginImpossibleReturn400Test() {
                courierApi.createCourier(userWithoutLogin)
                .then().statusCode(SC_BAD_REQUEST);

    }
    @Test
    public void createUserWithoutFieldPasswordImpossibleReturn400Test() {
        courierApi.createCourier(userWithoutPassword)
                .then().statusCode(SC_BAD_REQUEST);

    }

    @Test
    public void successfulRequestReturnOkTrueTest(){
        courierApi.createCourier(firstUser)
                .then().assertThat().body("ok",equalTo(true));


    }

    @Test
    public void createUserWithoutFieldLoginImpossibleReturnMessageTest() {
        courierApi.createCourier(userWithoutLogin)
                .then().assertThat().body("message", equalTo(message));

    }
    @Test
    public void createUserWithoutFieldPasswordImpossibleReturnMessageTest() {
        courierApi.createCourier(userWithoutPassword)
                .then().assertThat().body("message", equalTo(message));

    }


 @After
    public void delUser(){

        courierApi.deleteUser();


 }

}
