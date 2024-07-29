/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsistem;

import java.math.BigDecimal;


/**
 *
 * @author ASUS
 */
public class ProductData {
    private String productId;
    private String brand;
    private String productName;
    private String status;
    private BigDecimal price;
    
    public ProductData(String productId, String brand, String productName, String status, BigDecimal price){
        this.productId = productId;
        this.brand = brand;
        this.productName = productName;
        this.status = status;
        this.price = price;
    } 
    
    public String getProductId(){
        return productId;
    } 
    
    public String getBrand(){
        return brand;
    }
    
    public String getProductName(){
        return productName; 
    }
    
    public String getStatus(){
        return status;
    }
    
    public BigDecimal getPrice(){
        return price;
    }
    
}
