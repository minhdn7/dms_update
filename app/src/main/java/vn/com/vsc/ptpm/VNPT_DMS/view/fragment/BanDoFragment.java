package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import vn.com.vsc.ptpm.VNPT_DMS.R;

public class BanDoFragment extends Fragment {

	public BanDoFragment() {
	}

	@Override
	public String toString() {
		return "bando_fragment";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_bando, container,
				false);
		FragmentManager fragMnger = getFragmentManager();
		final Fragment fragment = fragMnger
				.findFragmentById(R.layout.fragment_bando);
		if (fragment != null && fragment.isVisible()) {
			Log.i("TAG", "my fragment is visible");
			
		} else {
			Log.i("TAG", "my fragment is not visible");
		}
		// getActivity().setTitle("Your title");
//		((MainActivity) getActivity()).getActionBar().setDisplayHomeAsUpEnabled(false);
		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);// them option
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.bando_fragment_item, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.newAction:
			Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
			break;
		case android.R.id.home:
			// this takes the user 'back', as if they pressed the left-facing
			// triangle icon on the main android toolbar.
			// if this doesn't work as desired, another possibility is to call
			// `finish()` here.
			getActivity().onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
