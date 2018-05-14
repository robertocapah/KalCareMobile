package com.kalbenutritionals.kalcaremobie.Repo;

import android.content.Context;

import com.kalbenutritionals.kalcaremobie.Common.mConfigData;
import com.kalbenutritionals.kalcaremobie.Data.DatabaseHelper;
import com.kalbenutritionals.kalcaremobie.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Rian Andrivani on 11/21/2017.
 */

public class mConfigRepo {
    DatabaseHelper helper;
//    public String API = "http://10.171.10.19:8020/NHD/";
//    public String APIToken = "http://10.171.10.19:8020/token";
    public String strDomain = "10.171.10.28:8020";
//    public String strDomain = "10.171.10.13:8020";
//    public String strDomain = "appgw.kalbenutritionals.com/api/kalcaremobile";
    public String API = "http://"+strDomain+"/NHD/";
    public String APIToken = "http://"+strDomain+"/token";
//    public String API = "http://"+strDomain+"/NHD/";
//    public String APIToken = "http://"+strDomain+"/token/";

    public mConfigRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    public mConfigData findById(int value) throws SQLException {
        mConfigData item = null;
        try{
            item = helper.getmConfigDao().queryForId(value);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    public List<?> findAll() throws SQLException {
        List<mConfigData> items = null;
        try{
            items = helper.getmConfigDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public void InsertDefaultmConfig() throws SQLException {
        mConfigData data1 = new mConfigData();
        data1.setIntId(1);
        data1.setTxtName("android:versionCode");
        data1.setTxtValue("5");
        data1.setTxtDefaultValue("5");
        data1.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data1);

        data1 = new mConfigData();
        data1.setIntId(3);
        data1.setTxtName("Domain Kalbe");
        data1.setTxtValue("ONEKALBE.LOCAL");
        data1.setTxtDefaultValue("ONEKALBE.LOCAL");
        data1.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data1);

        data1 = new mConfigData();
        data1.setIntId(4);
        data1.setTxtName("Application Name");
        data1.setTxtValue("Kal Care");
        data1.setTxtDefaultValue("3VyizZ7haX2KCvR0wl64YwulEteHqsq5FLncJSL+pBM=");
        data1.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data1);

        data1 = new mConfigData();
        data1.setIntId(5);
        data1.setTxtName("UserId");
        data1.setTxtValue("rian.andrivani");
        data1.setTxtDefaultValue("rian.andrivani");
        data1.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data1);


        data1 = new mConfigData();
        data1.setIntId(6);
        data1.setTxtName("Text Footer");
        data1.setTxtValue("Copyright &copy; KN IT 2018");
        data1.setTxtDefaultValue("Copyright &copy; KN IT 2018");
        data1.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data1);

        data1 = new mConfigData();
        data1.setIntId(7);
        data1.setTxtName("Grant_type");
        data1.setTxtValue("password");
        data1.setTxtDefaultValue("password");
        data1.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data1);
    }
}
