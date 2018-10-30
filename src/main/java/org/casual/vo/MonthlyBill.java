package org.casual.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBill {

    private LocalDate date;

    private double expenseSum;

    private double callSum;

    private int mesSum;

    private double localTraffic;

    private double domesticTraffic;
}
