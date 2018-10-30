package org.casual.entity;

import lombok.Data;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Data
public class PackOffer {

    private long oid;

    private long pid;

    private int offerType;

    private double quantity;
}
