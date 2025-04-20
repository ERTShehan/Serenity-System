module com.assignment.serenity {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;
    requires java.desktop;

    opens com.assignment.serenity to javafx.fxml;
    opens com.assignment.serenity.controller to javafx.fxml;
    opens com.assignment.serenity.entity to org.hibernate.orm.core;
    opens com.assignment.serenity.dto to javafx.base;
    opens com.assignment.serenity.config to org.hibernate.orm.core;
    opens com.assignment.serenity.exception to javafx.base;

    exports com.assignment.serenity;
}