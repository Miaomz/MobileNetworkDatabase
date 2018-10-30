package org.casual.dao.pack;

import org.casual.dao.datautil.Templar;
import org.casual.entity.Pack;
import org.casual.entity.PackOffer;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
public class PackDAOImpl implements PackDAO {

    @Override
    public ResultMessage addPack(Pack pack) {
        return Templar.update("INSERT INTO PACK(pname, fee) VALUES(?, ?)", pack.getPname(), pack.getFee());
    }

    @Override
    public ResultMessage deletePack(long pid) {
        return Templar.update("DELETE FROM PACK WHERE pid = ?", pid);
    }

    @Override
    public ResultMessage updatePack(Pack pack) {
        return Templar.update("UPDATE PACK SET pname = ?, fee = ? WHERE pid = ?", pack.getPname(), pack.getFee(), pack.getPid());
    }

    @Override
    public Pack getPack(long pid) {
        return (Pack) Templar.getOne("SELECT * FROM PACK WHERE pid = ?", Pack.class, pid);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Pack> getPackList() {
        return Templar.getList("SELECT * FROM PACK", Pack.class);
    }

    @Override
    public ResultMessage addPackOffer(PackOffer offer) {
        return Templar.update("INSERT INTO PACK_OFFER(pid, offerType, quantity) VALUES(?, ?, ?)", offer.getPid(), offer.getOfferType(), offer.getQuantity());
    }

    @Override
    public ResultMessage deletePackOffer(long oid) {
        return Templar.update("DELETE FROM PACK_OFFER WHERE oid = ?", oid);
    }

    @Override
    public ResultMessage updatePackOffer(PackOffer offer) {
        return Templar.update("UPDATE PACK_OFFER SET pid = ?, offerType = ?, quantity = ? WHERE oid = ?", offer.getPid(), offer.getOfferType(), offer.getQuantity(), offer.getOid());
    }

    @Override
    public PackOffer getPackOffer(long oid) {
        return (PackOffer) Templar.getOne("SELECT * FROM PACK_OFFER WHERE oid = ?", PackOffer.class, oid);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PackOffer> getPackOfferList() {
        return Templar.getList("SELECT * FROM PACK_OFFER", PackOffer.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PackOffer> getPackOfferByPack(Pack pack) {
        return Templar.getList("SELECT * FROM PACK_OFFER WHERE pid = ?", PackOffer.class, pack.getPid());
    }
}
