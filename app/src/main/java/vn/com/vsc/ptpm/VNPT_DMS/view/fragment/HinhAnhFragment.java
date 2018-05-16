package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.user.DeleteImageImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.user.DeleteImagePresenter;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.ImagePopupActivity;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.HinhAnhAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HinhAnh;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.user.IDeleteImageView;
import vn.com.vsc.ptpm.VNPT_DMS.vn.UploadActivity;

@SuppressLint("ValidFragment")
public class HinhAnhFragment extends ListFragment implements OnItemClickListener, IDeleteImageView {
    // LogCat tag
    private static final String TAG = HinhAnhFragment.class.getSimpleName();
    private KhachHang KH;
    private String ID = "";
    private MainActivity mainActivity;
    Controller control;
    Button btn_upload, btn_take_photo, btn_refresh, btn_close;

    private ProgressDialog pDialog;
    private int pageCount = 0;
    private int totalPages = 0;
    private List<HinhAnh> arrHA = null;
    private HinhAnhAdapter adapter;
    private Uri fileUri; // file url to store image
    // private static final int PICK_IMAGE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    public static final String IMAGE_DIRECTORY_NAME = "DMS_FileUpload";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private String Img_Path = "";
    private FragmentManager fManager;
    private FragmentTransaction fTrans;
    private Uri picUri;
    private Activity activity;
    private GetDSKHTask getDSKHTask;
    public DeleteImagePresenter deleteImagePresenter = new DeleteImageImpl(this);

    public HinhAnhFragment() {
    }

    public HinhAnhFragment(KhachHang KH) {
        this.KH = KH;
        this.ID = KH.getId();
    }

    public void init() {
        arrHA = new ArrayList<HinhAnh>();
        adapter = null;
    }

