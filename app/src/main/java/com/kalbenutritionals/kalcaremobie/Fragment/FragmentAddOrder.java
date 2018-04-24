package com.kalbenutritionals.kalcaremobie.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.DeviceInformation.DeviceInformation;
import com.kalbe.mobiledevknlibs.DeviceInformation.ModelDevice;
import com.kalbe.mobiledevknlibs.Spinner.SpinnerCustom;
import com.kalbe.mobiledevknlibs.Toast.ToastCustom;
import com.kalbe.mobiledevknlibs.library.pulltorefresh.interfaces.IXListViewListener;
import com.kalbenutritionals.kalcaremobie.Common.clsCustomerData;
import com.kalbenutritionals.kalcaremobie.Common.clsDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsListMediaJasa;
import com.kalbenutritionals.kalcaremobie.Common.clsListPaymentMethod;
import com.kalbenutritionals.kalcaremobie.Common.clsListPaymentMethodAndTipe;
import com.kalbenutritionals.kalcaremobie.Common.clsProductDraft;
import com.kalbenutritionals.kalcaremobie.Common.clsToken;
import com.kalbenutritionals.kalcaremobie.Common.clsUserLogin;
import com.kalbenutritionals.kalcaremobie.Data.AlertDialog.CustomDatePicker;
import com.kalbenutritionals.kalcaremobie.Data.AlertDialog.clsDatePicker;
import com.kalbenutritionals.kalcaremobie.Data.Helper;
import com.kalbenutritionals.kalcaremobie.Data.VolleyResponseListener;
import com.kalbenutritionals.kalcaremobie.Data.VolleyUtils;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAdapterCustomerSearch;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAdapterOutet;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAdapterSearchResult;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAppAdapter;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAppAdapter2;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAppAdapterPreview;
import com.kalbenutritionals.kalcaremobie.Data.adapter.ListViewCustom;
import com.kalbenutritionals.kalcaremobie.Data.adapter.RVParentAdapter;
import com.kalbenutritionals.kalcaremobie.Data.adapter.RVPreviewAdapter;
import com.kalbenutritionals.kalcaremobie.Data.clsHardCode;
import com.kalbenutritionals.kalcaremobie.DrawableClickListener;
import com.kalbenutritionals.kalcaremobie.R;
import com.kalbenutritionals.kalcaremobie.Repo.clsCustomerDataRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsDraftRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsListMediaJasaRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsListPaymentMethodAndTipeRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsListPaymentMethodRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsProductDraftRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsTokenRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsUserLoginRepo;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMCustomers;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMItems;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMItemsPreview;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMLIstSo;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMOutlets;

import net.idik.lib.slimadapter.SlimAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jim.h.common.android.lib.zxing.config.ZXingLibConfig;
import jim.h.common.android.lib.zxing.integrator.IntentIntegrator;
import jim.h.common.android.lib.zxing.integrator.IntentResult;

/**
 * Created by Robert on 19/02/2018.
 */

public class FragmentAddOrder extends Fragment implements IXListViewListener, RVParentAdapter.ItemClickListener {
    View v;
    @BindView(R.id.etNoSo)
    EditText etNoSo;
    @BindView(R.id.etSoStatus)
    EditText etSoStatus;
    @BindView(R.id.input_layout_no_status)
    TextInputLayout inputLayoutNoStatus;
    @BindView(R.id.etDate)
    EditText etDate;
    @BindView(R.id.input_layout_date)
    TextInputLayout inputLayoutDate;
    @BindView(R.id.etAgentName)
    EditText etAgentName;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.etOrderLocation)
    EditText etOrderLocation;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etRemarks)
    EditText etRemarks;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.linearLayoutTop)
    LinearLayout linearLayoutTop;
    @BindView(R.id.imageScanner)
    ImageView imageScanner;
    @BindView(R.id.ivReset)
    ImageView ivReset;
    @BindView(R.id.etItemCode)
    EditText etItemCode;
    @BindView(R.id.etItemName)
    EditText etItemName;
    @BindView(R.id.btnSearchItem)
    Button btnSearchItem;
    @BindView(R.id.etQty)
    EditText etQty;
    @BindView(R.id.lvContentItemSearch)
    ListView lvContentItemSearch;
    @BindView(R.id.btnSaveDraft)
    Button btnSaveDraft;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnPreview)
    Button btnPreview;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;
    Unbinder unbinder;
    Unbinder unbinder2;
    @BindView(R.id.tvSoStatus)
    TextView tvSoStatus;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.textView10_2)
    TextView textView102;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.textView22)
    TextView textView22;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.llimgviewScanner)
    LinearLayout llimgviewScanner;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tvItemName)
    TextView tvItemName;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.tvItemPrice)
    TextView tvItemPrice;
    @BindView(R.id.etSOSource)
    EditText etSOSource;
    @BindView(R.id.etDeliverySchedule)
    EditText etDeliverySchedule;
    @BindView(R.id.input_layout_DeliverySchedule)
    TextInputLayout inputLayoutDeliverySchedule;
    @BindView(R.id.input_layout_telp2)
    TextInputLayout inputLayoutTelp2;
    @BindView(R.id.lvItemAdd)
    ListView lvItemAdd;
    private RVParentAdapter mAdapterItemHeader;
    private RVPreviewAdapter mAdapterItemPreview;
    List<VMItems> itemsadd = new ArrayList<VMItems>();
    List<VMItems> contentLibs = new ArrayList<VMItems>();
    List<VMItemsPreview> contentLibsPreviewItem = new ArrayList<VMItemsPreview>();
    @BindView(R.id.cbWalkIn)
    CheckBox cbWalkIn;
    @BindView(R.id.cbAttach)
    CheckBox cbAttach;
    @BindView(R.id.spnProvinceAddOrder)
    Spinner spnProvinceAddOrder;
    @BindView(R.id.spnKabKotAddOrder)
    Spinner spnKabKotAddOrder;
    @BindView(R.id.spnKecamatanAddOrder)
    Spinner spnKecamatanAddOrder;
    @BindView(R.id.lnCustomerDetail)
    LinearLayout lnCustomerDetail;
    @BindView(R.id.tvContactIDHeader)
    TextView tvContactIDHeader;
    @BindView(R.id.tvMemberNoHeader)
    TextView tvMemberNoHeader;
    @BindView(R.id.tvCustomerNameHeader)
    TextView tvCustomerNameHeader;
    @BindView(R.id.spnPostCodeAddOrder)
    Spinner spnPostCodeAddOrder;
    List<String> categoriesProv = new ArrayList<String>();
    List<String> categoriesPostCode = new ArrayList<String>();
    List<String> categoriesKabKot = new ArrayList<String>();
    List<String> categoriesKecamatan = new ArrayList<String>();
    List<String> categoriesKelurahan = new ArrayList<String>();

    List<String> paymentMethodList = new ArrayList<String>();
    List<String> sourceMediaPaymentList = new ArrayList<String>();
    List<String> paymentMethodAndTipe = new ArrayList<String>();

    private HashMap<String, String> HMPaymentmethodList = new HashMap<String, String>();
    private HashMap<String, String> HMsourceMediaPaymentList = new HashMap<String, String>();
    private HashMap<String, String> HMpaymentMethodAndTipe = new HashMap<String, String>();
    private HashMap<String, Integer> HMCardNumber = new HashMap<String, Integer>();
    private HashMap<String, Integer> HMTraceNumber = new HashMap<String, Integer>();

    boolean boolPaymentFill = false;

    EditText etCardNumber;
    EditText etBcaTraceNumber, etPayment;

    @BindView(R.id.spnKelurahanAddOrder)
    Spinner spnKelurahanAddOrder;
    @BindView(R.id.tvPhoneNumb)
    TextView tvPhoneNumb;
    @BindView(R.id.rv_parent)
    RecyclerView rvParent;
    //    @BindView(R.id.tvPostCodeHeader)
//    EditText tvPostCodeHeader;
    private ZXingLibConfig zxingLibConfig;
    Context context;
    List<clsToken> dataToken;
    final static String SPNMEMBERID = "Member ID";
    final static String SPNCHOOSEONE = "Choose One";
    final static String SPNPHONE = "Phone Number";
    private HashMap<String, String> HMItemCode = new HashMap<String, String>();
    private HashMap<String, String> HMItemName = new HashMap<String, String>();
    private HashMap<String, String> HMItemPrice = new HashMap<String, String>();
    private HashMap<String, String> HMItemDesc = new HashMap<String, String>();
    private HashMap<String, String> HMtxtProductCategory = new HashMap<String, String>();

    private HashMap<String, String> HMProvinceID = new HashMap<>();
    private HashMap<String, String> HMKabKotID = new HashMap<>();
    private HashMap<String, String> HMKecID = new HashMap<>();
    private HashMap<String, String> HMKel = new HashMap<>();
    private HashMap<String, String> HMKodePos = new HashMap<>();
    private HashMap<String, String> HMOutletCode = new HashMap<>();
    private HashMap<String, String> HMOrderLocationId = new HashMap<>();

    //final
    double dblTaxBasedAmountFinal = 0;
    double dbldecAmountFinal = 0;
    double dbldecTotalFinal = 0;
    double dbldecTotDiscountFinal = 0;
    double dbldecTotalPriceFinal = 0;
    double dbldecdecRoundedFinal = 0;

    boolean boolPaymentFilled = false;

    String txtPaymentMethodID = "";
    String txtMediaJasaID = "";
    String txtMediaJasaPaymentID = "";
    String txtCardNumber = "";
    String txtBCATraceNo = "";
    String txtMediaJasaName  = "";
    String txtPaymentMethodDesc = "";
    String txtCustomerFinal = "";
    String txtCustomerNameFinal = "";
    String txtPickUpLocationNameFinal = "";
    String txtmediajasapaymentFinal = "";

    String txtPropinsiIDFinal = "";
    String txtPropinsiNameFinal = "";
    String txtKabupatenKotaIDFinal = "";
    String txtKabupatenKotaNameFinal = "";
    String txtKecamatanIDFinal = "";
    String txtKecamatanNameFinal = "";
    String txtKelurahanIDFinal = "";
    String txtKelurahanNameFinal = "";
    String txtKodePosFinal ="";


    int intBitActiveFinal = 0;

    String access_token;
    String strPaymentMethod = "";
    List<String> contentLibs2 = new ArrayList<>();
    Boolean boolCustomerSet = true;
    clsUserLogin data;
    clsDraft draftData;
    clsDraft draft = null;
    ModelDevice deviceInfo = null;
    clsUserLogin dataLogin = null;
    boolean boolAttachCustomer = false;
    boolean boolRestore = false;
    private SlimAdapter slimAdapter;
    //    LoadToast lt = null;
    final static String STRSPINNEROPT = "--Choose one--"; // buat default spinner

    String provinceCustomer = "";
    String kabKotCustomer = "";
    String kecamatanCustomer = "";
    String kelurahanCustomer = "";
    String postCodeCustomer = "";
    ListView lvSearchResult;

    String paymentMethodId = "";
    String sourceMediaPaymentId = "";
    String paymentId = "";


    String mRequestBodyCheckout = "";
    String strLinkAPICheckout = "";
    //pop up search item
    EditText etQtySearch;
    EditText etSearchOnPopUp;
    EditText etDiscount;
    EditText etPrice;
    EditText etBonusPoint;
    EditText etBasedPoint;

    List<VMItems> contentSearchResult = new ArrayList<VMItems>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_order, container, false);
        context = getActivity().getApplicationContext();
        unbinder = ButterKnife.bind(this, v);
        addDefaultSpinnerAlamat();
        try {
            List<clsToken> dataToken = new clsTokenRepo(context).findAll();

            access_token = dataToken.get(0).txtUserToken.toString();
        } catch (Exception e) {
            ToastCustom.showToasty(context, "Token Empty", 2);
        }
//        lt = new LoadToast(context);
        deviceInfo = new ModelDevice();
        deviceInfo = DeviceInformation.getDeviceInformation();
        contentLibs = new ArrayList<VMItems>();
        lvItemAdd.setAdapter(new CardAppAdapter(context, new ArrayList<VMItems>(), Color.WHITE));
        rvParent.setAdapter(new RVParentAdapter(context, new ArrayList<VMItems>(), Color.WHITE));
        dataLogin = new clsUserLogin();
        dataLogin = new clsUserLoginRepo(context).getDataLogin(context);

        final Bundle arguments = getArguments();
        String noSO = "";
        if (arguments != null) {
            noSO = arguments.getString("noSO");
        }
        final SimpleDateFormat sdfi = new SimpleDateFormat("yyyy-MM-dd");
        final Date dtLoginData = dataLogin.getDtLogin();
        String currentDateandTime = sdfi.format(dtLoginData);
        final List<VMLIstSo> contentLibs = new ArrayList<>();
        try {
            draft = new clsDraftRepo(context).findByBitActive();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (draft != null) {
            if (!draft.getTxtSOStatus().equals("")) {
                etSoStatus.setText(draft.getTxtSOStatus());
            }
            if (!draft.getTxtNoSO().equals("")) {
                etNoSo.setText(draft.getTxtNoSO());
            }
            if (!draft.isBoolWalkin()) {
                getDataProvinsi();
                String txtPropinsiID = draft.getTxtProvinceID();
                String txtPropinsiName = draft.getTxtProvince();
                String txtKabupatenKotaID = draft.getTxtKabKotID();
                String txtKabupatenKotaName = draft.getTxtKabKot();
                String txtKecamatanID = draft.getTxtKecamatanID();
                String txtKecamatanName = draft.getTxtKecamatan();
                String txtKelurahanID = draft.getTxtKelurahan();
                String txtKelurahanName = draft.getTxtKelurahan();
                String txtAddress = draft.getTxtAddress();
                String txtKontakId = draft.getTxtContactID();
                String txtMemberId = draft.getTxtMemberID();
                String txtCustomerName = draft.getTxtCustomerName();
                String txtPhoneNum = draft.getTxtPhoneNumber();

                tvMemberNoHeader.setText(txtMemberId);
                tvCustomerNameHeader.setText(txtCustomerName);
                tvContactIDHeader.setText(txtKontakId);
                tvPhoneNumb.setText(txtPhoneNum);
                etAddress.setText(txtAddress);
                cbWalkIn.setChecked(false);
                String postCode = draft.getTxtPostCode() + "-" + draft.getTxtKelurahan();

                categoriesPostCode.add(postCode);
                HMKodePos.put(postCode, draft.getTxtPostCode());
                SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
                SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, postCode);

                categoriesProv.add(draft.getTxtProvince());
                HMProvinceID.put(txtPropinsiName, txtPropinsiID);
                SpinnerCustom.setAdapterSpinner(spnProvinceAddOrder, context, R.layout.custom_spinner, categoriesProv);
                SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProv, txtPropinsiName);

                categoriesKabKot.add(txtKabupatenKotaName);
                HMKabKotID.put(txtKabupatenKotaName, txtKabupatenKotaID);
                SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
                SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, txtKabupatenKotaName);

                categoriesKecamatan.add(txtKecamatanName);
                HMKecID.put(txtKecamatanName, txtKecamatanID);
                SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
                SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, txtKecamatanName);

                categoriesKelurahan.add(txtKelurahanName);
                HMKel.put(txtKelurahanName, txtKelurahanID);
                SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
                SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, txtKelurahanName);
                cbWalkIn.setChecked(false);
                cbAttach.setChecked(true);
                cbAttach.setVisibility(View.VISIBLE);
                lnCustomerDetail.setVisibility(View.VISIBLE);
            }
            if (draft.getDtDeliverySche() != null) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                String txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                etDeliverySchedule.setText(txtDeliverSche);
            }
            if (draft.isBoolAttachCustomer()) {
                getDataProvinsi();

                lnCustomerDetail.setVisibility(View.VISIBLE);
                cbAttach.setChecked(true);
                tvContactIDHeader.setText(draft.getTxtContactID());
                tvMemberNoHeader.setText(draft.getTxtMemberID());
                tvCustomerNameHeader.setText(draft.getTxtCustomerName());
                tvPhoneNumb.setText(draft.getTxtPhoneNumber());
                etAddress.setText(draft.getTxtAddress());
//                List<String> categoriesProv = new ArrayList<String>();
//                categoriesProv.add(draft.getTxtProvince());
                /*if (categoriesProv.size() == 0) {
                    categoriesProv.add(draft.getTxtProvince());
                }
                SpinnerCustom.setAdapterSpinner(spnProvinceAddOrder, context, R.layout.custom_spinner, categoriesProv);
                SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProv, draft.getTxtProvince());
                SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, draft.getTxtKabKot());
//                List<String> categoriesKabKot = new ArrayList<String>();

//                List<String> categoriesKecamatan = new ArrayList<String>();
                if (categoriesKecamatan.size() == 0) {
                    categoriesKecamatan.add(draft.getTxtKecamatan());
                }
                SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);

                if(categoriesKelurahan.size()==0){
                    categoriesKelurahan.add(draft.getTxtKelurahan());
                }
                SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
//                List<String> categoriesPostCode = new ArrayList<String>();
                if (categoriesPostCode.size() == 0) {
                    categoriesPostCode.add(draft.getTxtPostCode());
                }
                SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
                */
            }
            etRemarks.setText(draft.getTxtRemarks());
        }
        spnProvinceAddOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provinceName = categoriesProv.get(position).toString();
                String ProvinceID = HMProvinceID.get(provinceName);
                final JSONObject resJson = new JSONObject();
                try {
                    resJson.put("txtPropinsiID", ProvinceID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String strLinkSpnProvince = new clsHardCode().linkGetListKabupatenKota;

                final String mRequestBody = resJson.toString();
//                lt.setText("Retrieving data Kabupaten Kota...");
//                lt.show();

                new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkSpnProvince, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
                    @Override
                    public void onError(String response) {
                        ToastCustom.showToasty(context, response, 2);
//                        lt.hide();
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
                                        categoriesKabKot = new ArrayList<String>();
                                        categoriesKabKot.add(STRSPINNEROPT);
                                        HMKabKotID.put(STRSPINNEROPT, "0");
                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
                                            String ltxKeterangan = object.getString("ltxKeterangan");
                                            String txtNamaKabKot = object.getString("txtNamaKabKota");
                                            String txtKabKotID = object.getString("txtKabKotaID");
                                            String txtPropinsiID = object.getString("txtPropinsiID");
                                            categoriesKabKot.add(txtNamaKabKot);
                                            HMKabKotID.put(txtNamaKabKot, txtKabKotID);
                                        }
                                        spnKabKotAddOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                if (categoriesKabKot.size() > 0) {
                                                    String kabKotName = categoriesKabKot.get(position).toString();
                                                    String kabKotID = HMKabKotID.get(kabKotName);
                                                    if (!kabKotID.equals("0")) {
                                                        final JSONObject resJson = new JSONObject();
                                                        try {
                                                            resJson.put("txtKabupateKotaID", kabKotID);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        String strLinkSpnKabKot = new clsHardCode().linkGetListKecamatan;

                                                        final String mRequestBody = resJson.toString();
//                        lt.setText("Retrieving data Kecamatan...");
                                                        new VolleyUtils().makeJsonObjectRequestWithTokenWithoutProgressD(getActivity(), strLinkSpnKabKot, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
                                                            @Override
                                                            public void onError(String response) {
                                                                ToastCustom.showToasty(context, response, 2);
//                                lt.hide();
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
                                                                                categoriesKecamatan = new ArrayList<String>();
                                                                                categoriesKecamatan.add(STRSPINNEROPT);
                                                                                HMKecID.put(STRSPINNEROPT, "0");
                                                                                for (int n = 0; n < jsn.length(); n++) {
                                                                                    JSONObject object = jsn.getJSONObject(n);
                                                                                    String txtKecamatanID = object.getString("txtKecamatanID");
                                                                                    String txtNamaKecamatan = object.getString("txtNamaKecamatan");
                                                                                    String ltxKeterangan = object.getString("ltxKeterangan");
                                                                                    String txtKabKotaID = object.getString("txtKabKotaID");
                                                                                    categoriesKecamatan.add(txtNamaKecamatan);
                                                                                    HMKecID.put(txtNamaKecamatan, txtKecamatanID);
                                                                                }
                                                                                spnKecamatanAddOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                    @Override
                                                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                        spinnerKecamatan(position);
                                                                                    }

                                                                                    @Override
                                                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                                                    }
                                                                                });
                                                                                if (!boolAttachCustomer) {
                                                                                    SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
                                                                                } else {
//                                                                                    boolAttachCustomer = false;
                                                                                    SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
                                                                                    SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, kecamatanCustomer);
                                                                                }


                                                                                if (draft != null) {
                                                                                    SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, draft.getTxtKecamatan());
                                                                                } else {
//                                                                                    SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, STRSPINNEROPT);
//                                                                                    categoriesKelurahan.add(STRSPINNEROPT);
//                                                                                    SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKelurahan, STRSPINNEROPT);
//                                                                                    categoriesPostCode.add(STRSPINNEROPT);
//                                                                                    SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, STRSPINNEROPT);
                                                                                }

                                                                            }
                                                                        }
                                                                    } catch (JSONException e) {

                                                                    }

                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        categoriesKecamatan = new ArrayList<>();
                                                        categoriesPostCode = new ArrayList<>();
                                                        categoriesKelurahan = new ArrayList<>();
                                                        addDefaultSpinnerAlamat();
                                                        SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
                                                        SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, STRSPINNEROPT);

                                                        SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
                                                        SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, STRSPINNEROPT);

