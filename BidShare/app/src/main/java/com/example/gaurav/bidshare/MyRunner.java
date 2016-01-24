package com.example.gaurav.bidshare;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MyRunner extends AuctionWindow implements Runnable {

    private  int choice=0;
    private  int inc_bid=0;
    private boolean logint;
    private String usr=null,pass=null;
    private String FILE_TO_RECEIVED="/sdcard/BidShare";
    public final static int FILE_SIZE = 10022386;
    public String MainS="192.168.43.107";
    public int port=12002;

    public MyRunner(int i){choice=i;}



    public MyRunner(String u,String p,int i)
    {
        usr=u;
        pass=p;
        choice=i;
    }

    public MyRunner(int i,String u,int j){choice=i;usr=u;inc_bid=j;};

    public MyRunner(String i){choice=3;MainS=i;port=13267;FILE_TO_RECEIVED="/sdcard/received_file";}
    @Override
    public void run() {

        try {
            Socket s=new Socket(MainS,port);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            DataInputStream din=new DataInputStream(s.getInputStream());
            System.out.println("My Runner created with choice 4");
            if(choice==1)
            {
                dout.writeInt(1);
                dout.writeUTF(usr);
                dout.writeUTF(pass);

                String returnfromserver = din.readUTF();

                if(returnfromserver.compareTo("true")==0)
                {
                    Log.i("got authenticated", "");
                    logint =true;
                }
                else {
                    Log.i("not authenticated","");
                    //it should be false but it is required to set true to check file browser
                    //of this app which can be used in the beyondbook app
                    logint =false;

                }
                dout.flush();
            }

            else if(choice==2)
            {
                int bytesRead,current;
                dout.writeInt(2);
                dout.flush();
                productname=din.readUTF();
                base_price=din.readInt();
                bidder_name=din.readUTF();
                max_bid=din.readInt();
                filedisc=din.readUTF();
                FileOutputStream fos=null;
                BufferedOutputStream bos=null;
                System.out.println("---------------------");
                System.out.println(productname);
                System.out.println(base_price);
                System.out.println(bidder_name);
                System.out.println(max_bid);
                System.out.println(filedisc);

                byte [] mybytearray  = new byte [FILE_SIZE];
                InputStream is = s.getInputStream();
                fos = new FileOutputStream(FILE_TO_RECEIVED);
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(mybytearray,0,mybytearray.length);
                current = bytesRead;

                do {
                    System.out.println("recieving");
                    bytesRead =
                            is.read(mybytearray, current, (mybytearray.length-current));
                    if(bytesRead >= 0) current += bytesRead;
                } while(bytesRead > -1);

                bos.write(mybytearray, 0 , current);
                bos.flush();
                System.out.println("File " + FILE_TO_RECEIVED
                        + " downloaded (" + current + " bytes read)");
                choice--;
                FILE_TO_RECEIVED+='d';
                return;

            }
            else if (choice==3)
            {
                int bytesRead,current;

                FileOutputStream fos=null;
                BufferedOutputStream bos=null;
                System.out.println("----------inside choice 3-----------");


                byte [] mybytearray  = new byte [FILE_SIZE];
                InputStream is = s.getInputStream();
                fos = new FileOutputStream(FILE_TO_RECEIVED);
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(mybytearray,0,mybytearray.length);
                current = bytesRead;
                int cc=0;

                do {
                    System.out.println("recieving"+current);
                    bytesRead =
                            is.read(mybytearray, current, (mybytearray.length-current));
                    if(bytesRead >= 0) current += bytesRead;

                    bos.write(mybytearray, cc , current);
                    bos.flush();
                    cc=current;

                } while(bytesRead > -1);

               /* bos.write(mybytearray, 0 , current);
                bos.flush();*/
                System.out.println("File " + FILE_TO_RECEIVED
                        + " downloaded (" + current + " bytes read)");
                choice--;
                FILE_TO_RECEIVED+='d';
                return;
            }

            else if(choice==4)
            {
                System.out.println("4 Choice ");
                dout.writeInt(4);
                dout.writeUTF(usr);
                dout.writeInt(inc_bid);

                productname=din.readUTF();
                base_price=din.readInt();
                bidder_name=din.readUTF();
                max_bid=din.readInt();


            }
            logint=true;
            din.close();
            s.close();
            dout.flush();
            dout.close();
        } catch (IOException e) {e.printStackTrace();}

        System.out.println("login from runner : " + logint);
    }

    public boolean getReturn()
    {
        return logint;
    }
}



