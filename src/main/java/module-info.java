module lk.ijse.gdse.mentalhealth {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;
    requires java.desktop;

    opens lk.ijse.gdse.mentalhealth to javafx.fxml;
    opens lk.ijse.gdse.mentalhealth.controller to javafx.fxml;
    opens lk.ijse.gdse.mentalhealth.entity to org.hibernate.orm.core;
    opens lk.ijse.gdse.mentalhealth.view.tdm to javafx.base;
//    opens lk.ijse.gdse.mentalhealth.dto to javafx.base;
    opens lk.ijse.gdse.mentalhealth.config to jakarta.persistence;
    opens lk.ijse.gdse.mentalhealth.exception to javafx.base;

    exports lk.ijse.gdse.mentalhealth;
}