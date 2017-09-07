package layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alex.reminder1.CustomListAdapter;
import com.example.alex.reminder1.Message;
import com.example.alex.reminder1.R;

import java.util.ArrayList;
import java.util.Calendar;


public class ListFragment extends Fragment {

    private ArrayList<Message> listm=new ArrayList<Message>();


    private Integer[] imgArr={
            R.drawable.star1,
            R.drawable.star2,
            R.drawable.star3,
            R.drawable.star4,
            R.drawable.star5};

    private int id=0;
    private View view;
    private FragmentPostnew fragment=null;
    private FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentManager=getFragmentManager();
        if(getResources().getConfiguration().orientation==1) {
            System.out.println("protrait chech in ListFragment");
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
            return view;
        }
        view=inflater.inflate(R.layout.fragment_list, container, false);
        System.out.println("list fragment oncreate");

        creatlist();
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("onConfigurationChanged");
    }

    public void setlistm(ArrayList<Message> listm){
        this.listm=listm;
    }

    public void creatlist(){
        int size=listm.size();
        String[] titlename=new String[size];
        Integer[] imgid=new Integer[size];
        Calendar[] calendars=new Calendar[size];

        for(int i=0;i<listm.size();i++){
            titlename[i]=listm.get(i).getTitle();
            imgid[i]=imgArr[listm.get(i).getStar()-1];
            calendars[i]=listm.get(i).getCalendar();
        }




        CustomListAdapter adapter=new CustomListAdapter(getActivity(), titlename, imgid, calendars );
        ListView list=(ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Message m=listm.get(position);
                if(true){
                    System.out.println("onclick fragment list");
                    removeFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new FragmentPostnew();
                    fragment.setM(m);
                    fragmentTransaction.add(R.id.linear,fragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    public void removeFragment(){
        if(fragment!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
