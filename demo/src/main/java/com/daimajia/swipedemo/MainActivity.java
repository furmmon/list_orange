package com.daimajia.swipedemo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.swipe.util.Attributes;
import com.daimajia.swipedemo.adapter.RecyclerViewAdapter;
import com.daimajia.swipedemo.adapter.util.DividerItemDecoration;
import com.daimajia.swipedemo.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class MainActivity extends Activity {

    /**
     * RecyclerView: The new recycler view replaces the list view. Its more modular and therefore we
     * must implement some of the functionality ourselves and attach it to our recyclerview.
     * <p/>
     * 1) Position items on the screen: This is done with LayoutManagers
     * 2) Animate & Decorate views: This is done with ItemAnimators & ItemDecorators
     * 3) Handle any touch events apart from scrolling: This is now done in our adapter's ViewHolder
     */

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<Contact> mDataSet;

    // Les contacts issus de la base de données du téléphone
    private ArrayList<Contact> contactsFromPhoneDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //TODO : Version ??
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Liste des contacts");
            }
        }

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());


        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);

        // Lancement de la recherche des changements dans les contacts

        // Update de la BdD de l'application

        ChangesDetector.updateApplicationDatabase(this.getApplicationContext());

        // Adapter:

        //Generate data
        Generator gn = new Generator(getApplicationContext());
        mDataSet = gn.getList1();


        mAdapter = new RecyclerViewAdapter(this, mDataSet);
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);





    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };




}