package com.kalbenutritionals.kalcaremobie.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kalbe.mobiledevknlibs.Toast.ToastCustom;
import com.kalbenutritionals.kalcaremobie.Common.clsDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsProductDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsToken;
import com.kalbenutritionals.kalcaremobie.Common.clsUserLogin;
import com.kalbenutritionals.kalcaremobie.Data.Helper;
import com.kalbenutritionals.kalcaremobie.Data.VolleyResponseListener;
import com.kalbenutritionals.kalcaremobie.Data.VolleyUtils;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAdapterListSo;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAppAdapter;
import com.kalbenutritionals.kalcaremobie.Data.adapter.ListViewCustom;
import com.kalbenutritionals.kalcaremobie.Data.clsHardCode;
import com.kalbenutritionals.kalcaremobie.Fragment.dummy.DummyContent.DummyItem;
import com.kalbenutritionals.kalcaremobie.R;
import com.kalbenutritionals.kalcaremobie.Repo.clsDraftRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsProductDraftRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsTokenRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsUserLoginRepo;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMItems;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMLIstSo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentSalesOrder extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    ListView lvSO;
    FloatingActionButton fabAdd;
    String access_token;
    Context context;
    clsUserLogin dataLogin = null;
    ScrollView scrollViewList;
    LinearLayout lnNoData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentSalesOrder() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_so, container, false);
        context = getActivity().getApplicationContext();
        lnNoData = (LinearLayout) view.findViewById(R.id.lnNoData);
        dataLogin = new clsUserLogin();
        dataLogin = new clsUserLoginRepo(context).getDataLogin(context);
