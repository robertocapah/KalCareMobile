package com.kalbenutritionals.kalcaremobie.BL;

import android.content.Context;

import com.kalbenutritionals.kalcaremobie.Common.clsStatusMenuStart;
import com.kalbenutritionals.kalcaremobie.Common.clsUserLogin;
import com.kalbenutritionals.kalcaremobie.Repo.clsUserLoginRepo;
import com.kalbenutritionals.kalcaremobie.Repo.enumStatusMenuStart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class clsMainBL {
    public clsStatusMenuStart checkUserActive(Context context) throws ParseException {
        clsUserLoginRepo login = new clsUserLoginRepo(context);
        clsStatusMenuStart _clsStatusMenuStart =new clsStatusMenuStart();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String now = dateFormat.format(cal.getTime()).toString();
//        if(repo.CheckLoginNow()){
        clsUserLogin DataLogin = null;
        try {
            DataLogin = (clsUserLogin) login.getDataLogin(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (DataLogin != null && !DataLogin.getNmUser().toString().equals("null") ){
                    _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.UserActiveLogin);
        }else{
            _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.FormLogin);
        }


        return _clsStatusMenuStart;
    }
}
