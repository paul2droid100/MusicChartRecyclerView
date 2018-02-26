package com.example.paulo.assignment2again.Model;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import com.example.paulo.assignment2again.api.ApiCall;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MusicianDataProvider extends Application {

    private ArrayList<Muscian> mTemporaryArrayHolder = new ArrayList<>();
    private String mTermHolder;
    //Had to add this context as i wanted to use toast thus had to make a constructor that takes context
    //as an argument and I had to add a context argument into all the classes that invoked MusicianDataProvider class
    //e.g all the Fragment classes.
    private Context mContext;
    static int mRockLimiter = 0;
    static int mClassicLimiter = 0;
    static int mPopLimiter = 0;

    public MusicianDataProvider(Context context){
        this.mContext = context;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    private ApiCall apiCall;
    private OnDataProviderCallback providerCallback;


    public void init(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://itunes.apple.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiCall = retrofit.create(ApiCall.class);
    }

    public void getMusicianData(String term, OnDataProviderCallback callback){
        this.providerCallback = callback;

        Call<MuscianBig> MusciansQ = apiCall.getMuscians(term);
        mTermHolder = term;
        MusciansQ.enqueue(new Callback<MuscianBig>() {

            @Override
            public void onResponse(Call<MuscianBig> call, Response<MuscianBig> response) {
                if (response.isSuccessful()) {

                    MuscianBig body = response.body();

                    mTemporaryArrayHolder.clear();
                    mTemporaryArrayHolder.addAll(body.getResults());

                    Realm realm2 = Realm.getDefaultInstance();


                    //Only use this if i want to wipe the realm database

//                    realm2.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            RealmResults<Muscian> result = realm.where(Muscian.class).findAll();
//                            result.deleteAllFromRealm();
//                        }
//                    });



                    //Check the size of the realm table storing Muscian
                    RealmResults<Muscian> mCheckSize = realm2.where(Muscian.class).findAll();

                    if(!(mCheckSize.size() == 150)) {
                        System.out.println("Not full yet");

                        //This limiter is put here so that rock muscians will only be assigned once to the realm database
                        if (mRockLimiter == 0) {
                            if (mTermHolder.equals("rock")) {

                                realm2.beginTransaction();

                                for (int x = 0; x < body.getResults().size(); x++) {
                                    Muscian artist = realm2.createObject(Muscian.class);
                                    artist.setArtistName(mTemporaryArrayHolder.get(x).getArtistName());
                                    artist.setArtworkUrl60(mTemporaryArrayHolder.get(x).getArtworkUrl60());
                                    artist.setCollectionName(mTemporaryArrayHolder.get(x).getCollectionName());
                                    artist.setPreviewUrl(mTemporaryArrayHolder.get(x).getPreviewUrl());
                                    artist.setTrackPrice(mTemporaryArrayHolder.get(x).getTrackPrice());
                                    artist.setType("rock");
                                }

                                System.out.println("saved rock");
                                mRockLimiter++;
                                realm2.commitTransaction();
                            }

                        }

                        if (mClassicLimiter == 0) {
                            if (mTermHolder.equals("classic")) {

                                realm2.beginTransaction();

                                for (int x = 0; x < body.getResults().size(); x++) {
                                    Muscian classicartist = realm2.createObject(Muscian.class);
                                    classicartist.setArtistName(mTemporaryArrayHolder.get(x).getArtistName());
                                    classicartist.setArtworkUrl60(mTemporaryArrayHolder.get(x).getArtworkUrl60());
                                    classicartist.setCollectionName(mTemporaryArrayHolder.get(x).getCollectionName());
                                    classicartist.setPreviewUrl(mTemporaryArrayHolder.get(x).getPreviewUrl());
                                    classicartist.setTrackPrice(mTemporaryArrayHolder.get(x).getTrackPrice());
                                    classicartist.setType("classic");
                                }

                                System.out.println("saved classic");
                                mClassicLimiter++;
                                realm2.commitTransaction();
                            }
                        }

                        if (mPopLimiter == 0) {
                            if (mTermHolder.equals("pop")) {

                                realm2.beginTransaction();

                                for (int x = 0; x < body.getResults().size(); x++) {
                                    Muscian popartist = realm2.createObject(Muscian.class);
                                    popartist.setArtistName(mTemporaryArrayHolder.get(x).getArtistName());
                                    popartist.setArtworkUrl60(mTemporaryArrayHolder.get(x).getArtworkUrl60());
                                    popartist.setCollectionName(mTemporaryArrayHolder.get(x).getCollectionName());
                                    popartist.setPreviewUrl(mTemporaryArrayHolder.get(x).getPreviewUrl());
                                    popartist.setTrackPrice(mTemporaryArrayHolder.get(x).getTrackPrice());
                                    popartist.setType("pop");
                                }

                                System.out.println("saved pop");
                                mPopLimiter++;
                                realm2.commitTransaction();
                            }
                        }
                    }else{
                        System.out.println("Realm is full");
                    }


                    if (providerCallback != null) {
                        providerCallback.onProvideList(body.getResults());
                    }


                } else {}
            }

            @Override
            public void onFailure(Call<MuscianBig> call, Throwable t) {

                Realm realm2 = Realm.getDefaultInstance();

                if (mTermHolder.equals("rock")) {

                RealmResults<Muscian> mSavedRockMusicians = realm2.where(Muscian.class).equalTo("type","rock").findAll();

                ArrayList<Muscian> mRockList = new ArrayList<>();
                for (int i = 0; i < mSavedRockMusicians.size(); i++) {
                    mRockList.add(new Muscian(mSavedRockMusicians.get(i).getArtistName(), mSavedRockMusicians.get(i).getCollectionName(), mSavedRockMusicians.get(i).getArtworkUrl60(), mSavedRockMusicians.get(i).getTrackPrice(), mSavedRockMusicians.get(i).getPreviewUrl(), mSavedRockMusicians.get(i).getType()));

                }

                System.out.println("WE GOT ROCK DATA");
                System.out.println(mSavedRockMusicians.size());
                Toast.makeText(mContext, "Loaded "+mSavedRockMusicians.size()+" Rock Tracks" ,Toast.LENGTH_SHORT).show();
                providerCallback.onProvideList(mRockList);


                }

                if (mTermHolder.equals("classic")) {

                    RealmResults<Muscian> mSavedClassicMusicians = realm2.where(Muscian.class).equalTo("type","classic").findAll();

                    ArrayList<Muscian> mClassicList = new ArrayList<>();
                    for (int i = 0; i < mSavedClassicMusicians.size(); i++) {
                        mClassicList.add(new Muscian(mSavedClassicMusicians.get(i).getArtistName(), mSavedClassicMusicians.get(i).getCollectionName(), mSavedClassicMusicians.get(i).getArtworkUrl60(), mSavedClassicMusicians.get(i).getTrackPrice(), mSavedClassicMusicians.get(i).getPreviewUrl(), mSavedClassicMusicians.get(i).getType()));

                    }

                    System.out.println("WE GOT CLASSIC DATA");
                    System.out.println(mSavedClassicMusicians.size());
                    Toast.makeText(mContext, "Loaded "+mSavedClassicMusicians.size()+" Classic Tracks" ,Toast.LENGTH_SHORT).show();
                    providerCallback.onProvideList(mClassicList);


                }

                if (mTermHolder.equals("pop")) {

                    RealmResults<Muscian> mSavedPopMusicians = realm2.where(Muscian.class).equalTo("type","pop").findAll();

                    ArrayList<Muscian> mPopList = new ArrayList<>();
                    for (int i = 0; i < mSavedPopMusicians.size(); i++) {
                        mPopList.add(new Muscian(mSavedPopMusicians.get(i).getArtistName(), mSavedPopMusicians.get(i).getCollectionName(), mSavedPopMusicians.get(i).getArtworkUrl60(), mSavedPopMusicians.get(i).getTrackPrice(), mSavedPopMusicians.get(i).getPreviewUrl(), mSavedPopMusicians.get(i).getType()));

                    }

                    System.out.println("WE GOT POP DATA");
                    System.out.println(mSavedPopMusicians.size());
                    Toast.makeText(mContext, "Loaded "+mSavedPopMusicians.size()+" Pop Tracks" ,Toast.LENGTH_SHORT).show();
                    providerCallback.onProvideList(mPopList);

                }
            }
        });
    }
}