//                                                        categoriesPostCode.add(STRSPINNEROPT);
//                                                        HMKodePos.put(STRSPINNEROPT, "0");
                                                        SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
                                                        SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, STRSPINNEROPT);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                        if (!boolAttachCustomer) {
                                            SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
//                                            categoriesKecamatan.add(STRSPINNEROPT);
//                                            HMKecID.put(STRSPINNEROPT, "0");
                                            SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
                                            SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, STRSPINNEROPT);

//                                            categoriesKelurahan.add(STRSPINNEROPT);
//                                            HMKel.put(STRSPINNEROPT, "0");
                                            SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
                                            SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, STRSPINNEROPT);

//                                            categoriesPostCode.add(STRSPINNEROPT);
//                                            HMKodePos.put(STRSPINNEROPT, "0");
                                            SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
                                            SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, STRSPINNEROPT);

                                        } else {
//                                            boolAttachCustomer = false;
                                            SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
                                            SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, kabKotCustomer);

                                        }


                                        if (draft != null && draft.getTxtKabKot() != null) {
                                            SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, draft.getTxtKabKot());
                                        } else {
//                                            SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, STRSPINNEROPT);
                                        }

                                    }
                                }
                            } catch (JSONException e) {

                            }

                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


            /*spnKelurahanAddOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(categoriesKelurahan.size()>0){
                        String kelName = categoriesKelurahan.get(position).toString();
                        String kelID = HMKel.get(kelName);
                        if(!kelID.equals("0")){
                            final JSONObject resJson = new JSONObject();
                            try {
                                resJson.put("txtKecamatanID", kelID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String strLinkKel = new clsHardCode().linkGetListKelurahan;

                            final String mRequestBody = resJson.toString();
//                        lt.setText("Retrieving data Kode Pos...");
                            new VolleyUtils().makeJsonObjectRequestWithTokenWithoutProgressD(getActivity(), strLinkKel, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
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
                                                    categoriesKabKot = new ArrayList<String>();
                                                    for (int n = 0; n < jsn.length(); n++) {
                                                        JSONObject object = jsn.getJSONObject(n);
                                                        String ltxKeterangan = object.getString("ltxKeterangan");
                                                        String txtNamaKabKot = object.getString("txtNamaKabKota");
                                                        String txtKabKotID = object.getString("txtKabKotaID");
                                                        String txtPropinsiID = object.getString("txtPropinsiID");
                                                        categoriesKabKot.add(txtNamaKabKot);
                                                        HMKabKotID.put(txtNamaKabKot, txtKabKotID);
                                                    }

                                                    SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
                                                }
                                            }
                                        } catch (JSONException e) {

                                        }

                                    }
                                }
                            });
                        }else{

                        }

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/


        draftData = new clsDraft();

        etItemName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etItemCode != null) etItemCode.setText("");

            }
        });
        etItemCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etItemName != null) etItemName.setText("");
            }
        });
        data = new clsUserLoginRepo(context).getDataLogin(context);
        if (data != null) {
            etAgentName.setText(data.getNmUser());
            etSOSource.setText(data.getTxtNamaInstitusi());
            etOrderLocation.setText(data.getTxtNamaInstitusi());
            HMOutletCode.put(data.getTxtNamaInstitusi(), data.getTxtSumberDataID());
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            String date = sdf.format(dtLoginData);
            etDate.setText(date);
        }
        etOrderLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strLinkAPI = new clsHardCode().linkGetDataOutletKALCARE;
                final JSONObject resJson = new JSONObject();
                try {
                    resJson.put("txtOutlet", "");
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
                            try {
                                jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("intResult");
                                String warn = jsonObject.getString("txtMessage");
                                if (result == 1) {
                                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                                    AlertDialog.Builder alertDialogBuilderAttch = new AlertDialog.Builder(getActivity());
                                    final View promptViewOutlet = layoutInflater.inflate(R.layout.popup_outlet_option, null);
                                    final ListView lvOutletOption = (ListView) promptViewOutlet.findViewById(R.id.lvOutletOption);
                                    if (!jsonObject.getString("ListData").equals("null")) {
                                        JSONArray jsn = jsonObject.getJSONArray("ListData");
                                        final List<VMOutlets> contentOutletsResult = new ArrayList<VMOutlets>();
                                        VMOutlets item = new VMOutlets();
                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
                                            String txtSumberDataID = object.getString("txtSumberDataID");
                                            String lttxtTipeSumberDataID = object.getString("lttxtTipeSumberDataID");
                                            String txtPropinsiID = object.getString("txtPropinsiID");
                                            String txtKabKotaID = object.getString("txtKabKotaID");
                                            String txtKodePosID = object.getString("txtKodePosID");
                                            String txtNamaInstitusi = object.getString("txtNamaInstitusi");
                                            String txtAlamat = object.getString("txtAlamat");
                                            String txtCabangID = object.getString("txtCabangID");
                                            String txtSumberDataIDAlias = object.getString("txtSumberDataIDAlias");
                                            String txtNamaInstitusiAlias = object.getString("txtNamaInstitusiAlias");
                                            item = new VMOutlets();
                                            item.setTxtNamaInstitusi(txtNamaInstitusi);
                                            item.setTxtSumberDataID(txtSumberDataID);
                                            item.setTxtAlamat(txtAlamat);
                                            contentOutletsResult.add(item);
                                            HMOutletCode.put(txtNamaInstitusi, txtSumberDataID);
                                        }
                                        lvOutletOption.setAdapter(new CardAdapterOutet(context, contentOutletsResult, Color.WHITE));
                                        setListViewHeightBasedOnItems(lvOutletOption);
                                        lvOutletOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            }
                                        });
                                        AlertDialog.Builder alertDialogBuilderOutlet = new AlertDialog.Builder(getActivity());
                                        alertDialogBuilderAttch.setView(promptViewOutlet);
//        alertDialogBuilderAttch.setMessage("this is attachment of customer dialog");
                                        alertDialogBuilderAttch
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setCancelable(false)
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                        cbAttach.setChecked(false);
                                                    }
                                                });
                                        final AlertDialog alertD = alertDialogBuilderAttch.create();
                                        alertD.show();
                                        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (lvOutletOption.getCheckedItemPosition() > -1) {
                                                    int position = lvOutletOption.getCheckedItemPosition();
                                                    String txtOutlet = contentOutletsResult.get(position).getTxtNamaInstitusi();
                                                    etOrderLocation.setText(txtOutlet);
                                                    alertD.dismiss();
                                                } else {
                                                    ToastCustom.showToasty(context, "Please pick the outlet first", 3);
                                                }
                                            }
                                        });
                                    }
                                }
                            } catch (JSONException ex) {

                            }
                        }
                    }
                });
            }
        });

        lvContentItemSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastCustom.showToasty(context, "Item picked", 3);
                String itemName = contentLibs2.get(position).toString();
                String itemPrice = HMItemPrice.get(itemName);
                String itemCode = HMItemCode.get(itemName);
                etItemCode.setText(itemCode);
                etItemName.setText(itemName);
                tvItemPrice.setText(itemPrice);
                etQty.setFocusable(true);
            }
        });
        cbWalkIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Debug.waitForDebugger();
                if (isChecked) {
//                    cbAttach.setChecked(true);
                    cbAttach.setChecked(false);
                    cbAttach.setVisibility(View.GONE);
                    lnCustomerDetail.setVisibility(View.GONE);
                } else {
                    cbAttach.setChecked(true);
                    cbAttach.setVisibility(View.VISIBLE);
                    /*if (!cbAttach.isChecked()) {
                        etAddress.setVisibility(View.GONE);
                        lnCustomerDetail.setVisibility(View.GONE);
                    } else {
                        etAddress.setVisibility(View.VISIBLE);
                        lnCustomerDetail.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        });
        cbAttach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!boolRestore) {
                    if (isChecked) {
                        LayoutInflater layoutInflater = LayoutInflater.from(context);
                        AlertDialog.Builder alertDialogBuilderAttch = new AlertDialog.Builder(getActivity());
                        final View promptView = layoutInflater.inflate(R.layout.popup_customer, null);
//                    unbinder = ButterKnife.bind(this, promptView);
                        Button btnSearchCustomer = (Button) promptView.findViewById(R.id.btnSearchCustomer);
                        final Spinner spnOpsiSearchCustomer = (Spinner) promptView.findViewById(R.id.spnJenisCustomer);
                        final EditText etSearchCustomer = (EditText) promptView.findViewById(R.id.etSearchCustomer);
                        final EditText etContactID = (EditText) promptView.findViewById(R.id.etContactID);
                        final EditText etPhoneNumb = (EditText) promptView.findViewById(R.id.etPhoneNumb);
                        final EditText etMemberNo = (EditText) promptView.findViewById(R.id.etMemberNo);
                        final TextView tvMemberNo = (TextView) promptView.findViewById(R.id.tvMemberNo);
                        final EditText etCustomerName = (EditText) promptView.findViewById(R.id.etCustomerName);
//                    final EditText spnPostCodeCustomer = (EditText) promptView.findViewById(R.id.etPostCodeCustomer);
                        final Spinner spnPostCodeCustomer = (Spinner) promptView.findViewById(R.id.spnPostCodeCustomer);
                        final Spinner spnProvinceCustomer = (Spinner) promptView.findViewById(R.id.spnProvinceCustomer);
                        final Spinner spnKabKotCustomer = (Spinner) promptView.findViewById(R.id.spnKabKotCustomer);
                        final Spinner spnKecamatanCustomer = (Spinner) promptView.findViewById(R.id.spnKecamatanCustomer);
                        final Spinner spnKelurahanCustomer = (Spinner) promptView.findViewById(R.id.spnKelurahanCustomer);
                        final String strLinkAPI = new clsHardCode().linkSearchCustomer;
                        final JSONObject resJson = new JSONObject();
                        btnSearchCustomer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String keyword = etSearchCustomer.getText().toString();
                                if (keyword.equals("")) {
                                    ToastCustom.showToasty(context, "Keyword Empty", 4);
                                } else {
                                    if (spnOpsiSearchCustomer.getSelectedItem().toString().equals(SPNMEMBERID)) {
                                        try {
                                            resJson.put("txtPhone", "");
                                            resJson.put("txtMember", keyword);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
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
                                                JSONObject jsonObject = null;
                                                try {
                                                    jsonObject = new JSONObject(response);
                                                    int result = jsonObject.getInt("intResult");
                                                    String warn = jsonObject.getString("txtMessage");
                                                    if (result == 1) {
                                                        if (!jsonObject.getString("ListData").equals("null")) {
                                                            LayoutInflater layoutInflater = LayoutInflater.from(context);
                                                            final View promptView = layoutInflater.inflate(R.layout.popup_customer_search, null);

                                                            final ListView lvCustomerSearch = (ListView) promptView.findViewById(R.id.lvCustSearchResult);
                                                            final List<VMCustomers> contentSearchResult = new ArrayList<VMCustomers>();
                                                            VMCustomers item = new VMCustomers();

                                                            JSONArray jsn = jsonObject.getJSONArray("ListData");
                                                            for (int n = 0; n < jsn.length(); n++) {
                                                                JSONObject object = jsn.getJSONObject(n);
                                                                String txtContactID = object.getString("txtKontakID");
                                                                String txtAlamat = object.getString("txtAlamat");
                                                                String txtNama = object.getString("txtNama");
                                                                String txtKabKot = object.getString("txtNamaKabKota");
                                                                String txtProv = object.getString("txtNamaPropinsi");
                                                                String txtjenisAlm = object.getString("JenisAlamat");
                                                                String txtListMed = object.getString("txtListMedia");
                                                                String txtKec = object.getString("txtNamaKecamatan");
                                                                String txtKel = object.getString("txtNamaKelurahan");
                                                                String txtNHDSiteID = object.getString("txtNHDSiteID");
                                                                String txtProvID = object.getString("txtPropinsiID");
                                                                String txtKABKotID = object.getString("txtKabKotaID");
                                                                String txtKodePos = object.getString("txtKodePos");
                                                                String txtKacamatan = object.getString("txtKecamatan");

                                                                item = new VMCustomers();
                                                                item.setTxtContactID(txtContactID);
                                                                item.setTxtAlamat(txtAlamat);
                                                                item.setTxtNama(txtNama);
                                                                item.setTxtKabKot(txtKabKot);
                                                                item.setTxtProv(txtProv);
                                                                item.setTxtjenisAlm(txtjenisAlm);
                                                                item.setTxtListMed(txtListMed);
                                                                item.setTxtKec(txtKec);
                                                                item.setTxtKel(txtKel);
                                                                item.setTxtNHDSiteID(txtNHDSiteID);
                                                                item.setTxtProvID(txtProvID);
                                                                item.setTxtKABKotID(txtKABKotID);
                                                                item.setTxtKodePos(txtKodePos);
                                                                item.setTxtKecID(txtKacamatan);

                                                                contentSearchResult.add(item);
                                                            }
                                                            lvCustomerSearch.setAdapter(new CardAdapterCustomerSearch(context, contentSearchResult, Color.WHITE));
                                                            final VMItems itemSelected = null;
                                                            lvCustomerSearch.setSelection(0);
                                                            lvCustomerSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                @Override
                                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                    view.setSelected(true);

                                                                }
                                                            });
                                                            AlertDialog.Builder alertDialogBuilderAttch = new AlertDialog.Builder(getActivity());
                                                            alertDialogBuilderAttch.setView(promptView);
//        alertDialogBuilderAttch.setMessage("this is attachment of customer dialog");
                                                            alertDialogBuilderAttch
                                                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                        }
                                                                    })
                                                                    .setCancelable(false)
                                                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int id) {
                                                                            dialog.cancel();
                                                                        }
                                                                    });
                                                            final AlertDialog alertD = alertDialogBuilderAttch.create();
                                                            if (jsn.length() == 0) {
                                                                ToastCustom.showToasty(context, "Customer Not Found", 3);
                                                            } else {
                                                                alertD.show();
                                                                alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        String contactID = "";
                                                                        String province = "";
                                                                        String kabkot = "";
                                                                        String kecamatan = "";
                                                                        String kelurahan = "";
                                                                        String postCode = "";
                                                                        String name = "";
                                                                        String address = "";
                                                                        String provinceID = "";
                                                                        String kabKotID = "";
                                                                        String kecID = "";
                                                                        String kelID = "";
                                                                        String phoneNumb = "";

                                                                        if (lvCustomerSearch.getCheckedItemPosition() < 0) {
                                                                            ToastCustom.showToasty(context, "Pick customer first", 3);
                                                                        } else {
                                                                            int position = lvCustomerSearch.getCheckedItemPosition();
                                                                            contactID = contentSearchResult.get(position).getTxtContactID();
                                                                            province = contentSearchResult.get(position).getTxtProv();
                                                                            kabkot = contentSearchResult.get(position).getTxtKabKot();
                                                                            phoneNumb = contentSearchResult.get(position).getTxtListMed();
                                                                            kecamatan = contentSearchResult.get(position).getTxtKec();
                                                                            postCode = contentSearchResult.get(position).getTxtKodePos();
                                                                            address = contentSearchResult.get(position).getTxtAlamat();
                                                                            name = contentSearchResult.get(position).getTxtNama();
                                                                            provinceID = contentSearchResult.get(position).getTxtProvID();
                                                                            kabKotID = contentSearchResult.get(position).getTxtProvID();
                                                                            kecID = contentSearchResult.get(position).getTxtKecID();
                                                                            kelID = contentSearchResult.get(position).getTxtKel();
                                                                            kelurahan = contentSearchResult.get(position).getTxtKel();
                                                                            if (!spnOpsiSearchCustomer.getSelectedItem().toString().equals(SPNMEMBERID)) {
                                                                                tvMemberNo.setText("Phone Number");
                                                                            }
                                                                            etMemberNo.setText(keyword);
                                                                            etContactID.setText(contactID);
                                                                            etPhoneNumb.setText(phoneNumb);
//                                                                        spnPostCodeCustomer.setText(postCode);
                                                                            etCustomerName.setText(name);
                                                                            etAddress.setText(address);
                                                                            List<String> categoriesPostCode = new ArrayList<String>();
                                                                            String kodeposKelurahan = postCode + "-" + kelurahan;
                                                                            categoriesPostCode.add(kodeposKelurahan);
                                                                            HMKodePos.put(kodeposKelurahan, postCode);


                                                                            SpinnerCustom.setAdapterSpinner(spnPostCodeCustomer, context, R.layout.custom_spinner, categoriesPostCode);
                                                                            List<String> categoriesProv = new ArrayList<String>();
                                                                            categoriesProv.add(province);
                                                                            HMProvinceID.put(province, provinceID);
                                                                            SpinnerCustom.setAdapterSpinner(spnProvinceCustomer, context, R.layout.custom_spinner, categoriesProv);

                                                                            List<String> categoriesKabKot = new ArrayList<String>();
                                                                            categoriesKabKot.add(kabkot);
                                                                            HMKabKotID.put(kabkot, kabKotID);
                                                                            SpinnerCustom.setAdapterSpinner(spnKabKotCustomer, context, R.layout.custom_spinner, categoriesKabKot);

                                                                            List<String> categoriesKecamatan = new ArrayList<String>();
                                                                            categoriesKecamatan.add(kecamatan);
                                                                            HMKecID.put(kecamatan, kecID);
                                                                            SpinnerCustom.setAdapterSpinner(spnKecamatanCustomer, context, R.layout.custom_spinner, categoriesKecamatan);

                                                                            List<String> categoriesKelurahan = new ArrayList<String>();
                                                                            categoriesKelurahan.add(kelurahan);
                                                                            HMKel.put(kelurahan, kelurahan);
                                                                            SpinnerCustom.setAdapterSpinner(spnKelurahanCustomer, context, R.layout.custom_spinner, categoriesKelurahan);

                                                                            if (contactID.equals("") && province.equals("") && kabkot.equals("") && phoneNumb.equals("") && kecamatan.equals("") && kelurahan.equals("") && postCode.equals("")) {
                                                                                ToastCustom.showToasty(context, "Data Invalid", 2);
                                                                            } else {
                                                                                alertD.dismiss();
                                                                            }


                                                                        }

                                                                    }
                                                                });
                                                            }

                                                        }
                                                    } else {
                                                        ToastCustom.showToasty(context, warn, 2);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                                String a = "";
                                            }
                                        }
                                    });
                                }


                            }
                        });
                        List<String> categories = new ArrayList<String>();
//                    categories.add("Phone Number");
                        categories.add(SPNMEMBERID);
                        categories.add(SPNPHONE);
                        SpinnerCustom.setAdapterSpinner(spnOpsiSearchCustomer, context, R.layout.custom_spinner, categories);

                        alertDialogBuilderAttch.setView(promptView);
//                    alertDialogBuilderAttch.setMessage("this is attachment of customer dialog");
                        alertDialogBuilderAttch
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setCancelable(false)
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        cbAttach.setChecked(false);
                                        cbWalkIn.setChecked(true);
                                    }
                                });
                        final AlertDialog alertD = alertDialogBuilderAttch.create();
                        alertD.show();
                        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                try {
                                    resJson.put("txtPropinsiID", "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String strLinkProvince = new clsHardCode().linkGetListPropinsi;
//getDataProvinsi();
                                final String mRequestBody = resJson.toString();
                                new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkProvince, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
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

                                                        for (int n = 0; n < jsn.length(); n++) {
                                                            JSONObject object = jsn.getJSONObject(n);
                                                            String ltxKeterangan = object.getString("ltxKeterangan");
                                                            String txtNamaPropinsi = object.getString("txtNamaPropinsi");
                                                            String txtPropinsiID = object.getString("txtPropinsiID");
                                                            String txtNegaraID = object.getString("txtNegaraID");
                                                            categoriesProv.add(txtNamaPropinsi);
                                                            HMProvinceID.put(txtNamaPropinsi, txtPropinsiID);
                                                        }
                                                    }
                                                    if (etContactID.getText().toString().equals("")) {
                                                        ToastCustom.showToasty(context, "Customer Empty", 3);
                                                    } else {
                                                        tvContactIDHeader.setText(etContactID.getText().toString());
                                                        tvMemberNoHeader.setText(etMemberNo.getText().toString());
                                                        tvCustomerNameHeader.setText(etCustomerName.getText().toString());
                                                        tvPhoneNumb.setText(etPhoneNumb.getText().toString());

                                                        provinceCustomer = spnProvinceCustomer.getSelectedItem().toString();
                                                        kabKotCustomer = spnKabKotCustomer.getSelectedItem().toString();
                                                        kecamatanCustomer = spnKecamatanCustomer.getSelectedItem().toString();
                                                        kelurahanCustomer = spnKelurahanCustomer.getSelectedItem().toString();
                                                        postCodeCustomer = spnPostCodeCustomer.getSelectedItem().toString();


                                                        categoriesPostCode.add(postCodeCustomer);
                                                        SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
                                                        SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, postCodeCustomer);


                                                        SpinnerCustom.setAdapterSpinner(spnProvinceAddOrder, context, R.layout.custom_spinner, categoriesProv);
                                                        SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProv, provinceCustomer);

                                                        categoriesKabKot.add(kabKotCustomer);
                                                        SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
                                                        SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, kabKotCustomer);

                                                        categoriesKecamatan.add(kecamatanCustomer);
                                                        SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
                                                        SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, kecamatanCustomer);

                                                        categoriesKelurahan.add(kelurahanCustomer);
                                                        SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
                                                        SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, kelurahanCustomer);

                                                        alertD.dismiss();
                                                        lnCustomerDetail.setVisibility(View.VISIBLE);
                                                        boolAttachCustomer = true;
                                                    }
                                                }
                                            } catch (JSONException e) {

                                            }

                                        }
                                    }
                                });


                            }
                        });
                    } else {
                        lnCustomerDetail.setVisibility(View.GONE);
                        tvContactIDHeader.setText("");
                        tvMemberNoHeader.setText("");
                        tvCustomerNameHeader.setText("");
                        tvPhoneNumb.setText("");
                        categoriesPostCode.add("");
//                        categoriesPostCode = new ArrayList<>();
                        SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
//                    SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, "");

//                        categoriesProv = new ArrayList<>();
                        SpinnerCustom.setAdapterSpinner(spnProvinceAddOrder, context, R.layout.custom_spinner, categoriesProv);
//                    SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProv, "");

//                        categoriesKabKot = new ArrayList<>();
                        SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
//                    SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesKabKot, "");

//                        categoriesKecamatan = new ArrayList<>();
                        SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
//                    SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesKecamatan, "");

//                        categoriesKelurahan = new ArrayList<>();
                        SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
//                    SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, "");
                        cbWalkIn.setChecked(true);
                    }
                } else {
                    boolRestore = false;
                }

            }
        });

        /*//Spinner Address
        List<String> categoriesProvince = new ArrayList<String>();
        List<String> categoriesKabKot = new ArrayList<String>();
        List<String> categoriesKecamatan = new ArrayList<String>();

        categoriesProvince.add("--Select One--");
        categoriesProvince.add("DKI Jakarta");
        categoriesProvince.add("Bandung");

        categoriesKabKot.add("--Select One--");
        categoriesKabKot.add("Jakarta Utara");
        categoriesKabKot.add("Jakarta Selatan");


        categoriesKecamatan.add("--Select One--");
        categoriesKecamatan.add("Sunter Jaya");
        categoriesKecamatan.add("Kelapa Gading");


        SpinnerCustom.setAdapterSpinner(spnProvinceAddOrder, context, R.layout.custom_spinner, categoriesProvince);
        SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProvince, "Jakarta");

        SpinnerCustom.setAdapterSpinner(spnKabKotAddOrder, context, R.layout.custom_spinner, categoriesKabKot);
        SpinnerCustom.selectedItemByText(context, spnKabKotAddOrder, categoriesProvince, "Jakarta Utara");

        SpinnerCustom.setAdapterSpinner(spnKecamatanAddOrder, context, R.layout.custom_spinner, categoriesKecamatan);
        SpinnerCustom.selectedItemByText(context, spnKecamatanAddOrder, categoriesProvince, "Kelapa Gading");*/
        if (cbWalkIn.isChecked()) {
            cbAttach.setChecked(false);
            lnCustomerDetail.setVisibility(View.GONE);
        }
        return v;
    }

    private void getDataProvinsi() {
        final JSONObject resJson = new JSONObject();
        try {
            resJson.put("txtPropinsiID", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strLinkProvince = new clsHardCode().linkGetListPropinsi;

        final String mRequestBody = resJson.toString();
        new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkProvince, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
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

                                for (int n = 0; n < jsn.length(); n++) {
                                    JSONObject object = jsn.getJSONObject(n);
                                    String ltxKeterangan = object.getString("ltxKeterangan");
                                    String txtNamaPropinsi = object.getString("txtNamaPropinsi");
                                    String txtPropinsiID = object.getString("txtPropinsiID");
                                    String txtNegaraID = object.getString("txtNegaraID");
                                    categoriesProv.add(txtNamaPropinsi);
                                    HMProvinceID.put(txtNamaPropinsi, txtPropinsiID);

                                }
                                SpinnerCustom.setAdapterSpinner(spnProvinceAddOrder, context, R.layout.custom_spinner, categoriesProv);
                                SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProv, draft.getTxtProvince());
                            }
                        }
                    } catch (JSONException e) {

                    }

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult.getContents() == null && scanResult.getFormatName() == null) {
                ToastCustom.showToasty(context, "Barcode Scanner Canceled", 3);
                return;
            } else {
                ToastCustom.showToasty(context, "Barcode Scanner Done", 1);
                String barcode = scanResult.getContents().toString();
                String strLinkAPI = new clsHardCode().linkSearch;
                final JSONObject resJson = new JSONObject();
                try {
                    resJson.put("txtProductBarcode", barcode);
                    resJson.put("txtProductCode", "");
                    resJson.put("txtProductDesc", "");
//            resJson.put("txtRefreshToken", token);
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
                            try {
                                jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("intResult");
                                String warn = jsonObject.getString("txtMessage");
                                if (result == 1) {
                                    if (!jsonObject.getString("ListData").equals("null")) {

                                        LayoutInflater layoutInflater = LayoutInflater.from(context);
                                        final View promptView = layoutInflater.inflate(R.layout.popup_product_search_barcode, null);
                                        final ListView lvSearchResult = (ListView) promptView.findViewById(R.id.lvItemSearchResult);
                                        final EditText etQtySearch = (EditText) promptView.findViewById(R.id.etQty);
                                        final EditText etDiscount = (EditText) promptView.findViewById(R.id.etDiscount);
                                        final EditText etPrice = (EditText) promptView.findViewById(R.id.etPrice);
                                        final EditText etBonusPoint = (EditText) promptView.findViewById(R.id.etBonusPoint);
                                        final EditText etBasedPoint = (EditText) promptView.findViewById(R.id.etBasePoint);
                                        final List<VMItems> contentSearchResult = new ArrayList<VMItems>();
                                        VMItems item = new VMItems(promptView);

                                        JSONArray jsn = jsonObject.getJSONArray("ListData");
                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
                                            String txtProductCategory = object.getString("txtProductCategory");
                                            String txtBrand = object.getString("txtBrand");
                                            String txtGroupProduct = object.getString("txtGroupProduct");
                                            String txtProductBarcode = object.getString("txtProductBarcode");
                                            String txtProductCode = object.getString("txtProductCode");
                                            String txtProductDesc = object.getString("txtProductDesc");

                                            item = new VMItems(promptView);
                                            item.setGuiid(new Helper().GenerateGuid());
                                            item.setItemName(txtProductDesc);
//                                            item.setPrice(80000);
                                            item.setItemGroup(txtGroupProduct);
                                            item.setBarcode(txtProductBarcode);
                                            item.setItemCode(txtProductCode);
                                            item.setDesc(txtProductDesc);
                                            item.setItemBrand(txtBrand);

                                            contentSearchResult.add(item);

                                        }
                                        lvSearchResult.setAdapter(new CardAdapterSearchResult(context, contentSearchResult, Color.WHITE));
                                        final VMItems itemSelected = null;
                                        lvSearchResult.setSelection(0);
                                        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                view.setSelected(true);
                                                VMItems itemSelected = null;
                                                itemSelected = contentSearchResult.get(position);
                                                String txtGroupUser = itemSelected.getItemGroup();
                                                String txtItemCode = itemSelected.getItemCode();
                                                int Qty = itemSelected.getQty();
                                                String intQty = String.valueOf(Qty);
                                                String txtKontakID = dataLogin.getTxtKontakID();
                                                String txtNoLangganan = "";
                                                String intPeriodeLangganan = "0";
                                                final String strLinkAPI = new clsHardCode().linkGetPrice;
                                                final JSONObject resJson = new JSONObject();
                                                try {
                                                    resJson.put("txtGroupUser", txtGroupUser);
                                                    resJson.put("txtItemCode", txtItemCode);
                                                    resJson.put("intQty", intQty);
                                                    resJson.put("txtKontakID", txtKontakID);
                                                    resJson.put("txtNoLangganan", txtNoLangganan);
                                                    resJson.put("intPeriodeLangganan", intPeriodeLangganan);
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
                                                        JSONObject jsonObjectPrice = null;
                                                        if (response != null) {
                                                            try {
                                                                jsonObjectPrice = new JSONObject(response);
                                                                int result = jsonObjectPrice.getInt("intResult");
                                                                String warn = jsonObjectPrice.getString("txtMessage");
                                                                if (result == 1) {
                                                                    if (!jsonObjectPrice.getString("ListData").equals("null")) {
                                                                        JSONArray jsn = jsonObjectPrice.getJSONArray("ListData");
                                                                        for (int n = 0; n < jsn.length(); n++) {
                                                                            JSONObject object = jsn.getJSONObject(n);
                                                                            String txtDiscount = object.getString("decDiscount");
                                                                            String txtPrice = object.getString("decPriceList");
                                                                            String txtBasePoint = object.getString("decBasePoint");
                                                                            String txtDecBonus = object.getString("decBonus");
                                                                            etDiscount.setText(txtDiscount);
                                                                            etPrice.setText(txtPrice);
                                                                            etBonusPoint.setText(txtDecBonus);
                                                                            etBasedPoint.setText(txtBasePoint);
                                                                        }
                                                                    }
                                                                }
                                                            } catch (JSONException e) {

                                                            }

                                                            String a = "";
                                                        }
                                                    }
                                                });

                                            }
                                        });
                                        AlertDialog.Builder alertDialogBuilderAttch = new AlertDialog.Builder(getActivity());
                                        alertDialogBuilderAttch.setView(promptView);
