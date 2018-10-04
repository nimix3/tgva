
package org.telegram.tl.util;

import java.lang.reflect.Array;

public class ArrayUtils {
    private static Object[] EMPTY = new Object[0];
    private static final int CACHE_SIZE = 73;
    private static Object[] sCache = new Object[73];

    public static int idealByteArraySize(int need) {
        for (int i = 4; i < 32; ++i) {
            if (need > (1 << i) - 12) continue;
            return (1 << i) - 12;
        }
        return need;
    }

    public static int idealBooleanArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need);
    }

    public static int idealShortArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need * 2) / 2;
    }

    public static int idealCharArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need * 2) / 2;
    }

    public static int idealIntArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need * 4) / 4;
    }

    public static int idealFloatArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need * 4) / 4;
    }

    public static int idealObjectArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need * 4) / 4;
    }

    public static int idealLongArraySize(int need) {
        return ArrayUtils.idealByteArraySize(need * 8) / 8;
    }

    public static boolean equals(byte[] array1, byte[] array2, int length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        if (array1 == array2) {
            return true;
        }
        if (array1 == null || array2 == null || array1.length < length || array2.length < length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array1[i] == array2[i]) continue;
            return false;
        }
        return true;
    }

    public static <T> T[] emptyArray(Class<T> kind) {
        if (kind == Object.class) {
            return EMPTY;
        }
        int bucket = (System.identityHashCode(kind) / 8 & Integer.MAX_VALUE) % 73;
        Object cache = sCache[bucket];
        if (cache == null || cache.getClass().getComponentType() != kind) {
            ArrayUtils.sCache[bucket] = cache = Array.newInstance(kind, 0);
        }
        return (Object[])cache;
    }

    public static <T> boolean contains(T[] array, T value) {
        return ArrayUtils.indexOf(array, value) != -1;
    }

    public static <T> int indexOf(T[] array, T value) {
        for (int i = 0; i < array.length; ++i) {
            if (!(array[i] == null ? value == null : value != null && array[i].equals(value))) continue;
            return i;
        }
        return -1;
    }

    public static <T> boolean containsAll(T[] array, T[] check) {
        for (T checkItem : check) {
            if (ArrayUtils.contains(array, checkItem)) continue;
            return false;
        }
        return true;
    }

    public static boolean contains(int[] array, int value) {
        int[] arrayOfInt = array;
        int j = array.length;
        for (int i = 0; i < j; ++i) {
            int element = arrayOfInt[i];
            if (element != value) continue;
            return true;
        }
        return false;
    }

    public static long total(long[] array) {
        long total = 0L;
        long[] arrayOfLong = array;
        int j = array.length;
        for (int i = 0; i < j; ++i) {
            long value = arrayOfLong[i];
            total += value;
        }
        return total;
    }

    public static <T> T[] appendElement(Class<T> kind, T[] array, T element) {
        int end;
        Object[] result;
        if (array != null) {
            end = array.length;
            result = (Object[])Array.newInstance(kind, end + 1);
            System.arraycopy(array, 0, result, 0, end);
        } else {
            end = 0;
            result = (Object[])Array.newInstance(kind, 1);
        }
        result[end] = element;
        return result;
    }

    public static <T> T[] removeElement(Class<T> kind, T[] array, T element) {
        if (array != null) {
            int length = array.length;
            for (int i = 0; i < length; ++i) {
                if (array[i] != element) continue;
                if (length == 1) {
                    return null;
                }
                Object[] result = (Object[])Array.newInstance(kind, length - 1);
                System.arraycopy(array, 0, result, 0, i);
                System.arraycopy(array, i + 1, result, i, length - i - 1);
                return result;
            }
        }
        return array;
    }

    public static int[] appendInt(int[] cur, int val) {
        if (cur == null) {
            return new int[]{val};
        }
        int N = cur.length;
        for (int i = 0; i < N; ++i) {
            if (cur[i] != val) continue;
            return cur;
        }
        int[] ret = new int[N + 1];
        System.arraycopy(cur, 0, ret, 0, N);
        ret[N] = val;
        return ret;
    }

    public static int[] removeInt(int[] cur, int val) {
        if (cur == null) {
            return null;
        }
        int N = cur.length;
        for (int i = 0; i < N; ++i) {
            if (cur[i] != val) continue;
            int[] ret = new int[N - 1];
            if (i > 0) {
                System.arraycopy(cur, 0, ret, 0, i);
            }
            if (i < N - 1) {
                System.arraycopy(cur, i + 1, ret, i, N - i - 1);
            }
            return ret;
        }
        return cur;
    }
}

