package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import domain.Customer;
import domain.Waiter;
import domain.Chef;
import javafx.util.Duration; // Cambiado a javafx.util.Duration
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del juego que extiende GameApplication de FXGL
 * Esta clase maneja la inicialización y el ciclo principal del juego
 */
public class RestaurantGame extends GameApplication {
    // Configuración del restaurante
    private static final int RESTAURANT_CAPACITY = 20; // Número total de mesas
    private static final int NUM_WAITERS = 2; // 10% de la capacidad
    private static final int NUM_CHEFS = 3; // 15% de la capacidad

    // Listas para mantener el estado del restaurante
    private List<Entity> tables;
    private List<Waiter> waiters;
    private List<Chef> chefs;
    private List<Customer> waitingCustomers;

    @Override
    protected void initSettings(GameSettings settings) {
        // Configuración básica de la ventana del juego
        settings.setWidth(1024);
        settings.setHeight(768);
        settings.setTitle("Restaurant Simulator");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        // Inicializar las listas
        tables = new ArrayList<>();
        waiters = new ArrayList<>();
        chefs = new ArrayList<>();
        waitingCustomers = new ArrayList<>();

        // Inicializar el factory para crear entidades del juego
        GameFactory gameFactory = new GameFactory();
        FXGL.setLevelFromMap("restaurant.tmx"); // Mapa del restaurante (lo crearemos después)

        // Crear mesas
        for (int i = 0; i < RESTAURANT_CAPACITY; i++) {
            Entity table = gameFactory.createTable(100 + (i % 5) * 150, 100 + (i / 5) * 150);
            tables.add(table);
        }

        // Crear meseros
        for (int i = 0; i < NUM_WAITERS; i++) {
            waiters.add(new Waiter(i));
        }

        // Crear cocineros
        for (int i = 0; i < NUM_CHEFS; i++) {
            chefs.add(new Chef(i));
        }

        // Iniciar el generador de clientes
        startCustomerGenerator();
    }

    /**
     * Método que genera nuevos clientes usando distribución de Poisson
     */
    private void startCustomerGenerator() {
        FXGL.run(() -> {
            // Generar un nuevo cliente cada cierto tiempo según distribución de Poisson
            if (waitingCustomers.size() < RESTAURANT_CAPACITY * 2) {
                Customer newCustomer = new Customer(waitingCustomers.size());
                waitingCustomers.add(newCustomer);
                // Crear la entidad visual del cliente
                GameFactory.createCustomerEntity(newCustomer);
            }
        }, Duration.seconds(getPoissonRandomTime())); // Cambiado a Duration.seconds
    }

    /**
     * Genera un tiempo aleatorio siguiendo una distribución de Poisson
     */
    private double getPoissonRandomTime() {
        double lambda = 2.0; // Promedio de clientes por minuto
        return -Math.log(1.0 - Math.random()) / lambda;
    }

    public static void main(String[] args) {
        launch(args);
    }
}