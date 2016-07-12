package com.maxleap;

import android.content.Context;

import java.io.File;

import com.maxleap.utils.FileHandle;
import com.maxleap.utils.FileHandles;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class GetLog {
    public static String getLog(Context context){
        FileHandle handle = FileHandles.absolute(context.getApplicationContext().getDir(
                "LeapCloud", Context.MODE_PRIVATE));
        FileHandle rootHandle = FileHandles.absolute(handle, "AnalyticsCache");
        File rootDirectory = rootHandle.getFile();
        File[] files = rootDirectory.listFiles();
        FileHandle reader = FileHandles.absolute(files[0]);
        return  reader.tryReadString();
    }
}
                        