package com.x.chwang.demolist.sendmail;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.x.chwang.demolist.R;
import com.x.chwang.demolist.base.BaseActivity;

/**
 * Created by wangchaohu on 2017/2/17.
 *
 *
 * fun：利用java mail 实现android端发送邮件
 *
 */

public class SendEmailActivity extends BaseActivity{

    private EditText editText;
    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sendemail);

        editText = (EditText) findViewById(R.id.getEmail);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

send();
            }
        });
    }

    public void send(){
        String host = "smtp.yeah.net";
        String address = "xwangch@yeah.net";
        String from = "xwangch@yeah.net";
        String password = "zx1115hx";// 密码

        String port = "465";
        AnnexMailService.sendEmail(host, address, from, password, "839461699@qq.com", port, "测试", "测试测试");
    }
}
