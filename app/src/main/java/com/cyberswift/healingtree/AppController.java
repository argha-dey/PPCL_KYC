package com.cyberswift.healingtree;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Timer;
import java.util.TimerTask;


public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static AppController mInstance;
    /*    public DBHelper mDbHelper;
        private AsyncTaskGetNotification mAsyncTaskGetNotification;
        private AsyncTaskGetAnnouncement mAsyncTaskGetAnnouncement;
        private RequestQueue mRequestQueue;*/
    private TimerTask timerTask;
    private Timer timer;
    //   private Intent i = new Intent(FOREST_NOTIFICATION_INTENT);
    //   private ArrayList<DataAnnouncement> dataAnnouncement = new ArrayList<>();
    //  private ArrayList<DataNotification> dataNotification = new ArrayList<>();
    private com.nostra13.universalimageloader.core.ImageLoader mUniversalImageuploader;
    private boolean sentToSettings = false;
    private Activity mCurrentActivity = null;

    public AppController() {
        super();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /* public ArrayList<DataNotification> getDataNotification() {
         return dataNotification;
     }

     public void setDataNotification(ArrayList<DataNotification> dataNotification) {
         this.dataNotification = dataNotification;
     }

     public ArrayList<DataAnnouncement> getDataAnnouncement() {
         return dataAnnouncement;
     }

     public void setDataAnnouncement(ArrayList<DataAnnouncement> dataAnnouncement) {
         this.dataAnnouncement = dataAnnouncement;
     }
 */
    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
     //   initDB();
    }

    private void initComponent() {
        mInstance = AppController.this;
        MultiDex.install(mInstance);
      //  i = new Intent(FOREST_NOTIFICATION_INTENT);
        mUniversalImageuploader = globalUniversalImageLoader(this);
    }

/*    private void initDB() {
        if (mDbHelper == null) {
            mDbHelper = new DBHelper(mInstance);
        }
        try {
            mDbHelper.getWritableDatabase();
            mDbHelper.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

/*    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }*/

 /*   public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }*/


 /*   public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }*/

/*
    public void stopScheduler() {
        if (timerTask != null)
            timerTask.cancel();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        try {
            if (mAsyncTaskGetAnnouncement != null) {
                mAsyncTaskGetAnnouncement.cancel(true);
            }
            if (mAsyncTaskGetNotification != null) {
                mAsyncTaskGetNotification.cancel(true);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (dataAnnouncement != null)
            dataAnnouncement.clear();
        if (dataNotification != null)
            dataNotification.clear();
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
*/
    public void displayUniversalImg(String imgUrl, ImageView img_view, int defaultImgRes) {

        mUniversalImageuploader.displayImage(imgUrl, img_view, SetDisplaImageOption(defaultImgRes));
    }

    private com.nostra13.universalimageloader.core.ImageLoader globalUniversalImageLoader(Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);

        return com.nostra13.universalimageloader.core.ImageLoader.getInstance();

    }

    private DisplayImageOptions SetDisplaImageOption(int defaultImg) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .showImageForEmptyUri(defaultImg)
                .showImageOnFail(defaultImg)
                .cacheInMemory(true)
                .build();


        return options;

    }
}


