package prasetiyo.id.d4ljpjjcrud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import prasetiyo.id.d4ljpjjcrud.adapters.MahasiswaAdapter;
import prasetiyo.id.d4ljpjjcrud.models.Mahasiswa;
import prasetiyo.id.d4ljpjjcrud.utils.AsyncResponse;
import prasetiyo.id.d4ljpjjcrud.utils.PerformNetworkRequest;
import prasetiyo.id.d4ljpjjcrud.utils.RecyclerViewClickListener;
import prasetiyo.id.d4ljpjjcrud.utils.VARS;

public class MainActivity extends AppCompatActivity implements AsyncResponse, RecyclerViewClickListener {
    private MahasiswaAdapter mahasiswaAdapter;
    private List<Mahasiswa> mahasiswaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(i);
            }
        });

        RecyclerView mRecyclerView;
        mRecyclerView = findViewById(R.id.my_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mahasiswaAdapter = new MahasiswaAdapter(this, mahasiswaList);
        mRecyclerView.setAdapter(mahasiswaAdapter);

        mRecyclerView.setAdapter(mahasiswaAdapter);
    }

    private void getData() {
        mahasiswaList.clear();
        PerformNetworkRequest request = new PerformNetworkRequest(VARS.API_URL_GET, PerformNetworkRequest.GET, this);
        request.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reload) {
            getData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinished(JSONObject result) {
        try {
            JSONArray mhs = result.getJSONArray("mahasiswa");

            for (int i = 0; i < mhs.length(); i++) {
                JSONObject obj = mhs.getJSONObject(i);
                mahasiswaList.add(new Mahasiswa(obj.getInt("id"),
                                                obj.getString("nama"),
                                                obj.getString("alamat"),
                                                obj.getString("gender")));
            }
            mahasiswaAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailed(JSONObject response) throws JSONException {
        Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getData();
    }

    @Override
    public void onItemClickListener(final Mahasiswa mahasiswa) {
        final CharSequence[] items = {"Edit", "Delete", "Cancel"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Action");
        dialog.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int position) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(),InputActivity.class);
                        i.putExtra("mahasiswa",mahasiswa);
                        startActivity(i);
                        break;
                    case 1:
                        new PerformNetworkRequest(VARS.API_URL_DELETE+mahasiswa.getId(), PerformNetworkRequest.GET, new AsyncResponse() {
                            @Override
                            public void onFinished(JSONObject output) {
                                getData();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailed(JSONObject response) {

                            }
                        }).execute();
                        break;
                    default:
                        dialog.dismiss();
                }
            }

        });
        AlertDialog alert = dialog.create();
        alert.show();
    }
}
