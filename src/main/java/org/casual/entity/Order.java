package org.casual.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author miaomuzhi
 * @since 2018/10/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private long orderId;

    private long uid;

    private long pid;

    private LocalDate month;

    /**
     * 下一个月是否续费，用于隔月取消套餐
     */
    private boolean renewing = true;
}
