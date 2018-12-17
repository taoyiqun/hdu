package com.example.hdutyq.renzheng;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private List<Msg> mMsgList;
    static  class ViewHolder    extends RecyclerView.ViewHolder{
        LinearLayout leftloyout;
        LinearLayout    rightout;
        TextView leftMsg;
        TextView    rightMsg;
        public ViewHolder(View view){
            super(view);
            leftloyout=view.findViewById(R.id.left_layout);
            rightout=view.findViewById(R.id.right_layout);
            leftMsg=view.findViewById(R.id.left_msg);
            rightMsg=view.findViewById(R.id.right_msg);
        }
    }
    public MsgAdapter(List<Msg> msgList){
        mMsgList=msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View    view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_item,viewGroup,false);
        ViewHolder  viewHolder= new ViewHolder(view);
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg=mMsgList.get(position);
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftloyout.setVisibility(View.VISIBLE);
            holder.rightout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else  if(msg.getType()==Msg.TYPE_SENT){
            holder.leftloyout.setVisibility(View.GONE);
            holder.rightout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {

        return mMsgList.size();
    }
}