//        alertDialogBuilderAttch.setMessage("this is attachment of customer dialog");
                                        alertDialogBuilderAttch
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setCancelable(false)
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                        cbAttach.setChecked(false);
                                                        lvSearchResult.setSelected(false);
                                                    }
                                                });
                                        final AlertDialog alertD = alertDialogBuilderAttch.create();
                                        alertD.show();
                                        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (lvSearchResult.getCheckedItemPosition() > -1) {
                                                    if (etQtySearch.getText().toString().equals("")) {
                                                        ToastCustom.showToasty(context, "Quantity Empty", 3);
                                                        etQtySearch.setBackgroundResource(R.drawable.bg_edtext_error_border);
                                                    } else {
                                                        Object checkedItem = lvSearchResult.getAdapter().getItem(lvSearchResult.getCheckedItemPosition());
                                                        VMItems itemResult = new VMItems(promptView);
                                                        int position = lvSearchResult.getCheckedItemPosition();
                                                        String itemNameAdd = contentSearchResult.get(position).getItemName();
                                                        String itemBrandAdd = contentSearchResult.get(position).getItemBrand();
                                                        String itemCodeAdd = contentSearchResult.get(position).getItemCode();
                                                        String txtPrice = etPrice.getText().toString();
                                                        double itemPriceSearch = 0;
                                                        if (!txtPrice.equals("0")) {
                                                            itemPriceSearch = Double.parseDouble(txtPrice);
                                                        }
                                                        String txtDisc = etDiscount.getText().toString();
                                                        double itemDiscount = 0;
                                                        if (!txtDisc.equals("0")) {
                                                            itemDiscount = Double.parseDouble(txtDisc);
                                                        }

                                                        String itemQtyAdd = etQtySearch.getText().toString();


                                                        int itemQty = Integer.parseInt(itemQtyAdd);

                                                        Double itemTotalPrice = (itemPriceSearch * itemQty) - itemDiscount;

                                                        Double itemTaxAmount = itemTotalPrice * 0.1;

                                                        Double itemNetPrice = itemTotalPrice + itemTaxAmount;

                                                        String itemBasePoint = etBasedPoint.getText().toString();
                                                        String itemBonusPoint = etBonusPoint.getText().toString();

                                                        VMItems item = new VMItems(promptView);
                                                        item.setItemName(itemNameAdd);
                                                        item.setItemBrand(itemBrandAdd);
                                                        item.setItemCode(itemCodeAdd);
                                                        item.setProductCategory(HMtxtProductCategory.get(itemCodeAdd));
                                                        item.setPrice(itemPriceSearch);
                                                        item.setBasePoint(itemBasePoint);
                                                        item.setQty(itemQty);
                                                        item.setDiscount(itemDiscount);
                                                        item.setTotalPrice(itemTotalPrice);
                                                        item.setTaxAmount(itemTaxAmount);
                                                        item.setNetPrice(itemNetPrice);
                                                        item.setBonusPoint(itemBonusPoint);


                                                        boolean booladded = addItem(item);
                                                        if (booladded) {
                                                            alertD.dismiss();
                                                        }
                                                    }

                                                } else {
                                                    ToastCustom.showToasty(context, "Pick the item first", 4);
                                                }
                                            }
                                        });

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String a = "";
                        }
                    }
                });

            }
            final String result = scanResult.getContents();
            Log.d("Data info", "Scanning Success result = " + result);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnNext)
    public void onBtnNextClicked() {
        if (etDeliverySchedule.getText().toString().equals("")) {
            ToastCustom.showToasty(context, "Please set delivery schedule", 3);
        } else {
            lvItemAdd.setAdapter(new CardAppAdapter(context, new ArrayList<VMItems>(), Color.WHITE));
            rvParent.setAdapter(new RVParentAdapter(context, new ArrayList<VMItems>(), Color.WHITE));
            String noSO = etNoSo.getText().toString();
            String soStatus = etSoStatus.getText().toString();
            String soDate = etDate.getText().toString();
            String kontakID = tvContactIDHeader.getText().toString();
            String soSource = etSOSource.getText().toString();
            String deliverySche = etDeliverySchedule.getText().toString();
            String agentName = etAgentName.getText().toString();
            String orderLocation = etOrderLocation.getText().toString();
            boolean deliverByWalkIn = cbWalkIn.isChecked();
            boolean attchCust = cbAttach.isChecked();
            String remarks = etRemarks.getText().toString();
            String guiid = new Helper().GenerateGuid();

            try {
                clsDraft draft = new clsDraftRepo(context).findByBitActive();
                if (draft != null) {
                    draft.setBoolAttachCustomer(attchCust);
                    draft.setTxtNoSO(noSO);
                    draft.setIntStatus(0);
                    if (!soDate.equals("")) {
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//                    Calendar cal = Calendar.getInstance();
//                    String now = dateFormat.format(cal.getTime()).toString();
                        Date dtSoDate = null;
                        try {
                            dtSoDate = dateFormat.parse(soDate);
                            draft.setDtSODate(dtSoDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    draft.setTxtSoSource(soSource);
                    if (!deliverySche.equals("")) {
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                        Date dtDeliverySche = null;
                        try {
                            dtDeliverySche = dateFormat.parse(soDate);
                            draft.setDtDeliverySche(dtDeliverySche);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    draft.setTxtAgentName(agentName);
                    draft.setTxtOrderLocation(orderLocation);
                    draft.setBoolWalkin(deliverByWalkIn);
                    if (!deliverByWalkIn && attchCust) {
                        String contactId = tvContactIDHeader.getText().toString();
                        String memberNo = tvMemberNoHeader.getText().toString();
                        String customername = tvCustomerNameHeader.getText().toString();
                        String province = spnProvinceAddOrder.getSelectedItem().toString();
                        String provinceID = HMProvinceID.get(province);
                        String kabKot = spnKabKotAddOrder.getSelectedItem().toString();
                        String kabKotID = HMKabKotID.get(kabKot);
                        String Kec = spnKecamatanAddOrder.getSelectedItem().toString();
                        String kecID = HMKecID.get(Kec);
                        String kel = spnKelurahanAddOrder.getSelectedItem().toString();
                        String kelID = HMKel.get(kel);
                        String postCodeKel = spnPostCodeAddOrder.getSelectedItem().toString();
                        String postCode = HMKodePos.get(postCodeKel);
                        String phoneNumber = tvPhoneNumb.getText().toString();
                        String address = etAddress.getText().toString();
                        draft.setTxtContactID(contactId);
                        draft.setTxtMemberID(memberNo);
                        draft.setTxtPhoneNumber(phoneNumber);
                        draft.setTxtCustomerName(customername);
                        draft.setTxtProvinceID(provinceID);
                        draft.setTxtProvince(province);
                        draft.setTxtKabKot(kabKot);
                        draft.setTxtKabKotID(kabKotID);
                        draft.setTxtKontakID(kontakID);
                        draft.setTxtKecamatan(Kec);
                        draft.setTxtKecamatanID(kecID);
                        draft.setTxtKelurahan(kel);
                        draft.setTxtKelurahanID(kelID);
                        draft.setTxtPostCode(postCode);
                        draft.setTxtAddress(address);
                    } else {
                        draft.setTxtContactID("");
                        draft.setTxtMemberID("");
                        draft.setTxtCustomerName("");
                        draft.setTxtProvince("");
                        draft.setTxtKabKot("");
                        draft.setTxtKecamatan("");
                        draft.setTxtPostCode("");
                        draft.setTxtAddress("");
                        draft.setTxtKontakID("");
                    }
                    draft.setTxtRemarks(remarks);
                    draft.setIntStatus(0);
                    new clsDraftRepo(context).createOrUpdate(draft);
                } else {
                    draft = new clsDraft();
                    draft.setGuiId(guiid);
                    draft.setBoolAttachCustomer(attchCust);
                    draft.setTxtNoSO(noSO);
                    draft.setIntStatus(0);
                    if (!soDate.equals("")) {
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//                    Calendar cal = Calendar.getInstance();
//                    String now = dateFormat.format(cal.getTime()).toString();
                        Date dtSoDate = null;
                        try {
                            dtSoDate = dateFormat.parse(soDate);
                            draft.setDtSODate(dtSoDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    draft.setTxtSoSource(soSource);
                    if (!deliverySche.equals("")) {
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                        Date dtDeliverySche = null;
                        try {
                            dtDeliverySche = dateFormat.parse(deliverySche);
                            draft.setDtDeliverySche(dtDeliverySche);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    draft.setTxtAgentName(agentName);
                    draft.setTxtOrderLocation(orderLocation);
                    draft.setBoolWalkin(deliverByWalkIn);
                    draft.setBoolAttachCustomer(attchCust);

                    if (attchCust) {
                        String contactId = tvContactIDHeader.getText().toString();
                        String memberNo = tvMemberNoHeader.getText().toString();
                        String customername = tvCustomerNameHeader.getText().toString();
                        String province = spnProvinceAddOrder.getSelectedItem().toString();
                        String provinceID = HMProvinceID.get(province);
                        String kabKot = spnKabKotAddOrder.getSelectedItem().toString();
                        String kabKotID = HMKabKotID.get(kabKot);
                        String Kec = spnKecamatanAddOrder.getSelectedItem().toString();
                        String kecID = HMKecID.get(Kec);
                        String kel = spnKelurahanAddOrder.getSelectedItem().toString();
                        String kelID = HMKel.get(kel);
                        String postCodeKel = spnPostCodeAddOrder.getSelectedItem().toString();
                        String postCode = HMKodePos.get(postCodeKel);
                        String address = etAddress.getText().toString();
                        draft.setTxtContactID(contactId);
                        draft.setTxtMemberID(memberNo);
                        draft.setTxtCustomerName(customername);
                        draft.setTxtProvinceID(provinceID);
                        draft.setTxtProvince(province);
                        draft.setTxtKabKot(kabKot);
                        draft.setTxtKabKotID(kabKotID);
                        draft.setTxtKecamatan(Kec);
                        draft.setTxtKecamatanID(kecID);
                        draft.setTxtKelurahan(kel);
                        draft.setTxtKelurahanID(kelID);
                        draft.setTxtPostCode(postCode);
                        draft.setTxtAddress(address);
                        draft.setIntStatus(0);
                    }
                    draft.setTxtRemarks(remarks);
                    new clsDraftRepo(context).createOrUpdate(draft);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            draftData = new clsDraft();
            draftData.setGuiId(guiid);


            linearLayoutTop.setVisibility(View.GONE);
            linearLayoutBottom.setVisibility(View.VISIBLE);

            try {
                List<clsProductDraft> itemsDraft = new clsProductDraftRepo(context).findAll();
                boolean addedSucces = false;
                contentLibs = new ArrayList<VMItems>();
                for (clsProductDraft productDraft : itemsDraft) {
                    VMItems item = new VMItems(getView());
                    item.setItemName(productDraft.getTxtItemName());
                    item.setItemBrand(productDraft.getTxtItemBrand());
                    item.setItemCode(productDraft.getTxtItemCode());
                    item.setProductCategory(productDraft.getTxtProductCategory());
                    item.setGuiid(productDraft.getTxtProductDraftGUI());
                    item.setPrice(productDraft.getDblPrice());
                    item.setBasePoint(productDraft.getTxtBasedPoint());
                    item.setQty(productDraft.getIntQty());
                    item.setDiscount(productDraft.getDblItemDiscount());
                    item.setTotalPrice(productDraft.getDblTotalPrice());
                    item.setTaxAmount(productDraft.getDblItemTax());
                    item.setNetPrice(productDraft.getDblNetPrice());
                    item.setBonusPoint(productDraft.getTxtBonusPoint());
                    boolean booladded = addItem(item);
                    addedSucces = booladded;
                }

                if (addedSucces) {
                    ToastCustom.showToasty(context, "Draft items restored", 3);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.imageScanner)
    public void onImageScannerClicked() {
        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
    }

    @OnClick(R.id.btnSaveDraft)
    public void onBtnSaveDraftClicked() {
        saveBtnSaved();
    }

    public void saveBtnSaved() {
        boolean saveDraftResult = false;
        try {
            final clsDraft draft = new clsDraftRepo(context).findByBitActive();
            if (draft != null) {
                final String strLinkAPI = new clsHardCode().linkSaveDataSalesOrder;
                final JSONObject resJson = new JSONObject();
                JSONArray jsonProduct = new Helper().writeJSONSaveData(context, draft, contentLibs);
                try {
                    resJson.put("txtNewId", draft.getGuiId());
                    String strNO = draft.getTxtNoSO();
                    if (strNO.equals("Generated by system")) {
                        resJson.put("txtNoSo", "");
                    } else {
                        resJson.put("txtNoSo", draft.getTxtNoSO());
                    }


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());
                    resJson.put("dtDate", currentDateandTime);
                    String txtDeliverSche = "";
                    String deliverBy = "";
                    if (draft.isBoolAttachCustomer()) {
                        deliverBy = "1";
                    } else {
                        deliverBy = "0";
                    }
                    if (draft.getDtDeliverySche() != null) {
                        txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                    }

//                    resJson.put("txtAgentName", draft.getTxtAgentName());
//                    resJson.put("txtPickUpLocationName", "");
                    resJson.put("txtAgentName", draft.getTxtAgentName());
                    resJson.put("txtSourceOrder", data.getTxtSumberDataID());
                    resJson.put("txtSourceOrderName", draft.getTxtSoSource());

                    String walkin = "";
                    if (draft.isBoolWalkin()) {
                        walkin = "1";
                        clsCustomerData dataDefault = new clsCustomerData();
                        try {
                            dataDefault = new clsCustomerDataRepo(context).findOne();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        resJson.put("dtDelivery", txtDeliverSche);
                        resJson.put("intWalkIn", walkin);
                        resJson.put("intDeliveryBy", "0");
                        resJson.put("txtDeliveryBy", dataLogin.getTxtNamaInstitusi());

                        resJson.put("txtPickUpLocation", HMOutletCode.get(draft.getTxtOrderLocation()));
                        resJson.put("txtPickUpLocationName", draft.getTxtOrderLocation());

                       /*
                        resJson.put("txtCustomer", dataDefault.getTxtKontakID());
                        resJson.put("txtCustomerName", dataDefault.getTxtNama());
                        resJson.put("txtKelurahanID", dataDefault.getTxtNamaKelurahan());
                        resJson.put("txtKelurahanName", dataDefault.getTxtNamaKelurahan());
                        resJson.put("txtPropinsiID", dataDefault.getTxtPropinsiID());
                        resJson.put("txtPropinsiName", dataDefault.getTxtNamaPropinsi());
                        resJson.put("txtKabKotaID", dataDefault.getTxtKabKotaID());
                        resJson.put("txtKabupatenKotaName", dataDefault.getTxtNamaKabKota());
                        resJson.put("txtKecamatanID", dataDefault.getTxtKecamatan());
                        resJson.put("txtKecamatanName", dataDefault.getTxtKecamatan());
                        resJson.put("txtKodePos", dataDefault.getTxtKodePos());
                        resJson.put("txtDelivery", dataDefault.getTxtAlamat());*/

                        resJson.put("txtCustomer", null);
                        resJson.put("txtCustomerName", null);
                        resJson.put("txtKelurahanID", null);
                        resJson.put("txtKelurahanName", null);
                        resJson.put("txtPropinsiID", null);
                        resJson.put("txtPropinsiName", null);
                        resJson.put("txtKabKotaID", null);
                        resJson.put("txtKabupatenKotaName", null);
                        resJson.put("txtKecamatanID", null);
                        resJson.put("txtKecamatanName", null);
                        resJson.put("txtKodePos", null);
                        resJson.put("txtDelivery", null);

                        resJson.put("txtRemarks", draft.getTxtRemarks());
                        resJson.put("txtDeviceId", deviceInfo.getModel());
                        resJson.put("txtUser", dataLogin.getNmUser());
                        resJson.put("txtStatusDoc", "0");
                        resJson.put("Detail", jsonProduct);
                    } else {
                        walkin = "0";
                        resJson.put("dtDelivery", txtDeliverSche);
                        resJson.put("intWalkIn", walkin);
                        resJson.put("intDeliveryBy", "1");
                        resJson.put("txtDeliveryBy", dataLogin.getTxtNamaInstitusi());

                        resJson.put("txtPickUpLocation", HMOutletCode.get(draft.getTxtOrderLocation()));
                        resJson.put("txtPickUpLocationName", draft.getTxtOrderLocation());

                        resJson.put("txtKelurahanID", draft.getTxtKelurahanID());
                        resJson.put("txtKelurahanName", draft.getTxtKelurahan());

                        resJson.put("txtCustomer", draft.getTxtContactID());
                        resJson.put("txtCustomerName", draft.getTxtCustomerName());
                        resJson.put("txtPickUpLocation", draft.getTxtProvince());
                        resJson.put("txtPropinsiID", String.valueOf(draft.getTxtProvinceID()));
                        resJson.put("txtPropinsiName", draft.getTxtProvince());
                        resJson.put("txtKabKotaID", String.valueOf(draft.getTxtKabKotID()));
                        resJson.put("txtKabupatenKotaName", draft.getTxtKabKot());
                        resJson.put("txtKecamatanID", String.valueOf(draft.getTxtKecamatanID()));
                        resJson.put("txtKecamatanName", draft.getTxtKecamatan());
                        resJson.put("txtKodePos", draft.getTxtPostCode());
                        resJson.put("txtDelivery", draft.getTxtAddress());
                        resJson.put("txtRemarks", draft.getTxtRemarks());
                        resJson.put("txtDeviceId", deviceInfo.getModel());
                        resJson.put("txtUser", dataLogin.getNmUser());
                        resJson.put("txtStatusDoc", "0");
                        resJson.put("Detail", jsonProduct);
                    }

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
                            try {
                                jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("intResult");
                                String warn = jsonObject.getString("txtMessage");
                                if (result == 1) {
                                    if (!jsonObject.getString("ListData").equals("null")) {
                                        JSONArray jsn = jsonObject.getJSONArray("ListData");
                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
                                            String txtNoSO = object.getString("txtNoSO");
                                            String intStatus = object.getString("intStatus");
                                            String txtStatus = object.getString("txtStatus");
                                            draft.setTxtNoSO(txtNoSO);
                                            draft.setIntStatus(0);
                                            draft.setTxtSOStatus(txtStatus);
                                            int resultan = new clsDraftRepo(context).update(draft);
                                            if (resultan > -1) {
                                                Log.d("Update", "Berhasil");
                                                ToastCustom.showToasty(context, "Save done", 1);
                                            } else {
                                                ToastCustom.showToasty(context, "failed to save", 2);
                                            }
                                        }
                                    }
                                } else {
                                    ToastCustom.showToasty(context, warn, 2);

                                }
                            } catch (JSONException ex) {
                                String x = ex.getMessage();
                            }

                            String a = "";
                        }
                    }
                });
            }
        } catch (SQLException e) {
            saveDraftResult = false;
            e.printStackTrace();
            ToastCustom.showToasty(context, "Failed" + e.getMessage(), 2);
        }
        if (saveDraftResult) {
            ToastCustom.showToasty(context, "Berhasil menyimpan draft", 1);
        }
    }

    ;

    @OnClick(R.id.btnAdd)
    public void onBtnAddClicked() {


    }

    private void spinnerKecamatan(int position) {
        if (categoriesKecamatan.size() > 0) {
            String KecName = categoriesKecamatan.get(position).toString();
            String kecID = HMKecID.get(KecName);
            if (!kecID.equals("0")) {
                final JSONObject resJson = new JSONObject();
                try {
                    resJson.put("txtKecamatanID", KecName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String strLinkSpnKel = new clsHardCode().linkGetListKelurahan;

                final String mRequestBody = resJson.toString();
//                        lt.setText("Retrieving data Kelurahan...");
                //volley untuk ambil kelurahan dan kode pos
                new VolleyUtils().makeJsonObjectRequestWithTokenWithoutProgressD(getActivity(), strLinkSpnKel, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
                    @Override
                    public void onError(String response) {
                        ToastCustom.showToasty(context, response, 2);
//                                lt.hide();
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
//                                        categoriesKelurahan = new ArrayList<String>();
//                                        categoriesKelurahan.add(STRSPINNEROPT);
//                                        HMKel.put(STRSPINNEROPT, "0");

//                                        categoriesPostCode = new ArrayList<String>();
//                                        categoriesPostCode.add(STRSPINNEROPT);
//                                        HMKodePos.put(STRSPINNEROPT, "0");

                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
                                            String txtNamaKelurahan = object.getString("txtNamaKelurahan");
                                            String txtKodePosID = object.getString("txtKodePosID");
                                            categoriesKelurahan.add(txtNamaKelurahan);
                                            HMKel.put(txtNamaKelurahan, txtNamaKelurahan);
                                            categoriesPostCode.add(txtKodePosID + "-" + txtNamaKelurahan);
                                            HMKodePos.put(txtKodePosID + "-" + txtNamaKelurahan, txtKodePosID);
                                        }
                                        if (!boolAttachCustomer) {
                                            SpinnerCustom.setAdapterSpinner(spnKelurahanAddOrder, context, R.layout.custom_spinner, categoriesKelurahan);
                                            SpinnerCustom.setAdapterSpinner(spnPostCodeAddOrder, context, R.layout.custom_spinner, categoriesPostCode);
                                            if (draft != null) {
                                                SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, draft.getTxtKelurahan());
                                                SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, draft.getTxtPostCode() + "-" + draft.getTxtKelurahan());
                                            } else {
                                                SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, STRSPINNEROPT);
                                                SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, STRSPINNEROPT);
                                            }
                                        } else {
                                            SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, kelurahanCustomer);
                                            SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, postCodeCustomer);
                                            boolAttachCustomer = false;
                                        }


//                                                lt.success();
                                    }
                                }
                            } catch (JSONException e) {

                            }

                        }
                    }
                });
            } else {
                SpinnerCustom.selectedItemByText(context, spnKelurahanAddOrder, categoriesKelurahan, STRSPINNEROPT);
                SpinnerCustom.selectedItemByText(context, spnPostCodeAddOrder, categoriesPostCode, STRSPINNEROPT);
            }
        }
    }

    private void reset() {
        etItemName.setText("");
        etItemCode.setText("");
        tvItemPrice.setText("");
        etQty.setText("");

        contentLibs2 = new ArrayList<>();
        lvContentItemSearch.setAdapter(new CardAppAdapter2(context, contentLibs2, Color.WHITE));
        ListViewCustom.setListViewHeightBasedOnItems(getActivity(), lvContentItemSearch);
    }

    @OnClick(R.id.btnBack)
    public void onBtnBackClicked() {
        linearLayoutTop.setVisibility(View.VISIBLE);
        linearLayoutBottom.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnPreview)
    public void onBtnPreviewClicked() {
        boolean valid = true;
        if (contentLibs.size() > 0) {
            valid = true;
        } else {
            valid = false;
        }
        if (valid) {

            strLinkAPICheckout = new clsHardCode().linkCekValidasiDispatchPartner;
            final JSONObject resJson = new JSONObject();
            try {
                draft = new clsDraftRepo(context).findByBitActive();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JSONArray jsonProduct = new Helper().writeJSONSaveData(context, draft, contentLibs);
            boolean boolSavedDispatch = true;
            try {
                String strNO = draft.getTxtNoSO();

                if (strNO.equals("Generated by system")) {
                    boolSavedDispatch = false;
                    resJson.put("txtOrderNo", "");
                } else {
                    resJson.put("txtOrderNo", draft.getTxtNoSO());
                }
                String txtKontakID = dataLogin.getTxtKontakID();
                String txtTeleId = dataLogin.getTxtTeleID();
                resJson.put("txtTeleId", txtTeleId);

                String soDate = etDate.getText().toString();
                String dateSO = "";
                if (!soDate.equals("")) {
                    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                    DateFormat dateFormatStr = new SimpleDateFormat("yyyy-MM-dd");
//                    Calendar cal = Calendar.getInstance();
//                    String now = dateFormat.format(cal.getTime()).toString();
                    Date newDate = null;
                    try {
                        newDate = dateFormat.parse(soDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    newDate = dataLogin.getDtLogin();
                    dateSO = dateFormatStr.format(newDate);
                }
                resJson.put("dtKirim", dateSO);

                if (draft.isBoolWalkin()) {
                    clsCustomerData dataDefault = new clsCustomerData();
                    try {
                        dataDefault = new clsCustomerDataRepo(context).findOne();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    resJson.put("txtNamaPropinsi", "");
                    resJson.put("txtNamaKabKota", "");
                    resJson.put("txtNamaKecamatan", "");
                    resJson.put("txtNamaKelurahan", "");
                    resJson.put("txtRemarkSO", draft.getTxtRemarks());
                    resJson.put("txtPropinsiID", "");
                    resJson.put("txtKabKotaID", "");
                    resJson.put("txtKecamatanID", "");
                    resJson.put("txtKodePos", "");

                    resJson.put("txtKontakID", txtKontakID);
                    resJson.put("txtCustName", "");
                    resJson.put("txtCustPhone", "");
                    resJson.put("txtAlamatKirim", "");
                    /*
                    resJson.put("txtNamaPropinsi", dataDefault.getTxtNamaPropinsi());
                    resJson.put("txtNamaKabKota", dataDefault.getTxtNamaKabKota());
                    resJson.put("txtNamaKecamatan", dataDefault.getTxtKecamatan());
                    resJson.put("txtNamaKelurahan", dataDefault.getTxtNamaKelurahan());
                    resJson.put("txtRemarkSO", draft.getTxtRemarks());
                    resJson.put("txtPropinsiID", dataDefault.getTxtPropinsiID());
                    resJson.put("txtKabKotaID", dataDefault.getTxtKabKotaID());
                    resJson.put("txtKecamatanID", dataDefault.getTxtKecamatan());
                    resJson.put("txtKodePos", dataDefault.getTxtKodePos());

                    resJson.put("txtKontakID", txtKontakID);
                    resJson.put("txtCustName", dataDefault.getTxtNama());
                    resJson.put("txtCustPhone", dataDefault.getTxtPhoneNumber());
                    resJson.put("txtAlamatKirim", dataDefault.getTxtAlamat());*/
                } else {
                    resJson.put("txtNamaPropinsi", draft.getTxtProvince());
                    resJson.put("txtNamaKabKota", draft.getTxtKabKot());
                    resJson.put("txtNamaKecamatan", draft.getTxtKecamatan());
                    resJson.put("txtNamaKelurahan", draft.getTxtKelurahan());
                    resJson.put("txtRemarkSO", draft.getTxtRemarks());
                    resJson.put("txtPropinsiID", draft.getTxtProvinceID());
                    resJson.put("txtKabKotaID", draft.getTxtKabKotID());
                    resJson.put("txtKecamatanID", draft.getTxtKecamatanID());
                    resJson.put("txtKodePos", draft.getTxtPostCode());

                    resJson.put("txtKontakID", txtKontakID);
                    resJson.put("txtCustName", draft.getTxtCustomerName());
                    resJson.put("txtCustPhone", draft.getTxtPhoneNumber());
                    resJson.put("txtAlamatKirim", draft.getTxtAddress());
                }


                resJson.put("intNilaiPembulatan", 0);
                resJson.put("txtPaymentMethodID", "");
                resJson.put("txtMediaJasaID", "");
                resJson.put("txtMediaJasaPaymentID", "");
                resJson.put("txtCardNumber", "");
                resJson.put("txtBCATraceNo", "");
                resJson.put("txtUserID", dataLogin.getIdUser());
                resJson.put("txtTeleID", dataLogin.getTxtTeleID());
                resJson.put("txtCodeID", dataLogin.getTxtCodeId());
                resJson.put("Detail", jsonProduct);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mRequestBodyCheckout = resJson.toString();
            saveDataPreview();
            /*if (boolSavedDispatch) {
//                dispatchPartnerPreview(strLinkAPI,mRequestBody);
                *//*new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPI, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
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
                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
                                            String txtQTY = object.getString("intQTY");
                                            String decAmount = object.getString("decAmount");
                                            String bitAvailable = object.getString("bitAvailable");
                                            String intQtyAvailable = object.getString("intQtyAvailable");
                                            String txtPartnerAddress = object.getString("txtPartnerAddress");
                                            String txtPartnerID = object.getString("txtPartnerID");
                                            String txtPartnerName = object.getString("txtPartnerName");
                                            String txtPartnerPhone = object.getString("txtPartnerPhone");
                                            String txtProductCode = object.getString("txtProductCode");
                                            String txtProductDesc = object.getString("txtProductDesc");
                                            String intPriority = object.getString("intPriority");
                                            String intFlag = object.getString("intFlag");
                                            VMItemsPreview item = new VMItemsPreview();
                                            item.setPartnerAddress(txtPartnerAddress);
                                            item.setPartnerName(txtPartnerName);
                                            item.setProductCode(txtProductCode);
                                            item.setProductName(txtProductDesc);
                                            ;
                                            item.setPartnerPhone(txtPartnerPhone);
                                            item.setQtyAvailable(intQtyAvailable);
                                            item.setQty(txtQTY);
                                            item.setTxtIntFlag(intFlag);

                                            contentLibsPreviewItem.add(item);
                                        }
                                        LayoutInflater layoutInflater = LayoutInflater.from(context);
                                        final View promptView = layoutInflater.inflate(R.layout.popup_preview, null);
                                        //            final View promptViewCustomer = layoutInflater.inflate(R.layout.popup_preview, null);
                                        ListView lvPreview = (ListView) promptView.findViewById(R.id.lvItemPrev);
                                        Button btnCancelPrev = (Button) promptView.findViewById(R.id.btnCancelPrev);
//                                        Button btnCheckoutPrev = (Button) promptView.findViewById(R.id.btnCheckoutPrev);
                                        RecyclerView rvPreview = (RecyclerView) promptView.findViewById(R.id.rv_preview);

                                        TextView tvSOStatusPrev = (TextView) promptView.findViewById(R.id.tvSOStatusPrev);
                                        TextView tvSoDatePrev = (TextView) promptView.findViewById(R.id.tvSoDatePrev);
                                        TextView tvSOSourcePrev = (TextView) promptView.findViewById(R.id.tvSOSourcePrev);
                                        TextView tvAgentNamePrev = (TextView) promptView.findViewById(R.id.tvAgentNamePrev);
                                        TextView tvOrderLocationPrev = (TextView) promptView.findViewById(R.id.tvOrderLocationPrev);
                                        TextView tvDeliveryByPrev = (TextView) promptView.findViewById(R.id.tvDeliveryByPrev);
                                        TextView tvDeliverySchedulePrev = (TextView) promptView.findViewById(R.id.tvDeliverySchedulePrev);
                                        TextView tvRemarksPreview = (TextView) promptView.findViewById(R.id.tvRemarksPreview);
                                        final TextView tvPaymentMethod = (TextView) promptView.findViewById(R.id.tvPaymentMethod);

                                        final TextView etCustomerNamePrev = (TextView) promptView.findViewById(R.id.etCustomerNamePrev);
                                        final TextView etContactIDPrev = (TextView) promptView.findViewById(R.id.etContactIDPrev);
                                        final TextView etMemberNoPrev = (TextView) promptView.findViewById(R.id.etMemberNoPrev);
                                        final TextView tvPostCodeCustomerPrev = (TextView) promptView.findViewById(R.id.tvPostCodeCustomerPrev);
                                        final TextView tvAddressCustomerPrev = (TextView) promptView.findViewById(R.id.tvAddressCustomerPrev);
                                        final TextView tvProvinceCustomerPrev = (TextView) promptView.findViewById(R.id.tvProvinceCustomerPrev);
                                        final TextView tvCityCustomerPrev = (TextView) promptView.findViewById(R.id.tvCityCustomerPrev);
                                        final TextView tvRegionCustomerPrev = (TextView) promptView.findViewById(R.id.tvRegionCustomerPrev);
                                        TextView tvHideCustPrev = (TextView) promptView.findViewById(R.id.tvHideCustPrev);


                                        final LinearLayout lnAttacedCust = (LinearLayout) promptView.findViewById(R.id.lnAttacedCust);
                                        TextView tvCustomerPrev = (TextView) promptView.findViewById(R.id.tvCustomerPrev);


                                        String noSO = etNoSo.getText().toString();
                                        String soStatus = etSoStatus.getText().toString();
                                        String soDate = etDate.getText().toString();
                                        String soSource = etSOSource.getText().toString();
                                        String deliverySche = etDeliverySchedule.getText().toString();
                                        String agentName = etAgentName.getText().toString();
                                        String orderLocation = etOrderLocation.getText().toString();
                                        boolean deliverByWalkIn = cbWalkIn.isChecked();
                                        boolean attchCust = cbAttach.isChecked();
                                        String remarks = etRemarks.getText().toString();

                                        tvSOSourcePrev.setText(soSource);
                                        tvSoDatePrev.setText(soDate);
                                        tvDeliverySchedulePrev.setText(deliverySche);
                                        tvAgentNamePrev.setText(agentName);
                                        tvOrderLocationPrev.setText(orderLocation);

                                        tvPaymentMethod.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                LayoutInflater layoutInflater = LayoutInflater.from(context);
                                                final View promptView = layoutInflater.inflate(R.layout.popup_payment_method, null);
                                                final RadioGroup rg = promptView.findViewById(R.id.radioGroup);
                                                final RadioButton rbCash = (RadioButton) promptView.findViewById(R.id.radio_cash);
                                                final RadioButton rbDebt = (RadioButton) promptView.findViewById(R.id.radio_debit);
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                                alertDialogBuilder.setView(promptView);
                                                alertDialogBuilder.setTitle("Payment Method");
                                                alertDialogBuilder
                                                        .setCancelable(false)
                                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                            }
                                                        }).setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                    }
                                                });
                                                final AlertDialog alertD = alertDialogBuilder.create();
                                                alertD.show();
                                                alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        int selectedId = rg.getCheckedRadioButtonId();
                                                        if (rbCash.isChecked()) {
                                                            tvPaymentMethod.setText("Cash");
                                                            alertD.dismiss();
                                                        } else if (rbDebt.isChecked()) {
                                                            tvPaymentMethod.setText("Debit");
                                                            alertD.dismiss();
                                                        } else {
                                                            ToastCustom.showToasty(context, "Please select payment method", 3);
                                                        }
                                                    }
                                                });
                                            }
                                        });

                                        if (deliverByWalkIn) {
                                            tvDeliveryByPrev.setText("Walk-In");
                                        } else {
                                            tvDeliveryByPrev.setText("Delivery Order");
                                        }

                                        tvRemarksPreview.setText(remarks);

                                        if (attchCust) {

                                            etCustomerNamePrev.setText(tvCustomerNameHeader.getText().toString());
                                            etContactIDPrev.setText(tvContactIDHeader.getText().toString());
                                            etMemberNoPrev.setText(tvMemberNoHeader.getText().toString());
                                            tvProvinceCustomerPrev.setText(spnProvinceAddOrder.getSelectedItem().toString());
                                            tvCityCustomerPrev.setText(spnKabKotAddOrder.getSelectedItem().toString());
                                            tvRegionCustomerPrev.setText(spnKecamatanAddOrder.getSelectedItem().toString());
                                            tvPostCodeCustomerPrev.setText(spnPostCodeAddOrder.getSelectedItem().toString());
                                            tvAddressCustomerPrev.setText(etAddress.getText().toString());
                                            lnAttacedCust.setVisibility(View.VISIBLE);
                                            tvCustomerPrev.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    lnAttacedCust.setVisibility(View.VISIBLE);
                                                    etCustomerNamePrev.setText(tvCustomerNameHeader.getText().toString());
                                                    etContactIDPrev.setText(tvContactIDHeader.getText().toString());
                                                    etMemberNoPrev.setText(tvMemberNoHeader.getText().toString());
                                                    tvProvinceCustomerPrev.setText(spnProvinceAddOrder.getSelectedItem().toString());
                                                    tvCityCustomerPrev.setText(spnKabKotAddOrder.getSelectedItem().toString());
                                                    tvRegionCustomerPrev.setText(spnKecamatanAddOrder.getSelectedItem().toString());
                                                    tvPostCodeCustomerPrev.setText(spnPostCodeAddOrder.getSelectedItem().toString());
                                                    tvAddressCustomerPrev.setText(etAddress.getText().toString());
                                                }
                                            });

                                            tvHideCustPrev.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    lnAttacedCust.setVisibility(View.GONE);
                                                }
                                            });
                                        } else {
                                            tvCustomerPrev.setText("KalCare Outlet");
                                            tvCustomerPrev.setClickable(false);
                                        }
                                        tvCustomerPrev.setClickable(true);
                                        lnAttacedCust.setVisibility(View.GONE);


                                        lvPreview.setAdapter(new CardAppAdapterPreview(context, contentLibsPreviewItem, Color.WHITE));
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                        rvPreview.setLayoutManager(mLayoutManager);
                                        rvPreview.setItemAnimator(new DefaultItemAnimator());
                                        mAdapterItemPreview = new RVPreviewAdapter(context, contentLibsPreviewItem, Color.WHITE);
                                        rvPreview.setAdapter(mAdapterItemPreview);
                                        setListViewHeightBasedOnItems(lvPreview);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                        alertDialogBuilder.setView(promptView);
                                        alertDialogBuilder
                                                .setCancelable(false)
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                }).setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                if (cbWalkIn.isChecked()) {
                                                    dialog.cancel();

                                                } else {
                                                    if (boolCustomerSet) {
                                                        ToastCustom.showToasty(context, "Checking out", 3);
                                                    } else {
                                                        ToastCustom.showToasty(context, "Attach customer first", 4);
                                                    }

                                                }

                                            }
                                        });
                                        final AlertDialog alertD = alertDialogBuilder.create();
                                        alertD.show();
                                        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final String strLinkAPIFinalCheckout = new clsHardCode().linkCheckoutSalesOrder;
                                                final JSONObject resJsonFinalCheckout = new JSONObject();
                                                boolean boolSaved = true;
                                                try {
                                                    draft = new clsDraftRepo(context).findByBitActive();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                if (draft.getTxtNoSO().equals("Generated by system")) {
                                                    boolSaved = false;
                                                }
                                                JSONArray jsonProductCheckout = new Helper().writeJSONSaveData(context, draft, contentLibs);
                                                try {
//                                                resJsonFinalCheckout.put("txtNewId", draft.getGuiId());
                                                    String strNO = draft.getTxtNoSO();
                                                    if (strNO.equals("Generated by system")) {
                                                        resJsonFinalCheckout.put("txtOrderNo", "");
                                                    } else {
                                                        resJsonFinalCheckout.put("txtOrderNo", draft.getTxtNoSO());
                                                    }
                                                    clsCustomerData dataDefault = new clsCustomerData();
                                                    try {
                                                        dataDefault = new clsCustomerDataRepo(context).findOne();
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }

                                                    resJsonFinalCheckout.put("txtSourceOrder", data.getTxtSumberDataID());
//                                                    resJson.put("txtSourceOrder", data.getTxtSumberDataID());
//                                                    resJson.put("txtSourceOrderName", draft.getTxtSoSource());

                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    String currentDateandTime = sdf.format(new Date());
                                                    resJsonFinalCheckout.put("dtDate", currentDateandTime);
                                                    String txtDeliverSche = "";
                                                    if (draft.getDtDeliverySche() != null) {
                                                        txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                                                    }
                                                    resJsonFinalCheckout.put("dtDelivery", txtDeliverSche);
                                                    resJsonFinalCheckout.put("txtAgentName", draft.getTxtAgentName());

                                                    String walkin = "";
                                                    if (draft.isBoolWalkin()) {
                                                        walkin = "1";
                                                        resJsonFinalCheckout.put("txtAlamatKirim", dataDefault.getTxtAlamat());
                                                        resJsonFinalCheckout.put("txtCustPhone", dataLogin.getTxtPhoneNo());
                                                        resJsonFinalCheckout.put("txtCustName", dataDefault.getTxtNama());
                                                        resJsonFinalCheckout.put("txtKontakID", dataDefault.getTxtKontakID());
                                                        resJsonFinalCheckout.put("txtPickUpLocation", dataLogin.getTxtNamaInstitusi());

                                                        SimpleDateFormat sdfFinalCheckout = new SimpleDateFormat("yyyy-MM-dd");
                                                        if (draft.getDtDeliverySche() != null) {
                                                            txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                                                        }

                                                        resJsonFinalCheckout.put("dtKirim", txtDeliverSche);
                                                        resJsonFinalCheckout.put("txtKelurahanID", dataDefault.getTxtNamaKelurahan());
                                                        resJsonFinalCheckout.put("txtNamaKelurahan", dataDefault.getTxtNamaKelurahan());
                                                        resJsonFinalCheckout.put("txtPropinsiID", String.valueOf(dataDefault.getTxtPropinsiID()));
                                                        resJsonFinalCheckout.put("txtNamaPropinsi", dataDefault.getTxtNamaPropinsi());
                                                        resJsonFinalCheckout.put("txtKabKotaID", String.valueOf(dataDefault.getTxtKabKotaID()));
                                                        resJsonFinalCheckout.put("txtNamaKabKota", dataDefault.getTxtNamaKabKota());
                                                        resJsonFinalCheckout.put("txtKecamatanID", String.valueOf(dataDefault.getTxtKecamatan()));
                                                        resJsonFinalCheckout.put("txtNamaKecamatan", dataDefault.getTxtNamaKecamatan());
                                                        resJsonFinalCheckout.put("txtKodePos", dataDefault.getTxtKodePos());
                                                    } else {
                                                        walkin = "0";
                                                        resJsonFinalCheckout.put("txtCustName", draft.getTxtCustomerName());
                                                        resJsonFinalCheckout.put("txtAlamatKirim", draft.getTxtAddress());
                                                        resJsonFinalCheckout.put("txtCustPhone", draft.getTxtPhoneNumber());
                                                        resJsonFinalCheckout.put("txtCustName", draft.getTxtCustomerName());
                                                        resJsonFinalCheckout.put("txtKontakID", draft.getTxtKontakID());
                                                        resJsonFinalCheckout.put("txtPickUpLocation", draft.getTxtProvince());

//                                                    SimpleDateFormat sdfFinalCheckout = new SimpleDateFormat("yyyy-MM-dd");
                                                        if (draft.getDtDeliverySche() != null) {
                                                            txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                                                        }

                                                        resJsonFinalCheckout.put("dtKirim", txtDeliverSche);
                                                        resJsonFinalCheckout.put("txtKelurahanID", draft.getTxtKelurahanID());
                                                        resJsonFinalCheckout.put("txtNamaKelurahan", draft.getTxtKelurahan());
                                                        resJsonFinalCheckout.put("txtPropinsiID", String.valueOf(draft.getTxtProvinceID()));
                                                        resJsonFinalCheckout.put("txtNamaPropinsi", draft.getTxtProvince());
                                                        resJsonFinalCheckout.put("txtKabKotaID", String.valueOf(draft.getTxtKabKotID()));
                                                        resJsonFinalCheckout.put("txtNamaKabKota", draft.getTxtKabKot());
                                                        resJsonFinalCheckout.put("txtKecamatanID", String.valueOf(draft.getTxtKecamatanID()));
                                                        resJsonFinalCheckout.put("txtNamaKecamatan", draft.getTxtKecamatan());
                                                        resJsonFinalCheckout.put("txtKodePos", draft.getTxtPostCode());
                                                    }
                                                    resJsonFinalCheckout.put("intWalkIn", walkin);

                                                    String deliverBy = "";
                                                    if (draft.isBoolAttachCustomer()) {
                                                        deliverBy = "1";
                                                    } else {
                                                        deliverBy = "0";
                                                    }
                                                    resJsonFinalCheckout.put("intNilaiPembulatan", 0);
                                                    resJsonFinalCheckout.put("txtPaymentMethodID", "");
                                                    resJsonFinalCheckout.put("txtMediaJasaID", "");
                                                    resJsonFinalCheckout.put("txtMediaJasaPaymentID", "");
                                                    resJsonFinalCheckout.put("txtCardNumber", "");
                                                    resJsonFinalCheckout.put("txtBCATraceNo", "");
                                                    resJsonFinalCheckout.put("txtUserID", dataLogin.getIdUser());
                                                    resJsonFinalCheckout.put("txtTeleID", dataLogin.getTxtTeleID());
                                                    resJsonFinalCheckout.put("txtUserID", dataLogin.getIdUser());
                                                    resJsonFinalCheckout.put("txtCodeID", dataLogin.getTxtCodeId());
                                                    resJsonFinalCheckout.put("txtCustomer", draft.getTxtCustomerName());

                                                    resJsonFinalCheckout.put("txtRemarkSO", draft.getTxtRemarks());
                                                    resJsonFinalCheckout.put("txtDeviceId", deviceInfo.getModel());
                                                    resJsonFinalCheckout.put("txtUser", dataLogin.getNmUser());
                                                    resJsonFinalCheckout.put("txtStatusDoc", "0");
                                                    resJsonFinalCheckout.put("Detail", jsonProductCheckout);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                final String mRequestBodyFinalCheckout = resJsonFinalCheckout.toString();
                                                    new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPIFinalCheckout, mRequestBodyFinalCheckout, access_token, "Please Wait...", new VolleyResponseListener() {
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
                                                                            for (int n = 0; n < jsn.length(); n++) {

                                                                            }
                                                                        }
                                                                        alertD.dismiss();
                                                                        new clsProductDraftRepo(context).clearAllData();
                                                                        new clsDraftRepo(context).clearAllData();
                                                                        ToastCustom.showToasty(context, "Checkout Completed", 1);
                                                                        FragmentSalesOrder SOFragment = new FragmentSalesOrder();
                                                                        FragmentTransaction fragmentTransactionSO = getActivity().getSupportFragmentManager().beginTransaction();
                                                                        fragmentTransactionSO.replace(R.id.frame, SOFragment, "FragmentSalesOrder");
                                                                        fragmentTransactionSO.commit();


                                                                    } else {
                                                                        ToastCustom.showToasty(context, warn, 2);

                                                                    }
                                                                } catch (JSONException ex) {
                                                                    String x = ex.getMessage();
                                                                }
                                                                String a = "";
                                                            }
                                                        }
                                                    });


                                            }
                                        });

                                        btnCancelPrev.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertD.dismiss();
                                            }
                                        });
                                        *//**//*btnCheckoutPrev.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });*//**//*
                                    }
                                }
                            } catch (Exception ex) {
                                String exa = "sa";
                            }


                        }
                    }
                });*//*
            } else {
                saveDataPreview();
            }*/

        } else {
            ToastCustom.showToasty(context, "List empty !", 4);
        }
    }

    public void saveDataPreview() {
        boolean saveDraftResult = false;
        try {
            final clsDraft draft = new clsDraftRepo(context).findByBitActive();
            if (draft != null) {
                final String strLinkAPIi = new clsHardCode().linkSaveDataSalesOrder;
                final JSONObject resJsoni = new JSONObject();
                JSONArray jsonProducti = new Helper().writeJSONSaveData(context, draft, contentLibs);
                try {
                    resJsoni.put("txtNewId", draft.getGuiId());
                    String strNO = draft.getTxtNoSO();
                    if (strNO.equals("Generated by system")) {
                        resJsoni.put("txtNoSo", "");
                    } else {
                        resJsoni.put("txtNoSo", draft.getTxtNoSO());
                    }
                    String txtTeleId = dataLogin.getTxtTeleID();
                    resJsoni.put("txtTeleId", txtTeleId);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());
                    resJsoni.put("dtDate", currentDateandTime);
                    String txtDeliverSche = "";
                    String deliverBy = "";
                    if (draft.isBoolAttachCustomer()) {
                        deliverBy = "1";
                    } else {
                        deliverBy = "0";
                    }
                    if (draft.getDtDeliverySche() != null) {
                        txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                    }
                    resJsoni.put("txtAgentName", draft.getTxtAgentName());
                    resJsoni.put("txtPickUpLocationName", draft.getTxtOrderLocation());
                    resJsoni.put("txtPickUpLocation", HMOutletCode.get(draft.getTxtOrderLocation()));
                    resJsoni.put("txtAgentName", draft.getTxtAgentName());

//                            resJsoni.put("txtSourceOrder", draft.getTxtSoSource());

                    resJsoni.put("txtSourceOrder", data.getTxtSumberDataID());
                    resJsoni.put("txtSourceOrderName", draft.getTxtSoSource());
                    resJsoni.put("txtAgentName", draft.getTxtAgentName());

                    String walkin = "";
                    if (draft.isBoolWalkin()) {
                        walkin = "1";
                        clsCustomerData dataDefault = new clsCustomerData();
                        try {
                            dataDefault = new clsCustomerDataRepo(context).findOne();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        resJsoni.put("dtDelivery", txtDeliverSche);
                        resJsoni.put("intWalkIn", walkin);
                        resJsoni.put("intDeliveryBy", deliverBy);
                        resJsoni.put("txtDeliveryBy", dataLogin.getTxtNamaInstitusi());


                        /*resJsoni.put("txtCustomer", dataDefault.getTxtKontakID());
                        resJsoni.put("txtCustomerName", dataDefault.getTxtNama());

                        resJsoni.put("txtKelurahanID", dataDefault.getTxtNamaKelurahan());
                        resJsoni.put("txtKelurahanName", dataDefault.getTxtNamaKelurahan());

                        resJsoni.put("txtPropinsiID", dataDefault.getTxtPropinsiID());
                        resJsoni.put("txtPropinsiName", dataDefault.getTxtNamaPropinsi());
                        resJsoni.put("txtKabKotaID", dataDefault.getTxtKabKotaID());
                        resJsoni.put("txtKabupatenKotaName", dataDefault.getTxtNamaKabKota());
                        resJsoni.put("txtKecamatanID", dataDefault.getTxtKecamatan());
                        resJsoni.put("txtKecamatanName", dataDefault.getTxtKecamatan());
                        resJsoni.put("txtKodePos", dataDefault.getTxtKodePos());
                        resJsoni.put("txtDelivery", dataDefault.getTxtAlamat());*/

                        resJsoni.put("txtCustomer", "");
                        resJsoni.put("txtCustomerName", "");

                        resJsoni.put("txtKelurahanID", "");
                        resJsoni.put("txtKelurahanName", "");

                        resJsoni.put("txtPropinsiID", "");
                        resJsoni.put("txtPropinsiName", "");
                        resJsoni.put("txtKabKotaID", "");
                        resJsoni.put("txtKabupatenKotaName", "");
                        resJsoni.put("txtKecamatanID", "");
                        resJsoni.put("txtKecamatanName", "");
                        resJsoni.put("txtKodePos", "");
                        resJsoni.put("txtDelivery", "");


                        resJsoni.put("txtRemarks", draft.getTxtRemarks());
                        resJsoni.put("txtDeviceId", deviceInfo.getModel());
                        resJsoni.put("txtUser", dataLogin.getNmUser());
                        resJsoni.put("txtStatusDoc", "0");
                        resJsoni.put("Detail", jsonProducti);
                    } else {
                        walkin = "0";
                        resJsoni.put("dtDelivery", txtDeliverSche);
                        resJsoni.put("intWalkIn", walkin);
                        resJsoni.put("intDeliveryBy", deliverBy);
                        resJsoni.put("txtDeliveryBy", dataLogin.getTxtNamaInstitusi());

                        resJsoni.put("txtKelurahanID", draft.getTxtKelurahanID());
                        resJsoni.put("txtKelurahanName", draft.getTxtKelurahan());

                        resJsoni.put("txtCustomer", draft.getTxtContactID());
                        resJsoni.put("txtCustomerName", draft.getTxtCustomerName());
                        resJsoni.put("txtPropinsiID", String.valueOf(draft.getTxtProvinceID()));
                        resJsoni.put("txtPropinsiName", draft.getTxtProvince());
                        resJsoni.put("txtKabKotaID", String.valueOf(draft.getTxtKabKotID()));
                        resJsoni.put("txtKabupatenKotaName", draft.getTxtKabKot());
                        resJsoni.put("txtKecamatanID", String.valueOf(draft.getTxtKecamatanID()));
                        resJsoni.put("txtKecamatanName", draft.getTxtKecamatan());
                        resJsoni.put("txtKodePos", draft.getTxtPostCode());
                        resJsoni.put("txtDelivery", draft.getTxtAddress());
                        resJsoni.put("txtRemarks", draft.getTxtRemarks());
                        resJsoni.put("txtDeviceId", deviceInfo.getModel());
                        resJsoni.put("txtUser", dataLogin.getNmUser());
                        resJsoni.put("txtStatusDoc", "0");
                        resJsoni.put("Detail", jsonProducti);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBodyi = resJsoni.toString();
                new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPIi, mRequestBodyi, access_token, "Please Wait...", new VolleyResponseListener() {
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
                                        JSONObject jsnObjStatusSO = jsn.getJSONObject(0).getJSONObject("StatusSO");
                                        JSONObject jsnObjPaymentSO = jsn.getJSONObject(0).getJSONObject("PaymentSO");
                                        JSONObject jsnObjHeaderSO = jsn.getJSONObject(0).getJSONObject("HeaderSO");

                                        //StatusSO
                                        String txtNoSO = jsnObjStatusSO.getString("txtNoSO");
                                        String intStatus = jsnObjStatusSO.getString("intStatus");
                                        String txtStatus = jsnObjStatusSO.getString("txtStatus");

                                        //PaymentSO
                                        dblTaxBasedAmountFinal = jsnObjPaymentSO.getDouble("decTaxBaseAmount");
                                        dbldecAmountFinal = jsnObjPaymentSO.getDouble("decTaxBaseAmount");
                                        dbldecTotalFinal = jsnObjPaymentSO.getDouble("decTotal");
                                        dbldecTotDiscountFinal = jsnObjPaymentSO.getDouble("decTotDiscount");
                                        dbldecTotalPriceFinal = jsnObjPaymentSO.getDouble("decTotalPrice");
                                        dbldecdecRoundedFinal = jsnObjPaymentSO.getDouble("decRounded");
                                        intBitActiveFinal = jsnObjPaymentSO.getInt("bitActive");

                                        //HeaderSO
                                        String txtPropinsiID = jsnObjHeaderSO.getString("txtPropinsiID");
                                        String txtPropinsiName = jsnObjHeaderSO.getString("txtPropinsiName");
                                        String txtKabupatenKotaID = jsnObjHeaderSO.getString("txtKabupatenKotaID");
                                        String txtKabupatenKotaName = jsnObjHeaderSO.getString("txtKabupatenKotaName");
                                        String txtKecamatanID = jsnObjHeaderSO.getString("txtKecamatanID");
                                        String txtKecamatanName = jsnObjHeaderSO.getString("txtKecamatanName");
                                        String txtKelurahanID = jsnObjHeaderSO.getString("txtKelurahanID");
                                        String txtKelurahanName = jsnObjHeaderSO.getString("txtKelurahanName");
                                        String txtKodePos = jsnObjHeaderSO.getString("txtKodePos");
                                        String txtCustomer = jsnObjHeaderSO.getString("txtCustomer");
                                        String txtCustomerName = jsnObjHeaderSO.getString("txtCustomerName");
                                        String txtPickUpLocationName = jsnObjHeaderSO.getString("txtPickUpLocationName");
                                        String txtDelivery = jsnObjHeaderSO.getString("txtDelivery");

                                        txtCustomerFinal = txtCustomer;
                                        txtCustomerNameFinal = txtCustomerName;
                                        txtPickUpLocationNameFinal = txtPickUpLocationName;
                                        txtPropinsiIDFinal = txtPropinsiID;
                                        txtPropinsiNameFinal = txtPropinsiName;
                                        txtKabupatenKotaIDFinal = txtKabupatenKotaID;
                                        txtKabupatenKotaNameFinal = txtKabupatenKotaName;
                                        txtKecamatanIDFinal = txtKecamatanID;
                                        txtKecamatanNameFinal = txtKecamatanName;
                                        txtKelurahanIDFinal = txtKelurahanID;
                                        txtKelurahanNameFinal = txtKelurahanName;
                                        txtKodePosFinal = txtKodePos;


                                        resJsoni.put("txtCustomer", txtCustomer);
                                        resJsoni.put("txtCustomerName", txtCustomerName);

                                        resJsoni.put("txtKelurahanID", txtKelurahanID);
                                        resJsoni.put("txtKelurahanName", txtKelurahanName);

                                        resJsoni.put("txtPropinsiID", txtPropinsiID);
                                        resJsoni.put("txtPropinsiName", txtPropinsiName);
                                        resJsoni.put("txtKabKotaID", txtKabupatenKotaID);
                                        resJsoni.put("txtKabupatenKotaName", txtKabupatenKotaName);
                                        resJsoni.put("txtKecamatanID", txtKecamatanID);
                                        resJsoni.put("txtKecamatanName", txtKecamatanName);
                                        resJsoni.put("txtKodePos", txtKodePos);
                                        resJsoni.put("txtDelivery", txtDelivery);

                                        for (int n = 0; n < jsn.length(); n++) {
                                            JSONObject object = jsn.getJSONObject(n);
//                                            String txtNoSO = object.getString("txtNoSO");
//                                            String intStatus = object.getString("intStatus");
//                                            String txtStatus = object.getString("txtStatus");
                                            draft.setTxtNoSO(txtNoSO);
                                            draft.setIntStatus(0);
                                            draft.setTxtSOStatus(txtStatus);
                                            int resultan = new clsDraftRepo(context).createOrUpdate(draft);
                                            if (resultan > -1) {
                                                Log.d("Update", "Berhasil");
                                                ToastCustom.showToasty(context, "Save done", 1);
//                                                btnPreview.performClick();
                                                resJsoni.put("txtNoSo", txtNoSO);
                                                String mRequestBodyiCheckout = resJsoni.toString();
                                                dispatchPartnerPreview(strLinkAPICheckout, mRequestBodyiCheckout);
                                            } else {
                                                ToastCustom.showToasty(context, "failed to save", 2);
                                            }
                                        }
                                    }
                                } else {
                                    ToastCustom.showToasty(context, warn, 2);

                                }
                            } catch (JSONException ex) {
                                String x = ex.getMessage();
                            }

                            String a = "";
                        }
                    }
                });
                /*new clsProductDraftRepo(context).clearAllData();
                for (VMItems item : contentLibs) {
                    String guiid = new Helper().GenerateGuid();
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
                    new clsProductDraftRepo(context).createOrUpdate(product);
                    saveDraftResult = true;
                }*/
            }
        } catch (SQLException e) {
            saveDraftResult = false;
            e.printStackTrace();
            ToastCustom.showToasty(context, "Failed" + e.getMessage(), 2);
        }
        if (saveDraftResult) {
            ToastCustom.showToasty(context, "Berhasil menyimpan draft", 1);

        }
    }

    public void dispatchPartnerPreview(String strLinkAPI, String mRequestBody) {
        new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPI, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
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
                                contentLibsPreviewItem = new ArrayList<>();
                                for (int n = 0; n < jsn.length(); n++) {
                                    JSONObject object = jsn.getJSONObject(n);
                                    String txtQTY = object.getString("intQTY");
                                    String decAmount = object.getString("decAmount");
                                    String bitAvailable = object.getString("bitAvailable");
                                    String intQtyAvailable = object.getString("intQtyAvailable");
                                    String txtPartnerAddress = object.getString("txtPartnerAddress");
                                    String txtPartnerID = object.getString("txtPartnerID");
                                    String txtPartnerName = object.getString("txtPartnerName");
                                    String txtPartnerPhone = object.getString("txtPartnerPhone");
                                    String txtProductCode = object.getString("txtProductCode");
                                    String txtProductDesc = object.getString("txtProductDesc");
                                    String intPriority = object.getString("intPriority");
                                    String intFlag = object.getString("intFlag");
                                    VMItemsPreview item = new VMItemsPreview();
                                    item.setPartnerAddress(txtPartnerAddress);
                                    item.setPartnerName(txtPartnerName);
                                    item.setProductCode(txtProductCode);
                                    item.setProductName(txtProductDesc);
                                    ;
                                    item.setPartnerPhone(txtPartnerPhone);
                                    item.setQtyAvailable(intQtyAvailable);
                                    item.setQty(txtQTY);
                                    item.setTxtIntFlag(intFlag);

                                    contentLibsPreviewItem.add(item);
                                }
                                LayoutInflater layoutInflater = LayoutInflater.from(context);
                                final View promptView = layoutInflater.inflate(R.layout.popup_preview, null);
                                //            final View promptViewCustomer = layoutInflater.inflate(R.layout.popup_preview, null);
                                ListView lvPreview = (ListView) promptView.findViewById(R.id.lvItemPrev);
                                Button btnCancelPrev = (Button) promptView.findViewById(R.id.btnCancelPrev);
//                                        Button btnCheckoutPrev = (Button) promptView.findViewById(R.id.btnCheckoutPrev);
                                RecyclerView rvPreview = (RecyclerView) promptView.findViewById(R.id.rv_preview);

                                TextView tvSOStatusPrev = (TextView) promptView.findViewById(R.id.tvSOStatusPrev);
                                TextView tvSoDatePrev = (TextView) promptView.findViewById(R.id.tvSoDatePrev);
                                TextView tvSOSourcePrev = (TextView) promptView.findViewById(R.id.tvSOSourcePrev);
                                TextView tvAgentNamePrev = (TextView) promptView.findViewById(R.id.tvAgentNamePrev);
                                TextView tvOrderLocationPrev = (TextView) promptView.findViewById(R.id.tvOrderLocationPrev);
                                TextView tvDeliveryByPrev = (TextView) promptView.findViewById(R.id.tvDeliveryByPrev);
                                TextView tvDeliverySchedulePrev = (TextView) promptView.findViewById(R.id.tvDeliverySchedulePrev);
                                TextView tvRemarksPreview = (TextView) promptView.findViewById(R.id.tvRemarksPreview);
                                final TextView tvPaymentMethod = (TextView) promptView.findViewById(R.id.tvPaymentMethod);

                                final TextView etCustomerNamePrev = (TextView) promptView.findViewById(R.id.etCustomerNamePrev);
                                final TextView etContactIDPrev = (TextView) promptView.findViewById(R.id.etContactIDPrev);
                                final TextView etMemberNoPrev = (TextView) promptView.findViewById(R.id.etMemberNoPrev);
                                final TextView tvPostCodeCustomerPrev = (TextView) promptView.findViewById(R.id.tvPostCodeCustomerPrev);
                                final TextView tvAddressCustomerPrev = (TextView) promptView.findViewById(R.id.tvAddressCustomerPrev);
                                final TextView tvProvinceCustomerPrev = (TextView) promptView.findViewById(R.id.tvProvinceCustomerPrev);
                                final TextView tvCityCustomerPrev = (TextView) promptView.findViewById(R.id.tvCityCustomerPrev);
                                final TextView tvRegionCustomerPrev = (TextView) promptView.findViewById(R.id.tvRegionCustomerPrev);
                                TextView tvHideCustPrev = (TextView) promptView.findViewById(R.id.tvHideCustPrev);


                                final LinearLayout lnAttacedCust = (LinearLayout) promptView.findViewById(R.id.lnAttacedCust);

                                final LinearLayout linearLayoutPaymentDetail = (LinearLayout) promptView.findViewById(R.id.linearLayoutPaymentDetail);
                                final EditText etSourceMediaPayment = (EditText) promptView.findViewById(R.id.etSourceMediaPayment);
                                etCardNumber = (EditText) promptView.findViewById(R.id.etCardNumber);
                                etBcaTraceNumber = (EditText) promptView.findViewById(R.id.etBcaTraceNumber);
                                etPayment = (EditText) promptView.findViewById(R.id.etPayment);

                                //pelem

                                TextView tvCustomerPrev = (TextView) promptView.findViewById(R.id.tvCustomerPrev);


                                String noSO = etNoSo.getText().toString();
                                String soStatus = etSoStatus.getText().toString();
                                String soDate = etDate.getText().toString();
                                String soSource = etSOSource.getText().toString();
                                String deliverySche = etDeliverySchedule.getText().toString();
                                String agentName = etAgentName.getText().toString();
                                String orderLocation = etOrderLocation.getText().toString();
                                boolean deliverByWalkIn = cbWalkIn.isChecked();
                                boolean attchCust = cbAttach.isChecked();
                                String remarks = etRemarks.getText().toString();

                                tvSOSourcePrev.setText(soSource);
                                tvSoDatePrev.setText(soDate);
                                tvDeliverySchedulePrev.setText(deliverySche);
                                tvAgentNamePrev.setText(agentName);
                                tvOrderLocationPrev.setText(orderLocation);

                                tvPaymentMethod.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        LayoutInflater layoutInflater = LayoutInflater.from(context);
                                        View promptView = layoutInflater.inflate(R.layout.popup_payment_method, null);
                                        final RadioGroup rg = promptView.findViewById(R.id.radioGroup);
                                        final RadioButton rbCash = (RadioButton) promptView.findViewById(R.id.radio_cash);
                                        final RadioButton rbDebt = (RadioButton) promptView.findViewById(R.id.radio_debit);
                                        final Spinner spnPaymentMethod = (Spinner) promptView.findViewById(R.id.spnPaymentMethod);
                                        final Spinner spnPaymentMethodAndTipe = (Spinner) promptView.findViewById(R.id.spnSourceMediaPayment);
                                        final Spinner spnSourceMediaPaymentList = (Spinner) promptView.findViewById(R.id.spnPayment);

                                        final EditText etCardNumber2 = (EditText) promptView.findViewById(R.id.etCardNumber2);
                                        final EditText etBcaTraceNumber2 = (EditText) promptView.findViewById(R.id.etBcaTraceNumber2);

                                        final String txtPaymentMth1 = tvPaymentMethod.getText().toString();
                                        final String txtSourceMediaPayment1 = etSourceMediaPayment.getText().toString();
                                        final String txtCardNumber1 = etCardNumber.getText().toString();
                                        final String txtBcaTraceNumber1 = etBcaTraceNumber.getText().toString();
                                        final String txtPayment1 = etPayment.getText().toString();

                                        if (!txtPaymentMth1.equals("*Add payment method")){
                                            boolPaymentFilled = true;
                                        }else{
                                            boolPaymentFilled = false;
                                        }

                                        if (boolPaymentFilled){
                                            etCardNumber2.setText(txtCardNumber1);
                                            etBcaTraceNumber2.setText(txtBcaTraceNumber1);
                                            etBcaTraceNumber2.setEnabled(true);
                                            etBcaTraceNumber2.setFocusable(true);
                                            etBcaTraceNumber2.setFocusableInTouchMode(true);
                                            etBcaTraceNumber2.setClickable(true);
                                            etCardNumber2.setEnabled(true);
                                            etCardNumber2.setFocusable(true);
                                            etCardNumber2.setFocusableInTouchMode(true);
                                            etCardNumber2.setClickable(true);
                                        }
//                                        List<clsListPaymentMethod> mediajasas = new ArrayList<>();
//                                        clsListMediaJasa mediaJasaDefault = new clsListMediaJasa();
                                        /*mediaJasaDefault.setTypeid("000");
                                        mediaJasaDefault.setDescType("Choose one");
                                        mediajasas.add(mediaJasaDefault);*/
                                        List<clsListPaymentMethod> paymentMethods = new ArrayList<>();
                                        try {
                                            paymentMethods = new clsListPaymentMethodRepo(context).findAll();
                                            paymentMethodList = new ArrayList<>();
                                            paymentMethodList.add(SPNCHOOSEONE);
                                            HMPaymentmethodList.put(SPNCHOOSEONE, "999");
                                            for (clsListPaymentMethod i : paymentMethods) {
                                                paymentMethodList.add(i.getNama());
                                                HMPaymentmethodList.put(i.getNama(), i.getKode());
                                            }
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        if (paymentMethods.size() > 0) {
                                            SpinnerCustom.setAdapterSpinner(spnPaymentMethod, context, R.layout.custom_spinner, paymentMethodList);
                                            if (boolPaymentFilled){
                                                SpinnerCustom.selectedItemByText(context, spnPaymentMethod, paymentMethodList, txtPaymentMth1);
                                            }
//                                            SpinnerCustom.selectedItemByText(context, spnProvinceAddOrder, categoriesProv, txtPropinsiName);
                                        }
                                        etCardNumber2.setFocusable(false);
                                        etCardNumber2.setEnabled(false);
                                        etBcaTraceNumber2.setFocusable(false);
                                        etBcaTraceNumber2.setEnabled(false);

                                        sourceMediaPaymentList.add(SPNCHOOSEONE);
                                        paymentMethodAndTipe.add(SPNCHOOSEONE);
                                        //boole
                                        spnPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                String txtPaymentMethod = paymentMethodList.get(position);
                                                String idPaymentMethod = HMPaymentmethodList.get(txtPaymentMethod);
                                                if (!idPaymentMethod.equals("999")) {
                                                    try {

                                                        List<clsListPaymentMethodAndTipe> listPaymentMethodAndTipeSelected = new clsListPaymentMethodAndTipeRepo(context).findById2(idPaymentMethod);
                                                        paymentMethodAndTipe = new ArrayList<>();
                                                        paymentMethodAndTipe.add(SPNCHOOSEONE);
                                                        HMpaymentMethodAndTipe.put(SPNCHOOSEONE, "999");
                                                        for (clsListPaymentMethodAndTipe i : listPaymentMethodAndTipeSelected) {
                                                            paymentMethodAndTipe.add(i.getDesc_type());
                                                            HMpaymentMethodAndTipe.put(i.getDesc_type(), i.getTypeid());
                                                            HMCardNumber.put(i.getDesc_type(),i.getIntFillCardNumber());
                                                            HMTraceNumber.put(i.getDesc_type(),i.getIntFillTraceNumber());
                                                        }
                                                        if (paymentMethodAndTipe.size() > 0) {
                                                            SpinnerCustom.setAdapterSpinner(spnPaymentMethodAndTipe, context, R.layout.custom_spinner, paymentMethodAndTipe);
                                                            if (boolPaymentFilled){
                                                                SpinnerCustom.selectedItemByText(context, spnPaymentMethodAndTipe, paymentMethodAndTipe, txtSourceMediaPayment1);
                                                            }else{
                                                                SpinnerCustom.selectedItemByText(context, spnPaymentMethodAndTipe, paymentMethodAndTipe, SPNCHOOSEONE);
                                                            }
                                                        }

                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                        spnPaymentMethodAndTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                String txtPaymentMethodAndTipe = paymentMethodAndTipe.get(position);
                                                String idTxtSourceMedia = HMpaymentMethodAndTipe.get(txtPaymentMethodAndTipe);

                                                if (!idTxtSourceMedia.equals("999")) {
                                                    int cardNumber = HMCardNumber.get(txtPaymentMethodAndTipe);
                                                    int traceNumber = HMTraceNumber.get(txtPaymentMethodAndTipe);
                                                    if(cardNumber==1){
                                                        etCardNumber2.setEnabled(true);
                                                        etCardNumber2.setFocusable(true);
                                                        etCardNumber2.setFocusableInTouchMode(true);
                                                        etCardNumber2.setClickable(true);
                                                    }else{
                                                        etCardNumber2.setEnabled(false);
                                                        etCardNumber2.setFocusable(false);
                                                        etCardNumber2.setFocusableInTouchMode(false);
                                                        etCardNumber2.setClickable(false);
                                                        etCardNumber2.setText("");
                                                    }
                                                    if (traceNumber==1){
                                                        etBcaTraceNumber2.setEnabled(true);
                                                        etBcaTraceNumber2.setFocusable(true);
                                                        etBcaTraceNumber2.setFocusableInTouchMode(true);
                                                        etBcaTraceNumber2.setClickable(true);
                                                    }else{
                                                        etBcaTraceNumber2.setEnabled(false);
                                                        etBcaTraceNumber2.setFocusable(false);
                                                        etBcaTraceNumber2.setFocusableInTouchMode(false);
                                                        etBcaTraceNumber2.setClickable(false);
                                                        etBcaTraceNumber2.setText("");
                                                    }

                                                    List<clsListMediaJasa> listMediaJasasSelected = new ArrayList<>();
                                                    if (!idTxtSourceMedia.equals("999")) {
                                                        try {
                                                            listMediaJasasSelected = new clsListMediaJasaRepo(context).findById2(idTxtSourceMedia);
                                                            List<clsListMediaJasa> mediaJasas = new ArrayList<>();
                                                            mediaJasas = new clsListMediaJasaRepo(context).findAll();
                                                            sourceMediaPaymentList = new ArrayList<>();
                                                            sourceMediaPaymentList.add(SPNCHOOSEONE);
                                                            HMsourceMediaPaymentList.put(SPNCHOOSEONE, "999");
                                                            for (clsListMediaJasa i : listMediaJasasSelected) {
                                                                sourceMediaPaymentList.add(i.getTxtmediajasapayment());
                                                                HMsourceMediaPaymentList.put(i.getTxtmediajasapayment(), i.getTxtmediajasapaymentid());
                                                            }
                                                        } catch (SQLException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (listMediaJasasSelected.size() > 0) {
                                                            SpinnerCustom.setAdapterSpinner(spnSourceMediaPaymentList, context, R.layout.custom_spinner, sourceMediaPaymentList);
                                                            if (boolPaymentFilled){
                                                                SpinnerCustom.selectedItemByText(context, spnSourceMediaPaymentList, sourceMediaPaymentList, txtPayment1);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                        spnSourceMediaPaymentList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });


                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                        alertDialogBuilder.setView(promptView);
                                        alertDialogBuilder.setTitle("Payment Method");
                                        alertDialogBuilder
                                                .setCancelable(false)
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                }).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                        final AlertDialog alertD = alertDialogBuilder.create();
                                        alertD.show();
                                        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String paymentMethod = spnPaymentMethod.getSelectedItem().toString();
                                                String idPaymentMethod = HMPaymentmethodList.get(paymentMethod);
                                                boolean boolPaymentValid = true;
                                                if (!idPaymentMethod.equals("999")) {
                                                    txtPaymentMethodID = idPaymentMethod;
                                                    tvPaymentMethod.setText(paymentMethod);
                                                    boolPaymentValid = true;
                                                } else {
                                                    boolPaymentValid = false;
                                                }
                                                if (spnPaymentMethodAndTipe.getSelectedItem() != null && boolPaymentValid) {
                                                    String sourceMediaPayment = spnPaymentMethodAndTipe.getSelectedItem().toString();
                                                    String idSourceMediaPayment = HMpaymentMethodAndTipe.get(sourceMediaPayment);
                                                    if (!idSourceMediaPayment.equals("999")) {
                                                        txtMediaJasaID = idSourceMediaPayment;
                                                        txtMediaJasaName = sourceMediaPayment;
                                                        txtmediajasapaymentFinal = txtMediaJasaName;
                                                        etSourceMediaPayment.setText(sourceMediaPayment);


                                                        int cardNumb = HMCardNumber.get(sourceMediaPayment);
                                                        int traceBca = HMTraceNumber.get(sourceMediaPayment);
                                                        if (cardNumb == 1){
                                                            String cn = etCardNumber2.getText().toString();
                                                            etCardNumber.setText(cn);
                                                            txtCardNumber = cn;
                                                        }else{
                                                            etCardNumber.setText("0");
                                                            txtCardNumber = "0";
                                                        }
                                                        if (traceBca == 1){
                                                            String bcaTN = etBcaTraceNumber2.getText().toString();
                                                            etBcaTraceNumber.setText(bcaTN);
                                                            txtBCATraceNo = bcaTN;
                                                        }else{
                                                            etBcaTraceNumber.setText("0");
                                                            txtBCATraceNo = "0";
                                                        }

                                                        boolPaymentValid = true;
                                                    } else {
                                                        boolPaymentValid = false;
                                                    }

                                                } else {
                                                    boolPaymentValid = false;
                                                }
                                                if (spnSourceMediaPaymentList.getSelectedItem()!=null && boolPaymentValid){
                                                    String payment= spnSourceMediaPaymentList.getSelectedItem().toString();
                                                    String paymentId = HMsourceMediaPaymentList.get(payment);
                                                    if (!paymentId.equals("999")) {
                                                        txtMediaJasaPaymentID = paymentId;
                                                        etPayment.setText(payment);

                                                    }else{
                                                        boolPaymentValid = false;
                                                    }

                                                }
                                                if (boolPaymentValid) {
                                                    alertD.dismiss();
                                                    linearLayoutPaymentDetail.setVisibility(View.VISIBLE);
                                                } else {
                                                    ToastCustom.showToasty(context, "Invalid data payment", 3);
                                                }
                                                /* int selectedId = rg.getCheckedRadioButtonId();
                                                if (rbCash.isChecked()) {
                                                    tvPaymentMethod.setText("Cash");
                                                    alertD.dismiss();
                                                } else if (rbDebt.isChecked()) {
                                                    tvPaymentMethod.setText("Debit");
                                                    alertD.dismiss();
                                                } else {
                                                    ToastCustom.showToasty(context, "Please select payment method", 3);
                                                }*/
                                            }
                                        });
                                    }
                                });

                                if (deliverByWalkIn) {
                                    tvDeliveryByPrev.setText("Walk-In");
                                } else {
                                    tvDeliveryByPrev.setText("Delivery Order");
                                }

                                tvRemarksPreview.setText(remarks);

                                if (attchCust) {

                                    etCustomerNamePrev.setText(tvCustomerNameHeader.getText().toString());
                                    etContactIDPrev.setText(tvContactIDHeader.getText().toString());
                                    etMemberNoPrev.setText(tvMemberNoHeader.getText().toString());
                                    tvProvinceCustomerPrev.setText(spnProvinceAddOrder.getSelectedItem().toString());
                                    tvCityCustomerPrev.setText(spnKabKotAddOrder.getSelectedItem().toString());
                                    tvRegionCustomerPrev.setText(spnKecamatanAddOrder.getSelectedItem().toString());
                                    tvPostCodeCustomerPrev.setText(spnPostCodeAddOrder.getSelectedItem().toString());
                                    tvAddressCustomerPrev.setText(etAddress.getText().toString());
                                    lnAttacedCust.setVisibility(View.VISIBLE);
                                    tvCustomerPrev.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            lnAttacedCust.setVisibility(View.VISIBLE);
                                            etCustomerNamePrev.setText(tvCustomerNameHeader.getText().toString());
                                            etContactIDPrev.setText(tvContactIDHeader.getText().toString());
                                            etMemberNoPrev.setText(tvMemberNoHeader.getText().toString());
                                            tvProvinceCustomerPrev.setText(spnProvinceAddOrder.getSelectedItem().toString());
                                            tvCityCustomerPrev.setText(spnKabKotAddOrder.getSelectedItem().toString());
                                            tvRegionCustomerPrev.setText(spnKecamatanAddOrder.getSelectedItem().toString());
                                            tvPostCodeCustomerPrev.setText(spnPostCodeAddOrder.getSelectedItem().toString());
                                            tvAddressCustomerPrev.setText(etAddress.getText().toString());
                                        }
                                    });

                                    tvHideCustPrev.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            lnAttacedCust.setVisibility(View.GONE);
                                        }
                                    });
                                } else {
                                    tvCustomerPrev.setText("KalCare Outlet");
                                    tvCustomerPrev.setClickable(false);
                                }
                                tvCustomerPrev.setClickable(true);
                                lnAttacedCust.setVisibility(View.GONE);


                                lvPreview.setAdapter(new CardAppAdapterPreview(context, contentLibsPreviewItem, Color.WHITE));
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                rvPreview.setLayoutManager(mLayoutManager);
                                rvPreview.setItemAnimator(new DefaultItemAnimator());
                                mAdapterItemPreview = new RVPreviewAdapter(context, contentLibsPreviewItem, Color.WHITE);
                                rvPreview.setAdapter(mAdapterItemPreview);
                                setListViewHeightBasedOnItems(lvPreview);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setView(promptView);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        }).setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        if (cbWalkIn.isChecked()) {
                                            dialog.cancel();

                                        } else {
                                            if (boolCustomerSet) {
                                                ToastCustom.showToasty(context, "Checking out", 3);
                                            } else {
                                                ToastCustom.showToasty(context, "Attach customer first", 4);
                                            }

                                        }

                                    }
                                });
                                final AlertDialog alertD = alertDialogBuilder.create();
                                alertD.show();
                                alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String strLinkAPIFinalCheckout = new clsHardCode().linkCheckoutSalesOrder;
                                        final JSONObject resJsonFinalCheckout = new JSONObject();
                                        boolean boolSaved = true;
                                        try {
                                            draft = new clsDraftRepo(context).findByBitActive();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        if (draft.getTxtNoSO().equals("Generated by system")) {
                                            boolSaved = false;
                                        }
                                        JSONArray jsonProductCheckout = new Helper().writeJSONSaveData(context, draft, contentLibs);
                                        try {
//                                                resJsonFinalCheckout.put("txtNewId", draft.getGuiId());
                                            String strNO = draft.getTxtNoSO();
                                            if (strNO.equals("Generated by system")) {
                                                resJsonFinalCheckout.put("txtOrderNo", "");
                                            } else {
                                                resJsonFinalCheckout.put("txtOrderNo", draft.getTxtNoSO());
                                            }
                                            clsCustomerData dataDefault = new clsCustomerData();
                                            try {
                                                dataDefault = new clsCustomerDataRepo(context).findOne();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            resJsonFinalCheckout.put("txtLogisticID","");
                                            resJsonFinalCheckout.put("txtLogType","");
                                            resJsonFinalCheckout.put("intNilaiPembulatan",dbldecdecRoundedFinal);
                                            resJsonFinalCheckout.put("txtPaymentMethodID",txtPaymentMethodID);
                                            resJsonFinalCheckout.put("txtMediaJasaID",txtMediaJasaID);
                                            resJsonFinalCheckout.put("txtMediaJasaPaymentID",txtMediaJasaPaymentID);
                                            resJsonFinalCheckout.put("txtCardNumber",txtCardNumber);
                                            resJsonFinalCheckout.put("txtBCATraceNo",txtBCATraceNo);
                                            resJsonFinalCheckout.put("AdminSetPaidFromBankName",txtMediaJasaName);
                                            resJsonFinalCheckout.put("AdminSetPaidToBankName",txtMediaJasaName);
                                            resJsonFinalCheckout.put("AdminSetPaidFromName",txtCustomerFinal);
                                            double dblAdminSetPaidValue = dbldecTotalPriceFinal - dbldecdecRoundedFinal;
                                            resJsonFinalCheckout.put("AdminSetPaidValue",dblAdminSetPaidValue);
                                            resJsonFinalCheckout.put("AdminSetReferenceTransferNo",txtBCATraceNo);
                                            resJsonFinalCheckout.put("PaidDescription",tvPaymentMethod.getText().toString());
                                            resJsonFinalCheckout.put("decAmount",dbldecAmountFinal);
                                            resJsonFinalCheckout.put("decTaxBaseAmount",dblTaxBasedAmountFinal);
                                            resJsonFinalCheckout.put("decTotal",dbldecTotalFinal);
                                            resJsonFinalCheckout.put("decTotDiscount",dbldecTotDiscountFinal);
                                            resJsonFinalCheckout.put("decTotalPrice",dbldecTotalPriceFinal);
                                            resJsonFinalCheckout.put("txtmediajasapayment",txtmediajasapaymentFinal);

                                            /*resJsonFinalCheckout.put("intNilaiPembulatan", 0);
                                            resJsonFinalCheckout.put("txtPaymentMethodID", "");
                                            resJsonFinalCheckout.put("txtMediaJasaID", "");
                                            resJsonFinalCheckout.put("txtMediaJasaPaymentID", "");
                                            resJsonFinalCheckout.put("txtCardNumber", "");
                                            resJsonFinalCheckout.put("txtBCATraceNo", "");*/


                                            resJsonFinalCheckout.put("txtSourceOrder", data.getTxtSumberDataID());
//                                                    resJson.put("txtSourceOrder", data.getTxtSumberDataID());
//                                                    resJson.put("txtSourceOrderName", draft.getTxtSoSource());

                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            String currentDateandTime = sdf.format(new Date());
                                            resJsonFinalCheckout.put("dtDate", currentDateandTime);
                                            String txtDeliverSche = "";
                                            if (draft.getDtDeliverySche() != null) {
                                                txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                                            }
                                            resJsonFinalCheckout.put("dtDelivery", txtDeliverSche);
                                            resJsonFinalCheckout.put("txtAgentName", draft.getTxtAgentName());

                                            String walkin = "";
                                            if (draft.isBoolWalkin()) {
                                                walkin = "1";
                                                resJsonFinalCheckout.put("txtAlamatKirim", txtKelurahanNameFinal);
                                                resJsonFinalCheckout.put("txtCustPhone", dataLogin.getTxtPhoneNo());
                                                resJsonFinalCheckout.put("txtCustName", txtCustomerNameFinal);
                                                resJsonFinalCheckout.put("txtKontakID", txtCustomerFinal);
                                                resJsonFinalCheckout.put("txtPickUpLocation", dataLogin.getTxtNamaInstitusi());

                                                SimpleDateFormat sdfFinalCheckout = new SimpleDateFormat("yyyy-MM-dd");
                                                if (draft.getDtDeliverySche() != null) {
                                                    txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                                                }

                                                resJsonFinalCheckout.put("dtKirim", txtDeliverSche);
                                                resJsonFinalCheckout.put("txtKelurahanID", txtKelurahanIDFinal);
                                                resJsonFinalCheckout.put("txtNamaKelurahan", txtKelurahanNameFinal);
                                                resJsonFinalCheckout.put("txtPropinsiID", txtPropinsiIDFinal);
                                                resJsonFinalCheckout.put("txtNamaPropinsi", txtPropinsiNameFinal);
                                                resJsonFinalCheckout.put("txtKabKotaID", txtKabupatenKotaIDFinal);
                                                resJsonFinalCheckout.put("txtNamaKabKota", txtKabupatenKotaNameFinal );
                                                resJsonFinalCheckout.put("txtKecamatanID", txtKecamatanIDFinal);
                                                resJsonFinalCheckout.put("txtNamaKecamatan", txtKecamatanNameFinal );
                                                resJsonFinalCheckout.put("txtKodePos", txtKodePosFinal);
                                            } else {
                                                walkin = "0";
                                                resJsonFinalCheckout.put("txtCustName", draft.getTxtCustomerName());
                                                resJsonFinalCheckout.put("txtAlamatKirim", draft.getTxtAddress());
                                                resJsonFinalCheckout.put("txtCustPhone", draft.getTxtPhoneNumber());
                                                resJsonFinalCheckout.put("txtCustName", draft.getTxtCustomerName());
                                                resJsonFinalCheckout.put("txtKontakID", draft.getTxtKontakID());
                                                resJsonFinalCheckout.put("txtPickUpLocation", draft.getTxtProvince());

//                                                    SimpleDateFormat sdfFinalCheckout = new SimpleDateFormat("yyyy-MM-dd");
                                                if (draft.getDtDeliverySche() != null) {
                                                    txtDeliverSche = sdf.format(draft.getDtDeliverySche());
                                                }

                                                resJsonFinalCheckout.put("dtKirim", txtDeliverSche);
                                                resJsonFinalCheckout.put("txtKelurahanID", draft.getTxtKelurahanID());
                                                resJsonFinalCheckout.put("txtNamaKelurahan", draft.getTxtKelurahan());
                                                resJsonFinalCheckout.put("txtPropinsiID", String.valueOf(draft.getTxtProvinceID()));
                                                resJsonFinalCheckout.put("txtNamaPropinsi", draft.getTxtProvince());
                                                resJsonFinalCheckout.put("txtKabKotaID", String.valueOf(draft.getTxtKabKotID()));
                                                resJsonFinalCheckout.put("txtNamaKabKota", draft.getTxtKabKot());
                                                resJsonFinalCheckout.put("txtKecamatanID", String.valueOf(draft.getTxtKecamatanID()));
                                                resJsonFinalCheckout.put("txtNamaKecamatan", draft.getTxtKecamatan());
                                                resJsonFinalCheckout.put("txtKodePos", draft.getTxtPostCode());
                                            }
                                            resJsonFinalCheckout.put("intWalkIn", walkin);

                                            String deliverBy = "";
                                            if (draft.isBoolAttachCustomer()) {
                                                deliverBy = "1";
                                            } else {
                                                deliverBy = "0";
                                            }

                                            resJsonFinalCheckout.put("txtUserID", dataLogin.getIdUser());
                                            resJsonFinalCheckout.put("txtTeleID", dataLogin.getTxtTeleID());
                                            resJsonFinalCheckout.put("txtUserID", dataLogin.getIdUser());
                                            resJsonFinalCheckout.put("txtCodeID", dataLogin.getTxtCodeId());
                                            resJsonFinalCheckout.put("txtCustomer", draft.getTxtCustomerName());

                                            resJsonFinalCheckout.put("txtRemarkSO", draft.getTxtRemarks());
                                            resJsonFinalCheckout.put("txtDeviceId", deviceInfo.getModel());
                                            resJsonFinalCheckout.put("txtUser", dataLogin.getNmUser());
                                            resJsonFinalCheckout.put("txtStatusDoc", "0");
                                            resJsonFinalCheckout.put("Detail", jsonProductCheckout);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        final String mRequestBodyFinalCheckout = resJsonFinalCheckout.toString();
                                        if(!tvPaymentMethod.getText().toString().equals("*Add payment method")){
                                            boolPaymentFill = true;
                                        }else{
                                            boolPaymentFill = false;
                                        }
                                        if(boolPaymentFill){
                                            new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPIFinalCheckout, mRequestBodyFinalCheckout, access_token, "Please Wait...", new VolleyResponseListener() {
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
                                                                    for (int n = 0; n < jsn.length(); n++) {

                                                                    }
                                                                }
                                                                alertD.dismiss();
                                                                new clsProductDraftRepo(context).clearAllData();
                                                                new clsDraftRepo(context).clearAllData();
                                                                ToastCustom.showToasty(context, "Checkout Completed", 1);
                                                                FragmentSalesOrder SOFragment = new FragmentSalesOrder();
                                                                FragmentTransaction fragmentTransactionSO = getActivity().getSupportFragmentManager().beginTransaction();
                                                                fragmentTransactionSO.replace(R.id.frame, SOFragment, "FragmentSalesOrder");
                                                                fragmentTransactionSO.commit();


                                                            } else {
                                                                ToastCustom.showToasty(context, warn, 2);

                                                            }
                                                        } catch (JSONException ex) {
                                                            String x = ex.getMessage();
                                                        }
                                                        String a = "";
                                                    }
                                                }
                                            });
                                        }else{
                                            ToastCustom.showToasty(context,"Payment method empty !", 3);
                                        }
                                    }
                                });

                                btnCancelPrev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertD.dismiss();
                                    }
                                });
                                        /*btnCheckoutPrev.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });*/
                            }
                        }
                    } catch (Exception ex) {
                        String exa = "sa";
                    }


                }
            }
        });
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public boolean addItem(VMItems item) {
        String itemCode = etItemCode.getText().toString();
        final String itemName = etItemName.getText().toString();
        boolean valid = true;
        if (valid) {
            boolean boolMatch = false;
            int j = 0;
            for (VMItems i : contentLibs) {
                if (i.getItemName().equals(item.getItemName())) {
                    int intQty = item.getQty();
                    i.setQty(intQty);
                    boolMatch = true;
                    contentLibs.set(j, i);
                }
            }
            if (!boolMatch) {
                contentLibs.add(item);
                ToastCustom.showToasty(context, "Item added", 3);
            } else {
                ToastCustom.showToasty(context, "Item on list updated", 3);
            }
            lvItemAdd.setAdapter(new CardAppAdapter(context, contentLibs, Color.WHITE));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            rvParent.setLayoutManager(mLayoutManager);
            /*slimAdapter = SlimAdapter.create().register(R.layout.card_list_app, new SlimInjector<VMItems>() {
                @Override
                public void onInject(VMItems data, IViewInjector injector) {
                    injector.text(R.id.tvHeaderProductName, data.getItemName())
                }
            }).attachTo(rvParent);*/

            rvParent.setItemAnimator(new DefaultItemAnimator());
            mAdapterItemHeader = new RVParentAdapter(context, contentLibs, Color.WHITE);
            mAdapterItemHeader.setClickListener(this);
            rvParent.setAdapter(mAdapterItemHeader);

            rvParent.addOnItemTouchListener(new RecyclerTouchListener(context,
                    rvParent, new ClickListener() {
                /*@Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well
                    Toast.makeText(getActivity(), "Single Click on position :"+position,
                            Toast.LENGTH_SHORT).show();
                    ImageView picture=(ImageView)view.findViewById(R.id.imageScanner);
                    picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "Single Click on Image :"+position,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }*/

                @Override
                public void onLongClick(View view, final int position) {
                    Toast.makeText(getActivity(), "Long press on position :" + position,
                            Toast.LENGTH_LONG).show();
                    String productName = contentLibs.get(position).getItemName();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder.setMessage("Delete item " + productName + " from list?");
                    alertDialogBuilder
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    contentLibs.remove(position);
                                    lvItemAdd.setAdapter(new CardAppAdapter(context, contentLibs, Color.WHITE));
                                    rvParent.setAdapter(new RVParentAdapter(context, contentLibs, Color.WHITE));
                                }
                            })
                            .setCancelable(false)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    final AlertDialog alertD = alertDialogBuilder.create();
                    alertD.show();
                }
            }));


            rvParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   /* if (itemLongClickListener != null) {

                        final long id = recyclerView.getChildItemId(v);
                        return itemLongClickListener.onItemLongClick(recyclerView, v, position, id);
                    }*/
                    final int position = rvParent.getChildAdapterPosition(v);
                    String productName = contentLibs.get(position).getItemName();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder.setMessage("Delete item " + productName + " from list?");
                    alertDialogBuilder
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    contentLibs.remove(position);
                                    lvItemAdd.setAdapter(new CardAppAdapter(context, contentLibs, Color.WHITE));
                                    rvParent.setAdapter(new RVParentAdapter(context, contentLibs, Color.WHITE));
                                }
                            })
                            .setCancelable(false)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    final AlertDialog alertD = alertDialogBuilder.create();
                    alertD.show();
                    return false;
                }
            });
            lvItemAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final String productName = contentLibs.get(position).getItemName();
                    final String itemGroup = contentLibs.get(position).getItemBrand();
                    final String itemCode = contentLibs.get(position).getItemCode();
                    final String itemBrand = contentLibs.get(position).getItemBrand();
                    final String itemBarcode = contentLibs.get(position).getBarcode();
                    final String itemDesc = contentLibs.get(position).getDesc();
                    final Double itemPrice = contentLibs.get(position).getPrice();
                    final Double itemDiscount = contentLibs.get(position).getDiscount();
                    final String itemBasedPoint = contentLibs.get(position).getBasePoint();
                    final String itemBonusPoint = contentLibs.get(position).getBonusPoint();
                    final int intQty = contentLibs.get(position).getQty();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                    alertDialogBuilder.setMessage("Action for " + productName + " ?");
                    alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contentLibs.remove(position);
                            lvItemAdd.setAdapter(new CardAppAdapter(context, contentLibs, Color.WHITE));
