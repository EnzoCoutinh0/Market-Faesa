module com.example.marketfaesamain {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.marketfaesamain to javafx.fxml;
    exports com.example.marketfaesamain;
}