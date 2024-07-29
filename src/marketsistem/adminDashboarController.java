/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsistem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ASUS
 */
public class adminDashboarController implements Initializable {
    

    @FXML
    private Button Cashier_PayBttn;

    @FXML
    private Button Cashier_Receiptbttn;

    @FXML
    private Button Cashier_addBttn;

    @FXML
    private TextField Cashier_brandName;

    @FXML
    private Hyperlink Cashier_clear;

    @FXML
    private ComboBox<?> Cashier_productName;

    @FXML
    private Spinner<Integer> Cashier_quantity;

    @FXML
    private TableView<customerData> Cashier_table;

    @FXML
    private TableColumn<customerData, String> Cashier_table_brandName;

    @FXML
    private TableColumn<customerData, String> Cashier_table_price;

    @FXML
    private TableColumn<customerData, String> Cashier_table_productName;

    @FXML
    private TableColumn<customerData, String> Cashier_table_status;

    @FXML
    private Label Cashier_total;

    @FXML
    private Button Cashierbttn;
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private AnchorPane Cashierform;
    
    @FXML
    private AreaChart<?, ?> DB_incomechart;
    
    @FXML
    private Label DB_totalincome;

    @FXML
    private Label DB_tdincome;
    
    @FXML
    private AnchorPane DB_todaysIncome;

    @FXML
    private AnchorPane DB_totalExpenses;

    @FXML
    private AnchorPane DB_totalIncome;

    @FXML
    private AnchorPane Dashboardform_DB;

    @FXML
    private TextField MP_brandName;

    @FXML
    private Button MP_deletebttn;

    @FXML
    private TextField MP_price;

    @FXML
    private TextField MP_productID;

    @FXML
    private TextField MP_productName;

    @FXML
    private Button MP_savebttn;

    @FXML
    private TextField MP_search;

    @FXML
    private ComboBox<?> MP_status;

    @FXML
    private TableView<ProductData> MP_table;

    @FXML
    private TableColumn<ProductData, String> MP_table_brandName;

    @FXML
    private TableColumn<ProductData, String> MP_table_price;

    @FXML
    private TableColumn<ProductData, String> MP_table_productID;

    @FXML
    private TableColumn<ProductData, String> MP_table_productName;

    @FXML
    private TableColumn<ProductData, String> MP_table_status;
    
    @FXML
    private Button MP_updatebttn;

    @FXML
    private Button ManageProductsbttn;

    @FXML
    private AnchorPane ManageProductsform_MP;

    @FXML
    private Button backbttn;
    
    @FXML
    private Hyperlink clear;

    @FXML
    private Button closebttn;

    @FXML
    private Button dashboardbttn;

    @FXML
    private FontAwesomeIcon mini;

    @FXML
    private Button minimizebttn;
    
    @FXML
    private Label main_username;
    
