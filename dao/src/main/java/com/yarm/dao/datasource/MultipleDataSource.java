package com.yarm.dao.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * Created with IDEA
 * author:Yarm.Yang
 * Date:2019/10/23
 * Time:15:46
 * Des:多数据源
 */
@Slf4j
public class MultipleDataSource extends AbstractRoutingDataSource {
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("current dataSourceKey:"+DataSourceUtils.getCurrentKey());
        return DataSourceUtils.getCurrentKey();
    }
}