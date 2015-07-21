package com.meitu.scrawl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by mtdiannao on 2015/7/20.
 */
public class BufferUtils {

    public static FloatBuffer getBufferFromFloatArray(float[] array) {
        FloatBuffer result = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).position(0);
        return result;
    }

    public static IntBuffer getBufferFromIntArray(int[] array) {
        IntBuffer result = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).position(0);
        return result;
    }
}