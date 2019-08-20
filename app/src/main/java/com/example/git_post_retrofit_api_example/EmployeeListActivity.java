package com.example.git_post_retrofit_api_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.git_post_retrofit_api_example.adapter.EmployeeListAdapter;
import com.example.git_post_retrofit_api_example.network.retrofit.RetroClient;
import com.example.git_post_retrofit_api_example.network.retrofit.RetroInterface;
import com.example.git_post_retrofit_api_example.pojo.EmployeeDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.git_post_retrofit_api_example.network.retrofit.ApisModel.EMPLOYEE_LIST_API_URL;
import static com.example.git_post_retrofit_api_example.utils.Constant.DESIGNATION;
import static com.example.git_post_retrofit_api_example.utils.Constant.FULL_NAME;
import static com.example.git_post_retrofit_api_example.utils.Constant.IMAGE;
import static com.example.git_post_retrofit_api_example.utils.Constant.KEYID;
import static com.example.git_post_retrofit_api_example.utils.Constant.USER_GUID;

public class EmployeeListActivity extends AppCompatActivity {

    @BindView(R.id.rv_Employee_List)
    RecyclerView employee_List;
    String sessionId;
    ArrayList<EmployeeDetails> employeeDetails;
    EmployeeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        ButterKnife.bind(this);
         sessionId = getIntent().getStringExtra(KEYID);
        if(!sessionId.equals("")){
            callEmployeeList();
        }else{
            Toast.makeText(EmployeeListActivity.this,"Your Registration not success ,Please check your details",Toast.LENGTH_LONG).show();
        }
    }
    private void callEmployeeList() {
        RetroInterface apiService = RetroClient.getClient(EMPLOYEE_LIST_API_URL).create(RetroInterface.class);
        Call<ResponseBody> call = apiService.sendForgetPasswordRequest(sessionId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //Log.i("EmployeeListResult--1",response.toString());
                    String bodyString = new String(response.body().bytes(), "UTF-8");
                    //Log.i("EmployeeListResult--",bodyString);
                    employeeListdataShowing(bodyString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void employeeListdataShowing(String bodyString) {
        try {
            JSONObject jsonObject = new JSONObject(bodyString);
            String data = jsonObject.getString("data").replace(" ", "");
            Log.e("result--1-", data);
            JSONObject jsonObject1 = new JSONObject(data);
            String data1 = jsonObject1.getString("employees").replace(" ", "");
            JSONArray jsonArray = new JSONArray(data1);
            Log.e("result--2-", data1);
            employeeDetails = new ArrayList<>();
            employeeDetails.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonList = jsonArray.getJSONObject(i);
                EmployeeDetails model = new EmployeeDetails();
                model.setFullName(jsonList.getString(FULL_NAME));
                model.setDesignation(jsonList.getString(DESIGNATION));
                model.setUserGuid(jsonList.getString(USER_GUID));
                model.setImage(jsonList.getString(IMAGE));
                employeeDetails.add(model);
            }
            adapter = new EmployeeListAdapter(EmployeeListActivity.this, employeeDetails);
            employee_List.setHasFixedSize(true);
            employee_List.setLayoutManager(new LinearLayoutManager(this));
            employee_List.setAdapter(adapter);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
