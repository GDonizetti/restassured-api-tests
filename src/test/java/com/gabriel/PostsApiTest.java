package com.gabriel;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostsApiTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    void testGetPostReturns200() {
        given()
            .when()
            .get(BASE_URL + "/posts/1")
            .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("userId", equalTo(1))
            .body("title", notNullValue());
    }

    @Test
    void testGetAllPostsReturnsList() {
        given()
            .when()
            .get(BASE_URL + "/posts")
            .then()
            .statusCode(200)
            .body("size()", equalTo(100));
    }

    @Test
    void testCreatePost() {
        String body = """
                {
                    "title": "Teste Gabriel",
                    "body": "Conteúdo do post",
                    "userId": 1
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when()
            .post(BASE_URL + "/posts")
            .then()
            .statusCode(201)
            .body("title", equalTo("Teste Gabriel"))
            .body("id", notNullValue());
    }

    @Test
    void testUpdatePost() {
        String body = """
                {
                    "id": 1,
                    "title": "Título Atualizado",
                    "body": "Corpo atualizado",
                    "userId": 1
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when()
            .put(BASE_URL + "/posts/1")
            .then()
            .statusCode(200)
            .body("title", equalTo("Título Atualizado"));
    }

    @Test
    void testDeletePost() {
        given()
            .when()
            .delete(BASE_URL + "/posts/1")
            .then()
            .statusCode(200);
    }
}