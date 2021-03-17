package com.apollo.position;

import java.util.Arrays;
import java.util.Random;

/**
 * 输入基站和标签: unit m
 * 输出: unit: mm
 */
public class RangingUtil {

    private static byte rangingNum = 0;

    public static enum RangingMode {
        LOS, NLOS;
    }

    public static String ranging(double[][] anchorGroup, double[] tagLoc, double losSd, double nlosBias,
                                 double nlosSd, RangingMode mode) {
        int[] rangingData;  // 测距数据, unit: mm
        if (mode == RangingMode.LOS) {
            rangingData = ranging(anchorGroup, tagLoc, 0, losSd);
            return formatData(rangingData);
        } else if (mode == RangingMode.NLOS) {

        } else {
            throw new RuntimeException("输入参数有误!");
        }
        return null;
    }

    private static int[] ranging(double[][] anchorGroup, double[] tagLoc, double mean, double losSd) {
        int anchorNum = anchorGroup.length;
        double[] rangingData = new double[anchorNum];
        int[] rangingData_MM = new int[anchorNum];
        for (int i = 0; i < anchorNum; i++) {
            rangingData[i] = singleRanging(anchorGroup[i], tagLoc, mean, losSd);
            // 从 米 => 毫米
            rangingData_MM[i] = (int) Math.round(rangingData[i] * 1000);
        }

        return rangingData_MM;
    }

    // 单次测距
    private static double singleRanging(double[] anchor, double[] tag, double mean, double sd) {
        double squareSum = 0;
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            squareSum += Math.pow(anchor[i] - tag[i], 2);
        }
        // 加入误差
        return Math.sqrt(squareSum) + random.nextGaussian() * sd + mean;
    }


    private static String formatData(int[] rangingData) {
        int mask = 0;
        rangingData = Arrays.copyOf(rangingData, 4);
        for (int i = 0; i < 4; i++) {
            if (rangingData[i] != 0) mask |= 1 << i;
        }

        return String.format("mc %02x %08x %08x %08x %08x %04x %02x %08x a0:0\r\n", mask, rangingData[0],
                rangingData[1], rangingData[2], rangingData[3], 0, rangingNum++, 0);
    }

}
