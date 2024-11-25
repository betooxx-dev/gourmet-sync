package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import domain.Customer;
import domain.Waiter;
import domain.Chef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class RestaurantGame extends GameApplication {
    private static final int RESTAURANT_CAPACITY = 20;
    private static final int NUM_WAITERS = 2;
    private static final int NUM_CHEFS = 3;
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;

    private List<Entity> tables;
    private List<Waiter> waiters;
    private List<Chef> chefs;
    private List<Customer> waitingCustomers;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WINDOW_WIDTH);
        settings.setHeight(WINDOW_HEIGHT);
        settings.setTitle("Restaurant Simulator");
        settings.setVersion("1.0");
        settings.setManualResizeEnabled(true);
        settings.setScaleAffectedOnResize(true);
        settings.setPreserveResizeRatio(true);
    }

    @Override
    protected void initGame() {
        initializeGameComponents();
        createBackground();
        createKitchenAndWaitingArea();
        createTables();
        createStaff();
        startCustomerGenerator();
    }

    private void initializeGameComponents() {
        tables = new ArrayList<>();
        waiters = new ArrayList<>();
        chefs = new ArrayList<>();
        waitingCustomers = new ArrayList<>();
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
    }

    private void createBackground() {
        FXGL.entityBuilder()
            .at(0, 0)
            .view("background.png")
            .buildAndAttach();
    }
    
    private void createKitchenAndWaitingArea() {
        // Área de cocina
        FXGL.entityBuilder()
                .at(WINDOW_WIDTH - 180, 250)
                .view(new Rectangle(150, 200, Color.rgb(255, 255, 224, 0.5)))
                .buildAndAttach();

        // Área de espera
        FXGL.entityBuilder()
                .at(50, 150)
                .view(new Rectangle(150, 200, Color.rgb(173, 216, 230, 0.5)))
                .buildAndAttach();
    }

    private void createTables() {
        GameFactory gameFactory = new GameFactory();
        int startX = 262;      // Posición horizontal (izquierda/derecha)
        int startY = 279;      // Posición vertical (arriba/abajo) - Aumentado de 150 a 400
        int spacing = 120;     // Espacio entre mesas
         for (int i = 0; i < RESTAURANT_CAPACITY; i++) {
            int row = i / 5;
            int col = i % 5;
            Entity table = gameFactory.createTable(
                    startX + col * spacing,
                    startY + row * spacing);
            tables.add(table);
        }
    }

    private void createStaff() {
        // Crear meseros
        for (int i = 0; i < NUM_WAITERS; i++) {
            waiters.add(new Waiter(i));
            FXGL.spawn("waiter", 100, 300 + i * 50);
        }

        // Crear cocineros
        for (int i = 0; i < NUM_CHEFS; i++) {
            chefs.add(new Chef(i));
            FXGL.spawn("chef", WINDOW_WIDTH - 150, 100 + i * 50);
        }
    }

    private void startCustomerGenerator() {
        FXGL.run(() -> {
            if (waitingCustomers.size() < RESTAURANT_CAPACITY * 2) {
                Customer newCustomer = new Customer(waitingCustomers.size());
                waitingCustomers.add(newCustomer);
                GameFactory.createCustomerEntity(newCustomer);
            }
        }, Duration.seconds(getPoissonRandomTime()));
    }

    private double getPoissonRandomTime() {
        double lambda = 2.0;
        return -Math.log(1.0 - Math.random()) / lambda;
    }

    public static void main(String[] args) {
        launch(args);
    }
}