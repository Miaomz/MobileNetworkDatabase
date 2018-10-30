package org.casual.service.usage;

import org.casual.util.ResultMessage;
import org.casual.vo.MonthlyBill;

import java.time.LocalDate;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public interface UsageService {

    ResultMessage addCallUsage(long uid, double callTime);

    ResultMessage addMesUsage(long uid);

    ResultMessage addLocalTraffic(long uid, double traffic);

    ResultMessage addDomesticTraffic(long uid, double traffic);

    MonthlyBill getMonthlyBill(long uid, LocalDate date);
}
