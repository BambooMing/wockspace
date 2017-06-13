package csm.scme.org.music.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csm.scme.org.music.R;
import csm.scme.org.music.animation.ViewPagerAnimation;
import csm.scme.org.music.fragment.RecommendFragment;

public class NewMusicFragment extends Fragment{

    private View view;
    private ViewPager viewPge;
    private TabLayout tab;
    private int page = 0;
    private Context context;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPge.setCurrentItem(page);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.activity_new_music_fragment,null);
        initView();
        return view;
    }

    private void initView(){
        viewPge = (ViewPager)view.findViewById(R.id.new_music_vp);
        tab = (TabLayout)view.findViewById(R.id.tab);

        //表示切换界面不会重新加载（不会重新调用onCreate）
        viewPge.setOffscreenPageLimit(2);
        viewPge.setPageTransformer(true,new ViewPagerAnimation());
        tab.setTabTextColors(Color.parseColor("#ff6600"),Color.parseColor("#ff6600"));
        tab.setSelectedTabIndicatorColor(Color.parseColor("#ff6600"));
        tab.setupWithViewPager(viewPge);

        NewMusicAdapter adapter = new NewMusicAdapter(getChildFragmentManager());
        adapter.addFragment(new RecommendFragment(),"新曲");
        adapter.addFragment(new RecommendFragment(),"推荐");
        adapter.addFragment(new RecommendFragment(),"排行榜");
        viewPge.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public class NewMusicAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fList = new ArrayList<>();
        private ArrayList<String> sList = new ArrayList<>();

        public void addFragment(Fragment fragment,String  title){
            fList.add(fragment);
            sList.add(title);
        }


        public NewMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fList.get(position);
        }

        @Override
        public int getCount() {
            return fList.size();
        }
    }
}
