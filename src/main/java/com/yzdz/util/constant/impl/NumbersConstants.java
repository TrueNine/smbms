package com.yzdz.util.constant.impl;

import com.yzdz.util.constant.IntConstantInter;

/**
 * 一些数值型的常量
 *
 * @author TrueNine
 * @version 1.0
 * @date 2020/6/11
 */
public enum NumbersConstants implements IntConstantInter {
    ONE(1),
    TWO(2),
    THREE(3);

    private int num;

    NumbersConstants(int number) {
        this.num = number;
    }

    @Override
    public Integer val() {
        return num;
    }
}
