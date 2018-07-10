package com.example.dmitriy.recyclerview;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,PopOutMenuInterface{
    public static final int ADD_REQUEST=1,EDIT_REQUEST=2;
    public static final String REQUES_CODE = "requestCode",EDITING_POINT="editingPoint";
    public static final ArticleDB articleDB= new ArticleDB("articlesTable", "dbHeader",
            "dbDescription", "dbText","articlesDB",2);
    RecyclerView recyclerView;
    ArrayList<Article>articles;
    ArticleAdapter adapter;
    DBHelper dbHelper;
    DbManager dbManager;
    SQLiteDatabase db;
    int editingPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycled);
        dbHelper= new DBHelper(this,articleDB);
        dbManager= new DbManager(dbHelper);
        db = dbHelper.getWritableDatabase();
        articles=dbManager.load(articleDB);
        adapter = new ArticleAdapter(articles, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {

                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra(REQUES_CODE, ADD_REQUEST);
                startActivityForResult(intent, ADD_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case ADD_REQUEST:
                        String frow = data.getStringExtra(articleDB.firstRow);
                        String srow = data.getStringExtra(articleDB.secondRow);
                        String trow = data.getStringExtra(articleDB.thirdRow);
                        articles.add(new Article(frow,srow,trow));
                        adapter.notifyDataSetChanged();
                        break;
                    case EDIT_REQUEST:
                        articles.set(editingPosition, new Article(
                                data.getStringExtra(articleDB.firstRow),
                                data.getStringExtra(articleDB.secondRow),
                                data.getStringExtra(articleDB.thirdRow)

                        ));
                        adapter.notifyDataSetChanged();
                        break;

                }
            }
        }
    }
    public void showPopupMenu(View v, final int irrr) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.pop_out_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menuDel:
                        db.execSQL("DELETE FROM "+articleDB.name+" WHERE "+articleDB.firstRow+"='"+ articles.get(irrr).headder+"';");
                        articles.remove(irrr);
                        adapter.notifyDataSetChanged();
                        //updateList(articles2);
                        return true;
                    case R.id.menuEdit:
                        editingPosition=irrr;
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra(articleDB.firstRow, articles.get(irrr).headder);
                        intent.putExtra(articleDB.secondRow, articles.get(irrr).descriprion);
                        intent.putExtra(articleDB.thirdRow, articles.get(irrr).text);
                        intent.putExtra(EDITING_POINT,articles.get(irrr).text);
                        intent.putExtra(REQUES_CODE,EDIT_REQUEST);
                        startActivityForResult(intent,EDIT_REQUEST);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

}
