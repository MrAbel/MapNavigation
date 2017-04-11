package com.example.mapnavigation.ui.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzg on 17-4-9.
 */

public class SearchBar extends AutoCompleteTextView {

    private Context mContext;
    private Drawable mSearch;
    private Drawable mVoice;
    // -------------------构造函数----------------------
    public SearchBar(Context context) {
        super(context);
        init(context);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    // -------------------构造函数----------------------


    @SuppressWarnings("deprecation")
    private void init(Context context){
        mContext = context;
        //drawables = getCompoundDrawables();
        mSearch = getCompoundDrawables()[0];
        mVoice = getCompoundDrawables()[2];

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.performClick();

        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            int xDown = (int) event.getX();
            // 点击了搜索按钮
            if (xDown > 0 && xDown < getCompoundPaddingLeft()) {
                ToastUtils.showShort("search");
            }
            // 点击了语音按钮
            if (xDown >= (getWidth() - getCompoundPaddingRight())
                    && xDown < getWidth()) {
                ToastUtils.showShort("voice");
            }
        }
        return super.onTouchEvent(event);
    }
/*
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        String newText = text.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, "长沙");
        Inputtips inputTips = new Inputtips(mContext, inputquery);
        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                    List<String> listString = new ArrayList<String>();
                    for (int j = 0; j < list.size(); j++) {
                        listString.add(list.get(j).getName());
                    }
                    ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                            MapApplication.getContext(),
                            R.layout.drawer_list_item, listString);
                    mSearchBar.setAdapter(aAdapter);
                    aAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
        inputTips.requestInputtipsAsyn();
    }
    */
}

/*
<com.example.mapnavigation.ui.customview.SearchBar
        android:id="@+id/id_editText_search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="10dp"
        android:layout_marginEnd="22dp"
        android:layout_marginStart="22dp"
        android:layout_marginTop="13dp"
        android:alpha="0.8"
        android:drawablePadding="5dp"
        android:dropDownVerticalOffset="1dp"
        android:elevation="5dp"
        android:hint="@string/hint_search"
        android:inputType="text|textAutoComplete"
        android:paddingBottom="6dp"
        android:paddingEnd="10dp"
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        android:paddingStart="10dp"
        android:paddingTop="6dp"
        android:singleLine="true"
        android:textSize="14sp" />
*/
