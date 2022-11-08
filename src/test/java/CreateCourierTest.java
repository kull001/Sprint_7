import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.NotNull;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class CreateCourierTest {
    CreateUser firstUser;
    String message = "Ќедостаточно данных дл€ создани€ учетной записи";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        firstUser = new CreateUser("kull001","12345","firstUser");

    }


    @Test
    public void PossibleCreateCourierTest(){
                given()
                .header("Content-type", "application/json")
                .and()
                .body(firstUser)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);


    }

    @Test
    public void NotPossibleCreateExistingCourierTest(){
                given()
                .header("Content-type", "application/json")
                .and()
                .body(firstUser)
                .when()
                .post("/api/v1/courier");
                //.then().statusCode(201);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(firstUser)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409);
    }

    @Test
    public void CreateUserWithoutFieldLoginImpossibleReturn400Test() {
        String json = "{\"password\":\"12345\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);

    }
    @Test
    public void CreateUserWithoutFieldPasswordImpossibleReturn400Test() {
        String json = "{\"login\":\"kull001\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);

    }

    @Test
    public void SuccessfulRequestReturnOkTrueTest(){
                given()
                .header("Content-type", "application/json")
                .and()
                .body(firstUser)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().body("ok",equalTo(true));


    }

    @Test
    public void CreateUserWithoutFieldLoginImpossibleReturnMessageTest() {
        String json = "{\"password\":\"12345\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().body("message", equalTo(message));

    }
    @Test
    public void CreateUserWithoutFieldPasswordImpossibleReturnMessageTest() {
        String json = "{\"login\":\"kull001\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().body("message", equalTo(message));

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
