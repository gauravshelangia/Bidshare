package com.example.gaurav.bidshare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AfterLoginWindow extends ActionBarActivity {


    //private Button ShareFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_window);
       /* ShareFile = (Button)findViewById(R.id.ShareFile);
        View.OnClickListener SF = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentsharefile = new Intent(getBaseContext(), ShareFileWindow.class);
                startActivity(intentsharefile);
            }
        };
        ShareFile.setOnClickListener(SF);*/
    }

    public void sharefile(View v){
        Intent intentsharefile = new Intent(this, ShareFileWindow.class);
        startActivity(intentsharefile);
    }
    public void auction(View vi)
    {
        Intent intents = new Intent(this, AuctionWindow.class);
        intents.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(intents);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_after_login_window, menu);
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
