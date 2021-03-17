package com.apollo.trace;

public class Trace {
    private int x;
    private int y;
    private int z;
    private TraceType traceType;

    public Trace(TraceType traceType, int x, int y, int z) {
        this.traceType = traceType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] getLoc(int deltaT) {
        switch (traceType) {
            case FIXED_POINT:
                return getFixedPointLoc();
            case STRAIGHT_LINE:
                return getStraightLine(deltaT);
        }
        return null;
    }

    private int[] getFixedPointLoc() {
        return new int[] {x, y, z};
    }

    private int[] getStraightLine(int deltaT) {
        return null;
    }
}