//                            rvParent.setAdapter(new RVParentAdapter(context, contentLibs, Color.WHITE));
                        }
                    })
                            .setCancelable(false)
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    final AlertDialog alertD = alertDialogBuilder.create();
                    alertD.show();
                    alertD.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater layoutInflater = LayoutInflater.from(context);
                            final View promptView = layoutInflater.inflate(R.layout.popup_product_search, null);
                            lvSearchResult = (ListView) promptView.findViewById(R.id.lvItemSearchResult);
                            etQtySearch = (EditText) promptView.findViewById(R.id.etQty);
                            etSearchOnPopUp = (EditText) promptView.findViewById(R.id.etItemName);
                            etDiscount = (EditText) promptView.findViewById(R.id.etDiscount);
                            etPrice = (EditText) promptView.findViewById(R.id.etPrice);
                            etBonusPoint = (EditText) promptView.findViewById(R.id.etBonusPoint);
                            etBasedPoint = (EditText) promptView.findViewById(R.id.etBasePoint);
                            etSearchOnPopUp.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etSearchOnPopUp) {
                                @Override
                                public boolean onDrawableClick() {
//                                        ToastCustom.showToasty(context,"Toast bro",1);
                                    refreshListItemSearch(promptView);
                                    return false;
                                }
                            });
                            etSearchOnPopUp.setOnKeyListener(new View.OnKeyListener() {
                                @Override
                                public boolean onKey(View v, int keyCode, KeyEvent event) {
                                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                        refreshListItemSearch(promptView);
                                        return true;
                                    }
                                    return false;
                                }
                            });
                            contentSearchResult = new ArrayList<VMItems>();
                            VMItems item = new VMItems(promptView);

                            item = new VMItems(promptView);
                            item.setItemName(productName);
                            item.setGuiid(new Helper().GenerateGuid());
