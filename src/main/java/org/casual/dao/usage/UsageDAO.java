package org.casual.dao.usage;

import org.casual.entity.CallUsage;
import org.casual.entity.DomesticTraffic;
import org.casual.entity.LocalTraffic;
import org.casual.entity.MesUsage;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
public interface UsageDAO {

    ResultMessage addCallUsage(CallUsage callUsage);

    ResultMessage addMesUsage(MesUsage mesUsage);

    ResultMessage addLocalTraffic(LocalTraffic localTraffic);

    ResultMessage addDomesticTraffic(DomesticTraffic domesticTraffic);

    CallUsage getCallUsage(long callId);

    MesUsage getMesUsage(long mesId);

    LocalTraffic getLocalTraffic(long ltId);

    DomesticTraffic getDomesticTraffic(long dtId);

    List<CallUsage> getCallUsageList();

    List<MesUsage> getMesUsageList();

    List<LocalTraffic> getLocalTrafficList();

    List<DomesticTraffic> getDomesticTrafficList();

    Double currentCallUsage(long uid);

    Integer currentMesUsage(long uid);

    Double currentLocalTraffic(long uid);

    Double currentDomesticTraffic(long uid);
}
