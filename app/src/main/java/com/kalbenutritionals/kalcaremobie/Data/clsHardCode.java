package com.kalbenutritionals.kalcaremobie.Data;

import android.content.Context;
import android.os.Environment;

import com.kalbenutritionals.kalcaremobie.Repo.mConfigRepo;

import java.io.File;

/**
 * Created by Rian Andrivani on 11/7/2017.
 */

public class clsHardCode {
    Context context;
    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"KalCareMobile"+File.separator+"app_database"+File.separator;
    public String dbName = "Kalcare.db";
    public String txtFolderData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalCareMobile"+File.separator+"image_Person"+File.separator;

    public String linkLogin = new mConfigRepo(context).API + "Login";
    public String linkToken = new mConfigRepo(context).APIToken;
    public String linkSearch = new mConfigRepo(context).API + "ListProdukKHD";
    public String linkSearchCustomer = new mConfigRepo(context).API + "SearchCustomer";
    public String linkGetPrice = new mConfigRepo(context).API + "PriceDanPointByItem";
    public String linkGetListPropinsi = new mConfigRepo(context).API + "ListPropinsi";
    public String linkGetListKabupatenKota = new mConfigRepo(context).API + "ListKabupatenKota";
    public String linkGetListKecamatan = new mConfigRepo(context).API + "ListKecamatan";
    public String linkGetListKelurahan = new mConfigRepo(context).API + "ListKelurahan";
    public String linkGetDataOutletKALCARE = new mConfigRepo(context).API + "GetDataOutletKALCARE";
    public String linkSaveDataSalesOrder = new mConfigRepo(context).API + "SaveDataSalesOrder";
    public String linkCekValidasiDispatchPartner = new mConfigRepo(context).API + "CekValidasiDispatchPartner";
    public String linkCheckoutSalesOrder = new mConfigRepo(context).API + "CheckoutSalesOrder";
    public String linkGetDataSalesOrderMobile  = new mConfigRepo(context).API + "GetDataSalesOrderMobile";
    public String linkGetDataDetailSalesOrderMobile   = new mConfigRepo(context).API + "GetDataDetailSalesOrderMobile";

    /*public String copydb(Context context) throws IOException {
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/"+ new clsHardCode().dbName;

        try {
            File dbFile = new File(CURRENT_DATABASE_PATH);
            FileInputStream fis = new FileInputStream(dbFile);
            String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"backupDbTemplate";
            File yourFile = new File(txtPathUserData);
            yourFile.createNewFile();
            OutputStream output = new FileOutputStream(yourFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            String s= "hahaha";
        }

        return "hehe";
    }*/
}
