package com.liyou.uc.user.util;

import com.liyou.framework.base.utils.ExceptionUtils;
import com.liyou.framework.base.utils.StringUtils;
import com.liyou.uc.constant.ErrorCode;
import com.liyou.uc.user.exception.UserCenterException;
import com.liyou.uc.util.HexConvertUtils;

/**
 * @author: ivywooo
 * @date:2018/4/8
 **/

public class ShareCodeUtils {

    private static final int LENGTH = 7;

    /**
     * 工具类禁止实例化
     */
    private ShareCodeUtils() {
        throw new AssertionError();
    }

    public static void verifyInviteCode(String inviteCode) {
        boolean isThrow = StringUtils.isNotBlank(inviteCode) && inviteCode.length() > LENGTH;
        ExceptionUtils.tryThrow(isThrow, new UserCenterException(ErrorCode.PARAM_ERR, "注册邀请码不能超过7位"));
    }

    /**
     * 生成注册用户分享码
     *
     * @param userId
     * @return
     */
    public static String encode2ShareCode(Long userId) {
        //todo 分享码,userId不能>9位，否则不能还原
        String code = "000000" + HexConvertUtils.decToX36(userId);
        String shareCode = code.substring(code.length() - LENGTH, code.length());
        return shareCode;
    }

    public static Long unEncode2UserId(String inviteCode) {

        return HexConvertUtils.x36ToDec(inviteCode);
    }

    public static void main(String[] args) {
//        System.out.println(encode2ShareCode(999999999L));
//        System.out.println(unEncode2UserId("0gjdgxr"));
        System.out.println(encode2ShareCode(36L));
        System.out.println(encode2ShareCode(31L));
    }

}
