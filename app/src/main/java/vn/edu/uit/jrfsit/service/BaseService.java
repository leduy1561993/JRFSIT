package vn.edu.uit.jrfsit.service;


import vn.edu.uit.jrfsit.connect.Connect;

/**
 * Created by LeDuy on 11/19/2015.
 */
public class BaseService {
    public static String ROOT_URL_EMULATOR ="http://10.0.3.2/";
    public static String ROOT_URL_PHONE_COMPANY ="http://172.16.10.243/";
    public static String ROOT_URL_PHONE ="http://192.168.1.10/";
    public static String ROOT_URL =ROOT_URL_EMULATOR;

    public Connect initConnection(String connectionString){
        Connect connect = new Connect(ROOT_URL+connectionString);
        return connect;
    }
}
