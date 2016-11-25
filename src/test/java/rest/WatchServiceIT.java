/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entityconnection.EntityConnector;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import test.utils.EmbeddedTomcat;

/**
 *
 * @author dennisschmock
 */
public class WatchServiceIT {

    private static final int SERVER_PORT = 9999;
    private static final String APP_CONTEXT = "/vagtmanager";
    private static EmbeddedTomcat tomcat;
    private static String securityToken;

    public WatchServiceIT() {

    }

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

    @BeforeClass
    public static void setUpClass() throws ServletException, MalformedURLException, LifecycleException {
        EntityConnector.setPersistenceUnit("pu_test");
        tomcat = new EmbeddedTomcat();
        tomcat.start(SERVER_PORT, APP_CONTEXT);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = SERVER_PORT;
        RestAssured.basePath = APP_CONTEXT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSetWatch() throws Exception {
    }

    @Test
    public void testGetWatchesForSamarit_String() throws Exception {
    }

    @Test
    public void testGetWatchesForSamarit_String_String() throws Exception {
    }

}
