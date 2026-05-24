package tests;


import core.BaseTests;
import tickets.CreateTicketBody;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import static io.restassured.RestAssured.given;


public class CreateTicketTests extends BaseTests {


    private String token;


    @BeforeClass
    public void loadToken() throws Exception {
        FileReader reader = new FileReader("src/test/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();
    }


    @Test
    public void createTicket() throws IOException {
        CreateTicketBody bodyObj = new CreateTicketBody();
        String title = Utils.generateRandomTitle();
        String description = Utils.generateRandomDescription();
        String attachment = "https://example.com/attachment.png";


        JSONObject requestBody = bodyObj.getBodyCreateTickets(title, description, attachment, true);


        Response response = given()
                .cookie("__Secure-next-auth.session-token", token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/rest/createTicket")
                .then()
                .extract().response();


        System.out.println("=== Create Ticket Test ===");
        System.out.println("Response: " + response.asString());


        Assert.assertEquals(response.getStatusCode(), 200);


        String ticketId = response.jsonPath().getString("id");
        System.out.println("✅ TEST PASSED - Ticket Successfully Created");
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("==========================");
        System.out.println("Ticket ID: " + ticketId);


        JSONObject tiketJson = new JSONObject();
        tiketJson.put("ticket_id", ticketId);


        try (FileWriter file = new FileWriter("src/test/resources/json/ticket_id.json")) {
            file.write(tiketJson.toString(4));
            file.flush();
        }
    }
}
