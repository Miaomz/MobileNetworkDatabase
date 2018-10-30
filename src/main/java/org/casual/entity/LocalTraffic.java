package org.casual.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Data
public class LocalTraffic {

    private long ltId;

    private long uid;

    private LocalDateTime finishDatetime;

    private double traffic;
}
