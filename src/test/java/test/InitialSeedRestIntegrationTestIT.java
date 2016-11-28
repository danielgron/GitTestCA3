package test;

import entityconnection.EntityConnector;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import test.utils.EmbeddedTomcat;

public class InitialSeedRestIntegrationTestIT {

    private static final int SERVER_PORT = 9999;
    private static final String APP_CONTEXT = "/vagtmanager";
    private static EmbeddedTomcat tomcat;
    private String insertUserData = "{\"department\":{\"nameOfDepartment\":\"København\"},\"userName\":\"Testmand@gmail.com\",\"firstName\":\"gklwlægkw\",\"lastName\":\"klægwklæq\",\"adresse\":\"gwælkl\",\"zip\":\"ægwqlkæ\",\"city\":\"lkægwe\",\"phone\":\"klæwglkæw\",\"redCroosLevel\":\"wgæklwglkæ\",\"medicalLevel\":\"wglækwg\",\"driverLevel\":\"wgælwklwkæg\"}";

    public InitialSeedRestIntegrationTestIT() {
    }
    private static String securityToken;

    //Utility method to login and set the securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        System.out.println(json);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/api/login")
                .then()
                .extract().path("token");
        System.out.println("Token: " + securityToken);

    }

    private void logOut() {
        securityToken = null;
    }

    @BeforeClass
    public static void setUpBeforeAll() throws ServletException, MalformedURLException, LifecycleException {
        EntityConnector.setPersistenceUnit("pu_test");
        tomcat = new EmbeddedTomcat();
        tomcat.start(SERVER_PORT, APP_CONTEXT);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = SERVER_PORT;
        RestAssured.basePath = APP_CONTEXT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterClass
    public static void after() throws ServletException, MalformedURLException, LifecycleException {
        tomcat.stop();
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/demoall").then()
                .statusCode(200)
                .body("message", equalTo("result for all"));
    }

    @Test
    public void tesRestForAdmin() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + securityToken)
                .when()
                .get("/api/demoadmin").then()
                .statusCode(200)
                .body("message", equalTo("REST call accesible by only authenticated ADMINS"))
                .body("serverTime", notNullValue());
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/api/demouser").then()
                .statusCode(401)
                .body("error.message", equalTo("No authorization header provided"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/api/demoadmin").then()
                .statusCode(401)
                .body("error.message", equalTo("No authorization header provided"));

    }

    @Test
    public void coordinatorCreateUserTest() {
        login("coordinator", "test");
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + securityToken)
                .body(insertUserData)
                .when()
                .post("/api/coordinator").then()
                .statusCode(200); // Succes call
    }

    @Test
    public void createUserAsNonCoordinator() {
        login("sam", "test");
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + securityToken)
                .body("{\"jsonExample\":\"dummieJSON\"}")
                .when()
                .post("/api/coordinator").then()
                .statusCode(403); // Means that we are not authorized to preform this request
    }

    @Test
    public void provokeJSONParsingException() {
        String jsonToFail = "\"{\"title\":\"unavail\",\"samarit\":{\"userName\":\"coordinator\"},\"start\":\"2016-018415-08\",\"hehe\":ture,\"color\":\"red\"}\"";
        /*
      This JSON Doesn't fit to the JSON needed on the
      server, and the server should then respond with a JSON error
         */
        login("coordinator", "test");
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + securityToken)
                .body(jsonToFail)
                .when()
                .post("/api/watch/1")
                .then()
                .body("error.code", equalTo(500),
                        "error.message", isA(String.class));
    }

}
