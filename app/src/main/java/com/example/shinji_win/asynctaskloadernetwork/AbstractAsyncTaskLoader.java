package com.example.shinji_win.asynctaskloadernetwork;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AbstractAsyncTaskLoader<D> extends AsyncTaskLoader<D> {


    private D mResult;
    private boolean mIsStarted = false;

    public AbstractAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    abstract public D loadInBackground();

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
            return;
        }
        if (!mIsStarted || takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        mIsStarted = true;
    }

    @Override
    public void deliverResult(D data) {
        mResult = data;
        super.deliverResult(data);
    }
}
