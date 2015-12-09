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

    public void putAccount(String userId, String email, String password, boolean flag, String imageUrl)
    {
        editor.clear();
        editor.putString("userId", userId);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("isGoogle", flag);
        editor.putString("imageUrl", imageUrl);
        editor.commit();
    }
}
