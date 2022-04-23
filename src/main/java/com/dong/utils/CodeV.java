package com.dong.utils;

public class CodeV {
    private  String[] patch={"00000","0000","000","00","0"};
    public String codeV(String email){
        long hash = email.hashCode();
        int x=165194;
        long c=hash ^ x;
        long l = System.currentTimeMillis();
        long result=c ^ l;
        long code=result%100000;
        code=code<0?-code:code;
        String num=code+"";
        int len = num.length();
        String code1=patch[len-1]+num;
        return code1;
    }

//    public static void main(String[] args) {
//        System.out.println(new CodeV().codeV("123"));
//    }
}
