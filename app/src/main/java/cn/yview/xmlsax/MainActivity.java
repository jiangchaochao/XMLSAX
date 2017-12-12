package cn.yview.xmlsax;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG="MainActivity";
    File file;
    private String xmlname = "test.xml";
    private String xmlfilePath = Environment.getExternalStorageDirectory() + "/" + xmlname;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(xmlfilePath);
        sp = this.getPreferences(0);
        try {
            XmlUtil.setValueToSp(file, sp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String , String> hashMap = (HashMap<String, String>) sp.getAll();

        for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()) {
            Log.e(TAG, stringStringEntry.getKey()  + "--------------" + stringStringEntry.getValue());
        }

    }
}
