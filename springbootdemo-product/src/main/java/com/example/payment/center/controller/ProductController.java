package com.example.payment.center.controller;


import com.example.springbootdemoentity.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author YangPeng
 * @Title: ProductController
 * @ProjectName springbootdemo
 * @Description: TODO
 * @date 2019/3/25-19:26
 */
@RestController
public class ProductController {
    @RequestMapping(value = "getProduct")
    public String getProduct() {
     Product product = new Product();
        System.out.println("我是服务的提供者");
        return product.toString();
    }
}
