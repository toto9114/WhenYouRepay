package rnd.plani.co.kr.whenyourepay.DutchPay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.whenyourepay.R;

/**
 * Created by RND on 2016-07-04.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {

    public OnDelButtonClickListener onDelButtonClickListener;
    public void setOnDelButtonClickListener(OnDelButtonClickListener listener){
        onDelButtonClickListener = listener;
    }

    TextView borrowerName;

    public PersonViewHolder(View itemView) {
        super(itemView);
        borrowerName = (TextView)itemView.findViewById(R.id.text_borrower);
        ImageView btn = (ImageView)itemView.findViewById(R.id.image_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDelButtonClickListener!= null){
                    onDelButtonClickListener.onDelButtonClick(v,getAdapterPosition());
                }
            }
        });
    }
    public void setName(String name){
        borrowerName.setText(name);
    }
}
