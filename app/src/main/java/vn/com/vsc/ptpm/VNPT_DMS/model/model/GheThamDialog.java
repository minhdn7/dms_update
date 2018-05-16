package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;

public class GheThamDialog extends BaseDialog {

	public GheThamDialog(Context context, String title, String message) {
		super(context);

		setContentView(R.layout.custom_dialog_ghetham);
		final EditText txtBankinh = (EditText) findViewById(R.id.d_tongKH);
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		Spinner spin_gps = (Spinner) findViewById(R.id.spin_gps);
		TextView btn_close = (TextView) findViewById(R.id.btnSearchGPS);
		tvTitle.setText(title);
		
		
		btn_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String bk = txtBankinh.getText().toString();
				GheThamDialog.this.dismiss();
			}
		});

	}
}
