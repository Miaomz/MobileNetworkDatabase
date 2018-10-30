package org.casual.dao.datautil;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Getter
@Setter
public class DBConnection {
    private Connection connection;

    private static DBConnection ourInstance = new DBConnection();

    public static DBConnection getInstance() {
        return ourInstance;
    }

    private DBConnection() {}
}
