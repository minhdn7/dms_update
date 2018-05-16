package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import android.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PopupAdapterMaps implements InfoWindowAdapter {
	 LayoutInflater inflater=null;
	 
	  void PopupAdapter(LayoutInflater inflater) {
	    this.inflater=inflater;
	  }

	  @Override
	  public View getInfoWindow(Marker marker) {
	    return(null);
	  }

	  @Override
	  public View getInfoContents(Marker marker) {
	    View popup=inflater.inflate(R.layout.list_content, null);

	    TextView tv=(TextView)popup.findViewById(R.id.title);	    
	    tv.setText(marker.getTitle());
	    
	    //TextView tv1 = (TextView) popup.findViewById(R.id.content);
	    //tv.setText(marker.getSnippet());

	    return(popup);
	  }
}
