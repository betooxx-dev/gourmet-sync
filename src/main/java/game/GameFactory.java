package game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import domain.Customer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameFactory implements EntityFactory {
    @Spawns("table")
    public Entity createTable(double x, double y) {
        return FXGL.entityBuilder()
                .at(x, y)
                .type(GameType.TABLE)
                .viewWithBBox(new Rectangle(50, 50, Color.BROWN))
                .with("occupied", false)
                .buildAndAttach();
    }

    @Spawns("customer")
    public static Entity createCustomerEntity(Customer customer) {
        return FXGL.entityBuilder()
                .at(50, 50) // Posición inicial en el área de espera
                .type(GameType.CUSTOMER)
                .viewWithBBox(new Rectangle(30, 30, Color.BLUE))
                .with("customer", customer)
                .buildAndAttach();
    }

    @Spawns("waiter")
    public Entity createWaiter(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(GameType.WAITER)
                .viewWithBBox(new Rectangle(30, 30, Color.GREEN))
                .buildAndAttach();
    }

    @Spawns("chef")
    public Entity createChef(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(GameType.CHEF)
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .buildAndAttach();
    }
}