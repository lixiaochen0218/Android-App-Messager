package layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.alex.reminder1.MainActivity;
import com.example.alex.reminder1.Message;
import com.example.alex.reminder1.R;


public class FragmentPostnew extends Fragment {

    private int id=0;



    private Message m=null;
    private EditText editText;
    private EditText editTitle;
    private RatingBar mBar;
    private boolean edit=false;
    private View view;
    private Button btnsubmit;
    private Button btndelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

    }

    public void setId(int id){
        this.id=id;
    }
    public void setM(Message m){
        this.m=m;
    }
    public void setEdit(){
        this.edit=true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fragment_postnew, container, false);
        editText=(EditText)view.findViewById(R.id.editText);
       // editText.setHint("Write down anything you want!");
        editTitle=(EditText)view.findViewById(R.id.editTitle);
       // editTitle.setHint("Title");
        mBar = (RatingBar)view.findViewById(R.id.rating_bar);

        System.out.println("fragmentpostnew created _id = [" + id + "]");
        if(m!=null){
            editText.setText(m.getText());
            editTitle.setText(m.getTitle());

            mBar.setRating(m.getStar());
            id=m.getId();
            edit=true;
        }else{
            System.out.println("fragment_Mnull");
        }
        btnsubmit=(Button) view.findViewById(R.id.Finish);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        btndelete=(Button) view.findViewById(R.id.delete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              doDelete();

            }
        });

        return view;
    }

    public void submit(){
        //editText=(EditText)view.findViewById(R.id.editText);
        String text=editText.getText().toString();
        //editTitle=(EditText)view.findViewById(R.id.editTitle);
        String title=editTitle.getText().toString();

        mBar = (RatingBar)view.findViewById(R.id.rating_bar);
        int star=(int) mBar.getRating();

        if(title.trim().equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("fragment submit title:"+title+" text:"+text+" bar:"+star);
        Message m=new Message(id,title,text,star);
        if(edit){
            ((MainActivity) this.getActivity()).updateMessage(m);
        }else {
            ((MainActivity) this.getActivity()).addMessage(m);
        }
        removeData();
    }
    public void doDelete(){
        ((MainActivity)this.getActivity()).deleteMessage(id);
        removeData();
    }

    public void removeData(){
        editText.setText("");
        editTitle.setText("");
        mBar.setRating(3);
        id=((MainActivity)this.getActivity()).nextId();
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        edit=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("fragmentpostnew destory");
    }
}
