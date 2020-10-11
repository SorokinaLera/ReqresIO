import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OnlinerTest {

    @Test
    public void apiTest() {
        given()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency={curr}&type=nbrb", "USD")
                .then()
                .log().body()
                .statusCode(200)
                .body("amount", equalTo("2,6205"))
                .body("grow", equalTo(-1));

    }

    @Test
    public void postTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "\"login\":\"1515\",\n" +
                        "\"password\":\"1515\"\n" +
                        "}")
                .when()
                .post("https://www.onliner.by/sdapi/user.api/login")
                .then()
                .log().body()
                .body("errors[0].message", equalTo("Неверный логин или пароль"))
                .extract().response();
        System.out.println(response.getBody().asString());
    }
}
