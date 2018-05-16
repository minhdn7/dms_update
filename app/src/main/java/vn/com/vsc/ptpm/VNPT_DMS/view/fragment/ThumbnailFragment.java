package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.ShowThumbAdaper;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;

@SuppressLint("ValidFragment")
public class ThumbnailFragment extends DialogFragment {

    ViewPager vp;
    TextView tv;
    List<HangHoaEntity> mList;
    int pos;
    int widthScreen, heightScreen;

    public ThumbnailFragment() {
    }

    public ThumbnailFragment(List<HangHoaEntity> list, int position) {
        this.mList = list;
        this.pos = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog mDialog = new Dialog(getActivity());
        mDialog.getWindow().setLayout(350, 350);
        return mDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thumbnail, container);
        vp = (ViewPager) view.findViewById(R.id.vp_Thumbnail);
        tv = (TextView) view.findViewById(R.id.tv_posThumbnail);
        ShowThumbAdaper adapter = new ShowThumbAdaper(getActivity(), mList);
        vp.setAdapter(adapter);
        vp.setCurrentItem(pos);
        tv.setText("Vuốt ngang để xem hình tiếp theo " + (pos + 1) + "/"
                + mList.size());
        vp.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                int pos = arg0 + 1;
                tv.setText("Vuốt ngang để xem hình tiếp theo " + pos + "/"
                        + mList.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        return view;
    }
}
