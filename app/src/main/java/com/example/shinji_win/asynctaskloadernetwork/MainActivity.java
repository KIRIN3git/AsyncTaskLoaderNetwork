package com.example.shinji_win.asynctaskloadernetwork;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {


    private static final int LOADER_ID = 1;
    private static final String SAVE_INSTANCE_TASK_RESULT = "info.akkuma.loader.MainActivity.SAVE_INSTANCE_TASK_RESULT";
    private static final String ARG_EXTRA_PARAM = "ARG_EXTRA_PARAM";

    private static Context mContext;
    private ProgressDialog mDialog;

    private String mTaskResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        if (savedInstanceState != null) {
            mTaskResult = savedInstanceState.getString(SAVE_INSTANCE_TASK_RESULT);
        }

        if (mTaskResult != null) {
            TextView textView = (TextView) findViewById(R.id.text);
            textView.setText(mTaskResult);
        }

        initViews();

        // Activityが結果を持っていない場合ロードを行う
        //
        // 結果を保持しない性質のリクエストの場合
        // getSupportLoaderManager().getLoader(LOADER_ID) != null という条件にもできる
        if (mTaskResult == null) {

            Button button = (Button) findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString(ARG_EXTRA_PARAM, "サンプルパラメータ");
                    getSupportLoaderManager().initLoader(LOADER_ID, args, MainActivity.this);
                    mDialog.show();
                }
            });

        }
    }

    private void initViews() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle(R.string.app_name);
        mDialog.setMessage("データを更新しています...");
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_INSTANCE_TASK_RESULT, mTaskResult);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        String extraParam = args.getString(ARG_EXTRA_PARAM);
        return new MyAsyncTaskLoader(MainActivity.this, extraParam);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        getSupportLoaderManager().destroyLoader(loader.getId());

        // 結果は data に出てくる

        mTaskResult = data;

        Log.e( "DEBUG_DATA", "mTaskResult " + mTaskResult );

        if (mDialog != null) {
            mDialog.dismiss();
        }

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(mTaskResult);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // do nothing
    }
}