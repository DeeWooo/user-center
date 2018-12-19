package com.liyou.uc.user.util;

import com.google.common.collect.Maps;
import com.liyou.uc.user.enums.RoleCode;
import com.liyou.uc.user.service.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author: ivywooo
 * @date:2018/5/3
 **/
@Component
public class ClientAdapterUtils {

    @Autowired
    private Map<String, ClientUserService> userAdapterMap;

    private static Map<String, ClientUserService> clientAdapterMap = Maps.newHashMap();
    private static Map<RoleCode, ClientUserService> roleAdapterMap = Maps.newHashMap();

    @PostConstruct
    public void postConstruct() {
        userAdapterMap.values().forEach(e -> clientAdapterMap.put(e.getClient().getType(), e));
        userAdapterMap.values().forEach(e -> roleAdapterMap.put(e.getRoleCode(), e));
    }

    public static Map<String, ClientUserService> getClientAdapterMap() {
        return clientAdapterMap;
    }

    public static Map<RoleCode, ClientUserService> getRoleAdapterMap() {
        return roleAdapterMap;
    }
}
