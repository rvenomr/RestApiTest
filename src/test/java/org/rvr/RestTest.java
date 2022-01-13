package org.rvr;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void checkGetUser() throws ParseException {
        UserData user = get("/api/users/2").
                then().statusCode(200)
                .extract().body().jsonPath().getObject("data", UserData.class);
        Assert.assertNotNull("last_name is null", user.last_name);
        Assert.assertNotNull("id is null", user.id);
        Assert.assertNotNull("avatar is null", user.avatar);
        Assert.assertNotNull("first_name is null", user.first_name);
        Assert.assertNotNull("email is null", user.email);
    }

    @Test
    public void checkGetUserList() throws ParseException {
        List<UserData> users = get("/api/users?page=2").
                then().statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

        users.iterator().forEachRemaining(user -> {
            Assert.assertNotNull("last_name is null", user.last_name);
            Assert.assertNotNull("id is null", user.id);
            Assert.assertNotNull("avatar is null", user.avatar);
            Assert.assertNotNull("first_name is null", user.first_name);
            Assert.assertNotNull("email is null", user.email);
        });
    }

    @Test
    public void checkCreateUser() throws ParseException {
        String last_name = "Nickelson";
        String avatar = "avatar";
        String first_name = "Jack";
        String email = "example@mail.com";
        UserData user = new UserData(last_name, null, avatar, first_name, email);

        RestAssured.given().contentType(ContentType.JSON)
                .body(user)
                .post("/api/users/2")
                .then()
                .assertThat()
                .statusCode(201)
                .body("last_name", equalTo(user.last_name))
                .body("id", notNullValue())
                .body("avatar", equalTo(user.avatar))
                .body("first_name", equalTo(user.first_name))
                .body("email", equalTo(user.email))
                .body("createdAt", notNullValue());
    }
}