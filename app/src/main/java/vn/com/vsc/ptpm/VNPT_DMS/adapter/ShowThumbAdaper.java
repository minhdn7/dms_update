package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ImageZoom;

public class ShowThumbAdaper extends PagerAdapter {

	Context mContext;
	List<HangHoaEntity> mList;

	public ShowThumbAdaper(Context c, List<HangHoaEntity> list) {
		// TODO Auto-generated constructor stub
		this.mContext = c;
		this.mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public Object instantiateItem(View collection, int position) {
		ImageZoom iv = new ImageZoom(mContext);
		Picasso.with(mContext)
		.load(API_code.URL_API_ROOT + mList.get(position).getUrl())
		.into(iv);
		((ViewPager) collection).addView(iv);
		return iv;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}
