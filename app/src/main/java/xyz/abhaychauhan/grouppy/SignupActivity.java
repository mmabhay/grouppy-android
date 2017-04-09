package xyz.abhaychauhan.grouppy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.grouppy_title_tv)
    TextView grouppyTitleTv;

    @BindView(R.id.singup_title_tv)
    TextView signupTitleTv;

    @BindView(R.id.user_name_et)
    EditText userNameEt;

    @BindView(R.id.user_email_et)
    EditText userEmailEt;

    @BindView(R.id.user_username_et)
    EditText userUsernameEt;

    @BindView(R.id.user_password_et)
    EditText userPasswordEt;

    @BindView(R.id.user_confirm_password_et)
    EditText userConfirmPasswordEt;

    @BindView(R.id.btn_signup_tv)
    TextView signupBtnTv;

    @BindView(R.id.btn_cancel_tv)
    TextView cancelBtnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
        signupTitleTv.setTypeface(typeface);
        userNameEt.setTypeface(typeface);
        userEmailEt.setTypeface(typeface);
        userUsernameEt.setTypeface(typeface);
        userPasswordEt.setTypeface(typeface);
        userConfirmPasswordEt.setTypeface(typeface);
        cancelBtnTv.setTypeface(typeface);
        signupBtnTv.setTypeface(typeface);
    }
}
