package com.adurcup.pestcontrolsellerapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshivang on 20/07/16.
 * This activity is for Alert Notification with alert sound
 */
public class newOrderAlertActivity extends AppCompatActivity{

    AnimationDrawable arrowAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final List<Commitment> commitments = new ArrayList<>();

        setContentView(R.layout.activity_new_order_alert);
        Intent onIntent = getIntent();
        if (onIntent.getParcelableArrayListExtra(Constant.KEY_COMMITMENTS) != null) {
            int c_size = onIntent.getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).size();

            Commitment service = (Commitment) onIntent.
                    getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).get(0);

            ((TextView) findViewById(R.id.date_tv)).setText(service.getDate());
            ((TextView) findViewById(R.id.time_tv)).setText(service.getTime());
            ((TextView) findViewById(R.id.area_tv)).setText(service.getArea());
            ((TextView) findViewById(R.id.location_tv)).setText(service.getLocation());
            ((TextView) findViewById(R.id.address_line_1_tv)).setText(service.getAddressLine1());
            ((TextView) findViewById(R.id.address_line_2_tv)).setText(service.getAddressLine2());
            ((TextView) findViewById(R.id.outstanding_date_tv)).setText(service.getDate());


            for (int i = 1; i < c_size; i++) {
                Commitment commitment = (Commitment) onIntent.
                        getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).get(i);
                commitments.add(commitment);
            }

            SeekBar acceptSeekBar = (SeekBar) findViewById(R.id.accept_sb);

            final int thumbWidth = acceptSeekBar.getThumb().getIntrinsicWidth();

            acceptSeekBar.setOnTouchListener(new View.OnTouchListener() {
                private boolean isInvalidMove;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            return isInvalidMove = motionEvent.getX() > thumbWidth;
                        case MotionEvent.ACTION_MOVE:
                            return isInvalidMove;
                        case MotionEvent.ACTION_UP:
                            return isInvalidMove;
                    }
                    return false;
                }
            });

            arrowAnimation = (AnimationDrawable)acceptSeekBar.getThumb();
            arrowAnimation.start();


            acceptSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (seekBar.getProgress() < 100) {
                        ObjectAnimator anim = ObjectAnimator.ofInt(seekBar, "progress", 0);
                        anim.setInterpolator(new AccelerateDecelerateInterpolator());
                        anim.setDuration(getResources().
                                getInteger(android.R.integer.config_shortAnimTime));
                        anim.start();
                    } else {
                        Toast.makeText(newOrderAlertActivity.this,
                                "Unlocked", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    arrowAnimation.stop();
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    findViewById(R.id.accept_tv).setAlpha(1f - progress * 0.02f);
                    if(progress == 0){
                        arrowAnimation.start();
                    }
                }
            });


            ListView listView = (ListView) findViewById(R.id.engagement_lv);
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return commitments.size();
                }

                @Override
                public Object getItem(int i) {
                    return commitments.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    ViewHolder viewHolder;
                    Commitment commitment = (Commitment) getItem(i);

                    if (view == null) {
                        view = LayoutInflater.from(newOrderAlertActivity.this).
                                inflate(R.layout.commitment_list, viewGroup, false);
                        viewHolder = new ViewHolder(view);
                        view.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) view.getTag();
                    }

                    viewHolder.time.setText(commitment.getTime());
                    viewHolder.location.setText(commitment.getLocation());
                    viewHolder.addressLine1.setText(commitment.getAddressLine1());
                    viewHolder.addressLine2.setText(commitment.getAddressLine2());

                    return view;
                }
            });
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent onIntent) {
        super.onNewIntent(onIntent);

        final List<Commitment> commitments = new ArrayList<>();

        if (onIntent.getParcelableArrayListExtra(Constant.KEY_COMMITMENTS) != null) {
            int c_size = onIntent.getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).size();

            Commitment service = (Commitment) onIntent.
                    getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).get(0);

            ((TextView) findViewById(R.id.date_tv)).setText(service.getDate());
            ((TextView) findViewById(R.id.time_tv)).setText(service.getTime());
            ((TextView) findViewById(R.id.area_tv)).setText(service.getArea());
            ((TextView) findViewById(R.id.location_tv)).setText(service.getLocation());
            ((TextView) findViewById(R.id.address_line_1_tv)).setText(service.getAddressLine1());
            ((TextView) findViewById(R.id.address_line_2_tv)).setText(service.getAddressLine2());
            ((TextView) findViewById(R.id.outstanding_date_tv)).setText(service.getDate());

            for (int i = 1; i < c_size; i++) {
                Commitment commitment = (Commitment) onIntent.
                        getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).get(i);
                commitments.add(commitment);
            }

            ListView listView = (ListView) findViewById(R.id.engagement_lv);
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return commitments.size();
                }

                @Override
                public Object getItem(int i) {
                    return commitments.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    ViewHolder viewHolder;
                    Commitment commitment = (Commitment) getItem(i);

                    if (view == null) {
                        view = LayoutInflater.from(newOrderAlertActivity.this).
                                inflate(R.layout.commitment_list, viewGroup, false);
                        viewHolder = new ViewHolder(view);
                        view.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) view.getTag();
                    }

                    viewHolder.time.setText(commitment.getTime());
                    viewHolder.location.setText(commitment.getLocation());
                    viewHolder.addressLine1.setText(commitment.getAddressLine1());
                    viewHolder.addressLine2.setText(commitment.getAddressLine2());

                    return view;
                }
            });
        }
    }

    private class ViewHolder {
        protected TextView time, location, addressLine1, addressLine2;

        public ViewHolder(View view) {
            this.time = (TextView) view.findViewById(R.id.time_tv);
            this.location = (TextView) view.findViewById(R.id.location_tv);
            this.addressLine1 = (TextView) view.findViewById(R.id.address_line_1_tv);
            this.addressLine2 = (TextView) view.findViewById(R.id. address_line_2_tv);
        }
    }

}
