package org.casual;

import org.casual.dao.datautil.DBConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * @author miaomuzhi
 * @since 2018/10/22
 */
public class Main {

    public static void main(String[] args) {
        try {
            new Main().linkToDB();
        } catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void linkToDB() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MobileNetwork?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT&useSSL=false",
                "root","123456");
        DBConnection.getInstance().setConnection(connection);
        createTablesIfNotExist(connection);
    }

    private void createTablesIfNotExist(Connection connection) throws SQLException{
        try (Statement createTableState = connection.createStatement()){
            try (ResultSet resultset = createTableState.executeQuery("SHOW TABLES")){//the key is data to be inserted
                if (!resultset.next()){//if result set is empty
                    runScript(connection, "/create-tables.sql");
                    runScript(connection, "/seed-data.sql");
                }
            }
        }
    }

    private void runScript(Connection connection, String relativePath) throws SQLException{
        File script = new File(getClass().getResource(relativePath).getFile());
        try (BufferedReader reader = new BufferedReader(new FileReader(script))){
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                if (line.isEmpty()){
                    sb.append('/');//for split
                } else {
                    sb.append(line).append(System.lineSeparator());
                }
            }

            //although it is a stupid way, but necessary due to mysql's limitations
            try (Statement s = connection.createStatement()){
                for (String create : sb.toString().split("/")) {
                    s.executeUpdate(create);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
