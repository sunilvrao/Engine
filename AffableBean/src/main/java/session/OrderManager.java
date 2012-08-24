package session;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.DBUtil;
import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import entity.Product;

/**
 *
 * @author tgiunipero
 */
public class OrderManager {

    private CustomerFacade customerFacade = new CustomerFacade();
    private ProductFacade productFacade = new ProductFacade();
    private CustomerOrderFacade customerOrderFacade = new CustomerOrderFacade();
    private OrderedProductFacade orderedProductFacade = new OrderedProductFacade();
    private DBUtil util = new DBUtil();
    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {

        try {
            Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);

        customer = persist(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);

        return persist(order);
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {


        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {

            int productId = scItem.getProduct().getId();

            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderId(order.getId());
            orderedProductPK.setProductId(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);
            
            orderedItem.setCustomerOrder(order);
            orderedItem.setProduct(scItem.getProduct());

            // set quantity
            orderedItem.setQuantity(scItem.getQuantity());

            persist(orderedItem);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map getOrderDetails(int orderId) {
 
        Map orderMap = new HashMap();

        // get order
        CustomerOrder order = customerOrderFacade.find(orderId);

        // get customer
        Customer customer = order.getCustomer();

        // get all ordered products
        List<OrderedProduct> orderedProducts = orderedProductFacade.findByOrderId(orderId);

        // get product details for ordered items
        List<Product> products = new ArrayList<Product>();

        for (OrderedProduct op : orderedProducts) {

            if(productFacade == null){
                productFacade = new ProductFacade();
            }
            Product p = (Product) productFacade.find(op.getProduct().getId());
            products.add(p);
        }

        // add each item to orderMap
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("products", products);

        return orderMap;
    }
    
    private void persist(OrderedProduct orderedItem){
        System.out.println("OrderManager::persist Ordered Product=" +orderedItem.getProduct().getName());
        Connection con = util.getConnection();
        String sql = "insert into ordered_product (CUSTOMER_ORDER_ID,PRODUCT_ID,QUANTITY) values(?,?,?)";
        
        Product product = orderedItem.getProduct();
        CustomerOrder co = orderedItem.getCustomerOrder();
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setInt(1, co.getId());
            statement.setInt(2,product.getId());
            statement.setShort(3,orderedItem.getQuantity());
            
            statement.execute();
        } catch (SQLException e) { 
            e.printStackTrace();
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) { 
                    e.printStackTrace();
                }
            }
        }
    }
    
    private CustomerOrder persist(CustomerOrder order){ 
        System.out.println("OrderManager::persist Customer Order=" +order.getId());
        Connection con = util.getConnection();
        String sql = "insert into customer_order (AMOUNT,CONFIRMATION_NUMBER,CUSTOMER_ID) values(?,?,?)";
        
        Customer customer = order.getCustomer();
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql); 
            statement.setBigDecimal(1, order.getAmount());
            statement.setInt(2, order.getConfirmationNumber());
            statement.setInt(3, customer.getId());
            
            statement.execute();
        } catch (SQLException e) { 
            e.printStackTrace();
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) { 
                    e.printStackTrace();
                }
            }
        }
        return customerOrderFacade.findByCustomer(customer.getId());
    }
    

    private Customer persist(Customer customer){  
        System.out.println("OrderManager::persist customer=" +customer.getName());
        Connection con = util.getConnection();
        String sql = "insert into customer (NAME,EMAIL,PHONE,ADDRESS,CITY_REGION,CC_NUMBER) values(?,?,?,?,?,?)";
        
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, customer.getName());

            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getCityRegion());
            statement.setString(6, customer.getCcNumber());
            
            statement.execute();
        } catch (SQLException e) { 
            e.printStackTrace();
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) { 
                    e.printStackTrace();
                }
            }
        }
        
        //Get the stored customer ID
        return customerFacade.find(customer.getName());
    }

}