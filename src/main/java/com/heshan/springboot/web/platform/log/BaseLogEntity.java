package com.heshan.springboot.web.platform.log;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志信息收集(HTTP Response headers使用)
 *
 * @author Frank
 * @version 2.0
 * @date 2017/9/2 22:45
 */
@Data
public class BaseLogEntity implements Serializable {
    private int port;//端口
    private String remoteUser;//当前登陆人
    private String serverIp;//当前服务节点IP
    private String threadToken;//请求链线程token

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date startTime = new Date();//请求开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date endTime;//请求结束时间

    public static BaseLogEntity builder(BaseLogEntity obj) {
        BaseLogEntity target = new BaseLogEntity();
        target.setPort(obj.getPort());
        target.setRemoteUser(obj.getRemoteUser());
        target.setServerIp(obj.getServerIp());
        target.setThreadToken(obj.getThreadToken());
        target.setStartTime(obj.getStartTime());
        target.setEndTime(obj.getEndTime());
        return target;
    }

    /**
     * 消耗时长=请求开始时间-请求结束时间
     *
     * @return
     */
    public Long getWasteTime() {
        return this.endTime.getTime() - this.startTime.getTime();
    }

}
