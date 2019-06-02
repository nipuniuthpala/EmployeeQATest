import com.jayway.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;

public class EmployeeCreateTest extends EmployeeTest {

    @Test
    public void basicPingTest() {
        given().when().get("/employees").then().statusCode(200);
    }

    @Test
    public void createVerifyAndDeleteEmployee() throws Exception {

        Employee employee = new Employee();

        String name = "QATest-" + UUID.randomUUID();
        String age = "23";
        String salary = "55000";

        employee.setName(name);
        employee.setSalary(salary);
        employee.setAge(age);

        System.out.println("=== CREATE EMPLOYEE");
        JsonPath createResponseJsonPath = given()
                .contentType("application/json")
                .accept("text/html")
                .body(employee)
                .when().post("/create")
                .then().log().all()
                .extract()
                .jsonPath();

        assert name.equals(createResponseJsonPath.getString("name"));
        assert age.equals(createResponseJsonPath.getString("age"));
        assert salary.equals(createResponseJsonPath.getString("salary"));

        String id = createResponseJsonPath.getString("id");
        assert id != null;

        System.out.println("=== GET EMPLOYEE");

        JsonPath getResponseJsonPath = given().pathParam("ID", id)
                .when().get("/employee/{ID}").then().log().all()
                .and().extract().jsonPath();

        System.out.println(getResponseJsonPath.get("id"));

        assert name.equals(getResponseJsonPath.getString("employee_name"));
        assert age.equals(getResponseJsonPath.getString("employee_age"));
        assert salary.equals(getResponseJsonPath.getString("employee_salary"));
        assert getResponseJsonPath.getString("profile_image").isEmpty();

        System.out.println("=== DELETE EMPLOYEE");
        given().pathParam("ID", id).when().delete("/delete/{ID}").then()
                .log().all()
                .statusCode(200);

    }
}
