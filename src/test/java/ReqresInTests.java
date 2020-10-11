import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresInTests {

    String URL = "https://reqres.in";

    @Test
    public void listUsersTest() {

        given()
                .when()
                .get(URL + "/api/users?page=2")
                .then()
                .log().body()
                .body("data[1].id", equalTo(8))
                .statusCode(200);
    }

    @Test
    public void singleUserTest() {

        given()
                .when()
                .get(URL + "/api/users/2")
                .then()
                .log().body()
                .body("ad.company", equalTo("StatusCode Weekly"))
                .statusCode(200);
    }

    @Test
    public void singleUserNotFoundTest() {

        given()
                .when()
                .get(URL + "/api/users/23")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void statusCodeTest3() {

        given()
                .when()
                .get(URL + "/api/users?page=2")
                .then()
                .log().body()
                .body("total_pages",equalTo(2))
                .body("ad.text",equalTo("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things."))
                .statusCode(200);
    }

    @Test
    public void createTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"name\": \"lola\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post(URL + "/api/users")
                .then()
                .log().body()
                .body("name", equalTo("lola"))
                .extract().response();
    }

    @Test
    public void loginSuccessfulTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{ \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"}")
                .when()
                .post(URL + "/api/register")
                .then()
                .log().body()
                .body("id",equalTo(4))
                .body("token",equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200)
                .extract().response();

    }

    @Test
    public void deleteTest(){
        given()
                .delete(URL + "/api/users/2")
                .then()
                .log().body()
                .statusCode(204);

    }

    @Test
    public void updatePatchTest(){
        given()
                .patch(URL + "/api/users/2")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test
    public void updatePutTest(){
        given()
                .header(HTTP.CONTENT_TYPE,"application/json")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .put(URL + "/api/users/2")
                .then()
                .log().body()
                .body("job",equalTo("zion resident"))
                .statusCode(200);

    }

    @Test
    public void loginUnsuccessfulTest(){

        given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"email\": \"peter@klaven\"\n" +
                        "}")
                .when()
                .post(URL + "/api/login")
                .then()
                .log().body()
                .body("error",equalTo("Missing password"))
                .statusCode(400);
    }
}
