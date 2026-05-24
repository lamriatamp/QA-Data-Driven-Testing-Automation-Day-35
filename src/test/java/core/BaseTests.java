package core;


import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;


public class BaseTests {


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
    }


}
