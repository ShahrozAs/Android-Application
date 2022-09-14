## Move RecyclerView to Fragment

```

                AppCompatActivity activity=(AppCompatActivity) view.getContext();
                playerFragment playerFragment=new playerFragment();
                activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.songsFragment,playerFragment)
                .addToBackStack(null)
                .commit();

```

