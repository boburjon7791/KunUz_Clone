package com.example.demo.utils;

public class Utils {
    /*
    * this method can help setting default page and size values,
    * if not exist.
    * page default value=0,
    * size default value=10
    * */
    public static int[] number(String page,String size){
        int pageNumber=0;
        int pageSize=10;
        try {
            pageNumber= Integer.parseInt(page);
        }catch (Exception ignore){}
        try {
            pageSize= Integer.parseInt(size);
        }catch (Exception ignore){}
        if (pageSize==0) {
            pageSize++;
        }
        return new int[]{pageNumber,pageSize};
    }
}
