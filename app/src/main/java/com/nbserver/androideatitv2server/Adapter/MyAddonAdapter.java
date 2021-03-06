package com.nbserver.androideatitv2server.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nbserver.androideatitv2server.Callback.IRecyclerClickListener;
import com.nbserver.androideatitv2server.EventBus.SelectedAddonModel;
import com.nbserver.androideatitv2server.EventBus.UpdateAddonModel;
import com.nbserver.androideatitv2server.Model.AddonModel;
import com.nbserver.androideatitv2server.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyAddonAdapter extends RecyclerView.Adapter<MyAddonAdapter.MyViewHolder> {

    Context context;
    List<AddonModel> addonModels;
    UpdateAddonModel updateAddonModel;
    int editPos;

    public MyAddonAdapter(Context context, List<AddonModel> addonModels) {
        this.context = context;
        this.addonModels = addonModels;
        updateAddonModel = new UpdateAddonModel();
        editPos=-1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_size_addon_display, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_name.setText(addonModels.get(position).getName());
        holder.txt_price.setText(String.valueOf(addonModels.get(position).getPrice()));

        holder.img_delete.setOnClickListener(v -> {
            //Delete Item
            addonModels.remove(position);
            notifyItemRemoved(position);
            updateAddonModel.setAddonModel(addonModels); // Set for Event
            EventBus.getDefault().postSticky(updateAddonModel); //Send for Event
        });

        holder.setListener(new IRecyclerClickListener() {
            @Override
            public void onItemClickListener(View view, int pos) {
                editPos = position;
                EventBus.getDefault().postSticky(new SelectedAddonModel(addonModels.get(pos)));
            }
        });


    }

    @Override
    public int getItemCount() {
        return addonModels.size();
    }

    public void addNewAddon(AddonModel addonModel) {
        addonModels.add(addonModel);
        notifyItemInserted(addonModels.size()-1);
        updateAddonModel.setAddonModel(addonModels);
        EventBus.getDefault().postSticky(updateAddonModel);
    }

    public void editAddon(AddonModel addonModel) {
        if(editPos!=-1)
        {
            addonModels.set(editPos, addonModel);
            notifyItemChanged(editPos);
            editPos=-1; //Reset variable after success
            //Send update
            updateAddonModel.setAddonModel(addonModels);
            EventBus.getDefault().postSticky(updateAddonModel);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.txt_price)
        TextView txt_price;
        @BindView(R.id.img_delete)
        ImageView img_delete;

        Unbinder unbinder;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view ->
                    listener.onItemClickListener(view, getAdapterPosition()));

        }
    }
}

