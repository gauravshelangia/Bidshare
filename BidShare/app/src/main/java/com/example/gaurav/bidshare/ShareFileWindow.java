package com.example.gaurav.bidshare;

import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class ShareFileWindow extends ActionBarActivity {

    private static final int REQUEST_PICK_FILE = 1;
    public String file;
    private File selectedFile;
    public TextView filePath; //= (EditText)findViewById(R.id.file_path);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share_file_window);
        filePath = (TextView)findViewById(R.id.file_path);
        Intent intent2=getIntent();
        Button SEND = (Button) findViewById(R.id.send);
        Button RECEIVE = (Button) findViewById(R.id.receive);

        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartServer();
            }
        });


        RECEIVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Receive1();
            }

        });
    }

    public void BrowseFile(View view){
        Intent intentBrowse = new Intent(this,FilePicker.class);
        startActivityForResult(intentBrowse, REQUEST_PICK_FILE);;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            switch(requestCode) {

                case REQUEST_PICK_FILE:

                    if(data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));
                        filePath.setText(selectedFile.getPath());
                        file = filePath.getText().toString();
                        System.out.println("FILE PATH IS"+file);
                    }
                    break;
            }
        }
    }

    public void StartServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Todo Here we have to put server code
                int SOCKET_PORT = 13267;  // you may change this
                String FILE_TO_SEND = file;  // you may change this
                System.out.println("----------------" + FILE_TO_SEND);
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                OutputStream os = null;
                //servsock = null;
                //sock = null;

                try {
                    ServerSocket servsock = new ServerSocket(SOCKET_PORT);

                    System.out.println("Waiting...");

                    Socket sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    // send file
                    File myFile = new File(FILE_TO_SEND);
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    os = sock.getOutputStream();
                    System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray, 0, mybytearray.length);

                    System.out.println("Done.");


                } catch (NetworkOnMainThreadException e) {
                    System.out.println("Bhosadike do it outside");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
    public void Receive1()
    {
        EditText IP =(EditText)findViewById(R.id.ipaddress);  // localhost
        String SERVER =  IP.getText().toString();
        System.out.println("Accepted connection :--------- " + SERVER);
        MyRunner myRunner=new MyRunner(SERVER);
        Thread th = new Thread(myRunner);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void Receive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int SOCKET_PORT = 13267;      // you may change this
                EditText IP =(EditText)findViewById(R.id.ipaddress);  // localhost
                String SERVER =  IP.getText().toString();
                System.out.println("Accepted connection :--------- " + SERVER);
                String FILE_TO_RECEIVED = "/sdcard/RECEIVED";  // you may change this, I give a
                // different name because i don't want to
                // overwrite the one used by server...

                int FILE_SIZE = 40022386; // file size temporary hard coded
                // should bigger than the file to be downloaded
                int bytesRead;
                int current = 0;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;

                try {
                    Socket sockclient = new Socket(SERVER, SOCKET_PORT);
                    System.out.println("Connecting...");

                    // receive file
                    byte[] mybytearray = new byte[FILE_SIZE];
                    InputStream is = sockclient.getInputStream();
                    fos = new FileOutputStream(FILE_TO_RECEIVED);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;

                    do {
                        System.out.println("recieving");
                        bytesRead =
                                is.read(mybytearray, current, (mybytearray.length - current));
                        if (bytesRead >= 0) current += bytesRead;
                    } while (bytesRead > -1);

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    System.out.println("File " + FILE_TO_RECEIVED
                            + " Recieved (" + current + " bytes read)");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_file_window, menu);
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
}
