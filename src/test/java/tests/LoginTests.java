package tests;


import auth.LoginBody;
import core.BaseTests;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Utils;


import java.io.FileWriter;
import java.io.IOException;


import static io.restassured.RestAssured.given;


public class LoginTests extends BaseTests {


    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return Utils.getTestData("src/test/resources/data/ApiDataTest.xlsx", "User-Data");
    }


    @Test(dataProvider = "loginData")
    public void loginDataDriven(String usernameOrEmail, String password, String expected) {
        JSONObject body = new JSONObject();
        body.put("usernameOrEmail", usernameOrEmail);
        body.put("password", password);


        Response response = given()
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .post("/api/rest/login")
                .then()
                .extract().response();


        int statusCode = response.getStatusCode();
        System.out.println("=== Login Data Driven Test ===");
        System.out.println("User: " + usernameOrEmail);
        System.out.println("Expected: " + expected + " | Status Code: " + statusCode);
        System.out.println("Response: " + response.asString());


        if ("success".equalsIgnoreCase(expected)) {
            Assert.assertEquals(statusCode, 200);
            String token = response.jsonPath().getString("token");
            Assert.assertNotNull(token, "Token should not be null");
            Assert.assertFalse(token.isEmpty(), "Token should not be empty");
            System.out.println("✅ TEST PASSED - Login SUCCESS for user: " + usernameOrEmail);
        } else {
            Assert.assertNotEquals(statusCode, 200);
            System.out.println("✅ TEST PASSED - Login FAILED as expected for user: " + usernameOrEmail + " (Status: " + statusCode + ")");
        }
        System.out.println("==============================");
    }


    @Test
    public void login() throws IOException {
        LoginBody loginBody = new LoginBody();


        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginBody.loginValidData().toString())
                .when()
                .post("/api/rest/login")
                .then()
                .extract().response();


        System.out.println("=== Login Test ===");
        System.out.println("Response: " + response.asString());


        Assert.assertEquals(response.getStatusCode(), 200);


        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
        System.out.println("✅ TEST PASSED - Login SUCCESS");
        System.out.println("Token: " + token);


        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", token);


        try (FileWriter file = new FileWriter("src/test/resources/json/token.json")) {
            file.write(tokenJson.toString(4));
            file.flush();
        }


        System.out.println("Token berhasil disimpan di src/test/resources/json/token.json");
        System.out.println("==================");
    }
}
