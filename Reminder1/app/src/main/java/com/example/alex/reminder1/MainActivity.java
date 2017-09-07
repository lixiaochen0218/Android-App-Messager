package com.example.alex.reminder1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import layout.FragmentPostnew;
import layout.ListFragment;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Message> listm=new ArrayList<Message>();


    private Integer[] imgArr={
            R.drawable.star1,
            R.drawable.star2,
            R.drawable.star3,
            R.drawable.star4,
            R.drawable.star5};

    private int id=0;


    private static final int POSTNEW_ACTIVITY_RESULT_CODE = 0;
    FragmentManager fragmentManager;
    FragmentPostnew fragment=null;
    ListFragment list_fragment=null;

    Boolean land=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey("listm")){
                listm=(ArrayList<Message>)savedInstanceState.getSerializable("listm");
            }
        }
        System.out.println("MainActivity oncreate");
        System.out.println("screen:"+getResources().getConfiguration().orientation);
        fragmentManager = getFragmentManager();
        if(getResources().getConfiguration().orientation==2){
            System.out.println("landscape ");
            land=true;
        }
        if(getResources().getConfiguration().orientation==1) {
            System.out.println("protrait ");
            land=false;
        }
        fresh();

    }



    public void addNewPostFragment(){
        System.out.println("add fragment");
        removeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new FragmentPostnew();
        fragment.setId(nextId());

        fragmentTransaction.add(R.id.linear,fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("listm",listm);
        super.onSaveInstanceState(savedInstanceState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                if(land){

                    addNewPostFragment();
                }else {
                    Intent toNewPost = new Intent(this, PostNew.class);
                    toNewPost.putExtra("id", nextId());
                    startActivityForResult(toNewPost, POSTNEW_ACTIVITY_RESULT_CODE);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void portraitFresh(){

        System.out.println("portraitFresh listm size = [" +listm.size()+ "]");

        int size=listm.size();
        String[] titlename=new String[size];
        Integer[] imgid=new Integer[size];
        Calendar[] calendars=new Calendar[size];

        for(int i=0;i<listm.size();i++){
            titlename[i]=listm.get(i).getTitle();
            imgid[i]=imgArr[listm.get(i).getStar()-1];
            calendars[i]=listm.get(i).getCalendar();
        }

        CustomListAdapter adapter=new CustomListAdapter(this, titlename, imgid, calendars );
        ListView list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Message m=listm.get(position);
                System.out.println("onclick list");
                Intent toNewPost = new Intent(getApplicationContext(), PostNew.class);
                toNewPost.putExtra("edit", m);
                startActivityForResult(toNewPost, POSTNEW_ACTIVITY_RESULT_CODE);

            }
        });
    }

    private void landscapeFresh() {
        System.out.println("landscapeFresh listm size = [" +listm.size()+ "]");
        removeFragment();
        removelistFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        list_fragment = new ListFragment();
        list_fragment.setlistm(listm);
        fragmentTransaction.add(R.id.linear2,list_fragment);
        fragmentTransaction.commit();

    }

    public int nextId(){
        if(listm.size()==0){
            return 1;
        }else if(listm.size()>0){
            int a=listm.get(listm.size()-1).getId()+1;
            int b=listm.size()+1;
            return Math.max(a,b);
        }else{
            return 0;
        }
    }

    public void addMessage(Message message){
        if(message.getId()==0){
            Toast.makeText(getApplicationContext(), getString(R.string.errorMessage), Toast.LENGTH_SHORT).show();
            fresh();
            return;
        }
        listm.add(message);
        fresh();


        Toast.makeText(getApplicationContext(), getString(R.string.addANewMessage), Toast.LENGTH_SHORT).show();
    }

    public void updateMessage(Message message){
        System.out.println("updateMessage message id="+message.getId());
        for(Message m: listm){
            if(m.getId()==message.getId()){
                System.out.println("updatemethod");
                m.setTitle(message.getTitle());
                m.setText(message.getText());
                m.setStar(message.getStar());
                m.updateTime();
            }
        }
        fresh();
        Toast.makeText(getApplicationContext(), getString(R.string.updateAMessage), Toast.LENGTH_SHORT).show();
    }

    public void deleteMessage(int id){
        if(id==-1||id==0){return;}

        for(Message m: listm){
            if(m.getId()==id){
                listm.remove(m);
                break;
            }
        }
        fresh();
        Toast.makeText(getApplicationContext(), getString(R.string.deleteAMessage), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == POSTNEW_ACTIVITY_RESULT_CODE) {

            if (resultCode == RESULT_OK) {
                Message m = (Message) data.getSerializableExtra("message");
                if(data.getStringExtra("option").equals("edit")){
                    //System.out.println("result update messsage");
                    updateMessage(m);

                }else{
                    //System.out.println("result add messsage");
                    addMessage(m);

                }
            }else if(resultCode == 3){
                //System.out.println("result delete messsage");
                deleteMessage(data.getIntExtra("id",-1));

            }
        }
    }
    public void removeFragment(){
        if(fragment!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public void removelistFragment(){
        if(list_fragment!=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(list_fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void fresh(){
        if(land){
            landscapeFresh();
        }else{
            portraitFresh();
        }
    }
}
