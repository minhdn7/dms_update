package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.BinhLuanAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.dao.BinhLuanDAL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BinhLuan;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

@SuppressLint("ValidFragment")
public class BinhLuanFragment extends ListFragment implements OnItemClickListener {
    List<BinhLuan> arrBL = null;
    private String ID = "";
    private String ASSIGN_ID = "";
    private String COMMENT = "";
    private ProgressDialog pDialog;
    private int pageCount = 0;
    private int totalPages = 0;
    private Button btn_send;
    private Button btn_close;
    private EditText txt_comment;
    Controller control;
    ConvertFont conv = new ConvertFont();
    Handler handler;
    BinhLuanAdapter adapter;
    private Activity activity;

    private GetDSKHTask getDSKHTask;

    public BinhLuanFragment() {
    }

    public BinhLuanFragment(KhachHang KH) {
        this.ID = KH.getId();
        this.ASSIGN_ID = KH.getAssign_id();
    }

    private String getURL_BinhLuan() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_binhluan&pageno=" + pageCount + "&pagerec=10&org_id=" + ID + "&assign_id="
                + ASSIGN_ID;
        Log.i("url-binhluan", url);
        return url;
    }

    private String getURL_BL_Them() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_binhluan_themmoi&org_id=" + ID + "&assign_id=" + ASSIGN_ID + "&comment="
                + COMMENT;
        Log.i("url-binhluan-moi", url);
        return url;
    }

    private String getURL_NopNor() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_binhluan&org_id=" + ID + "&assign_id=" + ASSIGN_ID;
        return url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_binhluan, container, false);
        btn_send = (Button) view.findViewById(R.id.btn_sendBL);
        btn_close = (Button) view.findViewById(R.id.btn_close);
        txt_comment = (EditText) view.findViewById(R.id.txt_binhluan);
        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = txt_comment.getText().toString().trim();

                if (content.equals("")) {
                    control.showAlertDialog(activity, "Thông báo", "Mời bạn nhập nhập nội dung bình luận", false);
                    txt_comment.requestFocus();
                } else {
                    // COMMENT = content;
                    // COMMENT = conv.getEnStringFromVnString(content);
                    try {
                        COMMENT = URLEncoder.encode(content, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.i("COMMENT", COMMENT);

                    if (NetworkType.internetIsAvailable(activity)) {
                        sendComment();
                    } else {
                        saveBinhluanOffline();
                    }
                }
            }
        });
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Config.isOpenNew = false;
                activity.onBackPressed();
            }
        });
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        control = new Controller(activity);
        init();
        if (NetworkType.internetIsAvailable(activity)) {
            GetDSKHTask task = new GetDSKHTask();
            task.execute();
        } else {
            getDataOffline();
        }
    }

    private void getDataOffline() {
        try {
            arrBL = new BinhLuanDAL(activity).getByTuyenKhachHang(ID, ASSIGN_ID);
            if (arrBL.size() > 0) {
                adapter = new BinhLuanAdapter(activity, arrBL);
                setListAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Toast.makeText(activity, "pos=" + pos, Toast.LENGTH_SHORT).show();
    }

    public void init() {
        arrBL = new ArrayList<BinhLuan>();
        adapter = null;
        pageCount = 0;
        totalPages = 0;
    }

    class GetDSKHTask extends AsyncTask<Void, List<BinhLuan>, String> {

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
                try {
                    NorAndNop n = control.getNorNop(getURL_NopNor());
                    totalPages = Integer.parseInt(n.getNop());
                } catch (Exception e) {
                }
                Log.d("cuong total binh luan", totalPages + "");
            }
            String json_dsbl = control.jsonValues(getURL_BinhLuan(), false);
            Type listType = new TypeToken<List<BinhLuan>>() {
            }.getType();
            List<BinhLuan> ds_bl = new ArrayList<>();
            try {
                ds_bl = (List<BinhLuan>) new Gson().fromJson(json_dsbl, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ds_bl != null) {
                for (int i = 0; i < ds_bl.size(); i++) {
                    arrBL.add(ds_bl.get(i));
                }
            }
            publishProgress(arrBL);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<BinhLuan>... values) {
            super.onProgressUpdate(values);
            if (adapter == null) {
                adapter = new BinhLuanAdapter(activity, values[0]);
                setListAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            getListView().setOnScrollListener(onScrollListener());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
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

    private void sendComment() {
        try {
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang gửi bình luận...");
            pDialog.setCancelable(true);
            pDialog.show();
            new Thread() {
                @SuppressWarnings("static-access")
                public void run() {
                    String json_result = "";
                    // json_result = control.jsonValues(control.convertURL(getURL_BL_Them()), false);
                    json_result = control.jsonValues(getURL_BL_Them(), false);
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putString("json_tbl", conv.GetNCRDecimalString(json_result));
                    Log.i("json-bl", conv.GetNCRDecimalString(json_result));
                    msg.setData(b);
                    messageHandler.sendMessage(msg);
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String aResponse = (String) (msg.getData().getString("json_tbl").trim());
            JSONObject emp;
            String empname = "";
            try {
                emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                empname = emp.optString("result");
                Log.i("json_tbl", empname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (empname.equals("0")) {
                dismissWithCheck(pDialog);
                txt_comment.setText("");
                control.showAlertDialog(activity, "Thông báo", "Gửi bình luận thành công", true);
                txt_comment.setText("");
                init();
                GetDSKHTask task = new GetDSKHTask();
                task.execute();
            } else {
                dismissWithCheck(pDialog);
                control.showAlertDialog(activity, "Thông báo", "Gửi bình luận không thành công", false);
            }

        }
    };

    private void saveBinhluanOffline() {
        try {
            String date = "";
            try {
                date = Utilities.getDate(System.currentTimeMillis(), "dd/MM/yyyy HH:mm");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            BinhLuan binhluan = new BinhLuan(ID, ASSIGN_ID, COMMENT, 1, date, Config.username);
            long resultInsert = new BinhLuanDAL(activity).add(binhluan);
            if (resultInsert > 0) {
                control.showAlertDialog(activity, "Thông báo", "Thêm bình luận Offline thành công", true);
                txt_comment.setText("");
                getDataOffline();
            } else {
                control.showAlertDialog(activity, "Thông báo", "Thêm bình luận Offline không thành công", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
