import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CreateUser firstUser;
    String message = "Учетная запись не найдена";
    String message2 = "Недостаточно данных для входа";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        firstUser = new CreateUser("kull001","12345","firstUser");
        given()
                .header("Content-type", "application/json")
                .body(firstUser)
                .post("/api/v1/courier");

    }

    @Test
    public void SuccessLoginTest(){

        given()
                .header("Content-type", "application/json")
                .body(firstUser)
                .post("/api/v1/courier/login")
                .then().statusCode(200);
    }

    @Test
    public void LoginUserWithoutFieldLoginImpossibleReturnError400Test(){
        String json = "{\"login\":,\"password\":\"12345\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400);
    }
    @Test
    public void LoginUserWithoutFieldPasswordImpossibleReturnError400Test() {
        String json = "{\"login\":\"kull001\",\"password\":}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400);

    }
    @Test
    public void LoginUserWithInvalidLoginImpossibleReturnMessageTest(){
        String json = "{\"login\":\"kull002\",\"password\":\"12345\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo(message));
    }
    @Test
    public void LoginUserWithInvalidPasswordImpossibleReturnMessageTest(){
        String json = "{\"login\":\"kull001\",\"password\":\"11111\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo(message));
    }
    @Test
    public void LoginUserWithoutFieldLoginImpossibleReturnMessageTest(){
        String json = "{\"login\":\"\",\"password\":\"12345\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo(message2));
    }
    @Test
    public void LoginUserWithoutFieldPasswordImpossibleReturnMessageTest() {
        String json = "{\"login\":\"kull001\",\"password\": \"\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().body("message", equalTo(message2));


    }
    @Test
    public void SuccessLoginReturnIdTest(){
        given()
                .header("Content-type", "application/json")
                .body(firstUser)
                .post("/api/v1/courier/login")
                .then().assertThat().body("id", notNullValue());
    }





    @After
    public void DelUser(){

        GetId firstUserId = given()
                .header("Content-type", "application/json")
                .body(firstUser)
                .post("/api/v1/courier/login")
                .body().as(GetId.class);

        given()
                .header("Content-type", "application/json")
                .delete("api/v1/courier/"+ firstUserId.getId());

    }
}
