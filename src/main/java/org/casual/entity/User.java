package org.casual.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author miaomuzhi
 * @since 2018/10/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long uid;

    private String uname;

    private double balance;

    /**
     * the user will be frozen if he/she has debt
     */
    private boolean frozen;
}
