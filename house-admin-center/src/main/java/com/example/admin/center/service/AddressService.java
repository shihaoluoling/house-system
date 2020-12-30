package com.example.admin.center.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shihao
 * @Title: addressService
 * @ProjectName Second-order-center
 * @Description: 地址
 * @date Created in
 * @Version: $
 */
@FeignClient(name = "user-center",path ="/user" )
@Component
public interface AddressService {
    @RequestMapping(value = "/Address/getIngAndLat")
    JSONObject getIngAndLat(@RequestParam("address") String address);
}
