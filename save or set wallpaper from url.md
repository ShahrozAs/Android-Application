package com.example.wallpaper_grid.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpaper_grid.R;
import com.example.wallpaper_grid.databinding.ActivityFullimageBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FullimageActivity extends AppCompatActivity {


    ActivityFullimageBinding binding;
    WallpaperManager wallpaperManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding=ActivityFullimageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        wallpaperManager=WallpaperManager.getInstance(getApplicationContext());

        int image=getIntent().getIntExtra("image", R.drawable.ic_launcher_background);
        //binding.fullImage.setImageResource(image);

        String url=getIntent().getStringExtra("imageUrl");


        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.progress_animation)
                .into(binding.fullImage);




        binding.btnSetAsWallpaper.setOnClickListener(view -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(FullimageActivity.this);
            builder1.setMessage("Do You want Change the Background Wallpaper.");
            builder1.setCancelable(true);

            builder1.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    Glide.with(FullimageActivity.this)
                            .asBitmap()
                            .load(url)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                    try {
                                       // Bitmap bitmap=getResizedBitmap(resource,320,480);
                                        wallpaperManager.setBitmap(resource);
                                        dialogInterface.dismiss();
                                    }catch (Exception e){
                                        Toast.makeText(FullimageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }

                                }
                            });
                }
            });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        });



        binding.btnDownload.setOnClickListener(view -> {
            new DownloadsImage().execute(url);
        });

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    public class DownloadsImage extends AsyncTask<String, Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm =    BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/WallpaperApp"); //Creates app specific folder

            //todo:: File path=0/emulated/android/Picture/Wallpaper/imagename.png;

            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, System.currentTimeMillis() +".png"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(FullimageActivity.this,new String[] { imageFile.getAbsolutePath() }, null, (path1, uri) -> {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    //Log.i("ExternalStorage", "-> uri=" + uri);
                });
            } catch(Exception ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            _toast("Saved Image");
        }
    }

    public void _toast(String msg){
        Toast.makeText(FullimageActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}