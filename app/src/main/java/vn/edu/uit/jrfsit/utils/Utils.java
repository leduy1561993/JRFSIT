package vn.edu.uit.jrfsit.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by LeDuy on 10/21/2015.
 */
public class Utils {
    public static void print(Context context,String str){
        Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
    }
}
