package com.kalbenutritionals.kalcaremobie.Fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.Toast.ToastCustom;
import com.kalbenutritionals.kalcaremobie.BL.clsActivity;
import com.kalbenutritionals.kalcaremobie.Common.clsPhotoProfile;
import com.kalbenutritionals.kalcaremobie.Common.clsUserLogin;
import com.kalbenutritionals.kalcaremobie.Data.Helper;
import com.kalbenutritionals.kalcaremobie.MainMenu;
import com.kalbenutritionals.kalcaremobie.R;
import com.kalbenutritionals.kalcaremobie.Repo.clsPhotoProfilRepo;
import com.kalbenutritionals.kalcaremobie.Repo.clsUserLoginRepo;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View v;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvUserId)
    TextView tvUserId;
    @BindView(R.id.tvUserGRP)
    TextView tvUserGRP;
    @BindView(R.id.tvOutlet)
    TextView tvOutlet;
    @BindView(R.id.tvSumberID)
    TextView tvSumberID;
    @BindView(R.id.tvTeleName)
    TextView tvTeleName;
    private static byte[] phtProfile;
    private Uri uriImage, selectedImage;
    clsPhotoProfilRepo repoUserImageProfile;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final int SELECT_FILE_PROFILE = 6;
    final int PIC_CROP_PROFILE = 50;
    clsUserLogin dataLogin;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    private OnFragmentInteractionListener mListener;
    Context context;
    private static final int CAMERA_REQUEST_PROFILE = 120;
    List<clsPhotoProfile> dataImageProfile = null;
    private static Bitmap photoProfile, mybitmapImageProfile;
    public FragmentProfile() {
        // Required empty public constructor
    }

    Unbinder unbinder;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dataLogin = new clsUserLoginRepo(context).getDataLogin(context);
        String name = dataLogin.getNmUser();
        String idUser = dataLogin.getIdUser();
        String grp = dataLogin.getGrpUser();
        String outlet = dataLogin.getTxtNamaInstitusi();
        String sumberDataId = dataLogin.getTxtSumberDataID();
        String teleName = dataLogin.getTxtTeleName();
        tvUsername.setText(name);
        tvUserId.setText(idUser);
        tvUserGRP.setText(grp);
        tvOutlet.setText(outlet);
        tvSumberID.setText(sumberDataId);
        tvTeleName.setText(teleName);
        phtProfile = null;

        if (photoProfile != null) {
            profileImage.setImageBitmap(photoProfile);
            photoProfile.compress(Bitmap.CompressFormat.PNG, 100, output);
            phtProfile = output.toByteArray();
        }
        try {
            repoUserImageProfile = new clsPhotoProfilRepo(context);
            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dataImageProfile.size() > 0) {
            viewImageProfile();
        }
        return v;
    }
    protected void viewImageProfile() {
        try {
            repoUserImageProfile = new clsPhotoProfilRepo(context);
            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/data/data/KalbeCare/tempdata/Foto_Profil");
        folder.mkdir();

        for (clsPhotoProfile imgDt : dataImageProfile){
            final byte[] imgFile = imgDt.getTxtImg();
            if (imgFile != null) {
                mybitmapImageProfile = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
                Bitmap bitmap = Bitmap.createScaledBitmap(mybitmapImageProfile, 150, 150, true);
                profileImage.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_PROFILE) {
            if (resultCode == -1) {
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    String uri = uriImage.getPath().toString();

                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropProfile();

//                    previewCaptureImage2(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 0) {
                Toast.makeText(context, "User cancel take image", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    photoProfile = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PIC_CROP_PROFILE) {
            if (resultCode == -1) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = null;
//                if  (extras != null){
//                    thePic = extras.getParcelable("data");
//                }else{
//                    Uri uri = data.getData();
//                    thePic = decodeUriAsBitmap(uri);
//                }
                Uri uri = data.getData();
                if (uri != null) {
                    thePic = decodeUriAsBitmap(uri);
                }
                if (extras != null) {
                    Bitmap tempBitm = extras.getParcelable("data");
                    if (tempBitm != null) {
                        thePic = tempBitm;
                    }
                }
                previewCaptureImageProfile(thePic);
            } else if (resultCode == 0) {
                Toast.makeText(context, "User cancel take image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_FILE_PROFILE) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    selectedImage = data.getData();
                    String uri = selectedImage.getPath().toString();
                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropGalleryProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);

            if (CropImage.isReadExternalStoragePermissionsRequired(getActivity(), imageUri)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
//                Intent intent = new Intent(this, CropDisplayPicture.class);
//                String strName = imageUri.toString();
//                intent.putExtra("uriPicture", strName);
//                startActivity(intent);
                getActivity().finish();
            }
        } else if (requestCode == 100 || requestCode == 130 || requestCode == 99) {
            for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                try {
                    fragment.onActivityResult(requestCode, resultCode, data);
                } catch (Exception ex) {

                }
            }
        }
    }
    private void performCropGalleryProfile() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(selectedImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    private void previewCaptureImageProfile(Bitmap photo) {
        try {
            Bitmap bitmap = new clsActivity().resizeImageForBlob(photo);
            profileImage.setVisibility(View.VISIBLE);
            output = null;
            try {
                output = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            phtProfile = output.toByteArray();
            profileImage.setImageBitmap(photo_view);

            saveImageProfile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    protected void saveImageProfile() {
        try {
            clsPhotoProfilRepo repo = new clsPhotoProfilRepo(context);
            dataImageProfile = (List<clsPhotoProfile>) repo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clsPhotoProfile data = new clsPhotoProfile();
        data.setTxtGuiId(new Helper().GenerateGuid());
        data.setTxtImg(phtProfile);

        new clsPhotoProfilRepo(context).createOrUpdate(data);
        int i = new clsPhotoProfilRepo(getActivity().getApplicationContext()).createOrUpdate(data);
        getActivity().finish();
        startActivity(new Intent(getActivity(), MainMenu.class));
        Toast.makeText(getActivity().getApplicationContext(), "Image Profile Saved", Toast.LENGTH_SHORT).show();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
    protected void tedChooserPicture() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getActivity())
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected uri
                        uriImage = uri;
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        performCropProfile();
//                        Bitmap bitmap;
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                        bitmap = BitmapFactory.decodeFile(uri2, bitmapOptions);
//
//                        performCropProfile();
                    }
                })
                .create();

        tedBottomPicker.show(getActivity().getSupportFragmentManager());
    }
    private void performCropProfile() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");


//            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
//            int size = list.size();
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
            //start the activity - we handle returning in onActivityResult
            getActivity().startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            ToastCustom.showToasty(context,errorMessage,2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.profile_image)
    public void onViewClicked() {
        tedChooserPicture();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
