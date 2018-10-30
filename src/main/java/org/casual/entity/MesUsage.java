package org.casual.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
@Data
public class MesUsage {

    private long mesId;

    private long uid;

    /**
     * the time that message is committed
     */
    private LocalDateTime finishDatetime;
}
