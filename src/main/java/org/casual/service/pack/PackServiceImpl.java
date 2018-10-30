package org.casual.service.pack;

import org.casual.dao.pack.PackDAO;
import org.casual.dao.util.DaoFactory;
import org.casual.entity.Pack;
import org.casual.entity.PackOffer;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class PackServiceImpl implements PackService {

    private PackDAO packDAO = DaoFactory.getInstance().getPackDAO();

    @Override
    public ResultMessage addPack(Pack pack) {
        return packDAO.addPack(pack);
    }

    @Override
    public ResultMessage deletePack(long pid) {
        return packDAO.deletePack(pid);
    }

    @Override
    public ResultMessage updatePack(Pack pack) {
        return packDAO.updatePack(pack);
    }

    @Override
    public Pack getPack(long pid) {
        return packDAO.getPack(pid);
    }

    @Override
    public List<Pack> getPackList() {
        return packDAO.getPackList();
    }

    @Override
    public ResultMessage addPackOffer(PackOffer offer) {
        return packDAO.addPackOffer(offer);
    }

    @Override
    public ResultMessage deletePackOffer(long oid) {
        return packDAO.deletePackOffer(oid);
    }

    @Override
    public ResultMessage updatePackOffer(PackOffer offer) {
        return packDAO.updatePackOffer(offer);
    }

    @Override
    public PackOffer getPackOffer(long oid) {
        return packDAO.getPackOffer(oid);
    }

    @Override
    public List<PackOffer> getPackOfferList() {
        return packDAO.getPackOfferList();
    }

    @Override
    public List<PackOffer> getPackOfferByPack(Pack pack) {
        return packDAO.getPackOfferByPack(pack);
    }
}
