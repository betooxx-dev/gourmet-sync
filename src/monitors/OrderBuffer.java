package monitors;

import domain.Order;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Monitor para manejar el buffer de órdenes entre meseros y cocineros
 */
public class OrderBuffer {
    private final Queue<Order> pendingOrders; // Cola de órdenes pendientes
    private final Queue<Order> completedOrders; // Cola de órdenes completadas

    public OrderBuffer() {
        this.pendingOrders = new LinkedList<>();
        this.completedOrders = new LinkedList<>();
    }

    /**
     * Mesero añade una nueva orden al buffer
     * 
     * @param order Orden a añadir
     */
    public synchronized void addOrder(Order order) {
        pendingOrders.add(order);
        // Notificar a los cocineros que hay una nueva orden
        notifyAll();
    }

    /**
     * Cocinero toma una orden para preparar
     * 
     * @return Orden pendiente o null si no hay órdenes
     */
    public synchronized Order getOrderToCook() {
        while (pendingOrders.isEmpty()) {
            try {
                // Esperar si no hay órdenes pendientes
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return pendingOrders.poll();
    }

    /**
     * Cocinero marca una orden como completada
     * 
     * @param order Orden completada
     */
    public synchronized void completeOrder(Order order) {
        order.setState(Order.OrderState.READY);
        completedOrders.add(order);
        // Notificar a los meseros que hay una orden lista
        notifyAll();
    }

    /**
     * Mesero busca órdenes completadas para sus clientes
     * 
     * @return Orden completada o null si no hay órdenes listas
     */
    public synchronized Order getCompletedOrder() {
        return completedOrders.poll();
    }
}
