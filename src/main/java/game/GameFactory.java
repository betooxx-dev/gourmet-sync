package game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import domain.Customer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static com.almasb.fxgl.dsl.FXGL.texture;

public class GameFactory implements EntityFactory {
    @Spawns("table")
public Entity createTable(double x, double y) {
   var tableTexture = texture("table.png").copy();
   tableTexture.resize(30, 30);  // Reducido a 30x30 píxeles
   
   return FXGL.entityBuilder()
       .type(GameType.TABLE)
       .at(x, y)
       .viewWithBBox(tableTexture)
       .buildAndAttach();

    }
 

    @Spawns("customer")
    public static Entity createCustomerEntity(Customer customer) {
        return FXGL.entityBuilder()
                .at(65, 185) // Posición inicial en el área de espera
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