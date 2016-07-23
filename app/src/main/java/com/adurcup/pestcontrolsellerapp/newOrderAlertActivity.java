package com.adurcup.pestcontrolsellerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshivang on 20/07/16.
 * This activity is for Alert Notification with alert sound
 */
public class newOrderAlertActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_alert);

        final List<Commitment> commitments = new ArrayList<>();
        Intent onIntent = getIntent();

        int c_size = onIntent.getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).size();
        for (int i = 0; i < c_size; i++){
            Commitment commitment = (Commitment) onIntent.
                    getParcelableArrayListExtra(Constant.KEY_COMMITMENTS).get(i); commitments.add(commitment);
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
                Commitment commitment = (Commitment)getItem(i);

                if (view == null){
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
