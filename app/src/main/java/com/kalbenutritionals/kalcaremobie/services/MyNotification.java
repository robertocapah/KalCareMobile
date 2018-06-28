package com.kalbenutritionals.kalcaremobie.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kalbe.mobiledevknlibs.Toast.ToastCustom;
import com.kalbenutritionals.kalcaremobie.Common.clsToken;
import com.kalbenutritionals.kalcaremobie.Common.mConfigData;
import com.kalbenutritionals.kalcaremobie.Data.DatabaseHelper;
import com.kalbenutritionals.kalcaremobie.Data.DatabaseManager;
import com.kalbenutritionals.kalcaremobie.Data.VolleyResponseListener;
import com.kalbenutritionals.kalcaremobie.Data.VolleyUtils;
import com.kalbenutritionals.kalcaremobie.Data.clsHardCode;
import com.kalbenutritionals.kalcaremobie.MainMenu;
import com.kalbenutritionals.kalcaremobie.R;
import com.kalbenutritionals.kalcaremobie.Repo.clsTokenRepo;
import com.kalbenutritionals.kalcaremobie.Repo.mConfigRepo;
import com.kalbenutritionals.kalcaremobie.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Robert on 11/06/2018.
 */

public class MyNotification extends Service {
    String titleFinal = null;
    String descFinal = null;
    String className = null;
    String classNameF = null;
    Context context;
    private static long UPDATE_INTERVAL_TESTING = 30000;  //default
    String access_token;
    public MyNotification() {

    }

    private PackageInfo pInfo = null;
    mConfigData dtApp = new mConfigData();
    mConfigData dtUserName = new mConfigData();
    final String strLinkAPI = new clsHardCode().linkCheckingVersion;
    final JSONObject resJson = new JSONObject();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private static Timer timer = new Timer();
    private void _startService() {
        long intInverval = 0;
        intInverval = UPDATE_INTERVAL_TESTING;
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        try {
                            getVersionUpdate();
                            Log.i("Service berjalan","MyNotif");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, 3000, intInverval);
        //Log.i(getClass().getSimpleName(), "FileScannerService Timer started....");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        _startService();
        super.onStart(intent, startId);
        context = getApplicationContext();
        try {
            List<clsToken> dataToken = new clsTokenRepo(context).findAll();
            access_token = dataToken.get(0).txtUserToken.toString();
        } catch (Exception e) {
            ToastCustom.showToasty(context, "Token Empty", 2);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _startService();
        context = getApplicationContext();
        try {
            List<clsToken> dataToken = new clsTokenRepo(context).findAll();
            access_token = dataToken.get(0).txtUserToken.toString();
        } catch (Exception e) {
            ToastCustom.showToasty(context, "Token Empty", 2);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public boolean getVersionUpdate(){
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dtApp = new mConfigRepo(getApplicationContext()).findById(4);
            dtUserName = new mConfigRepo(getApplicationContext()).findById(5);
            try {
                resJson.put("txtClientId", dtApp.txtDefaultValue);
                resJson.put("txtUserid", dtUserName.txtDefaultValue);
//            resJson.put("txtRefreshToken", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        final MainMenu mainMenuActivity = MainMenu.instance;
        new VolleyUtils().makeJsonObjectRequestWithTokenWithoutProgressDService(mainMenuActivity, strLinkAPI, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                ToastCustom.showToasty(context, response, 2);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("intResult");
                    String warn = jsonObject.getString("txtMessage");


                    if (result == 0) {
                        ToastCustom.showToasty(getApplicationContext(), " Invalid Username or Password", 2);
                    } else {
                        if (!jsonObject.getString("ListData").equals("null")) {
                            JSONArray jsn = jsonObject.getJSONArray("ListData");

                            JSONObject jsnObject = jsn.getJSONObject(0);
                            String txtDataId = jsnObject.getString("txtDataId");
                            String txtAppName = jsnObject.getString("txtAppName");
                            String txtAppSecret = jsnObject.getString("txtAppSecret");
                            String txtVersion = jsnObject.getString("txtVersion");
                            String txtFileName = jsnObject.getString("txtFileName");
                            String txtDomain = jsnObject.getString("txtDomain");
                            if (pInfo.versionName.equals(txtVersion) == false) {
                                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                                i.putExtra("key_view", "Notification");
//                                i.putExtra(TAG_UUID, String.valueOf(dttNotificationData.get_guiID()));
                                i.setAction("notif");
                                int idn = 0;
                                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),idn, i, PendingIntent.FLAG_ONE_SHOT);
                                int icon = R.drawable.ic_alert_black;
                                long when = System.currentTimeMillis();
                                Notification tnotification = new Notification.Builder(MyNotification.this)
                                        .setContentIntent(pendingIntent)
                                        .setContentTitle("Update Notification")
                                        .setContentText("Versi Aplikasi Kalcare telah update ke "+txtVersion+" mohon logout sebelum melakukan transaksi")
                                        .setSmallIcon(icon)
                                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                                R.drawable.ic_kalcare_v2))
                                        .setWhen(when)
                                        .setTicker("Kalcare Mobile")
                                        .setPriority(Notification.PRIORITY_HIGH)
                                        .setAutoCancel(true)
                                        .setDefaults(Notification.DEFAULT_ALL | Notification.FLAG_SHOW_LIGHTS | Notification.PRIORITY_DEFAULT)
                                        .build();
                                NotificationManager tnotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                tnotification.defaults=Notification.DEFAULT_ALL;
                                tnotificationManager.notify(idn,tnotification);
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mainMenuActivity);
                                builder.setTitle("Update");
                                builder.setMessage("Aplikasi harus di update ke versi terbaru, silahkan logout dan update aplikasi.");

                                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(mainMenuActivity, SplashActivity.class);
                                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
                                            helper.clearDataAfterLogout();
                                            startActivity(intent);
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                android.app.AlertDialog alert = builder.create();
                                if (!alert.isShowing()){
                                    alert.show();
                                }else{
                                    alert.dismiss();
                                    alert.show();
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return false;
    }
}