    private double x = 0;
    private double y = 0;
    
    
    //database tools
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    private TextField cas;
    

    
    @FXML
    public void addproductsAdd(){
        String insertProduct = "INSERT INTO product"
                +"(product_id,brand,product_name,status,price)"
                +"VALUES(?, ?, ?, ?, ?)";
        
        connect = database.connectDb();

        try{
            Alert alert;
            
            if (MP_productID.getText().isEmpty()
                    || MP_brandName.getText().isEmpty()
                    || MP_productName.getText().isEmpty()
                    || MP_status.getSelectionModel().getSelectedItem() == null
                    || MP_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                String check = "SELECT product_id FROM product WHERE product_id = '"
                        +MP_productID.getText()+"'";
                
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                
                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + MP_productID.getText() + " was already exist!!!");
                    alert.showAndWait();
                }else{

                    prepare = connect.prepareStatement(insertProduct);
                    prepare.setString(1, MP_productID.getText());
                    prepare.setString(2, MP_brandName.getText());
                    prepare.setString(3, MP_productName.getText());
                    prepare.setString(4, (String)MP_status.getSelectionModel().getSelectedItem());
                   
           
                    prepare.setBigDecimal(5, new BigDecimal(MP_price.getText()));
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    
                    addProductsShowData();
                    addProductsClear();
                    
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
        // Tutup prepare statement dan koneksi untuk menghindari kebocoran sumber daya
        try {
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
    
    @FXML
    public void addProductsClear(){
        MP_productID.setText("");
        MP_brandName.setText("");
        MP_productName.setText("");
        MP_status.getSelectionModel().clearSelection();
        MP_price.setText("");;
    }
    
    private String[] statusList = {"Available", "Out of stock"};
    @FXML
    public void addProductsStatusList(){
        List<String> ListStts = new ArrayList<>();
        
        for(String data : statusList){
            ListStts.add(data);
        }
        
        ObservableList statusData = FXCollections.observableArrayList(ListStts);
        MP_status.setItems(statusData);
    }
    
    
    @FXML
    public void SearchProduct(){
        FilteredList<ProductData> filter = new FilteredList<>(addProductsList, e-> true);
        
        MP_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateProductData -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                
                if(predicateProductData.getProductId().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateProductData.getBrand().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateProductData.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateProductData.getProductName().toLowerCase().contains(searchKey)){
                    return true;
//                }else if (BigDecimal.toString(predicateProductData.getPrice()).contains(searchKey)){
//                    return true;
                } else return false;
                
            });
        });
        SortedList<ProductData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(MP_table.comparatorProperty());
        MP_table.setItems(sortList);
    }
    
    public ObservableList<ProductData> addProductsListData(){
        ObservableList<ProductData> prodList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM product";
        
        connect = database.connectDb();
        
        try{
            ProductData prod;
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                prod = new ProductData(result.getString("product_id"), 
                        result.getString("brand"), 
                        result.getString("product_name"), 
                        result.getString("status"), 
                        result.getBigDecimal("price"));
                
                prodList.add(prod);
            }
        
        }catch(Exception e){e.printStackTrace();
        }finally {
        // Tutup prepare statement dan koneksi untuk menghindari kebocoran sumber daya
        try {
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        return prodList;
    }
    
    private ObservableList<ProductData> addProductsList;
    public void addProductsShowData(){
        addProductsList = addProductsListData();
        
        MP_table_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        MP_table_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        MP_table_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        MP_table_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        MP_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        MP_table.setItems(addProductsList);
    }
    
    @FXML
    public void addproductsSelect(){
        ProductData prod = MP_table.getSelectionModel().getSelectedItem();
        int num = MP_table.getSelectionModel().getSelectedIndex();
        
        if((num-1) < -1){
            return;
        }
        
        MP_productID.setText(prod.getProductId());
        MP_brandName.setText(prod.getBrand());
        MP_productName.setText(prod.getProductName());
        MP_price.setText(prod.getPrice().toString());
        
    }
    
    
    
    @FXML
    public void logout(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get().equals(ButtonType.OK)){
                backbttn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                   stage.setX(event.getScreenX() - x);
                   stage.setY(event.getScreenY() - y);

                   stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                
                stage.setScene(scene);
                stage.show();
            } else return;
                
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        // Tutup prepare statement dan koneksi untuk menghindari kebocoran sumber daya
        try {
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
    
    @FXML
    public void addProductsUpdate(){
//        String updateProduct = "UPDATE product SET "
//                + "brand = '"+ MP_brandName.getText() +"', "
//                + "product_name = '"+ MP_productName.getText() +"'"
//                + "status = '"+ MP_status.getSelectionModel().getSelectedItem() +"'"
//                + "price = '"+ MP_price.getText() +"' "
//                + "WHERE product_id = '"+ MP_productID.getText() +"'";

            String updateProduct = "UPDATE product SET "
            + "brand = ?, "
            + "product_name = ?, "
            + "status = ?, "
            + "price = ? "
            + "WHERE product_id = ?";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(MP_productID.getText().isEmpty()
                    || MP_brandName.getText().isEmpty()
                    || MP_productName.getText().isEmpty()
                    || MP_status.getSelectionModel().getSelectedItem() == null
                    || MP_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Messege");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID:" + MP_productID.getText() + "?");
                
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                prepare = connect.prepareStatement(updateProduct);
                prepare.setString(1, MP_brandName.getText());
                prepare.setString(2, MP_productName.getText());
                prepare.setString(3, (String)MP_status.getSelectionModel().getSelectedItem());
                prepare.setString(4, MP_price.getText());
                prepare.setString(5, MP_productID.getText());
                
                int rowsUpdated = prepare.executeUpdate();
                
                if (rowsUpdated > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Information Message");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Successfully Updated!");
                    successAlert.showAndWait();
                    
                    addProductsShowData();
                    addProductsClear();
                }
                }else return;
            }
            
        }catch(Exception e){e.printStackTrace();
        }finally {
        // Tutup prepare statement dan koneksi untuk menghindari kebocoran sumber daya
        try {
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
    
    @FXML
    public void addProductsDelete(){
        String deleteProduct = "DELETE FROM `product` WHERE product_id = ?";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(deleteProduct);
            
            Alert alert;
            
            if(MP_productID.getText().isEmpty()
                    || MP_brandName.getText().isEmpty()
                    || MP_productName.getText().isEmpty()
                    || MP_price.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product ID: " + MP_productID.getText() + "?" );
                
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.isPresent() && option.get() == ButtonType.OK){
                    prepare.setString(1, MP_productID.getText());
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    addProductsShowData();
                    addProductsClear();
                } else {
                    return;
                }
            }
        }catch(Exception e){e.printStackTrace();
        }finally {
        // Tutup prepare statement dan koneksi untuk menghindari kebocoran sumber daya
        try {
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
    
    
    @FXML
    public void displeyUsername(){
        main_username.setText(GetData.username);
    }
    
    public void dashboardDisplayIncome(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) FROM customer_receipt WHERE date = '"
                +sqlDate+"'";
        
        BigDecimal sumT = BigDecimal.ZERO;
        connect= database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                sumT = result.getBigDecimal("SUM(total)");
            }
            DB_tdincome.setText("Rp "+String.valueOf(sumT));
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
        public void dashboardDisplayTotalIncome(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) FROM customer_receipt";
        
        BigDecimal sumTotal = BigDecimal.ZERO;
        connect= database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                sumTotal = result.getBigDecimal("SUM(total)");
            }
            DB_totalincome.setText("Rp "+String.valueOf(sumTotal));
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        
    public void dashboardChart(){
        DB_incomechart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
        
        connect = database.connectDb();
        
        try{
            XYChart.Series chart = new XYChart.Series();
 
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
                
            }
            DB_incomechart.getData().add(chart);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    
    }
        
    
    //customer
    public void purchaseAdd(){
        
        purchaseCustomerId();
        purchaseSpinnerValue();
        purchaseGetPrice();
        
     String insertProd = "INSERT INTO customer "
             + "(customer_id, brand, productName, quantity, price, date)"
             + "VALUES(?, ?, ?, ?, ?, ?)";
     
     connect = database.connectDb();
     
     try{
        Alert alert;
         
         Date date = new Date();
         java.sql.Date sqlDate = new java.sql.Date(date.getTime());
         
         
         if(Cashier_brandName.getText().isEmpty()
                 || Cashier_productName.getSelectionModel().getSelectedItem()== null
                 || qty == 0){
             alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error Message");
             alert.setHeaderText("null");
             alert.setContentText("Please choose product/s first");
             alert.showAndWait();  
         }else{
            prepare = connect.prepareStatement(insertProd);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, Cashier_brandName.getText());
            prepare.setString(3, (String) Cashier_productName.getSelectionModel().getSelectedItem());
            prepare.setString(4, String.valueOf(qty));
                
                totalPrice= qty * price;
                
            prepare.setString(5, String.valueOf(totalPrice));
            
            prepare.setString(6, String.valueOf(sqlDate));
            
            prepare.executeUpdate();
            
            //table view
            purchaseShowListData();
            //display sum
            purchaseDisplayTotal();
            
         }
         
     }catch(Exception e){e.printStackTrace();}
     
    }
    
 

    public void purchaseReset(){
        purchaseCustomerId();

        String resetData = "DELETE FROM customer WHERE customer_id = ?";

        connect = database.connectDb();

        try{

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to reset? The Product List including to reset");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
               PreparedStatement preparedStatement = connect.prepareStatement(resetData);
                preparedStatement.setString(1, String.valueOf(customerId));
                preparedStatement.executeUpdate();


                Cashier_brandName.setText("");
                Cashier_productName.getSelectionModel().clearSelection();
                purchaseSpinner();
                Cashier_total.setText("Rp 0");

                purchaseShowListData();
            }else return;

        }catch(Exception e){
            e.printStackTrace(); 
        } 
    }
    
    public void purchasePay(){
        purchaseCustomerId();
        purchaseDisplayTotal();
        String sql = "INSERT INTO customer_receipt (customer_id, total, date)"
                + "VALUES(?,?,?)";
        connect = database.connectDb();
        
        try{
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Alert alert;
            if(Cashier_table.getItems().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please chose the product first");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));
                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();
                }else return;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void purchaseReceipt(){
       
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("marketP", customerId);  
        
        try{

            Alert alert;
            if(totalP == BigDecimal.ZERO){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
            }else{
                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\asusv\\Documents\\NetBeansProjects\\MarketSistem\\src\\marketsistem\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

                JasperViewer.viewReport(jPrint, false);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    

    private BigDecimal totalP = BigDecimal.ZERO;
    public void purchaseDisplayTotal(){
        purchaseCustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '"
                +customerId+"'";
        
        connect = database.connectDb();
        
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                totalP = result.getBigDecimal("SUM(price)");
            }
            Cashier_total.setText("Rp" + String.valueOf(totalP));
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
private Double price = null;
private Double totalPrice = null;
public void purchaseGetPrice(){
    String gPrice = "SELECT price FROM product WHERE product_name = '"
            + Cashier_productName.getSelectionModel().getSelectedItem() + "'";
    
    connect = database.connectDb();
    
    try {
        statement = connect.createStatement();
        result = statement.executeQuery(gPrice);
        
        if(result.next()) {
            price = result.getDouble("price");
        }
        
    } catch(Exception e) {
        e.printStackTrace();
    }
}
    
    
    public void purchaseSearchBrand(){
        String searchB = "SELECT * FROM product WHERE brand = ? AND status = 'Available'";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(searchB);
            prepare.setString(1, Cashier_brandName.getText());
            result = prepare.executeQuery();
            
            ObservableList listProduct = FXCollections.observableArrayList();
            
            while(result.next()){
                    listProduct.add(result.getString("product_name"));
            }
            
            Cashier_productName.setItems(listProduct);
            
        }catch(Exception e){e.printStackTrace();}

    }
    
    private SpinnerValueFactory spinner;
    
    public void purchaseSpinner(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
        
        Cashier_quantity.setValueFactory(spinner);
    }
    
    private int qty;
    public void purchaseSpinnerValue(){
        qty = Cashier_quantity.getValue();
        
        //System.out.println(qty);
    }
    
    public ObservableList<customerData> purchaseListData(){
        
        purchaseCustomerId();
        ObservableList<customerData> customerList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM customer WHERE customer_id = '"+ customerId +"'";
        
        try{
            customerData custD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                custD = new customerData(result.getInt("customer_id"), 
                        result.getString("brand"), 
                        result.getString("productName"), 
                        result.getInt("quantity"),
                        
                        
                        result.getBigDecimal("price"), 
                        result.getDate("date"));
                customerList.add(custD);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return customerList;
    }
    
    private ObservableList<customerData> purchaseList;
    public void purchaseShowListData(){
        purchaseList = purchaseListData();
        
        Cashier_table_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        Cashier_table_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        Cashier_table_status.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        Cashier_table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        Cashier_table.setItems(purchaseList);
    }
    
    private int customerId;
    public void purchaseCustomerId(){
        
        String cID = "SELECT customer_id FROM customer";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(cID);
            result = prepare.executeQuery();
            
            while(result.next()){
                customerId = result.getInt("customer_id");
            }
            
            int checkNum = 0;
            
            String checkCustomerId = "SELECT customer_id FROM customer_receipt";
            
            statement = connect.createStatement();
            result = statement.executeQuery(checkCustomerId);
            
            while(result.next()){
                checkNum = result.getInt("customer_id");
            }
            
            if(customerId == 0){
                customerId += 1;
            }else if(checkNum == customerId){
                customerId += 1;
            }
            
        
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    @FXML
    public void switchForm(ActionEvent event){
        
        if(event.getSource() == dashboardbttn){
            Dashboardform_DB.setVisible(true);
            ManageProductsform_MP.setVisible(false);
            Cashierform.setVisible(false);
            
                dashboardbttn.setStyle("-fx-border-color: transparent transparent transparent #88dbff; -fx-border-width: 0 0 0 3px; -fx-background-color: #ffffff; -fx-background-radius: 8px");
                ManageProductsbttn.setStyle("-fx-background-color: transparent");
                Cashierbttn.setStyle("-fx-background-color: transparent");
                
                dashboardDisplayIncome();
                dashboardDisplayTotalIncome();
                dashboardChart();
                
        }else if(event.getSource() == Cashierbttn){
            Dashboardform_DB.setVisible(false);
            ManageProductsform_MP.setVisible(false);
            Cashierform.setVisible(true);
            
                dashboardbttn.setStyle("-fx-background-color: transparent");
                ManageProductsbttn.setStyle("-fx-background-color: transparent");
                Cashierbttn.setStyle("-fx-border-color: transparent transparent transparent #88dbff; -fx-border-width: 0 0 0 3px; -fx-background-color: #ffffff; -fx-background-radius: 8px");
        
        }else if(event.getSource() == ManageProductsbttn){
            Dashboardform_DB.setVisible(false);
            ManageProductsform_MP.setVisible(true);
            Cashierform.setVisible(false);
            
            
                dashboardbttn.setStyle("-fx-background-color: transparent");
                ManageProductsbttn.setStyle("-fx-border-color: transparent transparent transparent #88dbff; -fx-border-width: 0 0 0 3px; -fx-background-color: #ffffff; -fx-background-radius: 8px");
                Cashierbttn.setStyle("-fx-background-color: transparent");
            
            addProductsShowData();
            addProductsStatusList();
            SearchProduct();
        
        }
    }
    
    @FXML
    public void close(){
        System.exit(0);
    }

    @FXML
    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displeyUsername();
        
        dashboardDisplayIncome();
        dashboardDisplayTotalIncome();
        dashboardChart();
        
        addProductsShowData();
        addProductsStatusList();
        
        purchaseShowListData();
        purchaseSpinner();
        
        purchaseDisplayTotal();
        
        //purchaseSpinnerValue();
            
    }
    
}
