package xyz.abhaychauhan.grouppy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.grouppy.utils.NetworkController;
import xyz.abhaychauhan.grouppy.utils.UrlUtils;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

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

    @BindView(R.id.btn_signin_tv)
    TextView signinBtnTv;

    @BindView(R.id.sign_up_layout)
    RelativeLayout signupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        setTypefaceForView();

        signinBtnTv.setOnClickListener(this);
        signupBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
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
        signinBtnTv.setTypeface(typeface);
        signupBtnTv.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin_tv:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return;
        }
    }

    private void registerUser() {
        String fullName = userNameEt.getText().toString().trim();
        String email = userEmailEt.getText().toString().trim();
        String username = userUsernameEt.getText().toString().trim();
        String password = userPasswordEt.getText().toString().trim();
        String confirmPassword = userConfirmPasswordEt.getText().toString().trim();
        if (fullName.equals("") || fullName == null) {
            showSnackbarMessage("Name field required!!");
            return;
        }
        if (email.equals("") || email == null) {
            showSnackbarMessage("Email field required!!");
            return;
        }
        if (username.equals("") || username == null) {
            showSnackbarMessage("Username field required!!");
            return;
        }
        if (password.equals("") || password == null) {
            showSnackbarMessage("Password field required!!");
            return;
        }
        if (confirmPassword.equals("") || confirmPassword == null) {
            showSnackbarMessage("Confirm password field required!!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showSnackbarMessage("Passwords do not match!!");
            return;
        }
        sendRegistrationDataToServer(fullName, email, username, password);
    }

    private void sendRegistrationDataToServer(String fullName, String email, String username,
                                              String password) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", fullName);
            object.put("username", username);
            object.put("email", email);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlUtils.SIGNUP_URL,
                object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fetchDataFromResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Request error");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(request);
    }

    private void fetchDataFromResponse(JSONObject object) {
        if (object.has("status")) {
            Boolean status = object.optBoolean("status");
            if (!status) {
                showSnackbarMessage("Server error!!");
            } else {
                Boolean signup = object.optBoolean("signup");
                if (signup) {
                    showSnackbarMessage("User successfully registered!!");
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    showSnackbarMessage("User already exists!!");
                }
            }
        }
    }

    /**
     * This function display snackbar with {@link String} message passed as a parameter.
     *
     * @param message
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(signupLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
