##First one, you need to set the permission in your Manifest.xml file

``
 <uses-permission android:name="android.permission.SET_WALLPAPER"/>
``


##And you can set the background with this:

``
Button buttonSetWallpaper = (Button)findViewById(R.id.set);
ImageView imagePreview = (ImageView)findViewById(R.id.preview);
imagePreview.setImageResource(R.drawable.five);

buttonSetWallpaper.setOnClickListener(new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            WallpaperManager myWallpaperManager 
            = WallpaperManager.getInstance(getApplicationContext());
            try {
                myWallpaperManager.setResource(R.drawable.five);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
}});
``