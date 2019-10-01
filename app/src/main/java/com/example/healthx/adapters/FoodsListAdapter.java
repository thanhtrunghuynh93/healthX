package com.example.healthx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthx.R;
import com.example.healthx.app.AppController;
import com.example.healthx.database.DatabaseHelper;
import com.example.healthx.interfaces.ClickCallBack;
import com.example.healthx.models.FoodListModel;

import java.util.List;

/**
 * THis is receylerview's adapter class. that is used for listing to show data.
 */
public class FoodsListAdapter extends RecyclerView.Adapter<FoodsListAdapter.MyViewHolder> {

    private List<FoodListModel.ResultBean> resultBean;

    private Context mContext;
    ClickCallBack clickCallBack;
    public FoodsListAdapter(Context context, ClickCallBack clickCallBack, List<FoodListModel.ResultBean> vehicleList) {
        this.mContext = context;
        this.clickCallBack = clickCallBack;
        this.resultBean = vehicleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_foods_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        FoodListModel.ResultBean model= resultBean.get(position);
        holder.tvMealType.setText(model.getMeal());
        holder.tvFood.setText(model.getFood());
        holder.tvTime.setText(model.getTime());


    }


    @Override
    public int getItemCount() {
        if (resultBean != null)
            return resultBean.size();
        else
            return 0;
    }

    public void deleteItem(int position) {
        DatabaseHelper db = DatabaseHelper.getInstance(AppController.getCurrentActivity(), null, null);
        db.softDeleteRecord(resultBean.get(position).getId());
        resultBean.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMealType,tvFood,tvTime;
        CoordinatorLayout parent_layout;
        public MyViewHolder(View _view) {
            super(_view);

            tvMealType = _view.findViewById(R.id.tvMealType);
            tvFood = _view.findViewById(R.id.tvFood);
            tvTime = _view.findViewById(R.id.tvTime);
            parent_layout = _view.findViewById(R.id.rvParent_layout);
            _view.setOnClickListener(v ->
                    clickCallBack.callBack(resultBean.get(getLayoutPosition()), getLayoutPosition()));

            _view.setOnLongClickListener(v -> false);


        }

    }


}