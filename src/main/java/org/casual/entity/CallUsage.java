package org.casual.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/22
 */
@Data
public class CallUsage {

    private long callId;

    /**
     * user id
     */
    private long uid;

    /**
     * the time that phone call is committed
     */
    private LocalDateTime finishDatetime;

    /**
     * the time the call lasts for
     */
    private double callTime;
}
