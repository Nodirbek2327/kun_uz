package com.example.service;

import com.example.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {

    @Value("${my.eskiz.url}")
    private String url;

    @Value("${my.eskiz.uz.email}")
    private String email;

    @Value("${my.eskiz.uz.password}")
    private String password;

    public void send(String phone, String message) {

    }

    public void sendRegistrationSms(String phone) {
        String smsCode = RandomUtil.getRandomSmsCode();
        String text = "Kunuz-test ro'yhatdan o'tish uchun tasdiqlash kodi: \n" + smsCode;
       // sendSmsHTTP(phone, text, smsCode);
//        smsSenderService.sendSmsHTTP(smsHistory); TODO
    }


}
