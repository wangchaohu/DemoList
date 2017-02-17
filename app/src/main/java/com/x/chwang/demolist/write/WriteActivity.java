package com.x.chwang.demolist.write;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.x.chwang.demolist.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lapsen_wang on 2017/2/17/0017.
 */

public class WriteActivity extends AppCompatActivity {
    private EditText et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        et = (EditText) findViewById(R.id.edit_text);
        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeFile(et.getText().toString());
            }
        });
    }
    /**
     *
     * 获取默认的文件路径
     *
     */

    private String getDefaultFilePath() {
        String filepath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/myAccount";
        File file = new File(filepath);

        if (!file.exists()){
            file.mkdir();
        }

        file = new File(filepath,"abc.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return filepath;
    }

    /**
     * 使用bufferReader读取文件
     * */
    private void readFile(){
        try{
            File file = new File(getDefaultFilePath());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readLine = "";
            StringBuffer sb = new StringBuffer();
            while ((readLine = br.readLine()) != null){
                sb.append(readLine);
            }
            br.close();
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }
    }

    /**
     *
     * 使用bufferWrite写入文件
     * */

    private void writeFile(String content){
        try {
            File file = new File(getDefaultFilePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(content);
            bw.flush();
        }catch (IOException e){

        }
    }
}
