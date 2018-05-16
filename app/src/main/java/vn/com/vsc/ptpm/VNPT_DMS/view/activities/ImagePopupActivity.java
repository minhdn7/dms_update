package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;

import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BaseActivity;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.user.ChangeImageAvaImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.user.ChangeImageAvaPresenter;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.user.DeleteImageImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.user.DeleteImagePresenter;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.HinhAnhFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.user.IChangeImageView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.user.IDeleteImageView;

public class ImagePopupActivity extends BaseActivity implements IChangeImageView{

	private ImageView mImageView;
	private Button mButtonClose, btnDeleteimage, btnChangeAva;
	private String url_head = API_code.URL_API_ROOT;
	private String fragmentTag = "";
	private Integer imageId = 0;
	private Integer reletedId = 0;
	private Controller control= new Controller(this);
	private ChangeImageAvaPresenter changeImagePresenter = new ChangeImageAvaImpl(this);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int screenWidth = (int) (metrics.widthPixels * 0.60);
		int screenHeight = (int) (metrics.heightPixels * 0.70);
		setContentView(R.layout.activity_popup);
		getWindow().setLayout(screenWidth, screenHeight);

		mImageView = (ImageView) findViewById(R.id.image_view);
		mButtonClose = (Button) findViewById(R.id.button_close);
		btnChangeAva = (Button) findViewById(R.id.btnChangeAva);
		btnChangeAva = (Button) findViewById(R.id.btnChangeAva);



		// Get Image Url
		Bundle b = getIntent().getExtras();
		String url = b.getString("image-url", "");
		imageId = b.getInt("IMAGE_ID", 0);
		reletedId = b.getInt("RELATED_ID", 0);
		fragmentTag = b.getString("FRAGMENT_TAG", "");
		Log.e("IMAGE-URL", url);

		if (!url.equals("")) {
			Picasso.with(ImagePopupActivity.this).load(url_head + url).fit().into(mImageView);
		}
		addEvents();
	}

	private void addEvents() {
		mButtonClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnChangeAva.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgressBar();
				changeImagePresenter.changeImage(imageId, reletedId);
			}
		});

	}

	@Override
	public void onChangeImageSuccess(Object object) {
		hideProgressBar();
		try {
			Gson gson = new Gson();
			JsonElement jsonElement = gson.toJsonTree(object);
			CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
			List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
			dialogUpdateSuccess(
                    this,
                    "Thông báo",
                    response.get(0).getResult(),
                    false);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onChangeImageError(Object object) {
		hideProgressBar();
		try {
			Gson gson = new Gson();
			JsonElement jsonElement = gson.toJsonTree(object);
			CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
			List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
			control.showAlertDialog(
                    this,
                    "Thông báo",
                    response.get(0).getResult(),
                    false);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	public void dialogUpdateSuccess(Context context, String title, String message, Boolean status) {
		Log.e("CONCRETE", "context = " + context);

		if(context != null){
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle(title);
			alertDialog.setMessage(message);
			alertDialog.setIcon((status) ? R.drawable.ic_success : R.drawable.ic_fail);
			alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();
				}
			});
			alertDialog.show();
		}
	}

}
