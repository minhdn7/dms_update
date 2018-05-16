package vn.com.vsc.ptpm.VNPT_DMS.view.activities.glab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.config.UnicodeFormatter;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachChiTietDonHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.ThongTinDonHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabCapNhatTrangThaiImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabChiTietDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabThongTinDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatTrangThaiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabChiTietDonHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabThongTinDonHangView;

public class PrintActivity extends Activity implements Runnable, IGlabThongTinDonHangView, IGlabChiTietDonHangView, IGlabCapNhatTrangThaiView {
    private Button btnScan, btnDisable, btnExitPrinter, btnPrint;
    // setup printer
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    private String BILL;
    private KProgressHUD hud;
    private int pageNo = 1;
    private int pageRec = 100;
    BluetoothDevice mBluetoothDevice;
    Controller control = new Controller(this);
    private GlabThongTinDonHangImpl thongTinDonHangPresenter;
    private GlabChiTietDonHangImpl chiTietDonHangPresenter;
    private GlabCapNhatTrangThaiImpl capNhatTrangThaiPresenter;
    private String poId = "";
    private List<ThongTinDonHangResponse> thongTinDonHangResponse;
    private List<DanhSachChiTietDonHangResponse> chiTietDonHangResponses;
    // end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnExitPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    } else {
                        ListPairedDevices();
                        Intent connectIntent = new Intent(PrintActivity.this,
                                DeviceListActivity.class);
                        startActivityForResult(connectIntent,
                                REQUEST_CONNECT_DEVICE);
                    }
                }
            }
        });

        btnDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter != null)
                    mBluetoothAdapter.disable();
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                thongTinDonHangPresenter.getThongTinDonHang(Integer.parseInt(poId));

            }
        });
    }

    private void addControls() {
        // add Controls
        btnScan = (Button) findViewById(R.id.btnScan);
        btnDisable = (Button) findViewById(R.id.btnDisable);
        btnExitPrinter = (Button) findViewById(R.id.btnExitPrinter);
        btnPrint = (Button) findViewById(R.id.btnPrint);
        thongTinDonHangPresenter = new GlabThongTinDonHangImpl(this);
        chiTietDonHangPresenter = new GlabChiTietDonHangImpl(this);
        capNhatTrangThaiPresenter = new GlabCapNhatTrangThaiImpl(this);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("Đang kết nối...")
                .setCancellable(true)
                .setAnimationSpeed(3)
                .setDimAmount(0.5f);
        poId = getIntent().getStringExtra("PO_ID");
        chiTietDonHangResponses = new ArrayList<>();
        thongTinDonHangResponse = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }

    public void dialogPrintPreview(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_printer_preview);
        dialog.setCancelable(false);
        // chỉnh kích cỡ dialog show
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        height = (int) (height * 0.9);
        width = (int) (width * 0.5);
