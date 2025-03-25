import OrderManagementApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;
import java.util.HashMap;

public class OrderServer {
    public static void main(String args[]) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);
            
            // Get reference to RootPOA and activate the POAManager
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("RootPOA");
            POA rootpoa = POAHelper.narrow(objRef);
            rootpoa.the_POAManager().activate();

            // Create and register the servant
            OrderServiceImpl orderImpl = new OrderServiceImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(orderImpl);
            OrderService orderService = OrderServiceHelper.narrow(ref);

            // Bind the object reference in the Naming Service
            org.omg.CORBA.Object objRefNS = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRefNS);
            String name = "OrderService";
            ncRef.rebind(ncRef.to_name(name), orderService);

            System.out.println("OrderService Server ready and waiting...");
            orb.run();
        } 
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}

// Server implementation class that extends the generated POA skeleton.
class OrderServiceImpl extends OrderServicePOA {
    
    // Use a HashMap to store orders with username as the key, using the generated Order struct.
    private static HashMap<String, Order> orders = new HashMap<>();
    
    private static boolean managerActive = false;

    /**
     * Returns the menu as a string
     */
    public String getMenu() {
        return "Menu: Fried Chicken - $5, Cola - $1";
    }

    /**
     * Creates and stores a new order based on the provided arguments.
     * Overwrites any existing order with the same username
     * Returns a string confirming the order.
     */
    public String placeOrder(String username, int chickenQuantity, int colaQuantity) {
        
        // Create a new Order 
        double price = (chickenQuantity * 5) + (colaQuantity * 1);
        Order order = new Order(username, chickenQuantity, colaQuantity, price);

        // Store in the hash table
        orders.put(username, order);

        // Return confirmation string
        return "Order placed for " + username + ". Total: $" + order.totalPrice;
    }

    /**
     * Returns a string of information about the order places with the given username.
     * If no such order exists, no error is thrown but the user is notified.
     */
    public String checkOrderStatus(String username) {
        // Fetch order
        Order order = orders.get(username);
        
        // Check if the order exists and return corresponding information
        if (order == null) {
            return "Error: Order not found";
        }
        return "Order for " + order.username + ": Fried Chicken x" + order.chickenQuantity + 
               ", Cola x" + order.colaQuantity + ", Total: $" + order.totalPrice;
    }

    /**
     * Returns information about all current orders.
     * If no orders exist, returns to the user "No current orders"
     */
    public String viewCurrentOrders() {
        if (orders.isEmpty()) {
            return "No current orders.";
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (Order order : orders.values()) {
            sb.append("\tOrder for ").append(order.username)
              .append(": Fried Chicken x").append(order.chickenQuantity)
              .append(", Cola x").append(order.colaQuantity)
              .append(", Total: $").append(order.totalPrice)
              .append("\n");
        }
        return sb.toString();
    }

    public boolean managerLogin() {
      boolean res = managerActive;
      
      if (!managerActive) {
        managerActive = true;
      }

      return res;
    }

    public void managerDisconnect() {
      managerActive = false;
    }
}