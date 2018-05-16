package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.AttachFileModel;

/**
 * Created by ThaoPit on 11/3/2016.
 */

public class ChiTietKhuyenMaiFragment extends Fragment {
    private Controller control;
    private List<AttachFileModel> arrAttachFileModels;
    private TextView title_ctkm, time_ctkm, content_ctkm, url_ctkm, kichco_ctkm;
    private ListView list_attach;
    private int id;
    private ConvertFont conv = new ConvertFont();
    private AttachFileAdapter arrFileAdapter;
    private ProgressDialog pDialog;

    private GetDSAttachFile getDSAttachFile;
    private DownloadTask downloadTask;

    private Activity activity;

    public ChiTietKhuyenMaiFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chitiet_khuyenmai, container, false);

        control = new Controller(activity);
        arrAttachFileModels = new ArrayList<>();

        title_ctkm = (TextView) v.findViewById(R.id.title_chitiet_km);
        content_ctkm = (TextView) v.findViewById(R.id.content_chitiet_km);
        url_ctkm = (TextView) v.findViewById(R.id.ten_file);
        kichco_ctkm = (TextView) v.findViewById(R.id.kich_co_file);
        time_ctkm = (TextView) v.findViewById(R.id.time_chitiet_km);
        list_attach = (ListView) v.findViewById(R.id.lv_attach_file);

        Bundle b = this.getArguments();
        getDataBundle(b);

        if (getDSAttachFile == null || getDSAttachFile.getStatus() == AsyncTask.Status.FINISHED) {
            getDSAttachFile = new GetDSAttachFile();
            getDSAttachFile.execute();
        }
        return v;
    }

    private void getDataBundle(Bundle bundle) {
        if (bundle != null) {
            title_ctkm.setText(bundle.getString("code") + " - " + conv.getUTF8StringFromNCR(bundle.getString("name")));
            time_ctkm.setText("Thời gian khuyến mại: " + bundle.get("start_date") + " - " + bundle.get("end_date"));
            content_ctkm.setText("Chi tiết khuyến mại: " + "\n" + conv.getUTF8StringFromNCR(bundle.getString("description")) + "\n");
            id = bundle.getInt("id");
        }
    }


    class GetDSAttachFile extends AsyncTask<Void, List<AttachFileModel>, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "";
            String json_dsbl = "";
            try {
                json_dsbl = control.jsonValues(getURL_Attach_File(id + ""), false);
                Log.d("json attach file", json_dsbl);
                Type listType = new TypeToken<List<AttachFileModel>>() {
                }.getType();
                List<AttachFileModel> ds_bl = (List<AttachFileModel>) new Gson().fromJson(json_dsbl, listType);
                for (int i = 0; i < ds_bl.size(); i++) {
                    arrAttachFileModels.add(ds_bl.get(i));
                }
                publishProgress(arrAttachFileModels);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(List<AttachFileModel>... values) {
            super.onProgressUpdate(values);
            if (arrFileAdapter == null) {
                arrFileAdapter = new AttachFileAdapter(activity, values[0]);
                list_attach.setAdapter(arrFileAdapter);
            } else {
                arrFileAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private String getURL_Attach_File(String id) {
//        http://10.145.40.237:7777/vnpt/mobiapp_api_v1.jsp?callback=?&api_code=attack_promotion&pro_id=16
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=attack_promotion&pro_id=" + id;
        Log.i("url attach-file", url);
        return url;
    }

    private class AttachFileAdapter extends BaseAdapter {
        private Context mContext;
        private List<AttachFileModel> attachItems;
        private LayoutInflater inflater;

        private AttachFileAdapter(Context context, List<AttachFileModel> attachItems) {
            this.mContext = context;
            this.attachItems = attachItems;
        }

        @Override
        public int getCount() {
            return attachItems.size();
        }

        @Override
        public Object getItem(int position) {
            return attachItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.item_list_attach_file, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tenfile = (TextView) view.findViewById(R.id.ten_file);
                viewHolder.kichthuoc = (TextView) view.findViewById(R.id.kich_co_file);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final AttachFileModel attachFileModel = attachItems.get(position);
//            String[] s1 = attachFileModel.getName().split("\\.(?=[^\\.]+$)");
//            if (attachFileModel.getName().length() > 14) {
//                String s2 = s1[0].replace(" ", "");
//                ATTACH_FILE_NAME = s2.substring(0, 10) + "." + s1[1];
//            } else {
//                ATTACH_FILE_NAME = attachFileModel.getName();
//            }
//            ATTACH_FILE_NAME = System.currentTimeMillis() + "_" + attachFileModel.getName() + "";
            String name = null, hdd_file = null;
            try {
                if (attachFileModel != null) {
                    if (attachFileModel.getName() != null)
                        name = URLEncoder.encode(attachFileModel.getName(), "utf-8");
                    if (attachFileModel.getHdd_file() != null)
                        hdd_file = URLEncoder.encode(attachFileModel.getHdd_file(), "utf-8");
                    final String url_down_file = API_code.URL_API_ROOT + "smartoffice/jbm/download.jsp?5E1XCBS.=" + name + "&5FpXTEW.=" + hdd_file;
                    Log.e("url-down-attach-file", url_down_file);
                    viewHolder.tenfile.setText(attachFileModel.getName());
                    viewHolder.tenfile.setTextColor(Color.BLUE);
                    viewHolder.tenfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (downloadTask == null || downloadTask.getStatus() == AsyncTask.Status.FINISHED) {
                                    downloadTask = new DownloadTask();
                                    downloadTask.execute(url_down_file, attachFileModel.getName());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                if (attachFileModel != null){
                    if (attachFileModel.getFile_size() != null)
                        viewHolder.kichthuoc.setText("Kích thước: " + df.format(Double.parseDouble(attachFileModel.getFile_size()) / (1024 * 1024)) + " Mb");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            viewHolder.tenfile.setText(Html.fromHtml("<a href=\"" + url_down_file + "\">" + attachFileModel.getName() + "</a> "));
//            viewHolder.tenfile.setMovementMethod(LinkMovementMethod.getInstance());
//            stripUnderlines(viewHolder.tenfile);
            return view;
        }

        private class ViewHolder {
            TextView tenfile, kichthuoc;
        }
    }

    private class DownloadTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                File f = new File("/sdcard/DownloadVNPTDMS/");
                f.mkdir();
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString() + "/DownloadVNPTDMS/" + f_url[1]);

                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return f_url[1];
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(final String result) {
            dismissWithCheck(pDialog);
            new AlertDialog.Builder(activity)
                    .setTitle("Tải về hoàn tất")
                    .setMessage("Mở tệp ngay?")
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DownloadVNPTDMS/" + result);
                            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                            String[] filenameArray = result.split("\\.");
                            String extension = filenameArray[filenameArray.length - 1];
                            extension = extension.toLowerCase();
                            String type = mimeTypeMap.getMimeTypeFromExtension(extension);
//                            type = type.toLowerCase();
                            if (type == null) {
                                type = "*/*";
                            }
                            Intent target = new Intent(Intent.ACTION_VIEW);
                            target.setDataAndType(Uri.fromFile(file), type);
                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
                            Intent intent = Intent.createChooser(target, "Open File");
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                            }
                        }
                    })
                    .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //nothing
                        }
                    })
//                    .setIcon()
                    .show();
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
        } catch (final Exception e) {
            // Do nothing.
        }
    }
}
