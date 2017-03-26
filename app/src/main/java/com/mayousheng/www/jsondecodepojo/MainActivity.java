package com.mayousheng.www.jsondecodepojo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mayousheng.www.jsondecodepojo.pojo.TestPojo;
import com.mayousheng.www.jsondecodepojo.pojo.TestUser;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private TextView obj2str, str2obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obj2str = (TextView) findViewById(R.id.obj2str);
        str2obj = (TextView) findViewById(R.id.str2obj);

        TestPojo testPojo = new TestPojo();
        testPojo.clazz = "三<二>班";
        ArrayList<TestUser> userList = new ArrayList<TestUser>();
        for (int i = 0; i < 10; i++) {
            TestUser testUser = new TestUser();
            testUser.age = i;
            testUser.userName = "name_" + i;
            userList.add(testUser);
        }
        testPojo.userList = userList;
        obj2str.setText(testPojo.toString());

        String testStr = "{\"class\":\"三<二>班\",\"users\":" +
                "[{\"user_age\":0,\"user_name\":\"name_0\"}," +
                "{\"user_age\":1,\"user_name\":\"name_1\"}," +
                "{\"user_age\":2,\"user_name\":\"name_2\"}," +
                "{\"user_age\":3,\"user_name\":\"name_3\"}," +
                "{\"user_age\":4,\"user_name\":\"name_4\"}," +
                "{\"user_age\":5,\"user_name\":\"name_5\"}," +
                "{\"user_age\":6,\"user_name\":\"name_6\"}," +
                "{\"user_age\":7,\"user_name\":\"name_7\"}," +
                "{\"user_age\":8,\"user_name\":\"name_8\"}," +
                "{\"user_age\":9,\"user_name\":\"name_9\"}]}";
        TestPojo testPojo2 = new TestPojo(testStr);
        str2obj.setText(testPojo2.toString());

    }
}
