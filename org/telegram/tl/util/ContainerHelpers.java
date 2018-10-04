
package org.telegram.tl.util;

class ContainerHelpers {
    static final boolean[] EMPTY_BOOLEANS = new boolean[0];
    static final int[] EMPTY_INTS = new int[0];
    static final long[] EMPTY_LONGS = new long[0];
    static final Object[] EMPTY_OBJECTS = new Object[0];

    ContainerHelpers() {
    }

    static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = lo + hi >>> 1;
            int midVal = array[mid];
            if (midVal < value) {
                lo = mid + 1;
                continue;
            }
            if (midVal > value) {
                hi = mid - 1;
                continue;
            }
            return mid;
        }
        return ~ lo;
    }

    static int binarySearch(long[] array, int size, long value) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = lo + hi >>> 1;
            long midVal = array[mid];
            if (midVal < value) {
                lo = mid + 1;
                continue;
            }
            if (midVal > value) {
                hi = mid - 1;
                continue;
            }
            return mid;
        }
        return ~ lo;
    }
}

