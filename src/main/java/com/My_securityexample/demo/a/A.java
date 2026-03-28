package com.My_securityexample.demo.a;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A {
    public static void main(String[] args) {
        // this is changed.

        List<Integer> val = Arrays.asList(13,25,6,8,25,43,26);
        List<Integer> r = val.stream().map(x->x+10).collect(Collectors.toList());
        System.out.println(r);





    }
}
