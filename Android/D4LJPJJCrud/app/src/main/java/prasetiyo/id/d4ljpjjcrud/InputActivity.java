package prasetiyo.id.d4ljpjjcrud;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import prasetiyo.id.d4ljpjjcrud.models.Mahasiswa;
import prasetiyo.id.d4ljpjjcrud.utils.AsyncResponse;
import prasetiyo.id.d4ljpjjcrud.utils.PerformNetworkRequest;
import prasetiyo.id.d4ljpjjcrud.utils.VARS;

public class InputActivity extends AppCompatActivity{

    private EditText et_name, et_alamat;
    private View mProgressView;
    private View mLoginFormView;
    private RadioGroup radioGroup;
    private Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        et_name = findViewById(R.id.et_name);
        et_alamat = findViewById(R.id.et_alamat);
        radioGroup = findViewById(R.id.radioSex);
        Button btn_simpan = findViewById(R.id.btn_simpan);
        Button btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_simpan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSave();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mahasiswa = getIntent().getParcelableExtra("mahasiswa");

        if (mahasiswa!=null){
            et_name.setText(mahasiswa.getNama());
            et_alamat.setText(mahasiswa.getAlamat());
            switch (mahasiswa.getGender()){
                case "L": ((RadioButton)findViewById(R.id.radioMale)).setChecked(true);break;
                case "P": ((RadioButton)findViewById(R.id.radioFemale)).setChecked(true);
            }
        }
    }

    private void attemptSave() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);

        String name = et_name.getText().toString();
        String alamat = et_alamat.getText().toString();
        String gender = radioButton.getText().toString();

        if (name.isEmpty() || alamat.isEmpty()) {
            Toast.makeText(this, "Data not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("nama", name);
        params.put("alamat", alamat);
        params.put("gender", gender);
        String URL = VARS.API_URL_INSERT;

        if (mahasiswa!=null){
            params.put("id",""+mahasiswa.getId());
            URL=VARS.API_URL_UPDATE;

        }
        showProgress(true);
        new PerformNetworkRequest(URL,
                params,
                PerformNetworkRequest.POST,
                new AsyncResponse() {
                    @Override
                    public void onFinished(JSONObject result) throws JSONException {
                        showProgress(false);
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailed(JSONObject response) {

                    }
                }).execute();
    }

    /**
     * Shows the progress UI and hides the form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

