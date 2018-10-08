 # RecyclerView with ViewHolder pattern
 ## List item layout
1. Create a new Constraint layout named movie_item.xml in res/layout that will be used as a layout for each list item. Constraint Layout should have the height set to wrap_content or a fixed size in DP.
1(alternative) You can also add a 1px high dark grey linear layout with match parent width as a separator
2. Add title, release year and director's name TextViews style and name them accordingly.

## Data model class
3. Create a MovieModel.java model class that corresponds to your data structure and add a constructor that fills in the properties.

## TOOLS CLASS
4. Create a static Tools.java class with a method that will fill and return a List<MovieModel> with fake data.

## Using RecyclerView in your app ---
5. Add reference to app's build.gradle file using "implementation 'com.android.support:recyclerview-v7:28.0.0'"
6. Add RecyclerView component into res/layout/activity_main.xml file using the "android.support.v7.widget.RecyclerView" tag
7. Create ID for the recyclerview component (e.g. rv_movie_view)

## ViewHolder pattern preparation
8. Create a ViewHolder class named MovieHolder.java that contains TextViews for title, release year and director's name.
9. Extend the MovieHolder by RecyclerView.ViewHolder and implement the default constructor
10. In constructor, assign corresponding views from itemView (movie_item layout) to particular TextViews.

## RecyclerView adapter implementation
11. Create MovieAdapter.java class that extends RecyclerView.Adapter<MovieHolder>
12. Use ALT+Enter and override/implement all compulsory methods.
13. Create private global objects for List<MovieModel> and Context (or WeakReference<Context>)
14. Create a constructor that accepts List<MovieModel> and Context (or WeakReference<Context>) and assigns them to private objects created in 13.
15. Implement getItemCount method - watch out for null pointer exception.
16. Implement onBindViewHolder that sets the holder's public objects (textviews) text by .setText method to data values.
17. Implement onCreateViewHolder that returns new MovieHolder with inflated list item view into parent (viewgroup). Use LayoutInflater.from(Context context).inflate method.
18. Create a method refreshData(List<MovieModel>) that exchanges the data and refreshes the adapter using notifyDataSetChanged() method in adapter's context.

## Implement acitivity 
19. In MainActivity.java create private global variables for RecyclerView, RecyclerView.LayoutManager and RecyclerView.Adapter<MovieHolder>
20. In OnCreate method assign recyclerview to the private object from 19, assign LinearLayoutManager(Context) to private object from 19 and use the Tools.
21. Set layout manager to recyclerview and set it to have fixed size
22. Create recyclerview adapter and assign it to the recyclerview.
