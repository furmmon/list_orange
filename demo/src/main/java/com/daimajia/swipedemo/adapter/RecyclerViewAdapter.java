package com.daimajia.swipedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipedemo.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewPos;
        TextView textViewData;
        Button buttonDelete;
        ImageView imagesms;
        ImageView imagephone;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            textViewPos = (TextView) itemView.findViewById(R.id.position);
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

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter(Context context, ArrayList<String> objects) {
        this.mContext = context;
        this.mDataset = objects;
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
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick"+position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.imagesms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Message sent "+position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.imagephone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /*Toast.makeText(mContext, "Phone call "+position, Toast.LENGTH_SHORT).show();
                Uri phoneNumber = Uri.parse("tel:0606060606");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNumber);
                startActivity(callIntent);
                */
                Uri telephone = Uri.parse("tel:0606060606");
                Intent secondeActivite = new Intent(Intent.ACTION_DIAL, telephone);
                view.getContext().startActivity(secondeActivite);

            }
        });


        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "Want to delete? " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();

                /*System.out.println("mDataset :");
                System.out.println(mDataset.toString());
                System.out.println("position : ");
                System.out.println(position);
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mDataset.remove(position);
                System.out.println("notifyItemRemovedPosition");
                notifyItemRemoved(position);
                //notifyItemRangeChanged(position, mDataset.size());
                System.out.println("mDataset2 :");
                System.out.println(mDataset.toString());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                System.out.println("After notifyItemRemovedPosition");*/


                //mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mDataset.remove(position);
                notifyItemRemoved(position);

                notifyItemRangeChanged(position, mDataset.size());

                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.textViewPos.setText((position + 1) + ".");
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
