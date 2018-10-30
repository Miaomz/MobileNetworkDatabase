package org.casual.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Data
public class DomesticTraffic {

    private long dtId;

    private long uid;

    private LocalDateTime finishDatetime;

    private double traffic;
}
