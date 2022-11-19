import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {
    CreateUser firstUser = new CreateUser("kull001","12345","firstUser");
    final static String COURIER_URI = "/api/v1/courier/";
    final static String LOGIN_URI = "/api/v1/courier/login";

    public Response createCourier(CreateUser user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(COURIER_URI);
    }


public void deleteUser(){

    GetId firstUserId = given()
            .header("Content-type", "application/json")
            .body(firstUser)
            .post(LOGIN_URI)
            .body().as(GetId.class);

    given()
            .header("Content-type", "application/json")
            .delete(COURIER_URI + firstUserId.getId());

}

    public Response login(CreateUser user){
        return  given()
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN_URI);
    }

}
