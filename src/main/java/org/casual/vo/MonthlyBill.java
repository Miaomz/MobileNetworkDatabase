package org.casual.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    @Override
    public String toString() {
        NumberFormat format = new DecimalFormat("#0.00");
        return "月账单" + System.lineSeparator() +
                "月份：" + date.getYear() + "-" + date.getMonthValue() + System.lineSeparator() +
                "总花费：" + format.format(expenseSum) + "元" + System.lineSeparator() +
                "通话总时间：" + format.format(callSum) + "分钟" + System.lineSeparator() +
                "短信总条数：" + mesSum + "条" + System.lineSeparator() +
                "本地流量：" + format.format(localTraffic) + "M" + System.lineSeparator() +
                "全国流量：" + format.format(domesticTraffic) + "M";
    }
}
