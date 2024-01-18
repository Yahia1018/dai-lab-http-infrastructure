package dai.http;

import dai.database.Product;

import io.javalin.http.Context;

import java.util.concurrent.ConcurrentHashMap;

public class ProductController {
    private static final ConcurrentHashMap<Integer, Product> products = new ConcurrentHashMap<>();

    ProductController() {
        Product apple = new Product("Apple", 3.6, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Apple.jpg");
        products.put(apple.id(), apple);

        Product banana = new Product("Banana", 3.0, true, "Ecuador", "api/farmproducts-api/src/main/java/dai/ressources/Banana.jpg");
        products.put(banana.id(), banana);

        Product lemon = new Product("Lemon", 1.7, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Lemon.jpg");
        products.put(lemon.id(), lemon);

        Product strawberry = new Product("Strawberry", 5.6, false, "Italy", "api/farmproducts-api/src/main/java/dai/ressources/Strawberry.jpg");
        products.put(strawberry.id(), strawberry);

        Product orange = new Product("Orange", 2.5, true, "Spain", "api/farmproducts-api/src/main/java/dai/ressources/Orange.jpg");
        products.put(orange.id(), orange);

        Product tomato = new Product("Tomato", 3.6, false, "Spain", "api/farmproducts-api/src/main/java/dai/ressources/Tomato.jpg");
        products.put(tomato.id(), tomato);

        Product potato = new Product("Potato", 3.0, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Potato.jpg");
        products.put(potato.id(), potato);

        Product carrot = new Product("Carrot", 3.2, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Carrot.jpg");
        products.put(carrot.id(), carrot);

        Product onion = new Product("Onion", 4.8, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Onion.jpg");
        products.put(onion.id(), onion);

        Product pumpkin = new Product("Pumpkin", 3.6, false, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Pumpkin.jpg");
        products.put(pumpkin.id(), pumpkin);

        Product brocoli = new Product("Brocoli", 4.5, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Brocoli.jpg");
        products.put(brocoli.id(), brocoli);

        Product cauliflower = new Product("Cauliflower", 5.0, true, "Switzerland", "api/farmproducts-api/src/main/java/dai/ressources/Cauliflower.jpg");
        products.put(cauliflower.id(), cauliflower);

        Product spinach = new Product("Spinach", 2.4, false, "Italy", "api/farmproducts-api/src/main/java/dai/ressources/Spinach.jpg");
        products.put(spinach.id(), spinach);

        Product parisMushroom = new Product("Paris Mushroom", 2.0, false, "Netherlands", "api/farmproducts-api/src/main/java/dai/ressources/ParisMushroom.jpg");
        products.put(parisMushroom.id(), parisMushroom);

        Product grape = new Product("Grape", 2.65, false, "South Africa", "api/farmproducts-api/src/main/java/dai/ressources/Grapes.jpg");
        products.put(grape.id(), grape);
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
        products.put(product.id(), product);

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
