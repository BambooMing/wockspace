package csm.scme.org.music;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import csm.scme.org.music.adapter.MenuItemAdapter;
import csm.scme.org.music.animation.ViewPagerAnimation;
import csm.scme.org.music.fragment.MusicMainFragment;
import csm.scme.org.music.fragment.NewMusicFragment;
import csm.scme.org.music.view.MyViewPager;

public class MainActivity extends BaseActivity {


    private MyViewPager viewpage;
    private ListView listView;
    private ArrayList<String> list;
    private DrawerLayout dl;
    private ImageView bar_music, bar_net, bar_search, bar_menu;
    private Context context;
    private ArrayList<ImageView> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
        setViewPage();
        setMenuLetf();
    }

    private void initView() {
        viewpage = (MyViewPager) findViewById(R.id.viewpage);
        listView = (ListView) findViewById(R.id.listView);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        bar_music = (ImageView) findViewById(R.id.bar_music);
        bar_net = (ImageView) findViewById(R.id.bar_net);
        bar_search = (ImageView) findViewById(R.id.bar_search);
        bar_menu = (ImageView) findViewById(R.id.bar_menu);
        viewpage.setPageTransformer(true,new ViewPagerAnimation());
        imageList.add(bar_net);
        imageList.add(bar_music);

        bar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setViewPage() {
        final MusicMainFragment musicFragment = new MusicMainFragment();
        final NewMusicFragment newFragment = new NewMusicFragment();
        ViewPageAdapter vAdapter = new ViewPageAdapter(getSupportFragmentManager());
        vAdapter.addFragment(newFragment);
        vAdapter.addFragment(musicFragment);
        viewpage.setAdapter(vAdapter);
        viewpage.setCurrentItem(0);
        bar_net.setSelected(true);
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelete(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bar_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpage.setCurrentItem(1);
            }
        });
        bar_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpage.setCurrentItem(0);
            }
        });
    }

    private void setSelete(int position) {
        for (int i = 0; i < imageList.size(); i++) {
            ImageView iv = imageList.get(i);
            if (position == i) {
                iv.setSelected(true);
            } else {
                iv.setSelected(false);
            }

        }
    }

    public class ViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> listFragment = new ArrayList<>();

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            listFragment.add(fragment);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }
    }

    private void setMenuLetf() {
        list = new ArrayList<>();
        list.add("夜间模式");
        list.add("换肤");
        list.add("定时关闭音乐");
        list.add("退出");
        LayoutInflater from = LayoutInflater.from(this);
        View inflate = from.inflate(R.layout.menu_header, listView, false);
        listView.addHeaderView(inflate);
        MenuItemAdapter adapter = new MenuItemAdapter(this);
        adapter.setList(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 1:
                        dl.closeDrawers();
                        break;
                    case 2:
                        dl.closeDrawers();
                        break;
                    case 3:
                        dl.closeDrawers();
                        break;
                    case 4:
                        finish();
                        break;
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //Menu icon
                dl.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