//                                    item.setPrice(80000);
                            item.setItemGroup(itemGroup);
                            item.setBarcode(itemBarcode);
                            item.setItemCode(itemCode);
                            item.setDesc(itemDesc);
                            item.setItemBrand(itemBrand);

                            contentSearchResult.add(item);
                            lvSearchResult.setAdapter(new CardAdapterSearchResult(context, contentSearchResult, Color.WHITE));

                            lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    view.setSelected(true);
                                    getDetailItem(contentSearchResult, position);
                                }
                            });

                            etQtySearch.setText(String.valueOf(intQty));
                            etDiscount.setText(String.valueOf(itemDiscount));
                            etPrice.setText(String.valueOf(itemPrice));
                            etBasedPoint.setText(itemBasedPoint);
                            etBonusPoint.setText(itemBonusPoint);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setView(promptView);
                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            final AlertDialog alertDi = alertDialogBuilder.create();
                            alertDi.show();
                            alertDi.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String stQtySearch = etQtySearch.getText().toString();
                                    if (Integer.parseInt(stQtySearch) < 1) {
                                        ToastCustom.showToasty(context, "Invalid Qty", 2);
                                    } else {
                                        if (lvSearchResult.getCheckedItemPosition() > -1) {
                                            if (etQtySearch.getText().toString().equals("")) {
                                                ToastCustom.showToasty(context, "Quantity Empty", 3);
                                                etQtySearch.setBackgroundResource(R.drawable.bg_edtext_error_border);
                                            } else {
                                                Object checkedItem = lvSearchResult.getAdapter().getItem(lvSearchResult.getCheckedItemPosition());
                                                VMItems itemResult = new VMItems(promptView);
                                                int positionResult = lvSearchResult.getCheckedItemPosition();
                                                String itemNameAdd = contentSearchResult.get(positionResult).getItemName();
                                                String itemCodeAdd = contentSearchResult.get(positionResult).getItemCode();
                                                String itemBrand = contentSearchResult.get(positionResult).getItemBrand();
                                                String txtPrice = etPrice.getText().toString();
                                                double itemPriceSearch = 0;
                                                if (!txtPrice.equals("0")) {
                                                    itemPriceSearch = Double.parseDouble(txtPrice);
                                                }
                                                String txtDisc = etDiscount.getText().toString();
                                                double itemDiscount = 0;
                                                if (!txtDisc.equals("0")) {
                                                    itemDiscount = Double.parseDouble(txtDisc);
                                                }
                                                String itemQtyAdd = etQtySearch.getText().toString();

                                                int itemQty = Integer.parseInt(itemQtyAdd);

                                                Double itemTotalPrice = (itemPriceSearch * itemQty) - itemDiscount;

                                                Double itemTaxAmount = itemTotalPrice * 0.1;

                                                Double itemNetPrice = itemTotalPrice + itemTaxAmount;

                                                String itemBasePoint = etBasedPoint.getText().toString();
                                                String itemBonusPoint = etBonusPoint.getText().toString();

                                                VMItems item = new VMItems(promptView);
                                                item.setItemName(itemNameAdd);
                                                item.setGuiid(new Helper().GenerateGuid());
                                                item.setItemCode(itemCodeAdd);
                                                item.setPrice(itemPriceSearch);
                                                item.setItemBrand(itemBrand);
                                                item.setProductCategory(HMtxtProductCategory.get(itemCodeAdd));
                                                item.setBasePoint(itemBasePoint);
                                                item.setQty(itemQty);
                                                item.setDiscount(itemDiscount);
                                                item.setTotalPrice(itemTotalPrice);
                                                item.setTaxAmount(itemTaxAmount);
                                                item.setNetPrice(itemNetPrice);
                                                item.setBonusPoint(itemBonusPoint);

                                                boolean boolMatch = false;
                                                int j = 0;
                                                for (VMItems i : contentLibs) {
                                                    if (i.getItemName().equals(item.getItemName())) {
                                                        boolMatch = true;
                                                    }
                                                }
                                                if (boolMatch) {
                                                    ToastCustom.showToasty(context, "Product " + item.getItemName() + " has been in the list ", 2);
                                                } else {
                                                    contentLibs.set(position, item);
                                                    lvItemAdd.setAdapter(new CardAppAdapter(context, contentLibs, Color.WHITE));
                                                    alertDi.dismiss();
                                                    alertD.dismiss();

                                                }


//                                                boolean booladded = addItem(item);
//                                                if (booladded) {
//                                                    alertD.dismiss();
//                                                } else {
//                                                    ToastCustom.showToasty(context, "error system", 2);
//                                                }
                                            }

                                        } else {
                                            ToastCustom.showToasty(context, "Pick the item first", 4);
                                        }
                                    }


                                }
                            });
                        }
                    });
                    alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertDialogBuilderDelete = new AlertDialog.Builder(getActivity());
                            alertDialogBuilderDelete.setMessage("Delete item " + productName + " from list?");
                            alertDialogBuilderDelete
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false)
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            final AlertDialog alertDelete = alertDialogBuilderDelete.create();
                            alertDelete.show();
                            alertDelete.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    contentLibs.remove(position);
                                    lvItemAdd.setAdapter(new CardAppAdapter(context, contentLibs, Color.WHITE));
                                    rvParent.setAdapter(new RVParentAdapter(context, contentLibs, Color.WHITE));
                                    alertDelete.dismiss();
                                    alertD.dismiss();
                                }
                            });
                        }
                    });
