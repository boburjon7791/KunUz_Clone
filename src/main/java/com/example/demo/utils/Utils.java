package com.example.demo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Utils {
    /*
    * this method can help setting default page and size values,
    * if not exist.
    * page default value=0,
    * size default value=10
    * */
    public static int[] number(String page){
        int pageNumber=0;
        int pageSize=10;
        try {
            pageNumber= Integer.parseInt(page);
        }catch (Exception ignore){}
        return new int[]{pageNumber,pageSize};
    }
}
