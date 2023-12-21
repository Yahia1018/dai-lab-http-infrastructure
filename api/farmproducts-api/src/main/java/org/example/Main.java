package org.example;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        try (var app = Javalin.create(/*config*/)
                .routes(() -> {
                    path("users", () -> {
                       get(ProductController::getAllVegetables);
                    });
                })
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070)) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}