//        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        /*// Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }*/
        try {
            List<clsToken> dataToken = new clsTokenRepo(context).findAll();
            access_token = dataToken.get(0).txtUserToken.toString();
        } catch (Exception e) {
            ToastCustom.showToasty(context, "Token Empty", 2);
        }

        scrollViewList = (ScrollView) view.findViewById(R.id.scrollViewList);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        final String strLinkAPI = new clsHardCode().linkGetDataSalesOrderMobile;
        final JSONObject resJson = new JSONObject();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date dtLoginData = dataLogin.getDtLogin();
        String currentDateandTime = sdf.format(dtLoginData);
        final List<VMLIstSo> contentLibs = new ArrayList<>();

        //casting
        lvSO = (ListView) view.findViewById(R.id.lvContent);
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new clsProductDraftRepo(context).clearAllData();
                new clsDraftRepo(context).clearAllData();
                FragmentAddOrder fragmentAddOrder = new FragmentAddOrder();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragmentAddOrder, "Fragment_AddOrder");
                fragmentTransaction.commit();
            }
        });


        try {
            resJson.put("txtAgentName", dataLogin.getNmUser());
            resJson.put("dtDate", currentDateandTime);
            resJson.put("txtNoSo", "");
        } catch (JSONException e) {
            e.printStackTrace();
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
                    JSONObject jsonObject = null;
                    final List<VMLIstSo> content = new ArrayList<VMLIstSo>();
                    try {
                        jsonObject = new JSONObject(response);
                        int result = jsonObject.getInt("intResult");
                        String warn = jsonObject.getString("txtMessage");
                        if (result == 1) {
                            if (!jsonObject.getString("ListData").equals("null")) {
                                JSONArray jsn = jsonObject.getJSONArray("ListData");
                                for (int n = 0; n < jsn.length(); n++) {
                                    JSONObject object = jsn.getJSONObject(n);
                                    String txtNewId = object.getString("txtNewId");
                                    String txtNoSo = object.getString("txtNoSo");
                                    String txtGenerateNoSo = object.getString("txtGenerateNoSo");
                                    String dtDateGenerateSO = object.getString("dtDateGenerateSO");
                                    String dtDate = object.getString("dtDate");
                                    String txtSourceOrder = object.getString("txtSourceOrder");
                                    String dtDelivery = object.getString("dtDelivery");
                                    String txtAgentName = object.getString("txtAgentName");
                                    String txtPickUpLocation = object.getString("txtPickUpLocation");
                                    String intWalkIn = object.getString("intWalkIn");
                                    String intDeliveryBy = object.getString("intDeliveryBy");
                                    String txtDeliveryBy = object.getString("txtDeliveryBy");
                                    String txtCustomer = object.getString("txtCustomer");
                                    String txtCustomerName = object.getString("txtCustomerName");
                                    String txtDelivery = object.getString("txtDelivery");
                                    String txtPropinsiID = object.getString("txtPropinsiID");
                                    String txtPropinsiName = object.getString("txtPropinsiName");
                                    String txtKabupatenKotaID = object.getString("txtKabupatenKotaID");
                                    String txtKabupatenKotaName = object.getString("txtKabupatenKotaName");
                                    String txtKecamatanID = object.getString("txtKecamatanID");
                                    String txtKecamatanName = object.getString("txtKecamatanName");
                                    String txtKelurahanID = object.getString("txtKelurahanID");
                                    String txtKelurahanName = object.getString("txtKelurahanName");

                                    String txtDeviceId = object.getString("txtDeviceId");
                                    String intStatus = object.getString("intStatus");
                                    String txtRemarks = object.getString("txtRemarks");
                                    String txtStatus_code = object.getString("txtStatus_code");

                                    VMLIstSo data = new VMLIstSo();
                                    data.setTxtPropinsiName(txtPropinsiName);
                                    data.setTxtKabupatenKotaName(txtKabupatenKotaName);
                                    data.setTxtKecamatanName(txtKecamatanName);
                                    data.setTxtKelurahanName(txtKelurahanName);
                                    data.setTxtNoSo(txtNoSo);
                                    data.setTxtAgentName(txtAgentName);
                                    data.setTxtStatus(txtStatus_code);
                                    data.setIntStatus(intStatus);
                                    data.setTxtCustomerName(txtCustomerName);
                                    data.setTxtCustomerId(txtCustomer);
                                    data.setTxtNewId(txtNewId);
                                    data.setDtDateGenerateSO(txtGenerateNoSo);
                                    data.setDtDateGenerateSO(dtDateGenerateSO);
                                    data.setDtDate(dtDate);
                                    data.setTxtSourceOrder(txtSourceOrder);
                                    data.setDtDelivery(dtDelivery);
                                    data.setTxtPickupLocation(txtPickUpLocation);
                                    data.setIntWalkIn(Integer.parseInt(intWalkIn));
                                    data.setIntDeliveryBy(Integer.parseInt(intDeliveryBy));
                                    data.setTxtDeliveryBy(txtDeliveryBy);
                                    data.setTxtDelivery(txtDelivery);
                                    data.setTxtRemarks(txtRemarks);

                                    contentLibs.add(data);

                                }
                                lvSO.setAdapter(new CardAdapterListSo(getActivity().getApplicationContext(), contentLibs, Color.WHITE));
                                ListViewCustom.setListViewHeightBasedOnItems(getActivity(), lvSO);
                                lvSO.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                        final String so = contentLibs.get(position).getTxtNoSo();
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                        alertDialogBuilder.setMessage("Action for " + so + " ?");
                                        VMLIstSo itemSelected = null;
                                        itemSelected = contentLibs.get(position);
                                        if (itemSelected.getTxtStatus().equals("DRAFT")) {
                                            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                            alertDialogBuilder.setCancelable(false)
                                                    .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                        }else{
                                            alertDialogBuilder.setCancelable(false)
                                                    .setNegativeButton("View Detail", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                        }


                                        final AlertDialog alertD = alertDialogBuilder.create();
                                        alertD.show();
                                        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final JSONObject resJsonDelete = new JSONObject();
                                                final String strLinkAPIDelete = new clsHardCode().linkDelete;
                                                try {
                                                    resJsonDelete.put("txtNoSO", so);
                                                    resJsonDelete.put("txtUser", dataLogin.getNmUser());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                final String mRequestBodyDelete = resJsonDelete.toString();
                                                new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPIDelete, mRequestBodyDelete, access_token, "Please Wait...", new VolleyResponseListener() {
                                                    @Override
                                                    public void onError(String response) {
                                                        ToastCustom.showToasty(context, response, 2);
                                                    }

                                                    @Override
                                                    public void onResponse(String response, Boolean status, String strErrorMsg) {
                                                        if (response != null) {
                                                            JSONObject jsonObject = null;
                                                            try {
                                                                jsonObject = new JSONObject(response);
                                                                int result = jsonObject.getInt("intResult");
                                                                String warn = jsonObject.getString("txtMessage");
                                                                if (result == 1) {
                                                                    ToastCustom.showToasty(context,"Item has been deleted",1);
                                                                    alertD.dismiss();
                                                                    Fragment frg = null;
                                                                    frg = getActivity().getSupportFragmentManager().findFragmentByTag("FragmentSalesOrder");
                                                                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                                    ft.detach(frg);
                                                                    ft.attach(frg);
                                                                    ft.commit();
                                                                }else{
                                                                    ToastCustom.showToasty(context,warn, 2);

                                                                }
                                                            }catch (JSONException ex){
                                                                String x = ex.getMessage();
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                        alertD.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                VMLIstSo itemSelected = null;
                                                itemSelected = contentLibs.get(position);
                                                if (!itemSelected.getTxtStatus().equals("DRAFT")) {
                                                    ToastCustom.showToasty(context, "Transaction Approved", 1);
                                                    final String strLinkAPIApproved = new clsHardCode().linkGetDataDetailSalesOrderMobile;
                                                    final JSONObject resJsonpApproved = new JSONObject();

                                                    String currentDateandTimeApproved = sdf.format(dtLoginData);

                                                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                                                    AlertDialog.Builder alertDialogBuilderAttch = new AlertDialog.Builder(getActivity());
                                                    final View promptView = layoutInflater.inflate(R.layout.popup_preview_approved, null);
                                                    final ListView lvPreview = (ListView) promptView.findViewById(R.id.lvItemPrev);
                                                    final TextView tvSOStatusPrev = (TextView) promptView.findViewById(R.id.tvSOStatusPrev);
                                                    final TextView tvSoDatePrev = (TextView) promptView.findViewById(R.id.tvSoDatePrev);
                                                    final TextView tvSOSourcePrev = (TextView) promptView.findViewById(R.id.tvSOSourcePrev);
                                                    final TextView tvAgentNamePrev = (TextView) promptView.findViewById(R.id.tvAgentNamePrev);
                                                    final TextView tvOrderLocationPrev = (TextView) promptView.findViewById(R.id.tvOrderLocationPrev);
                                                    final TextView tvDeliveryByPrev = (TextView) promptView.findViewById(R.id.tvDeliveryByPrev);
                                                    final TextView tvDeliverySchedulePrev = (TextView) promptView.findViewById(R.id.tvDeliverySchedulePrev);
                                                    final TextView tvRemarksPreview = (TextView) promptView.findViewById(R.id.tvRemarksPreview);

                                                    final TextView etPaymentMethodPrev = (TextView) promptView.findViewById(R.id.etPaymentMethodPrev);
                                                    final TextView etSourceMediaPaymentPrev = (TextView) promptView.findViewById(R.id.etSourceMediaPaymentPrev);
                                                    final TextView etPaymentPrev = (TextView) promptView.findViewById(R.id.etPaymentPrev);
                                                    final TextView etCardNumberPrev = (TextView) promptView.findViewById(R.id.etCardNumberPrev);
                                                    final TextView etBcaTraceNumberPrev = (TextView) promptView.findViewById(R.id.etBcaTraceNumberPrev);
                                                    final TextView etNetPricePrev = (TextView) promptView.findViewById(R.id.etNetPricePrev);

                                                    final TextView etCustomerNamePrev = (TextView) promptView.findViewById(R.id.etCustomerNamePrev);
                                                    final TextView etContactIDPrev = (TextView) promptView.findViewById(R.id.etContactIDPrev);
                                                    final TextView etMemberNoPrev = (TextView) promptView.findViewById(R.id.etMemberNoPrev);
                                                    final TextView tvPostCodeCustomerPrev = (TextView) promptView.findViewById(R.id.tvPostCodeCustomerPrev);
                                                    final TextView tvProvinceCustomerPrev = (TextView) promptView.findViewById(R.id.tvProvinceCustomerPrev);
                                                    final TextView tvCityCustomerPrev = (TextView) promptView.findViewById(R.id.tvCityCustomerPrev);
                                                    final TextView tvRegionCustomerPrev = (TextView) promptView.findViewById(R.id.tvRegionCustomerPrev);
                                                    final TextView tvKelurahanCustomerPrev = (TextView) promptView.findViewById(R.id.tvKelurahanCustomerPrev);

                                                    final TextView tvHideCustPrev = (TextView) promptView.findViewById(R.id.tvHideCustPrev);
                                                    final TextView tvTaxBaseAmount3Prev = (TextView) promptView.findViewById(R.id.tvTaxBaseAmount3Prev);
                                                    final TextView tvTaxAmount3Prev = (TextView) promptView.findViewById(R.id.tvTaxAmount3Prev);
                                                    final TextView tvTotalPrev = (TextView) promptView.findViewById(R.id.tvTotalPrev);
                                                    final TextView tvDiscount3Prev = (TextView) promptView.findViewById(R.id.tvDiscount3Prev);
                                                    final TextView tvTotalPrice3Prev = (TextView) promptView.findViewById(R.id.tvTotalPrice3Prev);
                                                    final TextView tvRounded3Prev = (TextView) promptView.findViewById(R.id.tvRounded3Prev);
                                                    final TextView tvPaymentEnd3Prev = (TextView) promptView.findViewById(R.id.tvPaymentEnd3Prev);

                                                    final TextView tvKN1Poin3Prev = (TextView) promptView.findViewById(R.id.tvKN1Poin3Prev);
                                                    final TextView tvKN2Poin3Prev = (TextView) promptView.findViewById(R.id.tvKN2Poin3Prev);
                                                    final TextView tvKN3poin3Prev = (TextView) promptView.findViewById(R.id.tvKN3poin3Prev);
                                                    final TextView tvKN4poin3Prev = (TextView) promptView.findViewById(R.id.tvKN4poin3Prev);
                                                    final TextView tvKNOtherPoin3Prev = (TextView) promptView.findViewById(R.id.tvKNOtherPoin3Prev);
                                                    final TextView tvBonusPoin3Prev = (TextView) promptView.findViewById(R.id.tvBonusPoin3Prev);
                                                    final TextView tvTotalPoin3Prev = (TextView) promptView.findViewById(R.id.tvTotalPoin3Prev);


                                                    final LinearLayout lnAttacedCust = (LinearLayout) promptView.findViewById(R.id.lnAttacedCust);
                                                    final TextView tvCustomerPrev = (TextView) promptView.findViewById(R.id.tvCustomerPrev);


                                                    try {
                                                        resJsonpApproved.put("txtAgentName", dataLogin.getNmUser());
                                                        resJsonpApproved.put("dtDate", currentDateandTimeApproved);
                                                        resJsonpApproved.put("txtNoSo", itemSelected.getTxtNoSo());

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    final String mRequestBodyApproved = resJsonpApproved.toString();
                                                    new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPIApproved, mRequestBodyApproved, access_token, "Please Wait...", new VolleyResponseListener() {
                                                        @Override
                                                        public void onError(String response) {
                                                            ToastCustom.showToasty(context, response, 2);
                                                        }

                                                        @Override
                                                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                                                            if (response != null) {
                                                                JSONObject jsonObject = null;
                                                                try {
                                                                    jsonObject = new JSONObject(response);
                                                                    int result = jsonObject.getInt("intResult");
                                                                    String warn = jsonObject.getString("txtMessage");
                                                                    if (result == 1) {
                                                                        if (!jsonObject.getString("ListData").equals("null")) {
                                                                            JSONArray jsn = jsonObject.getJSONArray("ListData");

                                                                            JSONObject objData = jsn.getJSONObject(0).getJSONObject("DataSalesOrder");


                                                                            String txtNewIdSO = objData.getString("txtNewId");
                                                                            String txtNoSo = objData.getString("txtNoSo");
                                                                            String txtGenerateNoSo = objData.getString("txtGenerateNoSo");
                                                                            String dtDateGenerateSO = objData.getString("dtDateGenerateSO");
                                                                            String dtDate = objData.getString("dtDate");
                                                                            String txtSourceOrder = objData.getString("txtSourceOrder");
                                                                            String dtDelivery = objData.getString("dtDelivery");
                                                                            String txtAgentName = objData.getString("txtAgentName");
                                                                            String txtPickUpLocation = objData.getString("txtPickUpLocation");
                                                                            String intWalkIn = objData.getString("intWalkIn");
                                                                            String intDeliveryBy = objData.getString("intDeliveryBy");
                                                                            String txtDeliveryBy = objData.getString("txtDeliveryBy");
                                                                            String txtCustomer = objData.getString("txtCustomer");
                                                                            String txtCustomerName = objData.getString("txtCustomerName");
                                                                            String txtDelivery = objData.getString("txtDelivery");
                                                                            String txtPrfopinsiID = objData.getString("txtPropinsiID");
                                                                            String txtPropinsiName = objData.getString("txtPropinsiName");
                                                                            String txtKabupatenKotaID = objData.getString("txtKabupatenKotaID");
                                                                            String txtKabupatenKotaName = objData.getString("txtKabupatenKotaName");
                                                                            String txtKecamatanID = objData.getString("txtKecamatanID");
                                                                            String txtKecamatanName = objData.getString("txtKecamatanName");
                                                                            String txtKelurahanID = objData.getString("txtKelurahanID");
                                                                            String txtKelurahanName = objData.getString("txtKelurahanName");
                                                                            String txtKodePos = objData.getString("txtKodePos");
                                                                            String txtRemarks = objData.getString("txtRemarks");
                                                                            String txtDeviceId = objData.getString("txtDeviceId");
                                                                            String intStatus = objData.getString("intStatus");
                                                                            String txtStatus_code = objData.getString("txtStatus_code");

                                                                            tvSOSourcePrev.setText(txtSourceOrder);
                                                                            tvSoDatePrev.setText(dtDate);
                                                                            tvSOStatusPrev.setText(txtStatus_code);
                                                                            tvDeliverySchedulePrev.setText(dtDelivery);
                                                                            tvAgentNamePrev.setText(txtAgentName);
                                                                            tvOrderLocationPrev.setText(txtPickUpLocation);
                                                                            tvRemarksPreview.setText(txtRemarks);
                                                                            tvDeliveryByPrev.setText(txtDeliveryBy);
                                                                            tvCustomerPrev.setText(txtPickUpLocation);

//                                                                            if (intDeliveryBy.equals("1")) {
                                                                                tvCustomerPrev.setText("Customer Detail");
                                                                                etCustomerNamePrev.setText(txtCustomerName);
//                                                                            etContactIDPrev.setText();
                                                                                lnAttacedCust.setVisibility(View.VISIBLE);
                                                                                tvProvinceCustomerPrev.setText(txtPropinsiName);
                                                                                tvCityCustomerPrev.setText(txtKabupatenKotaName);
                                                                                tvRegionCustomerPrev.setText(txtKecamatanName);
                                                                                tvKelurahanCustomerPrev.setText(txtKelurahanName);
                                                                                tvPostCodeCustomerPrev.setText(txtKodePos);

//                                                                            } else {
//                                                                                lnAttacedCust.setVisibility(View.GONE);
//                                                                            }
                                                                            JSONObject objDataPayment = jsn.getJSONObject(0).getJSONObject("DataPayment");
                                                                            String txtPaymentMethodName = objDataPayment.getString("txtPaymentMethodName");
                                                                            String txtmediajasapayment = objDataPayment.getString("txtmediajasapayment");
                                                                            String txtmediajasa = objDataPayment.getString("txtmediajasa");
                                                                            String txtTraceNumber = objDataPayment.getString("txtTraceNumber");
                                                                            String txtCardNumber = objDataPayment.getString("txtCardNumber");
                                                                            String decTaxBaseAmount = objDataPayment.getString("decTaxBaseAmount");
                                                                            String decAmount = objDataPayment.getString("decAmount");
                                                                            String decTotal = objDataPayment.getString("decTotal");
                                                                            String decTotDiscount = objDataPayment.getString("decTotDiscount");
                                                                            String decTotalPriceSO = objDataPayment.getString("decTotalPrice");
                                                                            String decRounded = objDataPayment.getString("decRounded");
                                                                            String decPayment = objDataPayment.getString("decPayment");
                                                                            String decTotalPrice2 = objDataPayment.getString("decTotalPrice");
                                                                            etPaymentMethodPrev.setText(txtPaymentMethodName);
                                                                            etSourceMediaPaymentPrev.setText(txtmediajasapayment);
                                                                            etPaymentPrev.setText(txtmediajasa);
                                                                            etBcaTraceNumberPrev.setText(txtTraceNumber);
                                                                            etCardNumberPrev.setText(txtCardNumber);
                                                                            etNetPricePrev.setText(decTotalPriceSO);
                                                                            tvTaxBaseAmount3Prev.setText(decTaxBaseAmount);
                                                                            tvTaxAmount3Prev.setText(decAmount);
                                                                            tvTotalPrev.setText(decTotal);
                                                                            tvDiscount3Prev.setText(decTotDiscount);
                                                                            tvTotalPrice3Prev.setText(decTotalPrice2);
                                                                            tvRounded3Prev.setText(decRounded);
                                                                            tvPaymentEnd3Prev.setText(decPayment);

                                                                            JSONArray arrayData = jsn.getJSONObject(0).getJSONArray("ListDataDetail");
                                                                            final List<VMItems> contentItems = new ArrayList<VMItems>();
                                                                            for (int n = 0; n < jsn.length(); n++) {
                                                                                JSONObject obj = arrayData.getJSONObject(n);
                                                                                String txtNewId = obj.getString("txtNewId");
                                                                                String txtProductCode = obj.getString("txtProductCode");
                                                                                String txtProductName = obj.getString("txtProductName");
                                                                                String intQty = obj.getString("intQty");
                                                                                String decPrice = obj.getString("decPrice");
                                                                                String decDiscount = obj.getString("decDiscount");
                                                                                String decTotalPrice = obj.getString("decTotalPrice");
                                                                                String decTaxAmount = obj.getString("decTaxAmount");
                                                                                String decNetPrice = obj.getString("decNetPrice");
                                                                                String decBasePoint = obj.getString("decBasePoint");
                                                                                String decTotalBasePoint = obj.getString("decTotalBasePoint");
                                                                                String decBonusPoint = obj.getString("decBonusPoint");
                                                                                String txtNoSO = obj.getString("txtNoSO");
                                                                                VMItems item = new VMItems(getView());
                                                                                item.setItemName(txtProductName);
                                                                                item.setGuiid(new Helper().GenerateGuid());
                                                                                item.setItemCode(txtProductCode);
                                                                                item.setPrice(Double.parseDouble(decPrice));
//                                                                            item.setProductCategory(HMtxtProductCategory.get(itemCodeAdd));
                                                                                item.setBasePoint(decBasePoint);
                                                                                double dbQty = Double.parseDouble(intQty);
                                                                                int intQtyi = (int) dbQty;
                                                                                item.setQty(intQtyi);
                                                                                item.setDiscount(Double.parseDouble(decDiscount));
                                                                                item.setTotalPrice(Double.parseDouble(decTotalPrice));
                                                                                item.setTaxAmount(Double.parseDouble(decTaxAmount));
                                                                                item.setNetPrice(Double.parseDouble(decNetPrice));
                                                                                item.setBonusPoint(decBonusPoint);

                                                                                contentItems.add(item);
                                                                            }

                                                                            lvPreview.setAdapter(new CardAppAdapter(context, contentItems, Color.WHITE));

                                                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                                                            alertDialogBuilder.setView(promptView);
                                                                            alertDialogBuilder.setTitle("Detail Transaction");
                                                                            alertDialogBuilder
                                                                                    .setCancelable(false)
                                                                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                                                                        public void onClick(DialogInterface dialog, int id) {

                                                                                        }
                                                                                    });
                                                                            final AlertDialog alertD2 = alertDialogBuilder.create();
                                                                            alertD2.show();
                                                                            alertD.dismiss();
                                                                        }
                                                                    } else {
                                                                        ToastCustom.showToasty(context, warn, 2);

                                                                    }
                                                                } catch (JSONException ex) {
                                                                    String x = ex.getMessage();
                                                                }
                                                            }
                                                        }
                                                    });


                                                } else {
                                               /* new clsProductDraftRepo(context).clearAllData();
                                                new clsDraftRepo(context).clearAllData();*/


                                                    final String strLinkAPI = new clsHardCode().linkGetDataDetailSalesOrderMobile;
                                                    final JSONObject resJsonRestore = new JSONObject();
                                                    SimpleDateFormat sdfRestore = new SimpleDateFormat("yyyy-MM-dd");
                                                    Date dtLoginData = dataLogin.getDtLogin();
                                                    String currentDateandTime = sdfRestore.format(dtLoginData);
                                                    try {
                                                        resJsonRestore.put("txtAgentName", dataLogin.getNmUser());
                                                        resJsonRestore.put("dtDate", currentDateandTime);
                                                        resJsonRestore.put("txtNoSo", itemSelected.getTxtNoSo());

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    final String mRequestBodyRestore = resJsonRestore.toString();
                                                    new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPI, mRequestBodyRestore, access_token, "Please Wait...", new VolleyResponseListener() {
                                                        @Override
                                                        public void onError(String response) {
                                                            ToastCustom.showToasty(context, response, 2);
                                                        }

                                                        @Override
                                                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                                                            if (response != null) {
                                                                JSONObject jsonObject = null;
                                                                try {
                                                                    jsonObject = new JSONObject(response);
                                                                    int result = jsonObject.getInt("intResult");
                                                                    String warn = jsonObject.getString("txtMessage");
                                                                    if (result == 1) {
                                                                        if (!jsonObject.getString("ListData").equals("null")) {
                                                                            JSONArray jsn = jsonObject.getJSONArray("ListData");

                                                                            JSONObject objData = jsn.getJSONObject(0).getJSONObject("DataSalesOrder");

                                                                            String txtNewIdSO = objData.getString("txtNewId");
                                                                            String txtNoSo = objData.getString("txtNoSo");
                                                                            String txtGenerateNoSo = objData.getString("txtGenerateNoSo");
                                                                            String dtDateGenerateSO = objData.getString("dtDateGenerateSO");
                                                                            String dtDate = objData.getString("dtDate");
                                                                            String txtSourceOrder = objData.getString("txtSourceOrder");
                                                                            String dtDelivery = objData.getString("dtDelivery");
                                                                            String txtAgentName = objData.getString("txtAgentName");
                                                                            String txtPickUpLocation = objData.getString("txtPickUpLocation");
                                                                            String intWalkIn = objData.getString("intWalkIn");
                                                                            String intDeliveryBy = objData.getString("intDeliveryBy");
                                                                            String txtDeliveryBy = objData.getString("txtDeliveryBy");
                                                                            String txtCustomer = objData.getString("txtCustomer");
                                                                            String txtCustomerName = objData.getString("txtCustomerName");
                                                                            String txtDelivery = objData.getString("txtDelivery");
                                                                            String txtPropinsiID = objData.getString("txtPropinsiID");
                                                                            String txtPropinsiName = objData.getString("txtPropinsiName");
                                                                            String txtKabupatenKotaID = objData.getString("txtKabupatenKotaID");
                                                                            String txtKabupatenKotaName = objData.getString("txtKabupatenKotaName");
                                                                            String txtKecamatanID = objData.getString("txtKecamatanID");
                                                                            String txtKecamatanName = objData.getString("txtKecamatanName");
                                                                            String txtKelurahanID = objData.getString("txtKelurahanID");
                                                                            String txtKelurahanName = objData.getString("txtKelurahanName");
                                                                            String txtRemarks = objData.getString("txtRemarks");
                                                                            String txtDeviceId = objData.getString("txtDeviceId");
                                                                            String intStatus = objData.getString("intStatus");
                                                                            String txtStatus_code = objData.getString("txtStatus_code");
                                                                            String txtKodePos = objData.getString("txtKodePos");

                                                                            new clsProductDraftRepo(context).clearAllData();
                                                                            new clsDraftRepo(context).clearAllData();

                                                                            clsDraft draft = new clsDraft();
                                                                            draft.setGuiId(txtNewIdSO);
                                                                            draft.setTxtSOStatus(txtStatus_code);
                                                                            draft.setIntStatus(Integer.parseInt(intStatus));
                                                                            draft.setTxtSoSource(txtSourceOrder);
                                                                            draft.setTxtAgentName(txtAgentName);
                                                                            draft.setTxtNoSO(txtNoSo);
//                                    draft.setTxtContactID();
                                                                            draft.setTxtOrderLocation(txtPickUpLocation);

                                                                            JSONObject objDataUserInfo = jsn.getJSONObject(0).getJSONObject("dtclsUserInfo");
                                                                            String txtKontakID = objDataUserInfo.getString("txtKontakID");
                                                                            String txtMemberID = objDataUserInfo.getString("txtMemberID");
                                                                            String txtPhoneNo = objDataUserInfo.getString("txtListMedia");

                                                                            DateFormat dateFormati = new SimpleDateFormat("yyyy-MM-dd");
                                                                            if (!dtDate.equals("")) {
                                                                                Date dtdtDate = null;
                                                                                try {
                                                                                    dtdtDate = dateFormati.parse(dtDate);
                                                                                    draft.setDtSODate(dtdtDate);
                                                                                } catch (ParseException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                            if (!dtDelivery.equals("")) {
                                                                                Date dtdtDate = null;
                                                                                try {
                                                                                    dtdtDate = dateFormati.parse(dtDelivery);
                                                                                    draft.setDtDeliverySche(dtdtDate);
                                                                                } catch (ParseException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                            if (intWalkIn.equals("1")) {
                                                                                draft.setBoolWalkin(true);

                                                                            } else {
                                                                                draft.setBoolWalkin(false);
                                                                            }
                                                                            if (intDeliveryBy.equals("1")) {
                                                                                draft.setBoolAttachCustomer(true);
                                                                            } else {
                                                                                draft.setBoolAttachCustomer(false);
                                                                            }
                                                                            draft.setTxtPostCode(txtKodePos);
                                                                            draft.setTxtAgentName(txtDeliveryBy);
                                                                            draft.setTxtRemarks(txtRemarks);
                                                                            draft.setTxtCustomerName(txtCustomerName);
                                                                            draft.setTxtContactID(txtKontakID);
                                                                            draft.setTxtKontakID(txtKontakID);
                                                                            draft.setTxtMemberID(txtMemberID);
                                                                            draft.setTxtPhoneNumber(txtPhoneNo);
                                                                            draft.setTxtMemberID(txtMemberID);

                                                                            draft.setTxtAddress(txtDelivery);
                                                                            draft.setTxtProvinceID(txtPropinsiID);
                                                                            draft.setTxtProvince(txtPropinsiName);
                                                                            draft.setTxtKabKotID(txtKabupatenKotaID);
                                                                            draft.setTxtKabKot(txtKabupatenKotaName);
                                                                            draft.setTxtKecamatanID(txtKecamatanID);
                                                                            draft.setTxtKecamatan(txtKecamatanName);
                                                                            draft.setTxtKelurahanID(txtKelurahanID);
                                                                            draft.setTxtKelurahan(txtKelurahanName);
                                                                            draft.setIntStatus(0);
                                                                            new clsDraftRepo(context).createOrUpdate(draft);

                                                                            if (jsn.length() > 0) {
                                                                                JSONArray arrayData = jsn.getJSONObject(0).getJSONArray("ListDataDetail");
                                                                                for (int n = 0; n < arrayData.length(); n++) {
                                                                                    JSONObject obj = arrayData.getJSONObject(n);
                                                                                    String txtNewId = obj.getString("txtNewId");
                                                                                    String txtProductCode = obj.getString("txtProductCode");
                                                                                    String txtProductName = obj.getString("txtProductName");
                                                                                    String intQty = obj.getString("intQty");
                                                                                    String decPrice = obj.getString("decPrice");
                                                                                    String decDiscount = obj.getString("decDiscount");
                                                                                    String decTotalPrice = obj.getString("decTotalPrice");
                                                                                    String decTaxAmount = obj.getString("decTaxAmount");
                                                                                    String decNetPrice = obj.getString("decNetPrice");
                                                                                    String decBasePoint = obj.getString("decBasePoint");
                                                                                    String decTotalBasePoint = obj.getString("decTotalBasePoint");
                                                                                    String decBonusPoint = obj.getString("decBonusPoint");
                                                                                    String txtNoSO = obj.getString("txtNoSO");
                                                                                    String txtProductCategory = obj.getString("txtProductCategory");
                                                                                    String txtBrand = obj.getString("txtBrand");
                                                                                    String txtGroupProduct = obj.getString("txtGroupProduct");
                                                                                    String txtProductBarcode = obj.getString("txtProductBarcode");
                                                                                    String txtGroupItem = obj.getString("txtGroupItem");
                                                                                    String txtItemPacketID = obj.getString("txtItemPacketID");
                                                                                    int intItemID = obj.getInt("intItemID");
                                                                                    int intBitPaket = obj.getInt("intBitPaket");

                                                                                    clsProductDraft itemsDraft = new clsProductDraft();
                                                                                    itemsDraft.setTxtDraftGUI(txtNewIdSO);
                                                                                    itemsDraft.setIntItemId(intItemID);
                                                                                    itemsDraft.setTxtItemPacketId(txtItemPacketID);
                                                                                    itemsDraft.setIntBitPaket(intBitPaket);
                                                                                    itemsDraft.setTxtItemBrand(txtBrand);
                                                                                    itemsDraft.setTxtBonusPoint(decBonusPoint);
                                                                                    itemsDraft.setTxtBasedPoint(decTotalBasePoint);
                                                                                    itemsDraft.setTxtGroupItem(txtGroupItem);
                                                                                    itemsDraft.setTxtGroupProduct(txtGroupProduct);
                                                                                    itemsDraft.setTxtItemBarcode(txtProductBarcode);
                                                                                    itemsDraft.setTxtProductCategory(txtProductCategory);
                                                                                    itemsDraft.setTxtProductDraftGUI(txtNewId);
                                                                                    itemsDraft.setTxtItemCode(txtProductCode);
                                                                                    itemsDraft.setTxtItemName(txtProductName);
                                                                                    double dblQty = Double.parseDouble(intQty);
                                                                                    int intQtyf = (int) dblQty;
                                                                                    itemsDraft.setIntQty(intQtyf);
                                                                                    itemsDraft.setDblPrice(Double.parseDouble(decPrice));
                                                                                    itemsDraft.setDblItemDiscount(Double.parseDouble(decDiscount));
                                                                                    if (!decTotalPrice.equals("null")){
                                                                                        itemsDraft.setDblTotalPrice(Double.parseDouble(decTotalPrice));
                                                                                    }
                                                                                    if(!decTaxAmount.equals("null")){
                                                                                        itemsDraft.setDblItemTax(Double.parseDouble(decTaxAmount));
                                                                                    }
                                                                                    if(!decNetPrice.equals("null")){
                                                                                        itemsDraft.setDblNetPrice(Double.parseDouble(decNetPrice));
                                                                                    }
                                                                                    itemsDraft.setTxtBasedPoint(decBasePoint);
                                                                                    itemsDraft.setTxtBonusPoint(decBonusPoint);
                                                                                    new clsProductDraftRepo(context).createOrUpdate(itemsDraft);

                                                                                }
                                                                            } else {
                                                                            }

                                                                            FragmentAddOrder fragmentAddOrder = new FragmentAddOrder();
                                                                            Bundle arguments = new Bundle();
                                                                            arguments.putString("noSO", "");
                                                                            arguments.putString("bitSO", "1");
                                                                            fragmentAddOrder.setArguments(arguments);
                                                                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                                            fragmentTransaction.replace(R.id.frame, fragmentAddOrder, "Fragment_AddOrder");
                                                                            alertD.dismiss();

                                                                            fragmentTransaction.commit();
                                                                        }
                                                                    } else {
                                                                        ToastCustom.showToasty(context, warn, 2);

                                                                    }
                                                                } catch (JSONException ex) {
                                                                    String x = ex.getMessage();
                                                                }
                                                            }

                                                        }
                                                    });


                                                }
                                            }
                                        });
                                        return false;
                                    }
                                });

                                /*lvSO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {


                                    }
                                });*/
                                if (jsn.length() == 0) {
                                    scrollViewList.setVisibility(View.GONE);
                                    lnNoData.setVisibility(View.VISIBLE);
                                } else {
                                    scrollViewList.setVisibility(View.VISIBLE);
                                    lnNoData.setVisibility(View.GONE);
                                }


                            }
                        } else {
                            ToastCustom.showToasty(context, warn, 2);
                            scrollViewList.setVisibility(View.GONE);
                            lnNoData.setVisibility(View.VISIBLE);


                        }
                    } catch (JSONException ex) {
                        String x = ex.getMessage();
                    }
                }

            }
        });


        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
