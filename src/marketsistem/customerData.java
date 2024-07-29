/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsistem;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class customerData {
    
    private Integer customer_id; 
    private String brand;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private Date date;
    
    public customerData(Integer customer_id, String brand, String productName, Integer quantity, BigDecimal price, Date date){
        this.customer_id = customer_id;
        this.brand = brand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
    
    public Integer getCustomerId(){
        return customer_id;
    }
    
    public String getBrand(){
        return brand;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public Integer getQuantity(){
        return quantity;
    }
    
    public BigDecimal getPrice(){
        return price;
    }
    
    public Date getDate(){
        return date;
    }
    
}
