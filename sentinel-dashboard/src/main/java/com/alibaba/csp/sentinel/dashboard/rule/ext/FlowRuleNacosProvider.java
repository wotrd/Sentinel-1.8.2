package com.alibaba.csp.sentinel.dashboard.rule.ext;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author: wotrd
 * @date: 2021/11/6 20:54
 */
@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    public static final String FLOW_DATA_ID_POSTFIX = "-flow-sentinel";
    public static final String GROUP_ID = "DEFAULT_GROUP";

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + FLOW_DATA_ID_POSTFIX, GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
