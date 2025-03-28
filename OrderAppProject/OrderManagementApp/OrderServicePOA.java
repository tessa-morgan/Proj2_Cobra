package OrderManagementApp;


/**
* OrderManagementApp/OrderServicePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from OrderManagement.idl
*/

public abstract class OrderServicePOA extends org.omg.PortableServer.Servant
 implements OrderManagementApp.OrderServiceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getMenu", new java.lang.Integer (0));
    _methods.put ("placeOrder", new java.lang.Integer (1));
    _methods.put ("checkOrderStatus", new java.lang.Integer (2));
    _methods.put ("viewCurrentOrders", new java.lang.Integer (3));
    _methods.put ("managerLogin", new java.lang.Integer (4));
    _methods.put ("managerDisconnect", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  // For example: "Fried Chicken: $5, Cola: $1"
       case 0:  // OrderManagementApp/OrderService/getMenu
       {
         String $result = null;
         $result = this.getMenu ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }


  // Output: A confirmation message (e.g., "Order received: Total $X")
       case 1:  // OrderManagementApp/OrderService/placeOrder
       {
         String username = in.read_string ();
         int chickenQuantity = in.read_long ();
         int colaQuantity = in.read_long ();
         String $result = null;
         $result = this.placeOrder (username, chickenQuantity, colaQuantity);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }


  // Throws an error if no order exists for the username.
       case 2:  // OrderManagementApp/OrderService/checkOrderStatus
       {
         String username = in.read_string ();
         String $result = null;
         $result = this.checkOrderStatus (username);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }


  // For the manager role: returns all current orders as a formatted string.
       case 3:  // OrderManagementApp/OrderService/viewCurrentOrders
       {
         String $result = null;
         $result = this.viewCurrentOrders ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // OrderManagementApp/OrderService/managerLogin
       {
         boolean $result = false;
         $result = this.managerLogin ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 5:  // OrderManagementApp/OrderService/managerDisconnect
       {
         this.managerDisconnect ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:OrderManagementApp/OrderService:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public OrderService _this() 
  {
    return OrderServiceHelper.narrow(
    super._this_object());
  }

  public OrderService _this(org.omg.CORBA.ORB orb) 
  {
    return OrderServiceHelper.narrow(
    super._this_object(orb));
  }


} // class OrderServicePOA
