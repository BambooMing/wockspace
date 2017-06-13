package csm.scme.org.music.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import csm.scme.org.music.R;

/**
 * Created by admin on 2017/6/5.
 */

public class MenuItemAdapter  extends BaseAdapter{


    private final Context context;
    private ArrayList<String> list;

    public MenuItemAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<String> list){
        if(list!=null){
            this.list = list;
        }
    }

    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if(view ==null){
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
            vh.tv = (TextView) view.findViewById(R.id.tv);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
    String name = list.get(i);
        vh.tv.setText(name);

        return view;
    }

    class ViewHolder{
    TextView tv;
    }
}
