package csm.scme.org.music.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import csm.scme.org.music.R;
import csm.scme.org.music.animation.ViewPagerTowAnimation;

/**
 * 轮播图VIewPager
 */

public class CustomCarouselPager extends FrameLayout {
    //轮播图图片数量
    private final static int IMAGE_COUNT = 7;
    //自动轮播时间间隔
    private final static int TIME_INTERVAL = 3;
    private ScheduledExecutorService scheduledExecutorService;
    private ArrayList<View> imageViewList = new ArrayList<>();
    private ArrayList<ImageView> IVList = new ArrayList<>();
    private int[] imageStr;
    private ViewPager viewpager;
    private int currenTiem = 0;
    private boolean isAutoPlay = true;
    private CarViewPagerAdapter adapter;

    public CustomCarouselPager(Context context) {
        super(context);
        initView(context);
        if (isAutoPlay) {
            startCarousel();
        }
    }

    public CustomCarouselPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        if (isAutoPlay) {
            startCarousel();
        }
    }

    public CustomCarouselPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        if (isAutoPlay) {
            startCarousel();
        }
    }

    private void initView(Context context) {
        imageStr = new int[]{
                R.mipmap.first,
                R.mipmap.second,
                R.mipmap.third,
                R.mipmap.fourth,
                R.mipmap.five,
                R.mipmap.six,
                R.mipmap.seven
        };


        for (int i = 0; i < imageStr.length; i++) {
            ImageView iv = new ImageView(context);
            iv.setBackgroundResource(imageStr[i]);
            IVList.add(iv);
        }
        LayoutInflater.from(context).inflate(R.layout.carousel_viewpager, this, true);

        viewpager = (ViewPager) findViewById(R.id.car_vp);
        viewpager.setFocusable(true);
        imageViewList.add(findViewById(R.id.v_dot1));
        imageViewList.add(findViewById(R.id.v_dot2));
        imageViewList.add(findViewById(R.id.v_dot3));
        imageViewList.add(findViewById(R.id.v_dot4));
        imageViewList.add(findViewById(R.id.v_dot5));
        imageViewList.add(findViewById(R.id.v_dot6));
        imageViewList.add(findViewById(R.id.v_dot7));

        adapter = new CarViewPagerAdapter();
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new MyPageChange());
        viewpager.setPageTransformer(false,new ViewPagerTowAnimation());
        currenTiem = getStartSelectItem();
        //设置当前页为Integer.MAX_VALUE / 2
        viewpager.setCurrentItem(currenTiem);
    }

    private int getStartSelectItem() {
        // 我们设置当前选中的位置为Integer.MAX_VALUE / 2,这样开始就能往左滑动
        // 但是要保证这个值与getRealPosition 的 余数为0，因为要从第一页开始显示
        int currentItem = Integer.MAX_VALUE / 2;
        if (currentItem % getRealCount() == 0) {
            return currentItem;
        }
        // 直到找到从0开始的位置
        while (currentItem % getRealCount() != 0) {
            currentItem++;
        }
        return currentItem;
    }

    /**
     * 获取真实的Count
     *
     * @return
     */
    private int getRealCount() {
        return IVList == null ? 0 : IVList.size();
    }

    public class CarViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int mPosition = position % getRealCount();
            if (mPosition < 0) {
                mPosition = IVList.size() + mPosition;
            }
            ImageView view = IVList.get(mPosition);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public class MyPageChange implements ViewPager.OnPageChangeListener {


        private boolean isOnCilck = false;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currenTiem = position;//记住当前页，不然自动轮播累加会出错
            position = position % getRealCount();
            for (int i = 0; i < imageViewList.size(); i++) {
                if (i == position) {
                    imageViewList.get(i).setBackgroundResource(R.mipmap.red_point);
                } else {
                    imageViewList.get(i).setBackgroundResource(R.mipmap.grey_point);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 1:
                    isOnCilck= true;
                    stopCarousel();
                    break;
                case 2:
                    break;
                case 0:
                    //防止无线启用轮播图服务
                    if(isOnCilck){
                        startCarousel();
                        isOnCilck = false;
                    }
                    break;
            }
        }

    }

    /**
     * 开始轮播
     */
    private void startCarousel() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new LooviewTask(), 0, TIME_INTERVAL, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播
     */
    private void stopCarousel() {
        scheduledExecutorService.shutdown();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    class LooviewTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewpager) {
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewpager.setCurrentItem(currenTiem);
            currenTiem++;
        }
    };
}
