package bertanha.com.br.giftlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import smarthome.bertanha.com.br.smarthome.R;
import smarthome.bertanha.com.br.smarthome.model.Device;

/**
 * Created by berta on 11/7/2017.
 */

public class ListaPresentesAdapter extends FirebaseRecyclerAdapter<Device, DeviceAdapter.DeviceViewHolder> {

    /**
     * Device for each doorbell entry
     */
    public static class DeviceViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "DeviceViewHolder";

        final TextView name;
        final Button action;
        public DeviceViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.device_name);
            this.action = (Button) itemView.findViewById(R.id.device_action);


        }
    }

    private static final String TAG = "DeviceAdapter";
    private Context context;

    public ListaPresentesAdapter(Context context, DatabaseReference ref) {
        super(Device.class, R.layout.device_item, DeviceViewHolder.class, ref);

        context = context.getApplicationContext();
    }

    @Override
    protected void populateViewHolder(DeviceViewHolder viewHolder, final Device model, final int position) {
        viewHolder.name.setText(model.getName());

        viewHolder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + "CLICKED");
                getRef(position).child("value").setValue(model.getValue() == 0 ? 1 : 0);
            }
        });
    }
}
