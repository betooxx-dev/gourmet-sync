package game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import domain.Customer;
/**
 * Fábrica para crear entidades visuales del juego
 * Esta clase maneja la creación de todos los elementos visuales
 */
public class GameFactory implements EntityFactory {

    /**
     * Crea una mesa en la posición especificada
     */
    @Spawns("table")
    public Entity createTable(double x, double y) {
        return FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(new Rectangle(40, 40, Color.BROWN))
                .with("occupied", false)
                .buildAndAttach();
    }

    /**
     * Crea la representación visual de un cliente
     */
    @Spawns("customer")
    public static Entity createCustomerEntity(Customer customer) {
        return FXGL.entityBuilder()
                .at(0, 0) // Posición inicial (entrada del restaurante)
                .viewWithBBox(new Rectangle(30, 30, Color.BLUE))
                .with("customer", customer)
                .buildAndAttach();
    }

    /**
     * Crea la representación visual de un mesero
     */
    @Spawns("waiter")
    public Entity createWaiter(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox(new Rectangle(30, 30, Color.GREEN))
                .buildAndAttach();
    }

    /**
     * Crea la representación visual de un cocinero
     */
    @Spawns("chef")
    public Entity createChef(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .buildAndAttach();
    }
}
