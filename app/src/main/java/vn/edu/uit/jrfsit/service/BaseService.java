// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.service;

import vn.edu.uit.jrfsit.connect.Connect;

public class BaseService
{
    public static String ROOT_URL_EMULATOR = "http://10.0.3.2/";
    public static String ROOT_URL_PHONE ="http://192.168.1.10/";
    public static String ROOT_URL_PHONE_COMPANY_1 ="http://192.168.3.34/";
    public static String ROOT_URL_PHONE_COMPANY = "http://172.16.10.243/";
    public static String ROOT_URL  = ROOT_URL_PHONE;
    public BaseService()
    {
    }

    public Connect initConnection(String s)
    {
        return new Connect((new StringBuilder()).append(ROOT_URL).append(s).toString());
    }
}
