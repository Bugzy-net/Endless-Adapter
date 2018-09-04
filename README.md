# Endless-Adapter

An endless adapter, where you can add items easily, effeciently and endlessly

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

Your application must support API17+

### Installing

A step by step series of examples that tell you how to get a development env running

In you project build.gradle add this


```
allprojects{
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

then in your module build.gradle add this

```
dependencies {
    ...
    implementation 'com.github.Bugzy-net:Endless-Adapter:1.0.5'
}
```

## Enldess Adapter Class

### Properties

- *FOOTER_VIEW* (Loading view holder)
- *NORMAL_VIEW* (Item view holder)

- *data* is a List of a generic type specified with initialization of the class.
- *showLoading* indicates wether the adapter should show the loading viewholder at the end or not
- *loadMoreListener* a listener to be called when the loading view holder is shown.
- *inflater* took the inflater as a global variable instead creating new one with every create.
- *isLoading* flag indicates whether the adapter is waiting for new data or not.

### Generic Types
- *T* type of Object that will be used to fill the adapter.
- *VH* type of view holder used as a normal view holder.

## How to use

### Create Adapter
1. Create a class and extend *EndlessAdapter*
2. Implement *onCreateViewHolder*
```
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewHolder == null) {
            return new YourCustomViewHolder(inflater.inflate(R.layout.you_custom_layout, parent, false));
        } else {
            return viewHolder;
        }
    }
```
3. Implement *onBindNormalView*
```
    @Override
    protected void onBindNormalView(RecyclerView.ViewHolder viewHolder, int position, Item item) {
        YourCustomViewHolder itemViewHolder = (YourCustomViewHolder) viewHolder;
    }
```

### Use the adapter
```
itemAdapter = new ItemAdapter(context, createItems(), new LoadMoreListener() {
            @Override
            public void loadMore(final LoadCompletedListener loadCompletedListener) {
                    itemAdapter.addItems(newItems);
                    loadCompletedListener.onLoadCompleted(true);
                    
                    // Here you can check if this is the last patch
                    adapter.setEndless(lastPatch);
            }
        });
```
