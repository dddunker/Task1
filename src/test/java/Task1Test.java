import org.testng.annotations.Test;

import java.io.File;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.*;

public class Task1Test {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    private static final int USER_ID = 1;
    private static final int ID = 1;
    private static final String TITLE = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
    private static final String BODY = "quia et suscipit\nsuscipit recusandae consequuntur expedita et " +
            "cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";
    private static final int MAX_POST_ID = 100;

    @Test
    public void getRequest() {
        get(BASE_URL + "/posts/1").
        then().
                assertThat().
                body("userId", equalTo(USER_ID)).
                body("id", equalTo(ID)).
                body("title", equalTo(TITLE)).
                body("body", equalTo(BODY)).
                statusCode(200);
    }

    @Test
    public void postRequest() {
        given().
                contentType("application/json; charset=UTF-16").
                body("{\"userId\":101, \"title\": \"qwe\", \"body\":\"rty\"}").
        when().
                post(BASE_URL + "/posts").
        then().
                assertThat().
                body("userId", equalTo(101)).
                body("title", equalTo("qwe")).
                body("body", equalTo("rty")).
                body("id", equalTo(MAX_POST_ID + 1)).
                statusCode(200);
    }

    @Test
    public void deleteRequest() {
        expect().
                body(containsString("{}")).
                statusCode(200).
        when().
                delete(BASE_URL + "/posts/1");
    }

    @Test
    public void putRequest() {
        given().
                contentType("application/json; charset=UTF-16").
                body("{\"userId\":101, \"title\": \"qwe\", \"body\":\"rty\"}").
        when().
                put(BASE_URL + "/posts/1").
        then().
                body("userId", equalTo(101)).
                body("title", equalTo("qwe")).
                body("body", equalTo("rty")).
                body("id", equalTo(1)).
                statusCode(200);
    }

    @Test
    public void schemaValidation() {
        get(BASE_URL + "/posts/1").
        then().
                assertThat().
                body(matchesJsonSchema(new File("src/main/resources/schema.json"))).
                statusCode(200);
    }

}
