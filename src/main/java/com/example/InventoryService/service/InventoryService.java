package com.example.InventoryService.service;

import com.example.InventoryService.model.Inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryService {
    static final String serverName = "WINDOWS-CQ7O1HH";
    static final String dbName = "momento_mori";
    static final String url = "jdbc:sqlserver://" + serverName + ";database=" + dbName + ";encrypt=true;trustServerCertificate=true;";
    public final String user = "erki";
    public final String password = "abcdefg";


    public Inventory selectById(int id) throws ClassNotFoundException, SQLException {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection connection = DriverManager.getConnection(url, user,password);

        String sql = """ 
            SELECT * FROM dbo.Inventory WHERE id=?""";

        var prst = connection.prepareStatement(sql);
        prst.setInt(1, id);

        var result = prst.executeQuery();

        result.next();

        var value = new Inventory(result.getString(2),result.getInt(3));

        connection.close();

        return value;
    }

    public Iterable<Inventory> showAll() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user,password);

        String sql = """ 
            SELECT * FROM dbo.Inventory
            """;

        var prst = connection.prepareStatement(sql);

        var result = prst.executeQuery();

        var results = new ArrayList<Inventory>();

        while (result.next()){
            results.add(new Inventory(result.getString("name"),result.getInt("amount")));
        }
        connection.close();

        return results;
    }

    public void Buy(Inventory inventory) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);

        String sql = """ 
                INSERT INTO dbo.Inventory VALUES (?,?)""";

        var prst = connection.prepareStatement(sql);

        prst.setString(1, inventory.getName());
        prst.setInt(2, inventory.getAmount());


        prst.execute();

        connection.close();
    }

    public boolean Decrease(int id, int amount) throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(url, user, password);

        String sql = """ 
                UPDATE Inventory 
                SET Amount=?
                WHERE id=?""";

        var prst = connection.prepareStatement(sql);

        prst.setInt(2, id);

        int AmountInStock = (selectById(id)).getAmount();
        if (AmountInStock-amount >= 0) {
            prst.setInt(1, AmountInStock-amount);
        } else {
            connection.close();
            return false;
        }

        prst.execute();

        connection.close();

        return true;
    }
}


