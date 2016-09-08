package com.daimajia.swipedemo.adapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipedemo.Contact;
import com.daimajia.swipedemo.R;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    private final static int PICK_CONTACT = 1;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        LinearLayout upperLayout;
        TextView textViewData;
        Button buttonDelete;
        ImageView imagesms;
        ImageView imagephone;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            upperLayout = (LinearLayout) itemView.findViewById(R.id.upperLayout);
            textViewData = (TextView) itemView.findViewById(R.id.text_data);
            buttonDelete = (Button) itemView.findViewById(R.id.delete);
            imagesms = (ImageView) itemView.findViewById(R.id.sms);
            imagephone = (ImageView) itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                    Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Context mContext;
    private ArrayList<String> mDataset;
    private ArrayList<Contact> mData;

    //protected SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    public RecyclerViewAdapter(Context context, ArrayList<Contact> objects) {
        this.mContext = context;
        this.mData = objects;

        String str = "OBJECT";
        for(int i = 0; i<objects.size(); i++){
            str+="["+objects.get(i).getFirstName()+", "+objects.get(i).getLastName()+", "+objects.get(i).getPhoneNumber()+", "+objects.get(i).getId()+"]\n";
        }
        str += "mDATA";
        for(int i = 0; i<mData.size(); i++){
            str+="["+mData.get(i).getFirstName()+", "+mData.get(i).getLastName()+", "+mData.get(i).getPhoneNumber()+", "+mData.get(i).getId()+"]\n";
        }

        Log.v("STRING",str);

        ArrayList <String> noms = new ArrayList<String>();
        for (int i=0; i<objects.size();++i){
            noms.add(objects.get(i).getFirstName());
        }
        this.mDataset = noms;

        //Log.v("mDataset : ",mDataset.toString());
        //Log.v("mData : ",mData.toString());


    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }









    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        String item = mDataset.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {


            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        /*
        Double click on each element of the list
         */
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
        /*
        Click on the sms image to send a sms
         */
        viewHolder.imagesms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Message sent " + position, Toast.LENGTH_SHORT).show();
                Uri phoneNumber = Uri.parse("sms:"+mData.get(position).getPhoneNumber());
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, phoneNumber);
                view.getContext().startActivity(smsIntent);
            }
        });
        /*
        Click on the phone image to call
         */
        viewHolder.imagephone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "Phone call "+position, Toast.LENGTH_SHORT).show();
                Uri phoneNumber = Uri.parse("tel:"+mData.get(position).getPhoneNumber());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNumber);
                view.getContext().startActivity(callIntent);

            }
        });

        /*
        Long press click to get access to the profile of the contact
         */

        viewHolder.swipeLayout.setOnLongClickListener(new SwipeLayout.LongClickListener() {

            public void onLongPress(View view) {

                // Code pour aller vers un contact en particulier
                // MAIS CE CODE NE FONCTIONNE PAS (ENCORE !)
                /*
                Intent goContactIntent = new Intent(Intent.ACTION_VIEW);
                String contactID = mData.get(position).getId();
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactID);
                Toast.makeText(mContext,"uri : "+uri, Toast.LENGTH_LONG).show();
                goContactIntent.setData(uri);
                view.getContext().startActivity(goContactIntent);
                */


                // Code pour aller vers contact
                // Mais pas pour aller sur un profil en particulier

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity"));
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.addCategory("android.intent.category.DEFAULT");
                String contactID = mData.get(position).getId();
                Uri uri = Uri.parse("content://contacts?"+String.valueOf(contactID));
                Toast.makeText(mContext,"uri : "+uri, Toast.LENGTH_LONG).show();
                intent.setData(uri);
                view.getContext().startActivity(intent);

            }
        });


        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                mDataset.remove(position);
                mData.remove(position);
                notifyItemRangeChanged(position, mDataset.size());
                notifyDatasetChanged();
                mItemManger.closeAllItems();


            }
        });
        viewHolder.textViewData.setText(item);
        mItemManger.bind(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}


