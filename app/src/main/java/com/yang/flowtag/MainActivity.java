package com.yang.flowtag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Fragment_1 fragment_1;
    private Fragment_2 fragment_2;

    private LinearLayout F_1, F_2;

    private boolean b_f1=false;
    private boolean b_f2=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        F_1 = (LinearLayout) findViewById(R.id.layout_1);
        F_2 = (LinearLayout) findViewById(R.id.layout_2);

        F_1.setOnClickListener(this);
        F_2.setOnClickListener(this);
        click_1();
    }



    //监听点击的来源
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.layout_1:
                click_1();
                break;

            case R.id.layout_2:
                click_2();
                break;
        }
    }


    private void click_1() {

        fragment_1 = new Fragment_1();

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();//开启一个事务

//        if(b_f1==false){
            fragmentTransaction.replace(R.id.frame_content,fragment_1);
//            b_f1=true;
//        }else {
//            fragmentTransaction.hide(fragment_2);//使用另一个Fragment替换当前的，实际上就是remove()然后add()的合体
//            fragmentTransaction.show(fragment_1);
//        }
        fragmentTransaction.commit();//提交一个事务

        F_1.setSelected(true);
        F_1.setSelected(true);

        F_2.setSelected(false);
        F_2.setSelected(false);

    }

    private void click_2() {

        fragment_2 = new Fragment_2();

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
//        if(b_f2==false){
            fragmentTransaction.replace(R.id.frame_content,fragment_2);
//            b_f2=true;
//        }else {
//            fragmentTransaction.hide(fragment_1);//使用另一个Fragment替换当前的，实际上就是remove()然后add()的合体
//            fragmentTransaction.show(fragment_2);
//        }
        fragmentTransaction.commit();

        F_1.setSelected(false);
        F_1.setSelected(false);

        F_2.setSelected(true);
        F_2.setSelected(true);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    List<String> getResultData=data.getStringArrayListExtra("tag");
                }
                break;
            default:
        }
    }
}
