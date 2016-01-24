package com.example.gaurav.bidshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class AuctionWindow extends ActionBarActivity{
   // public String p=null;
    public String productname=null;
    public int base_price=0;
    public String bidder_name=null;
    public int max_bid=0;
    public String filedisc=null;
    private String user=null;
    public int inc_bid;
    boolean logintt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_window);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/


        MyRunner myRunner=new MyRunner(2);
        Thread th=new Thread(myRunner);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("32--------");
        System.out.println(myRunner.productname);
        System.out.println(myRunner.base_price);
        System.out.println(myRunner.bidder_name);
        System.out.println(myRunner.max_bid);
        System.out.println(myRunner.filedisc);

        user=getIntent().getStringExtra("name" );

        productname=myRunner.productname;
        base_price=myRunner.base_price;
        bidder_name=myRunner.bidder_name;
        max_bid=myRunner.max_bid;
        filedisc=myRunner.filedisc;



        File imgFile = new File("/sdcard/BidShare");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.itemimage);
            myImage.setImageBitmap(myBitmap);
        }


        Button Place = (Button) findViewById(R.id.bid);
        Place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceBid();
            }
        });


        TextView ITEM = (TextView) findViewById(R.id.item);
        ITEM.setText(productname);


        TextView DESC = (TextView) findViewById(R.id.desc);
       DESC.setText(filedisc);

        TextView MAXBID = (TextView) findViewById(R.id.maxbid);
        MAXBID.setText("Max bid : " +max_bid);

        TextView BASEBID = (TextView) findViewById(R.id.basebid);
        BASEBID.setText("Base bid : " +base_price);


        TextView BIDDER = (TextView) findViewById(R.id.bidder);
        BIDDER.setText("Max Bidder : "+bidder_name);

    }

    // things to be done after clicking on place bid
    public void PlaceBid() {
        EditText et = (EditText) findViewById(R.id.bidamount);
        String p=et.getText().toString();

        try
        {
            inc_bid= Integer.parseInt(p);
        }
        catch (NumberFormatException e)
        {
            // handle the exception
        }
        MyRunner myRunner2 = new MyRunner(4, user, inc_bid);
        Thread th2 = new Thread(myRunner2);
        th2.start();
        try {
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------");
        bidder_name=myRunner2.bidder_name;
        max_bid=myRunner2.max_bid;

        TextView MAXBID = (TextView) findViewById(R.id.maxbid);
        MAXBID.setText("Max bid : " +max_bid);

        TextView BIDDER = (TextView) findViewById(R.id.bidder);
        BIDDER.setText("Max Bidder : "+bidder_name);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auction_window, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
   /* public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_auction_window, container, false);
            return rootView;
        }
    }*/
}
