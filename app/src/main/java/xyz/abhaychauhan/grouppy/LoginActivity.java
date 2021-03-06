package xyz.abhaychauhan.grouppy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

    @BindView(R.id.btn_signup_tv)
    TextView signupBtnTv;

    @BindView(R.id.signin_layout)
    RelativeLayout signInLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setTypefaceForView();

        signInBtnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signupBtnTv.setOnClickListener(this);
    }

    /**
     * This function set font family for views.
     */
    private void setTypefaceForView() {
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/Chewy.ttf");
        grouppyTitleTv.setTypeface(typeface);
        signupBtnTv.setTypeface(typeface);
        loginTitleTv.setTypeface(typeface);
        signInBtnTv.setTypeface(typeface);
        userEmailEt.setTypeface(typeface);
        userPasswordEt.setTypeface(typeface);
    }

    private void loginUser() {
        String email = userEmailEt.getText().toString().trim();
        String password = userPasswordEt.getText().toString().trim();
        if (email.equals("") || email == null) {
            showSnackbarMessage("Email field required!!");
            return;
        }
        if (password.equals("") || password == null) {
            showSnackbarMessage("Password field required!!");
            return;
        }
        sendLoginDataToServer(email, password);
    }

    private void sendLoginDataToServer(String email, String password) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", email);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlUtils.LOGIN_URL,
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
                Boolean login = object.optBoolean("login");
                if (login) {
                    String token = object.optString("token");

                    // Storing token
                    SharedPreferences prefs = this.getSharedPreferences("SharedPref",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("token", token);
                    editor.commit();

                    showSnackbarMessage("User successfully loggedin");
                    Intent intent = new Intent(this, NetworkActivity.class);
                    startActivity(intent);
                } else {
                    showSnackbarMessage("Invalid credentials");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup_tv:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                return;
        }
    }

    /**
     * This function display snackbar with {@link String} message passed as a parameter.
     *
     * @param message
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(signInLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
