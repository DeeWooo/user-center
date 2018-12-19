package com.liyou.uc.model;

import com.liyou.framework.base.user.User;
import com.liyou.framework.base.user.UserAware;
import com.liyou.framework.base.utils.ContextUtils;
import com.liyou.framework.base.utils.StringUtils;
import com.liyou.framework.web.util.WebUtils;
import com.liyou.uc.user.dto.UserContext;
import com.liyou.uc.user.service.UserFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * @author ivywooo
 */
@Component
public class UcUserAware implements UserAware<Integer> {

    private static final String TOKEN_KEY = "TBSAccessToken";
    private static final String CLIENT_TYPE = "Client_Type";

    @Autowired
    private UserFacadeService userFacadeService;

    private static final Logger logger = LoggerFactory.getLogger(UcUserAware.class);

    public UcUserAware() {
        ContextUtils.setDelegate(this);
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    @Override
    public User<Integer> currentUser() {

        String token = WebUtils.findHeader(TOKEN_KEY);

        String clientType = WebUtils.findHeader(CLIENT_TYPE);

        if (StringUtils.isBlank(token)||
                StringUtils.isBlank(clientType)) {
            return null;
        }

        try {
            UserContext userContext =
                    userFacadeService.getUserByAuthorization(token, clientType, clientType);
            if (null != userContext) {
                return () -> userContext.getUser().getUserId().intValue();
            }

        } catch (Exception e) {
            logger.warn("获取用户错误", e);

        }
        return null;
    }

}
