## Glide An image loading and caching library for Android focused on smooth scrolling

## Use Gradle:

```
repositories {
   mavenCentral() 
   google()
}
```

## dependencies

```
dependencies {
   implementation 'com.github.bumptech.glide:glide:4.11.0'
   annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
}
```

```
// For a simple view:
Glide.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);
```