package com.apollo.test;

import com.apollo.position.AnchorGroup;
import com.apollo.position.RangingUtil;
import org.junit.Test;
import org.ujmp.core.Matrix;

import java.util.Arrays;
import java.util.Random;

public class CommonTest {
    @Test
    public void testMatrix() {
        Matrix zeros = Matrix.Factory.zeros(5, 4);
        System.out.println(zeros);
    }

    @Test
    public void testAnchorGroup() {
        double[][] anchorGroup = {{0, 0, 0}, {0, 5, 5}, {5, 0, 10}, {5, 5, 8}};
        System.out.println(Arrays.deepToString(anchorGroup));
        AnchorGroup anchorGroupMatrix = new AnchorGroup(anchorGroup);
        System.out.println(anchorGroupMatrix);
    }

    @Test
    public void testSquare() {
        System.out.println(Math.pow(2, 4));
        Random random = new Random();
        System.out.println(random.nextGaussian());
    }

    @Test
    public void testSingleRanging() {
        double[] anchor = {0, 0, 0};
        double[] tag = {3, 4, 12};
        // double singleRanging = RangingUtil.singleRanging(anchor, tag, 0, 0.1);
        // System.out.println(singleRanging);
    }

    @Test
    public void testRanging() {
        double[][] anchor = {{3, 4, 12}, {1, 2, 2}, {4, 4, 2}, {0, 4, 3}};
        double[] tag = {0, 0, 0};
        System.out.println(RangingUtil.ranging(anchor, tag, 0.1, 0, 0.15,
                        RangingUtil.RangingMode.LOS));
    }
}