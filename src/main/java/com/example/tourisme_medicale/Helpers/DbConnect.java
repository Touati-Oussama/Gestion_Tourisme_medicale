package com.example.tourisme_medicale.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnect {

    private static String HOST = "127.0.0.1";
    private static int PORT = 3306;
    private static String DB_NAME = "tp_java";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection ;

    public static DbConnect instance;//hedhi el attribut staituqe et publique

    public Connection getConnection() {
        return connection;
    }

    private DbConnect(){

        try {
            System.out.println("Connexion en cours");
            connection=  DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
            System.out.println("Connexion établie !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /*public static Connection getConnect (){
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return  connection;
    }*/

    public static DbConnect getInstance() {
        if (instance == null)
            instance = new DbConnect();
        return  instance;
    }
}
