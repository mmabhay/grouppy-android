package xyz.abhaychauhan.grouppy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.grouppy_title_tv)
    TextView grouppyTitleTv;

    @BindView(R.id.login_title_tv)
    TextView loginTitleTv;

    @BindView(R.id.user_email_et)
    EditText userEmailEt;

    @BindView(R.id.user_password_et)
    EditText userPasswordEt;

    @BindView(R.id.btn_signin_tv)
    TextView signInBtnTv;

    @BindView(R.id.btn_cancel_tv)
    TextView cancelBtnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        loginTitleTv.setTypeface(typeface);
        signInBtnTv.setTypeface(typeface);
        userEmailEt.setTypeface(typeface);
        userPasswordEt.setTypeface(typeface);
    }

}
