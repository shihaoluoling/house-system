package com.example.user.center.config;

/**
 * @author shihao
 * @Title: MyPasswordEncoder
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/**
 * 自定义的密码加密方法，实现了PasswordEncoder接口
 * @author 程就人生
 *
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
//        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
//        System.out.println(bcryptPasswordEncoder.encode(charSequence)+"密码");
        //加密方法可以根据自己的需要修改
//        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
//        bcryptPasswordEncoder.matches(charSequence,encodedPassword);
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
//        System.out.println(s);
//        System.out.println(charSequence);
        BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
        boolean a = bcp.matches(charSequence,s);
        System.out.println(a);
        return a;
    }
}
