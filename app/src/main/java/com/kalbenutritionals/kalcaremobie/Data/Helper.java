package com.kalbenutritionals.kalcaremobie.Data;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kalbenutritionals.kalcaremobie.Common.clsDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsProductDraft;
import com.kalbenutritionals.kalcaremobie.Repo.clsProductDraftRepo;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert on 21/02/2018.
 */

public class Helper {
    public static void setTableLayout(Context context, TableLayout tableLayout, String[] colTextHeader, ArrayList<String[]> colData) {
        tableLayout.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(context);

        TableLayout tl = new TableLayout(context);

        for (String text : colTextHeader) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));
            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);
        for(String[] data : colData){
            tr = new TableRow(context);
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin=0;
            int topMargin=0;
            int rightMargin=0;
            int bottomMargin=0;
            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            tr.setLayoutParams(tableRowParams);
            for (String dt : data){
                TextView tv = new TextView(context);
                tv.setTextSize(12);
                tv.setWidth(100);
                tv.setPadding(10, 10, 10, 10);
                tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv.setTextColor(Color.BLACK);
                tv.setText(dt);
                tr.addView(tv,params);
            }
            tl.addView(tr, tableRowParams);
        }

        tableLayout.addView(tl);
    }
    public String GenerateGuid() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }
    public Date ConvertToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }
    public JSONArray writeJSONSaveData(Context context, clsDraft draft, List<VMItems> contentLibs){
        boolean saveDraftResult = false;
        new clsProductDraftRepo(context).clearAllData();
        JSONArray items = new JSONArray();
        for (VMItems item : contentLibs) {
            String guiid = new Helper().GenerateGuid();
            JSONObject jsnItem = new JSONObject();
            clsProductDraft product = new clsProductDraft();
            product.setTxtDraftGUI(item.getGuiid());
            product.setTxtProductDraftGUI(guiid);
            product.setTxtItemName(item.getItemName());
            product.setTxtItemCode(item.getItemCode());
            product.setDblItemDiscount(item.getDiscount());
            product.setDblPrice(item.getPrice());
            product.setDblNetPrice(item.getNetPrice());
            product.setIntQty(item.getQty());
            product.setDblItemTax(item.getTaxAmount());
            product.setDblTotalPrice(item.getTotalPrice());
            String strDecBasePoint = item.getBasePoint();
            double decPrice = item.getPrice();
            double decBasePoint = 0;
            if (!strDecBasePoint.equals("") || !strDecBasePoint.equals("null")){
                decBasePoint = Double.parseDouble(item.getBasePoint());
            }
            double decDiscount = item.getDiscount();
            double intTotalBonus = 0;
            String strBonusPoint = item.getBonusPoint();
            if (!strBonusPoint.equals("") || !strBonusPoint.equals("null")){
                intTotalBonus = Double.parseDouble(item.getBonusPoint());
            }

            int qty = item.getQty();
            try {
                jsnItem.put("decQty",qty);
                jsnItem.put("ProductCode",item.getItemCode());
                jsnItem.put("decPrice",decPrice);
                jsnItem.put("decBasePoint",decBasePoint);
                jsnItem.put("intTotBonusPoin",intTotalBonus);
                jsnItem.put("decDiscount",decDiscount);
                jsnItem.put("bitPromo","1");
                jsnItem.put("intCampaignID","3");
                jsnItem.put("txtProductCategory",item.getProductCategory());
                jsnItem.put("txtDataId",draft.getGuiId());
                jsnItem.put("ProductCode",item.getItemCode());
                jsnItem.put("ProductName",item.getItemName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            items.put(jsnItem);
//            new clsProductDraftRepo(context).createOrUpdate(product);
            saveDraftResult = true;
        }
        return items;
    }
    public JSONArray writeJSONSaveDataFinal(Context context, clsDraft draft, List<VMItems> contentLibs){
        boolean saveDraftResult = false;
        new clsProductDraftRepo(context).clearAllData();
        JSONArray items = new JSONArray();
        for (VMItems item : contentLibs) {
            String guiid = new Helper().GenerateGuid();
            JSONObject jsnItem = new JSONObject();
            clsProductDraft product = new clsProductDraft();
            product.setTxtDraftGUI(draft.getGuiId());
            product.setTxtProductDraftGUI(guiid);
            product.setTxtItemName(item.getItemName());
            product.setTxtItemCode(item.getItemCode());
            product.setDblItemDiscount(item.getDiscount());
            product.setDblPrice(item.getPrice());
            product.setDblNetPrice(item.getNetPrice());
            product.setIntQty(item.getQty());
            product.setDblItemTax(item.getTaxAmount());
            product.setDblTotalPrice(item.getTotalPrice());
            String strDecBasePoint = item.getBasePoint();
            double decPrice = item.getPrice();
            double decBasePoint = 0;
            if (!strDecBasePoint.equals("") || !strDecBasePoint.equals("null")){
                decBasePoint = Double.parseDouble(item.getBasePoint());
            }
            double decDiscount = item.getDiscount();
            double intTotalBonus = 0;
            String strBonusPoint = item.getBonusPoint();
            if (!strBonusPoint.equals("") || !strBonusPoint.equals("null")){
                intTotalBonus = Double.parseDouble(item.getBonusPoint());
            }

            int qty = item.getQty();
            try {
                jsnItem.put("decQty",qty);
                jsnItem.put("ProductCode",item.getItemCode());
                jsnItem.put("decPrice",decPrice);
                jsnItem.put("decBasePoint",decBasePoint);
                jsnItem.put("intTotBonusPoin",intTotalBonus);
                jsnItem.put("decDiscount",decDiscount);
                jsnItem.put("bitPromo","1");
                jsnItem.put("intCampaignID","3");
                jsnItem.put("txtProductCategory",item.getProductCategory());
                jsnItem.put("txtDataId",draft.getGuiId());
                jsnItem.put("ProductCode",item.getItemCode());
                jsnItem.put("ProductName",item.getItemName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            items.put(jsnItem);
//            new clsProductDraftRepo(context).createOrUpdate(product);
            saveDraftResult = true;
        }
        return items;
    }

}
