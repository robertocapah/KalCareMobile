package com.kalbenutritionals.kalcaremobie;

import android.support.v7.app.AppCompatActivity;

import com.kalbenutritionals.kalcaremobie.Data.clsHardCode;

import org.json.JSONObject;

/**
 * Created by Robert on 28/02/2018.
 */

public class Temporary extends AppCompatActivity {
    final String strLinkAPI = new clsHardCode().linkSearchCustomer;
    final JSONObject resJson = new JSONObject();
    /*Button btnBro;

            String keyword = etSearchCustomer.getText().toString();
            if (keyword.equals("")){
                ToastCustom.showToasty(context,"Keyword Empty",4);
            }else{
                if (spnOpsiSearchCustomer.getSelectedItem().toString().equals(SPNMEMBERID)){
                    try {
                        resJson.put("txtPhone", "");
                        resJson.put("txtMember", keyword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        resJson.put("txtPhone", keyword);
                        resJson.put("txtMember", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                final String mRequestBody = resJson.toString();
                new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPI, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
                    @Override
                    public void onError(String response) {
                        ToastCustom.showToasty(context, response, 2);
                    }

                    @Override
                    public void onResponse(String response, Boolean status, String strErrorMsg) {
                        if (response != null) {

                            String a = "";
                        }
                    }
                });
            }
*/

}
