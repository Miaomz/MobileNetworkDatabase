package org.casual;

import org.casual.dao.util.DBConnection;
import org.casual.entity.Order;
import org.casual.entity.User;
import org.casual.service.order.OrderService;
import org.casual.service.usage.UsageService;
import org.casual.service.user.UserService;
import org.casual.service.util.ServiceFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import static org.casual.util.JsonUtil.toJson;

/**
 * @author miaomuzhi
 * @since 2018/10/22
 */
public class Main {

    private OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private UsageService usageService = ServiceFactory.getInstance().getUsageService();
    private UserService userService = ServiceFactory.getInstance().getUserService();

    public static void main(String[] args) {
        try {
            Main main = new Main();
            main.linkToDB();

            System.out.println("查询1号用户的套餐");
            long timeCost = System.currentTimeMillis();
            for (Order order : main.orderService.queryOrders(1)) {
                System.out.println(toJson(order));
            }
            timeCost = System.currentTimeMillis() - timeCost;
            System.out.println("用时" + timeCost + "毫秒");

            System.out.println("1号用户订购5号套餐");
            timeCost = System.currentTimeMillis();
            System.out.println(main.orderService.makeOrder(1, 5));
            timeCost = System.currentTimeMillis() - timeCost;
            System.out.println("用时" + timeCost + "毫秒");

            System.out.println("1号用户取消订购5号套餐");
            timeCost = System.currentTimeMillis();
            System.out.println(main.orderService.cancelOrderImmediately(1, 5));
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

            System.out.println("1号用户隔月取消订购1号套餐");
            timeCost = System.currentTimeMillis();
            System.out.println(main.orderService.cancelOrderNextMonth(1, 1));
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

            System.out.println("用户1通话13分钟");
            timeCost = System.currentTimeMillis();
            main.usageService.addCallUsage(1, 13);
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

            System.out.println("用户1使用当地流量100M");
            timeCost = System.currentTimeMillis();
            main.usageService.addLocalTraffic(1, 100);
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

            System.out.println("用户1查询本月账单");
            timeCost = System.currentTimeMillis();
            System.out.println(main.usageService.getMonthlyBill(1, LocalDate.now()));
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

            System.out.println("订单续订（此操作原本仅应该在月初进行）");
            timeCost = System.currentTimeMillis();
            System.out.println(main.orderService.renewOrder());
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

            System.out.println("冻结欠费用户（此操作原本仅应该在月初进行）");
            timeCost = System.currentTimeMillis();
            for (User user : main.userService.frozeUser()) {
                System.out.println(toJson(user));
            }
            System.out.println("用时" + (System.currentTimeMillis()-timeCost) + "毫秒");

        } catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void linkToDB() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MobileNetwork?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=CST&useSSL=false",
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
