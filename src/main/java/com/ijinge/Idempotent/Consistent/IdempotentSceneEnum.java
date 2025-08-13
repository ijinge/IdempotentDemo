package com.ijinge.Idempotent.Consistent;

public enum IdempotentSceneEnum {
    /**
     * 基于 RestAPI 场景验证
     */
    RESTAPI,
    /**
     * 基于 MQ 场景验证
     */
    MQ
}