//                    alertDialogBuilder.setView(promptView);

                    return false;
                }
            });

            ListViewCustom.setListViewHeightBasedOnItems(getActivity(), lvItemAdd);
            reset();
            return true;
        } else {
            return false;
        }

    }

    @OnClick(R.id.ivReset)
    public void onViewClicked() {
        reset();
    }

    @OnClick(R.id.btnSearchItem)
    public void onbtnSearchClicked() {

        String itemName = etItemName.getText().toString();
        String itemCode = etItemCode.getText().toString();
        if (!itemName.equals("")) {
            itemCode = "";
        }
        if (!itemCode.equals("")) {
            itemName = "";
        }
        String strLinkAPI = new clsHardCode().linkSearch;
        final JSONObject resJson = new JSONObject();
        try {
            resJson.put("txtProductBarcode", "");
            resJson.put("txtProductCode", itemCode);
            resJson.put("txtProductDesc", itemName);
//            resJson.put("txtRefreshToken", token);
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
                    try {
                        jsonObject = new JSONObject(response);
                        int result = jsonObject.getInt("intResult");
                        String warn = jsonObject.getString("txtMessage");
                        if (result == 1) {
                            if (!jsonObject.getString("ListData").equals("null")) {

                                LayoutInflater layoutInflater = LayoutInflater.from(context);
                                final View promptView = layoutInflater.inflate(R.layout.popup_product_search, null);
                                lvSearchResult = (ListView) promptView.findViewById(R.id.lvItemSearchResult);
                                etQtySearch = (EditText) promptView.findViewById(R.id.etQty);
                                etSearchOnPopUp = (EditText) promptView.findViewById(R.id.etItemName);
                                etDiscount = (EditText) promptView.findViewById(R.id.etDiscount);
                                etPrice = (EditText) promptView.findViewById(R.id.etPrice);
                                etBonusPoint = (EditText) promptView.findViewById(R.id.etBonusPoint);
                                etBasedPoint = (EditText) promptView.findViewById(R.id.etBasePoint);

                                etSearchOnPopUp.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etSearchOnPopUp) {
                                    @Override
                                    public boolean onDrawableClick() {
//                                        ToastCustom.showToasty(context,"Toast bro",1);
                                        refreshListItemSearch(promptView);
                                        return false;
                                    }
                                });
                                onclickKeyEnter(etSearchOnPopUp, promptView);

                                final List<VMItems> contentSearchResult = new ArrayList<VMItems>();
                                VMItems item = new VMItems(promptView);

                                JSONArray jsn = jsonObject.getJSONArray("ListData");
                                for (int n = 0; n < jsn.length(); n++) {
                                    JSONObject object = jsn.getJSONObject(n);
                                    String txtProductCategory = object.getString("txtProductCategory");
                                    String txtBrand = object.getString("txtBrand");
                                    String txtGroupProduct = object.getString("txtGroupProduct");
                                    String txtProductBarcode = object.getString("txtProductBarcode");
                                    String txtProductCode = object.getString("txtProductCode");
                                    HMtxtProductCategory.put(txtProductCode, txtProductCategory);
                                    String txtProductDesc = object.getString("txtProductDesc");

                                    item = new VMItems(promptView);
                                    item.setItemName(txtProductDesc);
                                    item.setGuiid(new Helper().GenerateGuid());
//                                    item.setPrice(80000);
                                    item.setItemGroup(txtGroupProduct);
                                    item.setBarcode(txtProductBarcode);
                                    item.setItemCode(txtProductCode);
                                    item.setDesc(txtProductDesc);
                                    item.setItemBrand(txtBrand);

                                    contentSearchResult.add(item);

                                }

                                lvSearchResult.setAdapter(new CardAdapterSearchResult(context, contentSearchResult, Color.WHITE));

                                lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        view.setSelected(true);
                                        getDetailItem(contentSearchResult, position);
                                    }
                                });
                                AlertDialog.Builder alertDialogBuilderAttch = new AlertDialog.Builder(getActivity());
                                alertDialogBuilderAttch.setView(promptView);
