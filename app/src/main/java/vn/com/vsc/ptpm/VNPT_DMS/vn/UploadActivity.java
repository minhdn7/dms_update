package vn.com.vsc.ptpm.VNPT_DMS.vn;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.Utils.AndroidMultiPartEntity;
import vn.com.vsc.ptpm.VNPT_DMS.Utils.AndroidMultiPartEntity.ProgressListener;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;

public class UploadActivity extends Activity {
    // LogCat tag
    private static final String TAG = UploadActivity.class.getSimpleName();
    private static final String FILE_UPLOAD_URL = API_code.URL_API_ROOT + "img_upload.jsp";
    // String sss =
    // API_code.URL_API_ROOT+"main?IyLlCc5f5w5fCES.=CB9Y6BnbJzLj43HwCy5c6BLbJy5bCQ9ZCBxV6Bxd3zAmCE9X5o..&IytmDEnf5BPVTEtYCES.=OxHL3w9QPm..&IzHbCEtw5BPV6BO.=Lm..&IzLeCzpV6BO.==";
    private ProgressBar progressBar;
    private String filePath = null;
    private TextView txtPercentage;
    private ImageView imgPreview;
    private VideoView vidPreview;
    private Button btnUpload;
    private Button btnClose;
    long totalSize = 0;
    private String POST_FIELD = "uploadform";
    private ProgressDialog dialog = null;
    private int serverResponseCode = 0;
    private String idCus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnClose = (Button) findViewById(R.id.btnClose);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        vidPreview = (VideoView) findViewById(R.id.videoPreview);
        //txtPercentage.setText("OK");

        Intent i = getIntent();
        filePath = i.getStringExtra("filePath");
        idCus = i.getStringExtra("idCus");
        Log.i("idCus", idCus);

        boolean isImage = (true);
        if (filePath != null) {
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // uploading the file to server
                // new UploadFileToServer().execute();
                // new AsyncUploadBitmaps().execute();

                if (NetworkType.internetIsAvailable(UploadActivity.this)) {
                    dialog = ProgressDialog.show(UploadActivity.this, "", "Đang tải ảnh lên Server. Vui lòng chờ trong giây lát...", false);
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            uploadFile(filePath, idCus);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                            });
                        }
                    });
                    thread.start();
                } else {
                    Controller control = new Controller(UploadActivity.this);
                    control.showAlertDialog(UploadActivity.this, "Thông báo", "Bạn không thể tải ảnh lên Server khi không có mạng", false);
                }

                // Toast.makeText(
                // UploadActivity.this,
                // uploadFile2Server(FILE_UPLOAD_URL, filePath, POST_FIELD),
                // Toast.LENGTH_SHORT).show();
            }
        });

        btnClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     * Displaying captured image/video on the screen
     */
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            if (ActivityCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                return;
            }
            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imgPreview.setImageBitmap(bitmap);
        } else {
            imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
        }
    }

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("fileUpload", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("DcAi43Pb5t9f5o", new StringBody("Lm.."));

                entity.addPart("IytmDEnf5BPVTEtYCES.", new StringBody(
                        "OxHL3w9QPm.."));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String uploadFile(String resourceUri, String idCus) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File((resourceUri));
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        // thoi gian chup anh.
        Date lastModDate = new Date(sourceFile.lastModified());
        Log.i(TAG, lastModDate.toString());
        String serverResponseMessage = null;
        String responce = null;

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "File not found !", Toast.LENGTH_LONG).show();
                }
            });

            return "no file";
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile.getPath());
                URL url = new URL(FILE_UPLOAD_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty(POST_FIELD, sourceFile.getName());
                dos = new DataOutputStream(conn.getOutputStream());
                if (dos != null) {
                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"related_id\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes(idCus);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    // add parameters
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"applied_table\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // assign value
                    dos.writeBytes("CRM_ORG");
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    // send image
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\""
                            + POST_FIELD + "\";filename="
                            + sourceFile.getName() + lineEnd);
                    dos.writeBytes(lineEnd);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    serverResponseCode = conn.getResponseCode();
                    serverResponseMessage = conn.getResponseMessage();
                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                }
                // ------------------ read the SERVER RESPONSE
                dis = new DataInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(dis));
                String line;
                final StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                Log.i(TAG, builder.toString());
                reader.close();
                dis.close();
                if (serverResponseCode <= 200) {
                    runOnUiThread(new Runnable() {
                        public void run() {

							/*Toast.makeText(
                                    UploadActivity.this,
									"File Upload Complete: "
											+ builder.toString(),
											Toast.LENGTH_SHORT).show();*/
                            AlertDialog.Builder alert = new AlertDialog.Builder(UploadActivity.this);
                            alert.setIcon(R.drawable.ic_success);
                            alert.setTitle("Thông báo");
                            alert.setMessage("Tải ảnh thành công");
                            alert.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent i = new Intent();
                                    setResult(2, i);
                                    finish();
                                }
                            });
                            alert.create().show();
                        }
                    });
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace(); // To change body of catch statement use
                // File | Settings | File Templates.
            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(UploadActivity.this,
                                "MalformedURLException", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            } catch (IOException e) {
                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(UploadActivity.this,
                                "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file",
                        "Exception : " + e.getMessage(), e);
            }
        }
        dialog.dismiss();

        return responce;
    }


    public String returnRespon(final HttpURLConnection urlConnection) {
        StringBuilder stringBuilder = null;
        DataInputStream inputStream = null;

        // ------------------ read the SERVER RESPONSE
        try {
            inputStream = new DataInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String uploadFile2Server(String uploadUrl, String filePath, String serverKeyName) {
        HttpURLConnection urlConnection = null;
        DataOutputStream dos = null;
        DataInputStream inputStream = null;
        String lineEnd = "rn";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        StringBuilder stringBuilder = null;
        try {
            // ------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(
                    filePath));
            // open a URL connection to the Server
            URL url = new URL(uploadUrl);
            // Open a HTTP connection to the URL
            urlConnection = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            urlConnection.setDoInput(true);
            // Allow Outputs
            urlConnection.setDoOutput(true);
            // Don't use a cached copy.
            urlConnection.setUseCaches(false);
            // Use a post method.
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\""
                    + serverKeyName + "\";filename=\"" + filePath + " \""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // ------------------ read the SERVER RESPONSE
        try {
            inputStream = new DataInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}