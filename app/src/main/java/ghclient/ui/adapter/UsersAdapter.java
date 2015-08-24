package ghclient.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import ghclient.R;
import ghclient.model.User;
import ghclient.ui.listeners.RecyclerClickListener;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ItemViewHolder> {

    private final RecyclerClickListener recyclerListener;
    private final List<User> arrUsers;
    private Context context;

    public UsersAdapter(List<User> arrUsers, Context context, RecyclerClickListener recyclerClickListener) {

        this.arrUsers = arrUsers;
        this.context = context;
        this.recyclerListener = recyclerClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(
                R.layout.item_user, parent, false);

        return new ItemViewHolder(rootView, recyclerListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.bindItem(arrUsers.get(position),recyclerListener);
    }

    @Override
    public int getItemCount() {

        return arrUsers.size();
    }

    public void addItem(User user) {
        arrUsers.add(user);
    }

    public User getItem(int location) {
       return arrUsers.get(location);
    }

    public void removeItems() {
        arrUsers.removeAll(arrUsers);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView mTitle;

        @Bind(R.id.iv_avatar)
        ImageView mAvatar;

        @Bind(R.id.iv_url)
        ImageView mUrl;

        public ItemViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindItem(User user, RecyclerClickListener recyclerClickListener1) {

            if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {

                Glide.with(context)
                        .load(user.getAvatarUrl())
                        .centerCrop()
                        .placeholder(R.drawable.images_holder)
                        .crossFade()
                        .into(mAvatar);
            }

            if (user.getName() != null) {
                mTitle.setText(user.getName());
            } else if (user.getLogin() != null) {
                mTitle.setText(user.getLogin());
            }


            mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        recyclerClickListener1.onItemClick(user);
                }
            });


            mUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        recyclerClickListener1.onItemClickUrl(user);
                }
            });

        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     if(v instanceof TextView)
                     recyclerClickListener.onItemClick(arrUsers.get(ItemViewHolder.this.getLayoutPosition()));
                 }
             });
        }
    }
}