//        alertDialogBuilderAttch.setMessage("this is attachment of customer dialog");
                                alertDialogBuilderAttch
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setCancelable(false)
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                cbAttach.setChecked(false);
                                                lvSearchResult.setSelected(false);
                                            }
                                        });
                                final AlertDialog alertD = alertDialogBuilderAttch.create();
                                if (jsn.length() == 0) {
                                    ToastCustom.showToasty(context, "Search Not Found", 3);
                                } else {
                                    alertD.show();
                                    alertD.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String stQtySearch = etQtySearch.getText().toString();
                                            if (Integer.parseInt(stQtySearch) < 1) {
                                                ToastCustom.showToasty(context, "Invalid Qty", 2);
                                            } else {
                                                if (lvSearchResult.getCheckedItemPosition() > -1) {
                                                    if (etQtySearch.getText().toString().equals("")) {
                                                        ToastCustom.showToasty(context, "Quantity Empty", 3);
                                                        etQtySearch.setBackgroundResource(R.drawable.bg_edtext_error_border);
                                                    } else {
                                                        Object checkedItem = lvSearchResult.getAdapter().getItem(lvSearchResult.getCheckedItemPosition());
                                                        VMItems itemResult = new VMItems(promptView);
                                                        int position = lvSearchResult.getCheckedItemPosition();
                                                        String itemNameAdd = contentSearchResult.get(position).getItemName();
                                                        String itemCodeAdd = contentSearchResult.get(position).getItemCode();
                                                        String itemBrand = contentSearchResult.get(position).getItemBrand();
                                                        String txtPrice = etPrice.getText().toString();
                                                        double itemPriceSearch = 0;
                                                        if (!txtPrice.equals("0")) {
                                                            itemPriceSearch = Double.parseDouble(txtPrice);
                                                        }
                                                        String txtDisc = etDiscount.getText().toString();
                                                        double itemDiscount = 0;
                                                        if (!txtDisc.equals("0")) {
                                                            itemDiscount = Double.parseDouble(txtDisc);
                                                        }
                                                        String itemQtyAdd = etQtySearch.getText().toString();

                                                        int itemQty = Integer.parseInt(itemQtyAdd);

                                                        Double itemTotalPrice = (itemPriceSearch * itemQty) - itemDiscount;

                                                        Double itemTaxAmount = itemTotalPrice * 0.1;

                                                        Double itemNetPrice = itemTotalPrice + itemTaxAmount;

                                                        String itemBasePoint = etBasedPoint.getText().toString();
                                                        String itemBonusPoint = etBonusPoint.getText().toString();

                                                        VMItems item = new VMItems(promptView);
                                                        item.setItemName(itemNameAdd);
                                                        item.setGuiid(new Helper().GenerateGuid());
                                                        item.setItemCode(itemCodeAdd);
                                                        item.setPrice(itemPriceSearch);
                                                        item.setItemBrand(itemBrand);
                                                        item.setProductCategory(HMtxtProductCategory.get(itemCodeAdd));
                                                        item.setBasePoint(itemBasePoint);
                                                        item.setQty(itemQty);
                                                        item.setDiscount(itemDiscount);
                                                        item.setTotalPrice(itemTotalPrice);
                                                        item.setTaxAmount(itemTaxAmount);
                                                        item.setNetPrice(itemNetPrice);
                                                        item.setBonusPoint(itemBonusPoint);

                                                        boolean booladded = addItem(item);
                                                        if (booladded) {
                                                            alertD.dismiss();
                                                        } else {
                                                            ToastCustom.showToasty(context, "error system", 2);
                                                        }
                                                    }

                                                } else {
                                                    ToastCustom.showToasty(context, "Pick the item first", 4);
                                                }
                                            }

                                        }
                                    });
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String a = "";
                }
            }
        });

    }

    private void getDetailItem(List<VMItems> contentSearchResult, int position) {
        VMItems itemSelected = null;
        itemSelected = contentSearchResult.get(position);
        String txtGroupUser = itemSelected.getItemGroup();
        final String txtItemCode = itemSelected.getItemCode();
        int Qty = itemSelected.getQty();
        String intQty = String.valueOf(Qty);
        String txtKontakID = dataLogin.getTxtKontakID();
        String txtNoLangganan = "";
        String intPeriodeLangganan = "0";
        final String strLinkAPI = new clsHardCode().linkGetPrice;
        final JSONObject resJson = new JSONObject();
        try {
            resJson.put("txtGroupUser", txtGroupUser);
            resJson.put("txtItemCode", txtItemCode);
            resJson.put("intQty", intQty);
            resJson.put("txtKontakID", txtKontakID);
            resJson.put("txtNoLangganan", txtNoLangganan);
            resJson.put("intPeriodeLangganan", intPeriodeLangganan);
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
                JSONObject jsonObjectPrice = null;
                if (response != null) {
                    try {
                        jsonObjectPrice = new JSONObject(response);
                        int result = jsonObjectPrice.getInt("intResult");
                        String warn = jsonObjectPrice.getString("txtMessage");
                        if (result == 1) {
                            if (!jsonObjectPrice.getString("ListData").equals("null")) {
                                JSONArray jsn = jsonObjectPrice.getJSONArray("ListData");
                                for (int n = 0; n < jsn.length(); n++) {
                                    JSONObject object = jsn.getJSONObject(n);
                                    String txtDiscount = object.getString("decDiscount");
                                    String txtPrice = object.getString("decPriceList");
                                    String txtBasePoint = object.getString("decBasePoint");
                                    String txtDecBonus = object.getString("decBonus");
                                    etDiscount.setText(txtDiscount);
                                    etPrice.setText(txtPrice);
                                    etBonusPoint.setText(txtDecBonus);
                                    etBasedPoint.setText(txtBasePoint);
                                    for (VMItems item : contentLibs) {
                                        if (item.getItemCode().equals(txtItemCode)) {
                                            etQtySearch.setText(String.valueOf(item.getQty()));
                                        }
                                    }
                                }
                                etQtySearch.requestFocus();
                            }
                        }
                    } catch (JSONException e) {

                    }

                    String a = "";
                }
            }
        });
    }

    private void onclickKeyEnter(EditText et, View v) {
        etSearchOnPopUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    refreshListItemSearch(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void refreshListItemSearch(final View promptView) {
        String strLinkAPISearchOnPopUp = new clsHardCode().linkSearch;
        String keywordOnPopUpSearch = etSearchOnPopUp.getText().toString();
        final JSONObject resJson = new JSONObject();
        try {
            resJson.put("txtProductBarcode", "");
            resJson.put("txtProductCode", "");
            resJson.put("txtProductDesc", keywordOnPopUpSearch);
//            resJson.put("txtRefreshToken", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new VolleyUtils().makeJsonObjectRequestWithToken(getActivity(), strLinkAPISearchOnPopUp, mRequestBody, access_token, "Please Wait...", new VolleyResponseListener() {
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
                        VMItems item = new VMItems(promptView);
                        contentSearchResult = new ArrayList<VMItems>();
                        if (result == 1) {
                            if (!jsonObject.getString("ListData").equals("null")) {
                                JSONArray jsn = jsonObject.getJSONArray("ListData");
                                for (int n = 0; n < jsn.length(); n++) {
                                    JSONObject object = jsn.getJSONObject(n);
                                    String txtProductCategory = object.getString("txtProductCategory");
                                    String txtBrand = object.getString("txtBrand");
                                    String txtGroupProduct = object.getString("txtGroupProduct");
                                    String txtProductBarcode = object.getString("txtProductBarcode");
                                    String txtProductCode = object.getString("txtProductCode");
                                    HMtxtProductCategory.put(txtProductCode, txtProductCategory);
                                    String txtProductDesc = object.getString("txtProductDesc");

                                    item = new VMItems(promptView);
                                    item.setItemName(txtProductDesc);
                                    item.setGuiid(new Helper().GenerateGuid());
//                                    item.setPrice(80000);
                                    item.setItemGroup(txtGroupProduct);
                                    item.setBarcode(txtProductBarcode);
                                    item.setItemCode(txtProductCode);
                                    item.setDesc(txtProductDesc);
                                    item.setItemBrand(txtBrand);

                                    contentSearchResult.add(item);
                                }
                                lvSearchResult.setAdapter(new CardAdapterSearchResult(context, contentSearchResult, Color.WHITE));
                                etDiscount.setText("");
                                etPrice.setText("");
                                etQtySearch.setText("");
                                etBasedPoint.setText("");
                                etBonusPoint.setText("");
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

    private void addDefaultSpinnerAlamat() {
        categoriesProv.add(STRSPINNEROPT);
        categoriesKabKot.add(STRSPINNEROPT);
        categoriesKecamatan.add(STRSPINNEROPT);
        categoriesKelurahan.add(STRSPINNEROPT);
        categoriesPostCode.add(STRSPINNEROPT);
        HMProvinceID.put(STRSPINNEROPT, "0");
        HMKabKotID.put(STRSPINNEROPT, "0");
        HMKecID.put(STRSPINNEROPT, "0");
        HMKel.put(STRSPINNEROPT, "0");
        HMKodePos.put(STRSPINNEROPT, "0");
    }

    @OnClick(R.id.etDeliverySchedule)
    public void onDeliveryScheduleClicked() {
        Calendar c = Calendar.getInstance();
        final Bundle args = new Bundle();
        args.putInt(CustomDatePicker.YEAR, c.get(Calendar.YEAR));
        args.putInt(CustomDatePicker.MONTH, 0);
        args.putInt(CustomDatePicker.DAY_OF_MONTH, 1);

        int max = data.getIntSchedel();
        c.add(Calendar.DATE, 0);
        args.putLong(CustomDatePicker.DATE_MIN, c.getTimeInMillis());
//                args.putLong(CustomDatePicker.DATE_MAX, System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        //4 di bawah bisa di ganti dengan max schedule dari api
        c.add(Calendar.DATE, max);
        args.putLong(CustomDatePicker.DATE_MAX, c.getTimeInMillis());

        //set hint for date
//        CustomDatePicker.showHint(etDeliverySchedule, args, CustomDatePicker.format.standard0);
        clsDatePicker.showDatePicker(getActivity(), context, etDeliverySchedule, "Delivery Schedule", args, clsDatePicker.format.standard1, android.R.style.Theme_Holo_Light_Dialog);
//        CustomDatePicker.showDatePicker(getActivity(), context, etDeliverySchedule, "Delivery Schedule", CustomDatePicker.format.standard0, args);
    }

    @OnClick(R.id.etOrderLocation)
    public void onOrderLocationClicked() {

    }

    @Override
    public void onItemClick(View view, int position) {
        String a = "adasd";
        ToastCustom.showToasty(context, "Tst", 2);
    }
}