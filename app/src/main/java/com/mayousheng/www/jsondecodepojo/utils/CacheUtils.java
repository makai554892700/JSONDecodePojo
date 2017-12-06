package com.mayousheng.www.jsondecodepojo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.mayousheng.www.jsondecodepojo.utils.cache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by marking on 2017/4/12.
 */

public class CacheUtils {

    public static final String CACHE_PATH = "bitmap";

    private static CacheUtils cacheUtils;
    private DiskLruCache diskLruCache;

    private CacheUtils() {
    }

    public static void init(Context context) {
        init(getDiskCacheDir(context.getApplicationContext()
                , context.getApplicationContext().getPackageName())
                , getAppVersion(context.getApplicationContext()));
    }

    public static void init(File cacheDir, int appVersion) {
        if (cacheUtils == null) {
            cacheUtils = new CacheUtils();
        }
        if (cacheUtils.diskLruCache == null && cacheDir != null && appVersion != 0) {
            try {
                cacheUtils.diskLruCache = DiskLruCache.open(cacheDir, appVersion, 1, 10 * 1024 * 1024);
            } catch (IOException e) {
            }
        }
    }

    public static CacheUtils getInstance() {
        return cacheUtils;
    }

    public Bitmap getBitmapByDisk(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String key = hashKeyForDisk(imageUrl);
            return getBitmap(diskLruCache, key);
        } else {
            return null;
        }
    }

    public Bitmap getBitmap(String imageUrl) {
        Bitmap result = null;
        do {
            if (imageUrl == null || imageUrl.isEmpty()) {
                break;
            }
            String key = hashKeyForDisk(imageUrl);
            result = getBitmap(diskLruCache, key);
            if (result != null) {
                break;
            }
            DiskLruCache.Editor editor;
            try {
                editor = diskLruCache.edit(key);
            } catch (Exception e) {
                break;
            }
            if (editor == null) {
                break;
            }
            OutputStream outputStream;
            try {
                outputStream = editor.newOutputStream(0);
            } catch (Exception e) {
                break;
            }
            if (downloadUrlToStream(imageUrl, outputStream)) {
                try {
                    editor.commit();
                } catch (Exception e) {
                    break;
                }
            } else {
                try {
                    editor.abort();
                } catch (Exception e) {
                    break;
                }
            }
            try {
                diskLruCache.flush();
            } catch (Exception e) {
                break;
            }
            result = getBitmap(diskLruCache, key);
        } while (false);
        return result;
    }

    private Bitmap getBitmap(DiskLruCache diskLruCache, String key) {
        Bitmap result = null;
        do {
            DiskLruCache.Snapshot snapShot;
            try {
                snapShot = diskLruCache.get(key);
            } catch (IOException e) {
                break;
            }
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                result = BitmapFactory.decodeStream(is);
            }
        } while (false);
        return result;
    }

    private Drawable getDrawable(String imageUrl) {
        return new BitmapDrawable(getBitmap(imageUrl));
    }

    private Drawable getDrawable(DiskLruCache diskLruCache, String key) {
        return new BitmapDrawable(getBitmap(diskLruCache, key));
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeSilently(out);
            closeSilently(in);
        }
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        if (mDigest == null) {
            cacheKey = String.valueOf(key.hashCode());
        } else {
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

}
