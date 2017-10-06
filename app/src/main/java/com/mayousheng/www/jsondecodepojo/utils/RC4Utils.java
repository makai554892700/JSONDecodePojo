package com.mayousheng.www.jsondecodepojo.utils;

public class RC4Utils {

    private static final String CODE = "UTF-8";

    public static String deCode(String key, String value) {
        if (null == value || "".equals(value)) {
            return "";
        }
        String tempStr;
        try {
            tempStr = new String(hexStringToBytes(value), CODE);
        } catch (Exception e) {
            System.out.println("e=" + e);
            tempStr = new String(hexStringToBytes(value));
        }
        return RC4(key, tempStr);
    }

    public static String enCode(String key, String value) {
        if (null == value || "".equals(value)) {
            return "";
        }
        String tempStr = RC4(key, value);
        byte[] tempByte;
        try {
            tempByte = tempStr.getBytes(CODE);
        } catch (Exception e) {
            tempByte = tempStr.getBytes();
        }
        return bytesToHexString(tempByte);
    }

    private static String RC4(String key, String value) {
        int[] iS = new int[256];
        byte[] iK = new byte[256];
        for (int i = 0; i < 256; i++)
            iS[i] = i;
        for (short i = 0; i < 256; i++) {
            iK[i] = (byte) key.charAt((i % key.length()));
        }
        int j = 0;
        for (int i = 0; i < 255; i++) {
            j = (j + iS[i] + iK[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
        }
        int i = 0;
        j = 0;
        char[] iInputChar = value.toCharArray();
        char[] iOutputChar = new char[iInputChar.length];
        for (short x = 0; x < iInputChar.length; x++) {
            i = (i + 1) % 256;
            j = (j + iS[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
            int t = (iS[i] + (iS[j] % 256)) % 256;
            int iY = iS[t];
            char iCY = (char) iY;
            iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
        }
        return new String(iOutputChar);
    }

    public static String bytesToHexString(byte[] srcs) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (srcs == null || srcs.length <= 0) {
            return null;
        }
        for (byte src : srcs) {
            int v = src & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String hexStringToString(String hexString) {
        String result = null;
        byte[] data = hexStringToBytes(hexString);
        try {
            result = new String(data, CODE);
        } catch (Exception e) {
            System.out.println("e=" + e);
        }
        return result;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}