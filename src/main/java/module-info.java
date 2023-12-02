module com.example.tourisme_medicale {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.mail;
    requires activation;


    opens com.example.tourisme_medicale to javafx.fxml;
    exports com.example.tourisme_medicale;
    opens com.example.tourisme_medicale.models to javafx.base;
    exports com.example.tourisme_medicale.models;

}