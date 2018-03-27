package com.kalbenutritionals.kalcaremobie.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.kalbe.mobiledevknlibs.Toast.ToastCustom;
import com.kalbenutritionals.kalcaremobie.Common.clsToken;
import com.kalbenutritionals.kalcaremobie.Common.clsUserLogin;
import com.kalbenutritionals.kalcaremobie.Data.VolleyResponseListener;
import com.kalbenutritionals.kalcaremobie.Data.VolleyUtils;
import com.kalbenutritionals.kalcaremobie.Data.adapter.CardAdapterListSo;
import com.kalbenutritionals.kalcaremobie.Data.adapter.ListViewCustom;
import com.kalbenutritionals.kalcaremobie.Data.clsHardCode;
import com.kalbenutritionals.kalcaremobie.Fragment.dummy.DummyContent.DummyItem;
import com.kalbenutritionals.kalcaremobie.R;
import com.kalbenutritionals.kalcaremobie.Repo.clsTokenRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsUserLoginRepo;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMLIstSo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dtLoginData = dataLogin.getDtLogin();
        String currentDateandTime = sdf.format(dtLoginData);
        final List<VMLIstSo> contentLibs = new ArrayList<>();

        //casting
        lvSO = (ListView) view.findViewById(R.id.lvContent);
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddOrder fragmentAddOrder = new FragmentAddOrder();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragmentAddOrder,"Fragment_AddOrder");
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
                                        String txtDelivery = object.getString("txtDelivery");
                                        String txtDeviceId = object.getString("txtDeviceId");
                                        String intStatus = object.getString("intStatus");
                                        String txtRemarks = object.getString("txtRemarks");
                                        String txtStatus_code = object.getString("txtStatus_code");

                                        VMLIstSo data = new VMLIstSo();
                                        data.setTxtNoSo(txtNoSo);
                                        data.setTxtAgentName(txtAgentName);
                                        data.setTxtStatus(txtStatus_code);
                                        data.setIntStatus(intStatus);
                                        data.setTxtCustomerName(txtCustomer);

                                        contentLibs.add(data);

                                    }
                                    lvSO.setAdapter(new CardAdapterListSo(getActivity().getApplicationContext(), contentLibs, Color.WHITE));
                                    ListViewCustom.setListViewHeightBasedOnItems(getActivity(),lvSO);
                                    lvSO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            VMLIstSo itemSelected = null;
                                            itemSelected = contentLibs.get(position);
                                            FragmentAddOrder fragmentAddOrder = new FragmentAddOrder();
                                            Bundle arguments = new Bundle();
                                            arguments.putString( "noSO" , itemSelected.getTxtNoSo());
                                            fragmentAddOrder.setArguments(arguments);
                                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.frame, fragmentAddOrder, "Fragment_AddOrder");

                                            fragmentTransaction.commit();
                                        }
                                    });
                                    if (jsn.length()==0){
                                        scrollViewList.setVisibility(View.GONE);
                                        lnNoData.setVisibility(View.VISIBLE);
                                    }else{
                                        scrollViewList.setVisibility(View.VISIBLE);
                                        lnNoData.setVisibility(View.GONE);
                                    }



                            }
                        }else{
                            ToastCustom.showToasty(context,warn, 2);
                                scrollViewList.setVisibility(View.GONE);
                                lnNoData.setVisibility(View.VISIBLE);


                        }
                    }catch (JSONException ex){
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
