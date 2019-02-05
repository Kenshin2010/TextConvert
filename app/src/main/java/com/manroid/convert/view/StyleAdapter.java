package com.manroid.convert.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manroid.convert.R;
import com.manroid.convert.utils.ClipboardManager;

import java.util.ArrayList;

public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.ViewHolder> {
    private final Activity context;
    private LayoutInflater inflater;
    private ArrayList<String> mList = new ArrayList<>();

    public StyleAdapter(Activity context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void add(String arg) {
        mList.add(arg);
        notifyItemInserted(mList.size() - 1);
    }

    public void setData(ArrayList<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.style_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String str = mList.get(position);
        holder.textView.setText(Html.fromHtml(mList.get(position)));
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, str);
                intent.setType("text/plain");
                context.startActivity(intent);
            }
        });
        holder.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager.setClipboard(context, str);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View imgCopy, imgShare;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            imgCopy = itemView.findViewById(R.id.img_copy);
            imgShare = itemView.findViewById(R.id.img_share);
        }
    }
}