    private String getURL_HA() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=khachhang_dsanh&org_id=" + ID;
        Log.i("url-ha", url);
        return url;
    }

    private void AddItems(View view) {
        btn_upload = (Button) view.findViewById(R.id.btn_ha_upload);
        btn_take_photo = (Button) view.findViewById(R.id.btn_ha_take_photo);
        btn_refresh = (Button) view.findViewById(R.id.btn_ha_refresh);
        btn_close = (Button) view.findViewById(R.id.btn_ha_close);

    }

    private void addBtn() {
        btn_upload.setOnClickListener(new HandleBtnClick());
        btn_take_photo.setOnClickListener(new HandleBtnClick());
        btn_refresh.setOnClickListener(new HandleBtnClick());
        btn_close.setOnClickListener(new HandleBtnClick());
    }




    private class HandleBtnClick implements OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_ha_upload:
                    selectImageFromGallery();
                    break;
                case R.id.btn_ha_take_photo:
                    takePhoto();
                    break;
                case R.id.btn_ha_refresh:
                    init();
                    if (NetworkType.internetIsAvailable(activity)) {
                        GetDSKHTask task = new GetDSKHTask();
                        if (task == null || task.getStatus() == AsyncTask.Status.FINISHED) {
                            task.execute();
                        }
                    }
                    break;
                case R.id.btn_ha_close:
                    Config.isOpenNew = false;
                    activity.onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }

    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;
        Log.i("frgmntName-created", fragmentTag);
        fManager = getFragmentManager();
        boolean fragmentPopped = fManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && fManager.findFragmentByTag(fragmentTag) == null) {
            fTrans = fManager.beginTransaction();
            fTrans.replace(R.id.frame_container, fragment, fragmentTag);

            fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fTrans.addToBackStack(backStateName);
            Log.i("frgmntName-create", fragmentTag);
            fTrans.commit();
        }
    }

    private void launchUploadActivity(boolean isImage) {
        Intent i = new Intent(activity, UploadActivity.class);
        i.putExtra("filePath", Img_Path);
        i.putExtra("idCus", ID);
        // startActivity(i);
        startActivityForResult(i, 2);
    }

    public void selectImageFromGallery() {
        // Intent intent = new Intent();
        // intent.setType("image/*");
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        // startActivityForResult(Intent.createChooser(intent,
        // "Select Picture"),
        // PICK_IMAGE);
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = getOutputMediaFile(1);
        picUri = Uri.fromFile(file); // create
        if (picUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
            startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                // Uri selectedImage = data.getData();
                Uri selectedImage = picUri;
                Log.e("URI", "" + selectedImage);
                /*
                 * String[] filePathColumn = { MediaStore.Images.Media.DATA };
				 *
				 * Cursor cursor =
				 * activity.getContentResolver().query(selectedImage,
				 * filePathColumn, null, null, null); cursor.moveToFirst();
				 *
				 * int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				 * String picturePath = cursor.getString(columnIndex);
				 * cursor.close();
				 */
                Img_Path = selectedImage.getPath();
                Log.i("HAF", Img_Path);
                launchUploadActivity(true);
            }

            // get URI image
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null,
                        null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Img_Path = picturePath;
                Log.i("HAF", Img_Path);
                launchUploadActivity(true);
                // ImageView imageView = (ImageView) findViewById(R.id.imgView);
                // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }

            if (resultCode == 2) {
                init();
                if (NetworkType.internetIsAvailable(activity)) {
                    GetDSKHTask task = new GetDSKHTask();
                    task.execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hinhanh, container, false);
        control = new Controller(activity);
        mainActivity = (MainActivity) getActivity();
        AddItems(view);
        addBtn();
        init();
        if (NetworkType.internetIsAvailable(activity)) {
            GetDSKHTask task = new GetDSKHTask();
//            if (task == null || task.getStatus() == AsyncTask.Status.FINISHED)
            task.execute();
        }
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setHasOptionsMenu(true);// hien thi cac option
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newAction:
                Toast.makeText(activity, "ok", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
        // TODO Auto-generated method stub
        Toast.makeText(activity, "pos=" + pos, Toast.LENGTH_SHORT).show();
    }

    class GetDSKHTask extends AsyncTask<Void, Void, List<HinhAnh>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(false);
            pDialog.show();
            // MA_KH = txtSearchKH.getText().toString().trim();

        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<HinhAnh> doInBackground(Void... params) {
            pageCount++;
            String json_dsha = control.jsonValues(getURL_HA(), false);
            Type listType = new TypeToken<List<HinhAnh>>() {
            }.getType();
            List<HinhAnh> ds_ha = new ArrayList<HinhAnh>();
            arrHA = new ArrayList<HinhAnh>();
            try {
                ds_ha = (List<HinhAnh>) new Gson().fromJson(json_dsha, listType);
                Log.i("kh size=", String.valueOf(ds_ha.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ds_ha.size() > 0) {
                arrHA.addAll(ds_ha);
//                for (int i = 0; i < ds_ha.size(); i++) {
//                    arrHA.add(ds_ha.get(i));
//                }
            }
            return arrHA;
        }

        @Override
        protected void onPostExecute(List<HinhAnh> values) {
            super.onPostExecute(values);
            dismissWithCheck(pDialog);

            if (adapter == null) {
                adapter = new HinhAnhAdapter(activity, arrHA, HinhAnhFragment.this);
                setListAdapter(adapter);
            } else {
                adapter.notifiDataChange(arrHA);
            }


            try {
                getListView().setOnScrollListener(onScrollListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private OnScrollListener onScrollListener() {
        return new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = getListView().getCount();
                Log.i("count", String.valueOf(count));
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (getListView().getLastVisiblePosition() >= count - threshold && pageCount < totalPages) {
                        // Execute LoadMoreDataTask AsyncTask
                        if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getDSKHTask = new GetDSKHTask();
                            getDSKHTask.execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

        };
    }

    /**
     * ------------ Helper Methods ----------------------
     */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                    return null;
                }
            }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        HinhAnh hinhanh = arrHA.get(position);
        Intent intent = new Intent(activity, ImagePopupActivity.class);
        intent.putExtra("image-url", hinhanh.getUrl());
        intent.putExtra("RELATED_ID", Integer.parseInt(hinhanh.getRelated_id()));
        intent.putExtra("IMAGE_ID", Integer.parseInt(hinhanh.getId()));
        intent.putExtra("FRAGMENT_TAG", TAG);
        startActivity(intent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void dismissWithCheck(Dialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

                // if the Context used here was an activity AND it hasn't been finished or destroyed
                // then dismiss it
                if (context instanceof Activity) {

                    // Api >=17
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                            dismissWithTryCatch(dialog);
                        }
                    } else {

                        // Api < 17. Unfortunately cannot check for isDestroyed()
                        if (!((Activity) context).isFinishing()) {
                            dismissWithTryCatch(dialog);
                        }
                    }
                } else
                    // if the Context used wasn't an Activity, then dismiss it too
                    dismissWithTryCatch(dialog);
            }
            dialog = null;
        }
    }

    public void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
        } catch (final Exception e) {
            // Do nothing.
        } finally {
            dialog = null;
        }
    }

    public void reloadDanhSachKhachHang(){
        if (NetworkType.internetIsAvailable(activity)) {
            GetDSKHTask task = new GetDSKHTask();
            task.execute();
        }
    }

    @Override
    public void onDeleteImageSuccess(Object object) {
        mainActivity.hideProgressBar();
        reloadDanhSachKhachHang();
    }

    @Override
    public void onDeleteImageError(Object object) {
        mainActivity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            control.showAlertDialog(
                    getActivity(),
                    "Thông báo",
                    response.get(0).getResult(),
                    false);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public void deleteImage(Integer idImage){
        mainActivity.hideProgressBar();
        deleteImagePresenter.deleteImage(idImage);
    }

}
