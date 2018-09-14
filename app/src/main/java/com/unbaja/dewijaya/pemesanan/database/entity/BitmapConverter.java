package com.unbaja.dewijaya.pemesanan.database.entity;

import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by sigit on 16/08/2018.
 */

public class BitmapConverter {

    @TypeConverter
    public static Bitmap byteArratToBitmap(byte[] bytes) {
        Bitmap bitmap = null;
        if(bytes != null) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }

    @TypeConverter
    public static byte[] BitmapToByteArray(Bitmap bitmap) {
        byte[] bytes = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bytes = stream.toByteArray();
        }
        return bytes;
    }
}
