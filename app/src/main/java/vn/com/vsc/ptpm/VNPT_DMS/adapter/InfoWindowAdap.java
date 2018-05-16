package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

public class InfoWindowAdap implements InfoWindowAdapter {
	private ConvertFont conv = new ConvertFont();
	private Activity context;
	private HashMap<Marker, KhachHang> kh;

	public InfoWindowAdap(Activity context, HashMap<Marker, KhachHang> kh) {
		this.context = context;
		this.kh = kh;
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// Getting view from the layout file info_window_layout
		View v = this.context.getLayoutInflater().inflate(
				R.layout.info_window_layout, null);
		LatLng latLng = arg0.getPosition();
		TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
		TextView tv_MaKH = (TextView) v.findViewById(R.id.tv_MaKH);
		TextView tv_Ten = (TextView) v.findViewById(R.id.tv_Ten);
		TextView tv_Dc = (TextView) v.findViewById(R.id.tv_Dc);
		tv_MaKH.setText(conv.getUTF8StringFromNCR(kh.get(arg0).getCode()
				.toString()));
		tv_Ten.setText(conv.getUTF8StringFromNCR(kh.get(arg0).getName()
				.toString()));
		tv_Dc.setText(conv.getUTF8StringFromNCR(kh.get(arg0).getAddress()
				.toString()));
		return v;
	}

	@Override
	public View getInfoWindow(Marker arg0) {

		return null;
	}

}