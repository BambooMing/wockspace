package csm.scme.org.music.animation;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by admin on 2017/6/12.
 */

public class ViewPagerTowAnimation implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.9F;

    /**
     * Apply a property transformation to the given page.
     *
     * @param page     Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     */
    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scale);
        } else {
            page.setScaleY(MIN_SCALE);
        }
    }
}
