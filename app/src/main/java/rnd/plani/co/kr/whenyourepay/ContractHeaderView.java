package rnd.plani.co.kr.whenyourepay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by RND on 2016-07-14.
 */
public class ContractHeaderView extends RecyclerView.ViewHolder {

    public interface OnContractHeaderClickListener{
        public void OnContractHeaderClick();
    }

    public OnContractHeaderClickListener contractHeaderClickListener;
    public void setOnOverDueHeaderClickListener(OnContractHeaderClickListener listener){
        contractHeaderClickListener = listener;
    }
    TextView contractView;
    public ContractHeaderView(View itemView) {
        super(itemView);
        contractView = (TextView)itemView.findViewById(R.id.text_contract);
        Button btn = (Button)itemView.findViewById(R.id.btn_complete);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contractHeaderClickListener!= null) {
                    contractHeaderClickListener.OnContractHeaderClick();
                }
            }
        });
    }
    public void setContractView(int count){
        contractView.setText("거래내역(진행중 " + count + "건)");
    }
}