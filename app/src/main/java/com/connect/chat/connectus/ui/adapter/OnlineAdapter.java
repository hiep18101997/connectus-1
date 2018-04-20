package com.connect.chat.connectus.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.connect.chat.connectus.R;
import com.connect.chat.connectus.model.ItemChat;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ItemOnline> {
    private ArrayList<ItemChat> listChat;
    private Context mContext;

    public OnlineAdapter(ArrayList<ItemChat> listChat, Context mContext) {
        this.listChat = listChat;
        this.mContext = mContext;
    }

    @Override
    public ItemOnline onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(mContext).inflate(R.layout.item_online,parent,false);
        return new ItemOnline(rootView);
    }

    @Override
    public void onBindViewHolder(ItemOnline holder, int position) {
        holder.tvName.setText(listChat.get(position).getName().toString());
        holder.tvContent.setText(listChat.get(position).getContent().toString());
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    public class ItemOnline extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        public ItemOnline(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

    }
}
