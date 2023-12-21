package org.example;

import java.util.Map;
import io.javalin.http.Context;
import java.util.*;

public class ProductController {
    public record Product(String name) {}

    private static final Map<String, Product> products = new HashMap<>();
    private static int productId = 0;

    static {
        var tempMap = Map.of(
          productId++, new Product("Apple"),
          productId++, new Product("Banana"),
          productId++, new Product("Lemon"),
          productId++, new Product("Strawberry")
        );
    }

    public static void getAllVegetables(Context ctx) {

    }
}
