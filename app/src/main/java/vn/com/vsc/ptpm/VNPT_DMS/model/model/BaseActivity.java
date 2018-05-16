package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import vn.com.vsc.ptpm.VNPT_DMS.R;

public class BaseActivity extends Activity {
	private KProgressHUD hud;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		hud = KProgressHUD.create(this)
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setDetailsLabel("Đang kết nối...")
				.setCancellable(true)
				.setAnimationSpeed(3)
				.setDimAmount(0.5f);
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_bando:
			Toast.makeText(getApplicationContext(),
					"Test base activity", Toast.LENGTH_SHORT).show();
			// Do Code Here

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void showProgressBar() {
		try{
			if (hud != null && !hud.isShowing())
				hud.show();
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
					WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		}catch (Exception ex){

		}

	}

	public void hideProgressBar() {
		try{
			if (hud != null && hud.isShowing())
				hud.dismiss();
			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		}catch (Exception ex){

		}

	}
}
