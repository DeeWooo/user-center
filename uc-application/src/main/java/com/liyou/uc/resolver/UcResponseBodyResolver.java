package com.liyou.uc.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.common.json.RawValue;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.web.resolver.CustomResponseBodyResolver;
import com.liyou.uc.model.UcResponse;
import com.liyou.uc.user.web.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;


/**
 * @author: ivywooo
 * @date:2018/4/25
 **/
@ControllerAdvice
public class UcResponseBodyResolver extends CustomResponseBodyResolver {
    private final static Logger logger = LoggerFactory.getLogger(UcResponseBodyResolver.class);

    @Override
    protected boolean isSkipPacket(Object body) {
        //判断是否已经封包了
        if (body instanceof UcResponse || body instanceof JSONPObject || body instanceof MappingJacksonValue) {
            return true;
        }
        return false;
    }

    @Override
    protected Object successPacket(Object value) {
        if (value instanceof String) {
            try {
                return JSONUtils.toJSON(UcResponse.success(new RawValue((String) value)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return UcResponse.success(value);
    }

    @Override
    protected Object failurePacket(Exception exception) {
//        if( exception instanceof NoLoginException ||
//                exception instanceof NoAuthorityException){
//            return UcResponse.unAuthentication(exception.getMessage());
//        }
        logger.error("", exception);
        return UcResponse.failure(exception);
    }
}
