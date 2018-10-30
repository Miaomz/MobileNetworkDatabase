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
public class LocalTraffic {
    private static double price = 2;

    public static double getPrice() {
        return price;
    }

    public static void setPrice(double price) {
        LocalTraffic.price = price;
    }

    private long ltId;

    private long uid;

    private LocalDateTime finishDatetime;

    private double traffic;
}
