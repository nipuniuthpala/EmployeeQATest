import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class EmployeeTest {

    @BeforeClass
    public static void setup() {
        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/api/v1/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://dummy.restapiexample.com";
        }
        RestAssured.baseURI = baseHost;
    }

}

