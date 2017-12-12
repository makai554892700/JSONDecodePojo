package com.mayousheng.www.jsondecodepojo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makai on 17/12/12.
 */

public class ShareUtils {

    public static void shareText(Context context, String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(StaticParam.SHARE_ONLY_TEXT);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        Intent realIntent = Intent.createChooser(intent, "分享");
        realIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.getApplicationContext().startActivity(realIntent);
        } catch (Exception e) {
            Log.e("-----1", "e=" + e);
        }
    }

    public static void shareLink() {

    }

    public static void shareImg(Context context, String imagePath) {
        shareImgs(context, imagePath);
    }

    public static void shareImgs(Context context, String... imgPaths) {
        if (context == null || imgPaths == null || imgPaths.length == 0) {
            return;
        }
        ArrayList<Uri> uriList = new ArrayList<>();
        File tempFile;
        for (String imgPath : imgPaths) {
            tempFile = new File(imgPath);
            if (tempFile.exists()) {
                uriList.add(Uri.fromFile(tempFile));
            }
        }
        if (uriList.isEmpty()) {
            return;
        }
        Intent intent = new Intent();
        if (uriList.size() == 1) {
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uriList.get(0));
        } else {
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        }
        intent.setType(StaticParam.SHARE_ALL_IMG);
        Intent realIntent = Intent.createChooser(intent, "分享");
        realIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.getApplicationContext().startActivity(realIntent);
        } catch (Exception e) {
            Log.e("-----1", "e=" + e);
        }
    }

    public static void shareItem(Context context, String title, String link) {
        // Standard message to send
        String msg = title + " " + link;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent targetedShareIntent = null;
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;
                targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                targetedShareIntent.setType("text/plain");
                if ("com.twitter.android".equals(packageName)) {
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
                } else if ("com.google.android.gm".equals(packageName)) {
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, Uri.encode(title + "\r\n" + link));
                } else if ("com.android.email".equals(packageName)) {
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, Uri.encode(title + "\n" + link));
                } else {
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
                }

                targetedShareIntent.setPackage(packageName);
                targetedShareIntents.add(targetedShareIntent);
            }

            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "分享");
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
//            context.getApplicationContext().startActivityForResult(chooserIntent, 0);
            context.getApplicationContext().startActivity(chooserIntent);
        }
    }

}
