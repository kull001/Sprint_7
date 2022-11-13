import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    String message = "Учетная запись не найдена";
    String message2 = "Недостаточно данных для входа";
    CreateUser firstUser = new CreateUser("kull001","12345","firstUser");
    CreateUser invalidUser1 = new CreateUser("kull0012","12345","firstUser");
    CreateUser invalidUser2 = new CreateUser("kull001","123456","firstUser");
    CreateUser userWithoutLogin = new CreateUser("", "12345","firstUser");
    CreateUser userWithoutPassword = new CreateUser("kull001", "","firstUser");
    CourierApi courierApi = new CourierApi();
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courierApi.createCourier(firstUser);


    }

    @Test
    public void successLoginTest(){
        courierApi.login(firstUser)
                .then().statusCode(SC_OK);
    }

    @Test
    public void loginUserWithoutFieldLoginImpossibleReturnError400Test(){
        courierApi.login(userWithoutLogin)
                .then().statusCode(SC_BAD_REQUEST);
    }
    @Test
    public void loginUserWithoutFieldPasswordImpossibleReturnError400Test() {
        courierApi.login(userWithoutPassword)
                .then().statusCode(SC_BAD_REQUEST);

    }
    @Test
    public void loginUserWithInvalidLoginImpossibleReturnMessageTest(){
        courierApi.login(invalidUser1)
                .then().assertThat().body("message", equalTo(message));
    }
    @Test
    public void loginUserWithInvalidPasswordImpossibleReturnMessageTest(){
        courierApi.login(invalidUser2)
                .then().assertThat().body("message", equalTo(message));
    }
    @Test
    public void loginUserWithoutFieldLoginImpossibleReturnMessageTest(){
        courierApi.login(userWithoutLogin)
                .then().assertThat().body("message", equalTo(message2));
    }
    @Test
    public void loginUserWithoutFieldPasswordImpossibleReturnMessageTest() {
        courierApi.login(userWithoutPassword)
                .then().assertThat().body("message", equalTo(message2));


    }
    @Test
    public void successLoginReturnIdTest(){
        courierApi.login(firstUser)
                .then().assertThat().body("id", notNullValue());
    }


    @After
    public void delUser() {
        courierApi.deleteUser();
//
    }

}