//        lp.width = height;
//        lp.height = width;

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        TextView txtPrintPreview = (TextView) dialog.findViewById(R.id.txtPrintPreview);
        Button btnInHoaDon = (Button) dialog.findViewById(R.id.btnInHoaDon);
        Button btnExitPrintPreview = (Button) dialog.findViewById(R.id.btnExitPrintPreview);
        // set text print preview
        BILL = "";
        BILL =  "      VNPT.DMS GreenLab   \n " +
                "      Bien Lai Thu Tien   \n" ;
        BILL = BILL
                + "-----------------------------------------------\n";
        if(thongTinDonHangResponse.size() > 0){
            try{
                BILL = BILL + "Ma Don Hang: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getMaDon()) + "\n";
                BILL = BILL + "Ngay Gio Lay mau: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getNgayLap()) + "\n";
                BILL = BILL + "Don Vi: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getSupplierName()) + "\n";
                BILL = BILL + "Ho va Ten: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getTenkh()) + "\n";
                BILL = BILL + "Nam sinh: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getNamsinhkh()) + "      ";
                if(thongTinDonHangResponse.get(0).getGioitinhkh().equals("1")){
                    BILL = BILL + "Gioi tinh: Nam" + "\n";
                }else {
                    BILL = BILL + "Gioi tinh: Nu" + "\n";
                }

                BILL = BILL + "Dia chi: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getDichikh()) + "\n";
                BILL = BILL + "Email: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getEmailkh()) + "\n";
                BILL = BILL + "Dien Thoai: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getMobilekh()) + "\n";
                BILL = BILL + "Chuan Doan: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getDienGiai()) + "\n";
                BILL = BILL + "Danh muc hang: "+ "\n";
                if(chiTietDonHangResponses.size() > 0){
                    BILL = BILL
                            + "-----------------------------------------------\n";
                    BILL = BILL + "\n " + String.format("%1$3s %2$10.8s %3$20.12s %4$10s", "STT",
                            "Ma",
                            "Ten",
                            "Don Gia");
                    for(int i = 0; i < chiTietDonHangResponses.size(); i++){
                        String stt = String.valueOf((i + 1));
                        BILL = BILL + "\n " + String.format("%1$3s %2$10.8s %3$20.12s %4$10s", stt,
                                ConvertTiengVietKhongDau(chiTietDonHangResponses.get(i).getLoaispId()),
                                ConvertTiengVietKhongDau(chiTietDonHangResponses.get(i).getLoaispTen()),
                                ConvertTiengVietKhongDau(chiTietDonHangResponses.get(i).getGianhap()));
                    }


                }
                BILL = BILL
                        + "\n-----------------------------------------------\n";

                try{
                    BILL = BILL + "Tong tien hang: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getTongtientruockm()) +"\n";
                }catch (Exception ex){

                }

                try{
                    BILL = BILL + "Khuyen mai: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getTienkm()) +"\n";
                }catch (Exception ex){

                }

                try{
                    BILL = BILL + "TONG TIEN: " + ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getTongtien()) +"\n";
                }catch (Exception ex){

                }

                BILL = BILL + "\n" + "Ky ten khach hang: "+ "\n" + "\n" + "\n";
                BILL = BILL + "Nguoi lay mau: "+ ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getNguoiLap()) + "\n";
                BILL = BILL + "Gio hen tra ket qua: "+ ConvertTiengVietKhongDau(thongTinDonHangResponse.get(0).getNgaymuonNhanhang()) + "\n";
                BILL = BILL + "\n" + "\n";
            }catch (Exception ex){

            }


        }

        // end
        txtPrintPreview.setText(BILL);

        btnExitPrintPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        btnInHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhatTrangThaiPresenter.getCapNhatTrangThai(Integer.parseInt(poId));
                Thread t = new Thread() {
                    public void run() {
                        try {
                            OutputStream os = mBluetoothSocket
                                    .getOutputStream();

                            os.write(BILL.getBytes());
                            //This is printer specific code you can comment ==== > Start

                            // Setting height
                            int gs = 29;
                            os.write(intToByteArray(gs));
                            int h = 104;
                            os.write(intToByteArray(h));
                            int n = 162;
                            os.write(intToByteArray(n));

                            // Setting Width
                            int gs_width = 29;
                            os.write(intToByteArray(gs_width));
                            int w = 119;
                            os.write(intToByteArray(w));
                            int n_width = 2;
                            os.write(intToByteArray(n_width));


                        } catch (Exception e) {
                            Log.e("MainActivity", "Exe ", e);
                        }
                    }
                };
                t.start();
            }
        });
    }

    @Override
    public void onGlabChiTietDonHangSuccess(Object object) {
        hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachChiTietDonHangResponse[] arrChiTietThongTinDonHang = gson.fromJson(jsonElement, DanhSachChiTietDonHangResponse[].class);
            chiTietDonHangResponses = new ArrayList<>(Arrays.asList(arrChiTietThongTinDonHang));
            dialogPrintPreview();


        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
            control.showAlertDialog(
                    this,
                    "Thông báo",
                    "Không lấy được danh sách chi tiết đơn hàng",
                    false);
        }

    }

    @Override
    public void onGlabChiTietDonHangError(Object object) {
        hideProgressBar();
        control.showAlertDialog(
                this,
                "Thông báo",
                "Không lấy được danh sách chi tiết đơn hàng",
                false);
    }

    @Override
    public void onGlabThongTinDonHangSuccess(Object object) {
        showProgressBar();
        chiTietDonHangPresenter.getChiTietDonHang(pageNo, pageRec, Integer.parseInt(poId), "");
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            ThongTinDonHangResponse[] arrThongTinDonHang = gson.fromJson(jsonElement, ThongTinDonHangResponse[].class);
            thongTinDonHangResponse = new ArrayList<>(Arrays.asList(arrThongTinDonHang));
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }

    }

    @Override
    public void onGlabThongTinDonHangError(Object object) {
        hideProgressBar();
        control.showAlertDialog(
                this,
                "Thông báo",
                "Không lấy được thông tin đơn hàng",
                false);
    }

    public void showProgressBar() {
        try{
            if (hud != null && !hud.isShowing())
                hud.show();
        }catch (Exception ex){

        }

    }

    public void hideProgressBar() {
        try {
            if (hud != null && hud.isShowing())
                hud.dismiss();
        } catch (Exception ex) {

        }
    }

    public String ConvertTiengVietKhongDau(String s){
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "");
    }

    @Override
    public void onGlabCapNhatTrangThaiSuccess(Object object) {
        hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrayResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> listResponse = new ArrayList<>(Arrays.asList(arrayResponse));
//            control.showAlertDialog(
//                    this,
//                    "Thông báo",
//                    listResponse.get(0).getResult(),
//                    false);
        }catch (Exception ex){

        }
    }

    @Override
    public void onGlabCapNhatTrangThaiError(Object object) {
        hideProgressBar();
//        control.showAlertDialog(
//                this,
//                "Thông báo",
//                "Không cập nhật được trạng thái đơn hàng",
//                false);
    }
}
