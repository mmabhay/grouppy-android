package xyz.abhaychauhan.grouppy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkActivity extends AppCompatActivity {

    @BindView(R.id.grouppy_title_tv)
    TextView grouppyTitleTv;

    @BindView(R.id.btn_cancel_tv)
    TextView cancelBtnTv;

    @BindView(R.id.btn_next_tv)
    TextView nextBtnTv;

    @BindView(R.id.grouppy_network_what_tv)
    TextView grouppyWhatTv;

    @BindView(R.id.network_name_et)
    EditText networkNameEt;

    private Typeface typeface = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);
        setTypefaceForView();
    }

    /**
     * This function set font family for views.
     */
    private void setTypefaceForView() {
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/Chewy.ttf");
        grouppyTitleTv.setTypeface(typeface);
        cancelBtnTv.setTypeface(typeface);
        nextBtnTv.setTypeface(typeface);
        grouppyWhatTv.setTypeface(typeface);
        networkNameEt.setTypeface(typeface);
    }
}
