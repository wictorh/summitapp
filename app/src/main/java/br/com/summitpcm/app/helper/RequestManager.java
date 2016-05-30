package br.com.summitpcm.app.helper;

import android.content.Context;

/**
 * Created by wictor.huggo on 29/05/2016.
 */
public class RequestManager {
    private static final String TAG = "RequestManager";
    private static RequestManager instance;
    public RequestProxy mRequestProxy;

    private RequestManager(Context context) {
        mRequestProxy = new RequestProxy(context);
    }
    public RequestProxy doRequest() {
        return mRequestProxy;
    }

    //este método deve ser chamado primeiro para fazer a inicialização Singleton
    public static synchronized RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public static synchronized RequestManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " não esta inicializado, chame o metodo getInstance(..) primeiro.");
        }
        return instance;
    }
}
