package com.heshan.springboot.web.platform.support;

import java.beans.Transient;
import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

/**
 * @Title: AbstractEntity.java
 * @Package com.heshan.springboot.web.platform.support
 * @Description: 抽象业务模型实体类
 * @author Frank
 * @Company www.flyheze.top
 * @date 2017年8月12日 上午9:26:56
 * @version V2.0
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity<ID extends Serializable> implements Serializable, Persistable<ID> {
    // 主键
    private ID id;

    /**
     * 设置：主键
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    @Override
    public ID getId() {
        return id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        AbstractEntity<?> that = (AbstractEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
