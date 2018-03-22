package com.kalbenutritionals.kalcaremobie.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kalbenutritionals.kalcaremobie.Common.clsDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsPhotoProfile;
import com.kalbenutritionals.kalcaremobie.Common.clsProductDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsToken;
import com.kalbenutritionals.kalcaremobie.Common.clsUserLogin;
import com.kalbenutritionals.kalcaremobie.Common.mConfigData;
import com.kalbenutritionals.kalcaremobie.Common.mMenuData;

import java.sql.SQLException;

/**
 * Created by Rian Andrivani on 11/21/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = new clsHardCode().dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    protected Dao<mConfigData, Integer> mConfigDao;

    protected Dao<clsUserLogin, Integer> loginDao;
    protected RuntimeExceptionDao<clsUserLogin, Integer> loginRuntimeDao = null;

    protected Dao<mMenuData, Integer> menuDao;
    protected RuntimeExceptionDao<mMenuData, Integer> menuRuntimeDao = null;

    protected Dao<clsPhotoProfile, Integer> profileDao;
    protected RuntimeExceptionDao<clsPhotoProfile, Integer> profileRuntimeDao;

    protected Dao<clsToken, Integer> tokenDao;
    protected RuntimeExceptionDao<clsToken, Integer> tokenRuntimeDao;

    protected Dao<clsDraft, Integer> draftDao;
    protected Dao<clsProductDraft, Integer> productDraftDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, mConfigData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserLogin.class);
            TableUtils.createTableIfNotExists(connectionSource, clsToken.class);
            TableUtils.createTableIfNotExists(connectionSource, mMenuData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsPhotoProfile.class);
            TableUtils.createTableIfNotExists(connectionSource, clsDraft.class);
            TableUtils.createTableIfNotExists(connectionSource, clsProductDraft.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Dao<clsUserLogin, Integer> dao = null;
        try {
            dao = getLoginDao();

//            if (oldVersion < 2) {
//                dao.executeRaw("ALTER TABLE `clsUserLogin` ADD COLUMN txtRefreshToken TEXT;");
//            }
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
//            TableUtils.dropTable(connectionSource, mConfigData.class, true);
//            TableUtils.dropTable(connectionSource, clsUserLogin.class, true);
//            TableUtils.dropTable(connectionSource, mMenuData.class, true);
//            TableUtils.dropTable(connectionSource, clsPhotoProfile.class, true);
//            TableUtils.dropTable(connectionSource, mProduct.class, true);
//            TableUtils.dropTable(connectionSource, tOrderHeader.class, true);
//            TableUtils.dropTable(connectionSource, tOrderDetail.class, true);

            //onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDataAfterLogout(){
        try {
            TableUtils.clearTable(connectionSource, clsUserLogin.class);
            TableUtils.clearTable(connectionSource, mMenuData.class);
            TableUtils.clearTable(connectionSource, clsPhotoProfile.class);
            TableUtils.clearTable(connectionSource, clsProductDraft.class);
            TableUtils.clearTable(connectionSource, clsDraft.class);
            // after we drop the old databases, we create the new ones
//            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void clearDataProductDraft(){
        try {
            TableUtils.clearTable(connectionSource, clsProductDraft.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void clearDataDraft(){
        try {
            TableUtils.clearTable(connectionSource, clsDraft.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<clsProductDraft, Integer> getProductDraftDao() throws SQLException {
        if (productDraftDao == null) {
            productDraftDao = getDao(clsProductDraft.class);
        }
        return productDraftDao;
    }
    public Dao<clsDraft, Integer> getDraftDao() throws SQLException {
        if (draftDao == null) {
            draftDao = getDao(clsDraft.class);
        }
        return draftDao;
    }


    public Dao<mConfigData, Integer> getmConfigDao() throws SQLException {
        if (mConfigDao == null) {
            mConfigDao = getDao(mConfigData.class);
        }
        return mConfigDao;
    }

    public Dao<clsToken, Integer> getTokenDao() throws SQLException {
        if (tokenDao == null) {
            tokenDao = getDao(clsToken.class);
        }
        return tokenDao;
    }

    public Dao<clsUserLogin, Integer> getLoginDao() throws SQLException {
        if (loginDao == null) {
            loginDao = getDao(clsUserLogin.class);
        }
        return loginDao;
    }

    public Dao<mMenuData, Integer> getMenuDao() throws SQLException {
        if (menuDao == null) {
            menuDao = getDao(mMenuData.class);
        }
        return menuDao;
    }

    public Dao<clsPhotoProfile, Integer> getProfileDao() throws SQLException {
        if (profileDao == null) {
            profileDao = getDao(clsPhotoProfile.class);
        }
        return profileDao;
    }


    @Override
    public void close() {
        mConfigDao = null;
        loginDao = null;
        tokenDao = null;
        menuDao = null;
        profileDao = null;
    }
}
