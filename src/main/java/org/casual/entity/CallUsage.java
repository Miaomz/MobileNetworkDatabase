package org.casual.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallUsage {

    private static double price = 0.5;

    public static double getPrice() {
        return price;
    }

    public static void setPrice(double price) {
        CallUsage.price = price;
    }

    private long callId;

    /**
     * user id
     */
    private long uid;

    /**
     * the time that phone call is committed
     */
    private LocalDateTime finishDatetime;

    /**
     * the time the call lasts for
     */
    private double callTime;
}
