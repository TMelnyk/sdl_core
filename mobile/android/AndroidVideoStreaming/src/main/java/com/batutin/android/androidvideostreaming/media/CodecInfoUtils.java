package com.batutin.android.androidvideostreaming.media;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;

import com.batutin.android.androidvideostreaming.activity.ALog;

import java.util.ArrayList;
import java.util.List;

public class CodecInfoUtils {

    public static final String MIME_TYPE = "video/avc";

    /**
     * Returns the first codec capable of encoding the specified MIME type, or null if no
     * match was found.
     */
    public static MediaCodecInfo selectFirstCodec(String mimeType) throws IllegalArgumentException {
        ALog.d("start getting MediaCodecInfo");
        List<MediaCodecInfo> infoList = getSupportedMediaCodecInfoList(mimeType);
        if (infoList == null || infoList.size() <= 0) {
            ALog.d("failed getting MediaCodecInfo");
            throw new IllegalArgumentException("no available codecs");
        }
        return infoList.get(0);
    }

    public static List<MediaCodecInfo> getSupportedMediaCodecInfoList(String mimeType) throws IllegalArgumentException {
        int numCodecs = MediaCodecList.getCodecCount();
        if (numCodecs <= 0) {
            throw new IllegalArgumentException("no available codecs");
        }
        List<MediaCodecInfo> infoList = new ArrayList<MediaCodecInfo>(1);
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            if (isCodecTypeMimeType(mimeType, codecInfo)) {
                infoList.add(codecInfo);
            }
        }
        return infoList;
    }

    private static boolean isCodecTypeMimeType(String mimeType, MediaCodecInfo codecInfo) {
        String[] types = codecInfo.getSupportedTypes();
        for (int j = 0; j < types.length; j++) {
            if (types[j].equalsIgnoreCase(mimeType)) {
                return true;
            }
        }
        return false;
    }
}