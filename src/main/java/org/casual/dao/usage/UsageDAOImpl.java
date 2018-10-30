package org.casual.dao.usage;

import org.casual.dao.util.Templar;
import org.casual.entity.CallUsage;
import org.casual.entity.DomesticTraffic;
import org.casual.entity.LocalTraffic;
import org.casual.entity.MesUsage;
import org.casual.util.ResultMessage;
import org.casual.vo.DoubleWrapper;
import org.casual.vo.IntegerWrapper;

import java.util.List;

import static org.casual.util.DateTimeUtil.getStartOfCurrentMonth;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
@SuppressWarnings("unchecked")
public class UsageDAOImpl implements UsageDAO {

    @Override
    public ResultMessage addCallUsage(CallUsage callUsage) {
        return Templar.update("INSERT INTO CALL_USAGE(uid, finishDatetime, callTime) VALUES(?, ?, ?)",
                callUsage.getUid(), callUsage.getFinishDatetime(), callUsage.getCallTime());
    }

    @Override
    public ResultMessage addMesUsage(MesUsage mesUsage) {
        return Templar.update("INSERT INTO MES_USAGE(uid, finishDatetime) VALUES(?, ?)", mesUsage.getUid(), mesUsage.getFinishDatetime());
    }

    @Override
    public ResultMessage addLocalTraffic(LocalTraffic localTraffic) {
        return Templar.update("INSERT INTO LOCAL_TRAFFIC(uid, finishDatetime, traffic) VALUES(?, ?, ?)",
                localTraffic.getUid(), localTraffic.getFinishDatetime(), localTraffic.getTraffic());
    }

    @Override
    public ResultMessage addDomesticTraffic(DomesticTraffic domesticTraffic) {
        return Templar.update("INSERT INTO DOMESTIC_TRAFFIC(uid, finishDatetime, traffic) VALUES(?, ?, ?)",
                domesticTraffic.getUid(), domesticTraffic.getFinishDatetime(), domesticTraffic.getTraffic());
    }

    @Override
    public CallUsage getCallUsage(long callId) {
        return (CallUsage) Templar.getOne("SELECT * FROM CALL_USAGE WHERE callId = ?", CallUsage.class, callId);
    }

    @Override
    public MesUsage getMesUsage(long mesId) {
        return (MesUsage) Templar.getOne("SELECT * FROM MES_USAGE WHERE mesId = ?", MesUsage.class, mesId);
    }

    @Override
    public LocalTraffic getLocalTraffic(long ltId) {
        return (LocalTraffic) Templar.getOne("SELECT * FROM LOCAL_TRAFFIC WHERE ltId = ?", LocalTraffic.class, ltId);
    }

    @Override
    public DomesticTraffic getDomesticTraffic(long dtId) {
        return (DomesticTraffic) Templar.getOne("SELECT * FROM DOMESTIC_TRAFFIC WHERE dtId = ?", DomesticTraffic.class, dtId);
    }

    @Override
    public List<CallUsage> getCallUsageList() {
        return Templar.getList("SELECT * FROM CALL_USAGE", CallUsage.class);
    }

    @Override
    public List<MesUsage> getMesUsageList() {
        return Templar.getList("SELECT * FROM MES_USAGE", MesUsage.class);
    }

    @Override
    public List<LocalTraffic> getLocalTrafficList() {
        return Templar.getList("SELECT * FROM LOCAL_TRAFFIC", LocalTraffic.class);
    }

    @Override
    public List<DomesticTraffic> getDomesticTrafficList() {
        return Templar.getList("SELECT * FROM DOMESTIC_TRAFFIC", DomesticTraffic.class);
    }

    @Override
    public Double currentCallUsage(long uid) {
        Object sum;
        if ((sum = Templar.getOne("SELECT SUM(callTime) as VAL FROM CALL_USAGE WHERE uid = ? and finishDatetime > ?",
                DoubleWrapper.class, uid, getStartOfCurrentMonth())) != null){
            return ((DoubleWrapper)sum).getVal();
        } else {
            return 0.0;
        }
    }

    @Override
    public Integer currentMesUsage(long uid) {
        Object sum;
        if ((sum = Templar.getOne("SELECT COUNT(*) as VAL FROM MES_USAGE WHERE uid = ? and finishDatetime > ?",
                IntegerWrapper.class, uid, getStartOfCurrentMonth())) != null){
            return ((IntegerWrapper)sum).getVal();
        } else {
            return 0;
        }
    }

    @Override
    public Double currentLocalTraffic(long uid) {
        Object sum;
        if ((sum = Templar.getOne("SELECT SUM(traffic) as VAL FROM LOCAL_TRAFFIC WHERE uid = ? and finishDatetime > ?",
                DoubleWrapper.class, uid, getStartOfCurrentMonth())) != null){
            return ((DoubleWrapper)sum).getVal();
        } else {
            return 0.0;
        }
    }

    @Override
    public Double currentDomesticTraffic(long uid) {
        Object sum;
        if ((sum = Templar.getOne("SELECT SUM(traffic) as VAL FROM DOMESTIC_TRAFFIC WHERE uid = ? and finishDatetime > ?",
                DoubleWrapper.class, uid, getStartOfCurrentMonth())) != null){
            return ((DoubleWrapper)sum).getVal();
        } else {
            return 0.0;
        }
    }
}
