package com.example.alex.reminder1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class PostNew extends AppCompatActivity {

    private int id;
    private EditText editText;
    private EditText editTitle;
    private RatingBar mBar;
    private boolean edit=false;
    private Button btnsubmit;
    private Button btndelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.newMessage);

        Message m = (Message) getIntent().getSerializableExtra("edit");
        id = getIntent().getIntExtra("id",0);
        if(m!=null){
            System.out.println(m.getTitle()+m.getText()+m.getStar());
            editText=(EditText)findViewById(R.id.editText);
            editText.setText(m.getText());
            editTitle=(EditText)findViewById(R.id.editTitle);
            editTitle.setText(m.getTitle());
            mBar = (RatingBar) findViewById(R.id.rating_bar);
            mBar.setRating(m.getStar());
            id=m.getId();
            edit=true;
        }else{
            System.out.println("null");
        }
        btnsubmit=(Button) findViewById(R.id.Finish);
        btndelete=(Button) findViewById(R.id.delete);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDelete();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.postnewmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void submit(){
        editText=(EditText)findViewById(R.id.editText);
        String text=editText.getText().toString();
        editTitle=(EditText)findViewById(R.id.editTitle);
        String title=editTitle.getText().toString();
        mBar = (RatingBar) findViewById(R.id.rating_bar);
        int star=(int) mBar.getRating();
        if(title.trim().equals("")){
            Toast.makeText(getApplicationContext(), "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Message m=new Message(id,title,text,star);

        Intent saveToMain = new Intent();
        if(edit){
            saveToMain.putExtra("option","edit");
        }else{
            saveToMain.putExtra("option","new");
        }
        saveToMain.putExtra("message",m);
        setResult(RESULT_OK, saveToMain);
        //calender();
        finish();
    }
    public void doDelete(){
        Intent saveToMain = new Intent();
        saveToMain.putExtra("id",id);
        setResult(3, saveToMain);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

//    public void calender(){
//        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
//        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
//
//        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
//                datePicker.getMonth(),
//                datePicker.getDayOfMonth(),
//                timePicker.getHour(),
//                timePicker.getMinute());
//
//        //Long time = calendar.getTimeInMillis();
//        System.out.println("Time: "+String.format("%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp", calendar));
//    }
}
