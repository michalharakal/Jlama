// Generated by jextract

package com.github.tjake.jlama.math.panama;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public class VectorNativeSimd  {

    public static final OfByte C_CHAR = Constants$root.C_CHAR$LAYOUT;
    public static final OfShort C_SHORT = Constants$root.C_SHORT$LAYOUT;
    public static final OfInt C_INT = Constants$root.C_INT$LAYOUT;
    public static final OfLong C_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static final OfLong C_LONG_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static final OfFloat C_FLOAT = Constants$root.C_FLOAT$LAYOUT;
    public static final OfDouble C_DOUBLE = Constants$root.C_DOUBLE$LAYOUT;
    public static final OfAddress C_POINTER = Constants$root.C_POINTER$LAYOUT;
    public static MethodHandle dot_product$MH() {
        return RuntimeHelper.requireNonNull(constants$0.dot_product$MH,"dot_product");
    }
    /**
     * {@snippet :
     * float dot_product(short* a, int aoffset, short* b, int boffset, int length);
     * }
     */
    public static float dot_product(MemorySegment a, int aoffset, MemorySegment b, int boffset, int length) {
        var mh$ = dot_product$MH();
        try {
            return (float)mh$.invokeExact(a, aoffset, b, boffset, length);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle accumulate$MH() {
        return RuntimeHelper.requireNonNull(constants$0.accumulate$MH,"accumulate");
    }
    /**
     * {@snippet :
     * void accumulate(short* a, short* b, int length);
     * }
     */
    public static void accumulate(MemorySegment a, MemorySegment b, int length) {
        var mh$ = accumulate$MH();
        try {
            mh$.invokeExact(a, b, length);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle saxpy$MH() {
        return RuntimeHelper.requireNonNull(constants$0.saxpy$MH,"saxpy");
    }
    /**
     * {@snippet :
     * void saxpy(float a, short* x, int xoffset, short* y, int yoffset, int length);
     * }
     */
    public static void saxpy(float a, MemorySegment x, int xoffset, MemorySegment y, int yoffset, int length) {
        var mh$ = saxpy$MH();
        try {
            mh$.invokeExact(a, x, xoffset, y, yoffset, length);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle scale$MH() {
        return RuntimeHelper.requireNonNull(constants$0.scale$MH,"scale");
    }
    /**
     * {@snippet :
     * void scale(float factor, short* t, int offset, int length);
     * }
     */
    public static void scale(float factor, MemorySegment t, int offset, int length) {
        var mh$ = scale$MH();
        try {
            mh$.invokeExact(factor, t, offset, length);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle debug$MH() {
        return RuntimeHelper.requireNonNull(constants$0.debug$MH,"debug");
    }
    /**
     * {@snippet :
     * void debug(short* x, int length);
     * }
     */
    public static void debug(MemorySegment x, int length) {
        var mh$ = debug$MH();
        try {
            mh$.invokeExact(x, length);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}


