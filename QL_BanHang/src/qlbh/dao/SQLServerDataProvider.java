/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Bao Nhu
 */
public class SQLServerDataProvider {
    private Connection connection;
    //phuong thuc ket noi
    public void open(){
        try {
            String strServer = "DESKTOP-Q1HN2VJ\\SQLEXPRESS";
            String strDatebase = "QL_BHTVPP";
            String strUserName = "sa";
            String strPassword = "123";
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionURL = "jdbc:sqlserver://" + strServer
                                    +":1433;databaseName=" + strDatebase
                                    +";user=" + strUserName
                                    +";password=" + strPassword;
            connection = DriverManager.getConnection(connectionURL);
            if(connection != null){
                System.out.println("Kết nối thành công");
            }else
                System.out.println("Thất bại");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //phuong thuc dong
    public void Close(){
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet excuteQuery(String sql){
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    public int excuteUpdate(String sql){
        int n = -1;
        try {
            Statement statement = connection.createStatement();
            n = statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
}
