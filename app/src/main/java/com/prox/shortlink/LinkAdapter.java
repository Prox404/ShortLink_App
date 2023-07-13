package com.prox.shortlink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {
    private Context context;
    private List<Link> linkList;

    public LinkAdapter(Context context, List<Link> linkList) {
        this.context = context;
        this.linkList = linkList;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = linkList.get(position);
        holder.tvShortLink.setText(link.getShortLink());
        holder.tvLink.setText(link.getDomainName());

        // Xử lý sự kiện khi nút chỉnh sửa được click
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Xử lý khi nút chỉnh sửa được click
            }
        });

        // Xử lý sự kiện khi nút xoá được click
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Xử lý khi nút xoá được click
            }
        });
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder {
        private TextView tvShortLink, tvLink;
        private Button btnEdit, btnDelete;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShortLink = itemView.findViewById(R.id.tvShortLink);
            tvLink = itemView.findViewById(R.id.tvLink);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
