package com.liyou.uc.user.web.otherapi;

import com.liyou.uc.others.dto.CustomerInfoParam;
import com.liyou.uc.others.service.CustomerInfoService;
import com.liyou.uc.user.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author: ivywooo
 * @date:2018/5/30
 **/
@RestController
@RequestMapping(value = "/other-api")
public class OthersController {

    @Autowired
    private CustomerInfoService customerInfoService;

    @PostMapping("/customer-info/id")
    public Long getCustomerId(
            @RequestBody CustomerInfoParam customerInfoParam){

        return customerInfoService.getNewCustomerId(customerInfoParam);

    }

    @GetMapping("find-userid-by-token")
    public JwtTokenUtils.TokenInfo findUserId(@RequestParam (value = "token") String token) throws UnsupportedEncodingException {
        JwtTokenUtils.TokenInfo tokenInfo = new JwtTokenUtils.TokenInfo();
        JwtTokenUtils.parseToken(token,tokenInfo);
        return tokenInfo;
    }
}
