package com.example.git_post_retrofit_api_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.git_post_retrofit_api_example.network.retrofit.RetroClient;
import com.example.git_post_retrofit_api_example.network.retrofit.RetroInterface;
import com.example.git_post_retrofit_api_example.utils.Utils;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.git_post_retrofit_api_example.network.retrofit.ApisModel.LOGIN_API_URL;
import static com.example.git_post_retrofit_api_example.utils.Constant.COMPANY_ID;
import static com.example.git_post_retrofit_api_example.utils.Constant.KEYID;
import static com.example.git_post_retrofit_api_example.utils.Constant.PASSWORD;
import static com.example.git_post_retrofit_api_example.utils.Constant.USER_ID;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bt_Submit)
    Button submit;

    @BindView(R.id.et_companyId)
    EditText companyId;

    @BindView(R.id.et_userId)
    EditText userId;

    @BindView(R.id.et_password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!companyId.getText().toString().equals("")){
                    if(!userId.getText().toString().equals("")){
                        if(!password.getText().toString().equals("")){
                            if (Utils.isConnectedToInternet(MainActivity.this)) {
                                submitDetails(companyId.getText().toString(),userId.getText().toString(),password.getText().toString());
                            } else {
                                Toast.makeText(MainActivity.this,"Please Check your internet connectivity and try again",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"Please enter Password",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"Please enter User id",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Please enter Company Id",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void submitDetails(String companyId, String userId, String password) {
       try{
           JsonObject queryMapping = new JsonObject();
           queryMapping.addProperty(COMPANY_ID, companyId);
           queryMapping.addProperty(USER_ID, userId);
           queryMapping.addProperty(PASSWORD, password);
           Log.i("---",queryMapping.toString());
           Call<ResponseBody> call = RetroClient.getClient(LOGIN_API_URL).create(RetroInterface.class).LoginTrack(queryMapping);
           call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   try {
                       String bodyString = new String(response.body().bytes(), "UTF-8");
                        dataShowing(bodyString);
                   } catch (Exception e) {
                       Toast.makeText(MainActivity.this,"Login credentials are invalid",Toast.LENGTH_LONG).show();
                       e.printStackTrace();
                   }
               }
               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                   Toast.makeText(MainActivity.this,"Login credentials are invalid",Toast.LENGTH_LONG).show();
               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    private void dataShowing(String bodyString) {
        try {
            JSONObject jsonObject = new JSONObject(bodyString);
            String data = jsonObject.getString("data").replace(" ", "");
            Log.e("result---", data);
            JSONObject jsonObject1 = new JSONObject(data);
            if (!jsonObject1.getString(KEYID).equals("")){
                Intent intent = new Intent(getBaseContext(), EmployeeListActivity.class);
                intent.putExtra(KEYID, jsonObject1.getString(KEYID));
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this,"Your Registration not success ,Please check your details",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
