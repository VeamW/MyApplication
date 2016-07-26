package com.example.apractice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viper02 on 2016/7/26.
 */
public class MyAdapter extends RecyclerView.Adapter {

    public interface  OnItemClickListener {
         void onItemClickListener(View view,int position);

        void onItemLongClickListener(View view, int position);
    }


    private OnItemClickListener mListener;
    public void setOnclickListener(OnItemClickListener  listener){
        mListener = listener;
    }



    public List<String> mdata = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;


    public MyAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        for (int i = 'A'; i <= 'z'; i++) {
            mdata.add((char) i + "");
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Viewholder(mInflater.inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Viewholder) holder).mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener(view, position);
            }
        });
        ((Viewholder) holder).mTextView.setText(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public  class  Viewholder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public Viewholder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.id_textview);
        }
    }
}
