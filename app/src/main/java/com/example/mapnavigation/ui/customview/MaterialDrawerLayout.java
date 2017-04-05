package com.example.mapnavigation.ui.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.mapnavigation.R;

/**
 * Created by zzg on 17-4-2.
 * 功能：这个自定义布局的主要目的是将material-menu、DrawerLayout封装到一起，并且实现DrawerLayout
 * 中menu的业务逻辑。
 */

public class MaterialDrawerLayout extends DrawerLayout {

    // 该布局所在的Activity的上下文环境
    private Context mContext;

    // 侧滑栏中的listView组件
    //@BindView(R.id.listView_drawer)
    public ListView mDrawerListView;

    // 用来标记导航栏是否打开
    private boolean isDrawerOpened;

    // 导航抽屉的监听器
    private ActionBarDrawerToggle mDarwerToggle;

    // 导航按钮的图标
    private MaterialMenuDrawable mMaterialMenuDrawable;


    // -------------------构造函数--------------------
    public MaterialDrawerLayout(Context context) {
        this(context, null);
    }

    public MaterialDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }
    // -------------------构造函数--------------------

    /**
     * 初始化侧栏标签,并且为ListView Item的点击事件设置监听器
     */
    private void initDrawerList(){

        mDrawerListView = (ListView)findViewById(R.id.listView_drawer);
        // 从strings.xml中获取数据
        String [] items = getResources().getStringArray(R.array.drawer_menu);

        // 生成适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mContext, R.layout.drawer_list_item, items);

        // 为ListView设置适配器
        mDrawerListView.setAdapter(adapter);

        // 为ListView Item设置监听器
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    /**
     * 对导航抽屉列表条目点击的处理
     * @param position
     */
    private void selectItem(int position){
        // add your code
        this.closeDrawer(mDrawerListView);
    }


    /**
     * 将DrawerLayout和Toolbar绑定
     * @param toolbar
     */
    public void bindToolbar(Toolbar toolbar){

        // 设置toolbar左上角的导航按钮图标
        mMaterialMenuDrawable = new MaterialMenuDrawable(
                mContext, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(mMaterialMenuDrawable);

        // 初始化导航抽屉列表
        initDrawerList();

        // 为导航抽屉的打开关闭事件设置监听
        setListener(toolbar);
    }

    /**
     * 为toolbar上的导航按钮设置监听
     * @param toolbar
     */
    private void setListener(Toolbar toolbar){
        mDarwerToggle = new ActionBarDrawerToggle(
                (Activity) mContext, this, toolbar, R.string.drawer_open, R.string.drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                isDrawerOpened = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            public void onDrawerStateChanged(int newState) {
                if(newState == DrawerLayout.STATE_IDLE){
                    if (isDrawerOpened){
                        mMaterialMenuDrawable.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    }else{
                        mMaterialMenuDrawable.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                mMaterialMenuDrawable.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset);
            }
        };


        this.setDrawerListener(mDarwerToggle);
    }
}
