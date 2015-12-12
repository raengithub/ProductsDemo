package com.self.test.core;

import com.self.test.model.Product;

import java.sql.*;

public class DatabaseUtility {

    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constant.dbClass);
            con = DriverManager.getConnection(Constant.dbUrl, Constant.dbUser, Constant.dbPwd);
        } catch (Exception e) {
            throw e;
        } finally {
            return con;
        }
    }

    /**
     * Method to insert uname and pwd in DB
     *
     * @param name
     * @param code
     * @param price
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static boolean insertProduct(String code, String name, Double price) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DatabaseUtility.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into product(code, name, price) values('"+code+ "',"+"'"
                    + name + "','" + price + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }

    public static Product retrieveProduct(String code) throws Exception {
        Connection dbConn = null;
        final Product product = new Product();
        try {
            try {
                dbConn = DatabaseUtility.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM product WHERE code = '" + code + "' limit 1";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                product.setCode(rs.getString("code"));
                product.setPrice(rs.getDouble("price"));
                product.setName(rs.getString("name"));
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return product;
    }
}
