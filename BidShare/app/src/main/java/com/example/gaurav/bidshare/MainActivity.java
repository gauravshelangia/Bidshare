package com.example.gaurav.bidshare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {



    private boolean login=true;
    public TextView textview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(),FilePicker.class));
        textview1 = (TextView)findViewById(R.id.forgetpasword);
        OnClickListener cl=new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent verifyIntent = new Intent(getBaseContext(),ForgetPasswordWindow.class);
                startActivity(verifyIntent);
            }
        };
        textview1.setOnClickListener(cl);
    }

    Intent intent1=getIntent();

    public void Login(View v){
        String usr = null, pass = null;
        EditText et, et1;

        try {
            et = (EditText) findViewById(R.id.username);
            usr = et.getText().toString();
            Log.i(usr, "username");

            et1 = (EditText) findViewById(R.id.password);
            pass = et1.getText().toString();
            Log.i(pass, "password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        MyRunner myRunner=new MyRunner(usr,pass,1);
        Thread th = new Thread(myRunner);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        login=myRunner.getReturn();
        System.out.println("Login: from client "+login);
        //login=true;
        login=true;
        if(login==true)
        {
            Toast.makeText(this, "Welcome: " + usr, Toast.LENGTH_LONG).show();
            myRunner=null;
            Intent intentLogin=new Intent(this,AfterLoginWindow.class);
            intentLogin.putExtra("name",usr);
            startActivity(intentLogin);
        }
        else
        {
            Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_LONG).show();
        }

    }

    public void SignUp(View view){
        Intent intentSignUp = new Intent(this,SignUpWindow.class);
        startActivity(intentSignUp);
    }

    //public void ForgetPassword(View view){
      //  Intent intentForgetPassword = new Intent(this,ForgetPasswordWindow.class);
   // }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
