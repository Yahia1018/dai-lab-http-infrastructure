package dai.http;

import dai.database.Product;

import io.javalin.http.Context;

import java.util.concurrent.ConcurrentHashMap;

public class ProductController {
    private static final ConcurrentHashMap<Integer, Product> products = new ConcurrentHashMap<>();
    private static int serialId = 0;

    ProductController() {
        products.put(++serialId, new Product(serialId, "Apple", 3.6, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Apple.jpg"));
        products.put(++serialId, new Product(serialId, "Banana", 3.0, true, "Ecuador", "api/farmproducts-api/src/main/java/dai/ressources/Banana.jpg"));
        products.put(++serialId, new Product(serialId, "Lemon", 1.7, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Lemon.jpg"));
        products.put(++serialId, new Product(serialId, "Strawberry", 5.6, false, "Italy", "api/farmproducts-api/src/main/java/dai/ressources/Strawberry.jpg"));
        products.put(++serialId, new Product(serialId, "Orange", 2.5, true, "Spain", "api/farmproducts-api/src/main/java/dai/ressources/Orange.jpg"));
        products.put(++serialId, new Product(serialId, "Tomato", 3.6, false, "Spain", "api/farmproducts-api/src/main/java/dai/ressources/Tomato.jpg"));
        products.put(++serialId, new Product(serialId, "Potato", 3.0, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Potato.jpg"));
        products.put(++serialId, new Product(serialId, "Carrot", 3.2, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Carrot.jpg"));
        products.put(++serialId, new Product(serialId, "Onion", 4.8, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Cucumber.jpg"));
        products.put(++serialId, new Product(serialId, "Pumpkin", 3.6, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Pumpkin.jpg"));
        products.put(++serialId, new Product(serialId, "Brocoli", 4.5, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Brocoli.jpg"));
        products.put(++serialId, new Product(serialId, "Cauliflower", 5.0, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Cauliflower.jpg"));
        products.put(++serialId, new Product(serialId, "Spinach", 2.4, false, "Italy", "api/farmproducts-api/src/main/java/dai/ressources/Spinach.jpg"));
        products.put(++serialId, new Product(serialId, "Paris Mushroom", 2.0, false, "Netherlands", "api/farmproducts-api/src/main/java/dai/ressources/ParisMushroom.jpg"));
        products.put(++serialId, new Product(serialId, "Grape", 2.65, false, "South Africa", "api/farmproducts-api/src/main/java/dai/ressources/Grapes.jpg"));
    }

    public void fetchAll(Context ctx) {
        ctx.json(products.values());
    }

    public void fetchOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("productId"));
        Product product = products.get(id);

        if (product == null) {
            ctx.status(404);
            return;
        }

        ctx.json(product);
    }

    public void save(Context ctx) {
        System.out.println("Hello");
        Product product = ctx.bodyAsClass(Product.class);
        products.put(++serialId, product);

        ctx.json(product);
        ctx.status(201);
    }

    public void update(Context ctx) {
        Product product = ctx.bodyAsClass(Product.class);
        products.put(product.id(), product);

        ctx.json(product);
        ctx.status(200);
    }

    public void delete(Context ctx) {
        int productId = Integer.parseInt(ctx.pathParam("productId"));
        products.remove(productId);
        ctx.status(204);
    }
}
