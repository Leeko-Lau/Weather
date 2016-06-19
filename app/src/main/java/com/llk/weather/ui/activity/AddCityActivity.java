package com.llk.weather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.llk.weather.R;
import com.llk.weather.dao.QualityDao;
import com.llk.weather.model.ActivityCollector;
import com.llk.weather.model.Future;
import com.llk.weather.model.Sk;
import com.llk.weather.model.Today;
import com.llk.weather.model.WeatherInfo;
import com.llk.weather.dao.FutureDao;
import com.llk.weather.dao.SkDao;
import com.llk.weather.dao.TodayDao;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends BaseActivity {

    private ListView listView;

    private List<String> data;

    private ArrayAdapter<String> adapter;

    private String cityId;

    private String weatherData;

    private Context context;

    private WeatherInfo weatherInfo;

    private FutureDao futureDao;

    private TodayDao todayDao;

    private SkDao skDao;

    private QualityDao qualityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        context = this;

        futureDao = new FutureDao(context);
        todayDao = new TodayDao(context);
        skDao = new SkDao(context);
        qualityDao = new QualityDao(context);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        listView = (ListView) findViewById(R.id.listView_addCity);
        data = new ArrayList<>();
        data.add("长沙");
        data.add("北京");
        data.add("北海");
        data.add("上海");
        data.add("广州");
        data.add("深圳");
        data.add("株洲");
        data.add("南宁");
        data.add("重庆");
        data.add("钦州");
        data.add("厦门");
        data.add("武汉");
        data.add("南京");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                cityId = data.get(position);

                Parameters parameters = new Parameters();
                try {
                    parameters.add("cityname", URLEncoder.encode(cityId, "utf-8"));
                    parameters.add("format", 2);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                /**
                 * 请求的方法 参数: 第一个参数 当前请求的context;
                 * 				  第二个参数 接口id;
                 * 				  第三个参数 接口请求的url;
                 * 				  第四个参数 接口请求的方式;
                 * 				  第五个参数 接口请求的参数,键值对
                 * 				  com.thinkland.sdk.android.Parameters类型;
                 * 				  第六个参数请求的回调方法,com.thinkland.sdk.android.DataCallBack;
                 *
                 */
                JuheData.executeWithAPI(
                        context,
                        39,
                        "http://v.juhe.cn/weather/index",
                        JuheData.GET,
                        parameters,
                        new DataCallBack() {
                            @Override
                            public void onSuccess(int statusCode, String s) {
                                StringBuffer buffer = new StringBuffer();
                                buffer.append(s + "\n");
                                weatherData = buffer.toString();

                                try {
                                    //对数据进行解析
                                    JSONObject root = new JSONObject(weatherData);
                                    JSONObject result = root.getJSONObject("result");

                                    //获取当前天气
                                    JSONObject skJson = result.getJSONObject("sk");
                                    String temp = skJson.getString("temp");
                                    Sk sk = new Sk(temp);
                                    skDao.add(sk);

                                    //获得今天的详细天气情况
                                    JSONObject todayJson = result.getJSONObject("today");
                                    String city = todayJson.getString("city");
                                    String temperature = todayJson.getString("temperature");
                                    String weather = todayJson.getString("weather");
                                    Today today = new Today(city, temperature, weather);
                                    todayDao.add(today);

                                    //活动城市的空气质量
                                    String dressing_index = todayJson.getString("dressing_index");
                                    String travel_index = todayJson.getString("travel_index");
                                    String wash_index = todayJson.getString("wash_index");
                                    String comfort_index = todayJson.getString("comfort_index");
                                    String exercise_index = todayJson.getString("exercise_index");
                                    String dressing_advice = todayJson.getString("dressing_advice");
                                    String uv_index = todayJson.getString("uv_index");
                                    String drying_index = todayJson.getString("drying_index");
                                    Today quality = new Today(city, dressing_index, travel_index,
                                            wash_index, comfort_index, exercise_index,
                                            dressing_advice, uv_index, drying_index);
                                    qualityDao.add(quality);

                                    //获得未来几天的天气情况
                                    JSONArray futureArray = result.getJSONArray("future");
                                    ArrayList<Future> futureList = new ArrayList<>();
                                    for (int i = 0; i < futureArray.length(); i++) {
                                        JSONObject futureJson = (JSONObject) futureArray.get(i);
                                        String temperature2 = futureJson.getString("temperature");
                                        String week = futureJson.getString("week");
                                        String weather2 = futureJson.getString("weather");
                                        Future future = new Future(temperature2, week, weather2);
                                        futureDao.add(future);
                                        futureList.add(future);
                                    }

                                    weatherInfo = new WeatherInfo(sk, today, futureList);
                                    Log.d("FF",futureList.toString());
                                    Log.d("WW",weatherInfo.toString());

                                    Intent intent = getIntent();

                                    int flags = intent.getFlags();
                                    String className = intent.getStringExtra("classname");
                                    int flag = intent.getIntExtra("flag",0);
                                    Log.d("FF", String.valueOf(flag));
                                    if(flag == 1){
                                        //flags等于1说明是从欢迎界面跳转过来的
                                        Intent intent1 = new Intent(
                                                AddCityActivity.this,
                                                MainActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("weatherInfo1", weatherInfo);
                                        intent1.putExtras(bundle);
                                        startActivity(intent1);
                                    }else if(flag == 2){
                                        //flags等于2说明是从主界面跳转过来的
                                        Intent intent2 = new Intent(AddCityActivity.this,
                                                MainActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("weatherInfo", weatherInfo);
                                        intent2.putExtras(bundle);
                                        intent2.putExtra("flag2",4);
                                        AddCityActivity.this.finish();
                                        startActivity(intent2);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFinish() {
                            }

                            @Override
                            public void onFailure(int i, String s, Throwable throwable) {
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JuheData.cancelRequests(context);
    }
}
