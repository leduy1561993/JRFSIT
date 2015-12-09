// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import vn.edu.uit.jrfsit.entity.Account;

public class AccountPreferences
{

    private String DATA;
    SharedPreferences.Editor editor;
    private SharedPreferences pre;

    public AccountPreferences(Context context)
    {
        DATA = "userLogin";
        pre = context.getSharedPreferences(DATA, 0);
        editor = pre.edit();
    }

    public void clearData()
    {
        editor.clear();
        editor.commit();
    }

    public Account getAccount()
    {
        return new Account(pre.getString("userId", ""), pre.getString("email", ""), pre.getString("password", ""), pre.getString("imageUrl", "https://cdn4.iconfinder.com/data/icons/linecon/512/photo-64.png"), pre.getBoolean("isGoogle", false));
    }

    public void putAccount(String s, String s1, String s2, boolean flag, String s3)
    {
        editor.clear();
        editor.putString("userId", s);
        editor.putString("email", s1);
        editor.putString("password", s2);
        editor.putBoolean("isGoogle", flag);
        editor.putString("imageUrl", s3);
        editor.commit();
    }
}
