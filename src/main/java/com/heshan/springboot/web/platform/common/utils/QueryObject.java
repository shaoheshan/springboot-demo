package com.heshan.springboot.web.platform.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heshan.springboot.web.platform.common.xss.SQLFilter;

/**
 * 查询参数
 *
 * @author
 * @email
 * @date 2012-03-14 23:15
 */
public class QueryObject extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    // 当前页码
    private Integer page = 1;
    // 每页条数(-1则不分页)
    private Integer limit = 20;

    public QueryObject(Map<String, Object> params) {
        this.putAll(params);

        // 分页参数
        Integer p = CommUtils.null2Int(params.get("page"));
        String l = CommUtils.null2Str(params.get("limit"));
        if (p > 0) {
            this.page = p;
        }
        if (StringUtils.isNotBlank(l)) {
            this.limit = Integer.parseInt(l);
        }

        // 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = CommUtils.null2Str(params.get("sidx"));
        String order = CommUtils.null2Str(params.get("order"));
        this.put("sidx", SQLFilter.sqlInject(sidx));
        this.put("order", SQLFilter.sqlInject(order));

        // page、limit参数<1则不分页
        if (this.limit <= 0) {
            this.put("offset", null);
            this.put("page", page);
            this.put("limit", null);
        } else {
            this.put("offset", (page - 1) * limit);
            this.put("page", page);
            this.put("limit", limit);
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
