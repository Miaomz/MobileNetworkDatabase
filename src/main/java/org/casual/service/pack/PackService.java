package org.casual.service.pack;

import org.casual.entity.Pack;
import org.casual.entity.PackOffer;
import org.casual.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public interface PackService {

    ResultMessage addPack(Pack pack);

    ResultMessage deletePack(long pid);

    ResultMessage updatePack(Pack pack);

    Pack getPack(long pid);

    List<Pack> getPackList();

    ResultMessage addPackOffer(PackOffer offer);

    ResultMessage deletePackOffer(long oid);

    ResultMessage updatePackOffer(PackOffer offer);

    PackOffer getPackOffer(long oid);

    List<PackOffer> getPackOfferList();

    List<PackOffer> getPackOfferByPack(Pack pack);
}
