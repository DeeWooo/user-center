package com.liyou.uc.user.util;

import com.google.common.collect.Maps;
import com.liyou.uc.user.enums.ThirdPartyChannel;
import com.liyou.uc.user.service.ThirdPartyAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author ivywu
 */
@Component
public class ThirdPartyAuthClientAdapteUtils {

    @Autowired
    private Map<String, ThirdPartyAuthClient> thirdPartyAuthClientMap;


    private static Map<ThirdPartyChannel, ThirdPartyAuthClient> thirdPartyChannelThirdPartyAuthClientMap = Maps.newHashMap();

    @PostConstruct
    public void postConstruct() {
        thirdPartyAuthClientMap.values().forEach(e -> thirdPartyChannelThirdPartyAuthClientMap.put(e.getChannel(), e));
    }

    public static Map<ThirdPartyChannel, ThirdPartyAuthClient> getThirdPartyChannelThirdPartyAuthClientMap() {
        return thirdPartyChannelThirdPartyAuthClientMap;
    }


}
