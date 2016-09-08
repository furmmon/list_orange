package com.daimajia.swipedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private ArrayList<String> mDatasetphone;

    //protected SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    public RecyclerViewAdapter(Context context, ArrayList<Contact> objects) {
        this.mContext = context;
        ArrayList <String> noms = new ArrayList<String>();
        ArrayList <String> phones = new ArrayList<String>();
        for (int i=0; i<objects.size();++i){
            noms.add(objects.get(i).getFirstName());
            phones.add(objects.get(i).getPhoneNumber());

        }
        this.mDataset = noms;
        this.mDatasetphone = phones;

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
                Uri phoneNumber = Uri.parse("sms:"+mDatasetphone.get(position));
                Intent smsIntent = new Intent(Intent.ACTION_SEND, phoneNumber);
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
                Uri phoneNumber = Uri.parse("tel:"+mDatasetphone.get(position));
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNumber);
                view.getContext().startActivity(callIntent);

            }
        });

        /*
        Long press click to get access to the profile of the contact
         */

        viewHolder.swipeLayout.setOnLongClickListener(new SwipeLayout.LongClickListener() {

            public void onLongPress(SwipeLayout layout, boolean surface) {
                //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                //this.getContext().startActivityForResult(intent, PICK_CONTACT);
                Toast.makeText(mContext,"Long click "+position, Toast.LENGTH_LONG).show();
            }
        });


        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                mDataset.remove(position);
                mDatasetphone.remove(position);
                notifyItemRangeChanged(position, mDataset.size());
                notifyDatasetChanged();
                mItemManger.closeAllItems();


                //mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                //notifyItemRemoved(position);

                //mDataset.remove(position);

                //notifyItemRangeChanged(position, mDataset.size());

                //mItemManger.closeAllItems();
                //Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();

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


