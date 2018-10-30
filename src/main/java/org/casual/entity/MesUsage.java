package org.casual.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesUsage {

    private static double price = 0.1;

    public static double getPrice() {
        return price;
    }

    public static void setPrice(double price) {
        MesUsage.price = price;
    }

    private long mesId;

    private long uid;

    /**
     * the time that message is committed
     */
    private LocalDateTime finishDatetime;
}
