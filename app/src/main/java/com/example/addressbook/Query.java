package com.example.addressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * @ProjectName: AddressBook
 * @Package: com.example.addressbook
 * @QQ: 1025377230
 * @Author: Fonrye
 * @CreateDate: 2021/4/6 21:54
 * @Email: fonrye@163.com
 * @Version: 1.0
 */

import java.util.HashMap;
import java.util.Map;

public class Query extends AppCompatActivity {
    private MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(Query.this);
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        final EditText edtName = (EditText) this.findViewById(R.id.Et_name);

        Button btnConfirm = (Button) this.findViewById(R.id.query);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Query.this, MainActivity.class);
                String newName = edtName.getText().toString();
                Bundle bundle = new Bundle();

                bundle.putString("sname", newName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}
