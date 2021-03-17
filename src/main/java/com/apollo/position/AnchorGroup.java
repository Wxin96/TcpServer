package com.apollo.position;

import org.ujmp.core.Matrix;

public class AnchorGroup {
    private int spatialDimension = 3;
    private Matrix anchorGroup;

    public AnchorGroup(Matrix anchorGroup) {
        this.anchorGroup = anchorGroup;
    }

    public AnchorGroup(double[][] anchorGroup) {
        if (anchorGroup == null || anchorGroup.length <= 0 || anchorGroup[0] == null || anchorGroup[0].length <= 0) {
            throw new RuntimeException("输入参数有误！");
        }
        int m = anchorGroup.length;
        int n = anchorGroup[0].length;
        this.anchorGroup = Matrix.Factory.zeros(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                this.anchorGroup.setAsDouble(anchorGroup[i][j], i, j);
            }
        }
    }

    @Override
    public String toString() {
        return "AnchorGroup{ " +
                "anchorGroup=\n" + anchorGroup +
                '}';
    }
}
