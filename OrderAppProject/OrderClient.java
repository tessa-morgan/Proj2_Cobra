import OrderManagementApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderClient {
    public static void main(String[] args) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);
            
            // Get the Naming Service reference
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            // Resolve the remote OrderService object
            String name = "OrderService";
            OrderService orderService = OrderServiceHelper.narrow(ncRef.resolve_str(name));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Welcome to the Distributed Order Management System");
            System.out.println("Select your role:");
            System.out.println("1. Customer");
            System.out.println("2. Manager");
            System.out.print("Enter your choice: ");

            int role = 0;
            boolean validRole = false;
            while (!validRole) {
                try {
                    role = Integer.parseInt(br.readLine().trim());
                    validRole = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            // Customer role
            if (role == 1) { 
                boolean done = false;
                while (!done) {
                    System.out.println("\nCustomer Options:");
                    System.out.println("1. View Menu");
                    System.out.println("2. Place Order");
                    System.out.println("3. Check Order Status");
                    System.out.println("4. Quit");
                    System.out.print("Enter your choice: ");
                    int choice = Integer.parseInt(br.readLine().trim());
                    switch(choice) {
                        case 1:
                            System.out.println("Menu: " + orderService.getMenu());
                            break;
                        case 2:
                            System.out.print("Enter username: ");
                            String username = br.readLine();
                            System.out.print("Enter quantity of Fried Chicken: ");
                            int chicken = Integer.parseInt(br.readLine().trim());
                            System.out.print("Enter quantity of Cola: ");
                            int cola = Integer.parseInt(br.readLine().trim());
                            System.out.println(orderService.placeOrder(username, chicken, cola));
                            break;
                        case 3:
                            System.out.print("Enter username: ");
                            String user = br.readLine();
                            try {
                                System.out.println("Order Status: " + orderService.checkOrderStatus(user));
                            } 
                            catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            break;
                        case 4:
                            done = true;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } 
            
            // Manager Role
            else if (role == 2) { 
                boolean done = false;
                boolean connectSuccess = !orderService.managerLogin();
                if (connectSuccess) {
                    while (!done) {
                        System.out.println("\nManager Options:");
                        System.out.println("1. View Current Orders");
                        System.out.println("2. Quit");
                        System.out.print("Enter your choice: ");
                        
                        int choice = 0;
                        boolean valid = false;
                        while (!valid) {
                            try {
                                choice = Integer.parseInt(br.readLine().trim());
                                valid = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }

                        switch(choice) {
                            case 1:
                                System.out.println("Current Orders: \n" + orderService.viewCurrentOrders());
                                break;
                            case 2:
                                done = true;
                                orderService.managerDisconnect();
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                }
                else {
                    System.out.println("Manager already connected. Please try later.");
                }
                
            } 
            else {
                System.out.println("Invalid role selected.");
            }
            orb.destroy();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}