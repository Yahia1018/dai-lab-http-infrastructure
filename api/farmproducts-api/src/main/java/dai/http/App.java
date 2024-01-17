package dai.http;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        // ProductController
        ProductController productController = new ProductController();
        app.get("/api/products", productController::fetchAll);
        app.get("/api/products/{productId}", productController::fetchOne);
        app.post("/api/products", productController::save);
        app.patch("/api/products", productController::update);
        app.delete("/api/products/{productId}", productController::delete);
    }
}
