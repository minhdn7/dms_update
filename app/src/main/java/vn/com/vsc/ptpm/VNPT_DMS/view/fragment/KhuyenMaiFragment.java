package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhuyenMaiAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DoAction;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhuyenMaiModel;

public class KhuyenMaiFragment extends ListFragment {

    ListView listview_KM;
    TextView tv_NoDataKM;
    public List<KhuyenMaiModel> arrKM = null;
    private String ID = "";
    private String ASSIGN_ID = "";
    private String COMMENT = "";
    private ProgressDialog pDialog;
    private int pageCount = 0;
    private int totalPages = 0;
    Controller control;
    ConvertFont conv = new ConvertFont();
    Handler handler;
    private KhuyenMaiAdapter adapter;
    private Boolean flgErr;
    private HashMap<Integer, String> hashMapId;
    private GetDSKMTask getDSKMTask;
    private Activity activity;

    public KhuyenMaiFragment() {
    }

    public static ChiTietKhuyenMaiFragment newInstance(String code, String name, String start_date, String end_date, String description, int id) {
        ChiTietKhuyenMaiFragment fragment = new ChiTietKhuyenMaiFragment();
        Bundle args = new Bundle();
        args.putString("code", code);
        args.putString("name", name);
        args.putString("start_date", start_date);
        args.putString("end_date", end_date);
        args.putString("description", description);
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khuyenmai, container, false);
        control = new Controller(activity);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview_KM = getListView();
        tv_NoDataKM = (TextView) view.findViewById(R.id.tv_NoDataKM);
        Log.d("cuong started view", "ok");
        flgErr = false;
        init();
        if (NetworkType.internetIsAvailable(activity)) {
            if (mAF.chkDieukien()) {
                if (getDSKMTask == null || getDSKMTask.getStatus() == AsyncTask.Status.FINISHED) {
                    getDSKMTask = new GetDSKMTask();
                    getDSKMTask.execute();
                }
            }
        } else {
            //getDataOffline();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class GetDSKMTask extends AsyncTask<Void, List<KhuyenMaiModel>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(true);
            pDialog.show();
            // MA_KH = txtSearchKH.getText().toString().trim();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            pageCount++;
            if (totalPages == 0) {
                //totalPages = 10;
                //tam bo
                try {
                    NorAndNop n = control.getNorNop(getURL_NopNor());
                    totalPages = Integer.parseInt(n.getNop());
                } catch (Exception e) {
                    totalPages = 10;
                }

            }
            String url, json_dsbl = "";
            url = getURL_KhuyenMai();

            try {
                json_dsbl = control.jsonValues(url, false);
                //json_dsbl = control.getDataJSON(url, false);  //Khi null hay bi loi
                Log.d("cuong result", json_dsbl);

                if (json_dsbl.contains("api_error")) {
                    Log.d("cuong lay km errr", "Lỗi lấy số liệu khuyến mại");
                    tv_NoDataKM.setText("Lỗi lấy số liệu khuyến mại");
                    tv_NoDataKM.setVisibility(View.VISIBLE);
                } else {
                    hashMapId = new HashMap<>();
                    Type listType = new TypeToken<List<KhuyenMaiModel>>() {
                    }.getType();
                    List<KhuyenMaiModel> ds_bl = (List<KhuyenMaiModel>) new Gson().fromJson(json_dsbl, listType);
                    for (int i = 0; i < ds_bl.size(); i++) {
                        arrKM.add(ds_bl.get(i));
                        hashMapId.put(i, arrKM.get(i).getId() + "");
                    }
                    publishProgress(arrKM);
                }
            } catch (Exception ex) {
                flgErr = true;
                Log.d("error get json data", ex.toString());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(List<KhuyenMaiModel>... values) {
            super.onProgressUpdate(values);
            if (adapter == null) {
                adapter = new KhuyenMaiAdapter(activity, values[0]);
                setListAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            listview_KM.setOnScrollListener(onScrollListener());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dismissWithCheck(pDialog);
            if (flgErr) {
                control.showAlertDialog(activity, getResources().getText(R.string.thongbao) + "", getResources().getText(R.string.errmsg) + "", false);
            }
        }

    }

    private OnScrollListener onScrollListener() {
        return new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = getListView().getCount();
                Log.i("cuong count", String.valueOf(count));
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.d("SCROLL_STATE_IDLE: ", pageCount + "-" + totalPages + "");
                    if (getListView().getLastVisiblePosition() >= count
                            - threshold
                            && pageCount < totalPages) {
                        // Execute LoadMoreDataTask AsyncTask
                        Log.d("cuong getpage km: ", pageCount + "-" + totalPages + "");
                        if (getDSKMTask == null || getDSKMTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getDSKMTask = new GetDSKMTask();
                            getDSKMTask.execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }

        };
    }

    public void init() {
        arrKM = new ArrayList<KhuyenMaiModel>();
//        arrAttachFileModels = new ArrayList<>();
        adapter = null;
        pageCount = 0;
        totalPages = 0;
    }


    private String getURL_KhuyenMai() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=get_promotion&pageno="
                + pageCount
                + "&pagerec=10&org_id=0"
                + ID
                + "&assign_id=0"
                + ASSIGN_ID;
        Log.i("cuong url-km", url);
        return url;
    }

    private String getURL_NopNor() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=get_promotion&org_id=0"
                + ID + "&assign_id=0" + ASSIGN_ID;
        return url;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Fragment fragment = KhuyenMaiFragment.newInstance(arrKM.get(position).getCode(), arrKM.get(position).getName(), arrKM.get(position).getStartDate(), arrKM.get(position).getEndDate(), arrKM.get(position).getDescription(), arrKM.get(position).getId());
//        Fragment f = new ChiTietKhuyenMaiFragment();
        if (fragment != null) {
            ((MainActivity) activity).replaceFragment(fragment);
        }
    }


    //cuongtm thêm :  Start lấy tiền khuyến mại //Thử nghiệm
    private class laytienKhuyenmaiTask extends AsyncTask<Void, String, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang tính khuyến mại ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = API_code.URL_DOACT;
            String json1 = "";

            DoAction dact = new DoAction("", "getkm", "pos_po", "promotion_amount, total_money", "", "id=188");
            json1 = "&json=" + new Gson().toJson(dact);

            url = url + json1;
            String json_result = "";
            try {
                json_result = control.getDataJSON(control.convertURL(url), true);
            } catch (Exception ex) {
                Log.d("error get tien km", url + ". Kq=" + ex.toString());
            }

            Log.d("get tien khuyenmai", url + ". Kq=" + json_result);
            publishProgress(json_result);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            if (values[0] != null) {
                try {
                    JSONObject jsonObject = new JSONObject(values[0].toString());
                    if (jsonObject != null) {
                        String result = jsonObject.getString("promotion_amount");
                        Log.d("cuong giatri=", result);
                        /**
                         String[] a = result.split("\\|"); //Do | là ký tự đặc biệt, cần đưa thêm \\ vào
                         if (a[0].trim().toUpperCase().contains("OK")) {
                         control.showAlertDialog(activity, "Thông báo", "Tính khuyến mại hoàn tất!", true);
                         if (NetworkType.internetIsAvailable(activity)) {
                         //Load lại dữ liệu
                         } else {
                         }

                         } else {
                         control.showAlertDialog(activity, "Thông báo", "Tính khuyến mại không thành công. Vui lòng thử lại!", false);
                         }
                         **/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                control.showAlertDialog(activity, "Error", "Không kết nối được tới máy chủ. Vui lòng thử lại!", false);
            }

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }
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
}
