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
public class DomesticTraffic {

    private static double price = 5;

    public static double getPrice() {
        return price;
    }

    public static void setPrice(double price) {
        DomesticTraffic.price = price;
    }

    private long dtId;

    private long uid;

    private LocalDateTime finishDatetime;

    private double traffic;
}
