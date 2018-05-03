package com.connect.chat.connectus.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.model.ItemOffline;

public class OffLineAdapter extends RecyclerView.Adapter<OffLineAdapter.ItemOff> {

    private iCom iCom;

    public OffLineAdapter(OffLineAdapter.iCom iCom) {
        this.iCom = iCom;
    }

    @Override
    public ItemOff onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemOff(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemOff holder, int position) {

        ItemOffline itemOffline = iCom.getItem(position);

        holder.txtName.setText(itemOffline.getName());
        holder.txtMac.setText(itemOffline.getMac());

        switch (itemOffline.getStatus()) {

            case BluetoothDevice.BOND_BONDED:
                holder.txtStatus.setText("Paired");
                break;

            case BluetoothDevice.BOND_BONDING:
                holder.txtStatus.setText("Pairing");
                break;

            case BluetoothDevice.BOND_NONE:
                holder.txtStatus.setText("Unknown");
                break;

            default:
        }

    }

    @Override
    public int getItemCount() {
        return iCom.getCount();
    }

    public class ItemOff extends RecyclerView.ViewHolder {

        private TextView txtName, txtMac, txtStatus;

        public ItemOff(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name_device);
            txtMac = itemView.findViewById(R.id.txt_mac_item);
            txtStatus = itemView.findViewById(R.id.txt_item_status);

        }
    }

    public interface iCom {

        int getCount();

        ItemOffline getItem(int position);

    }

}
