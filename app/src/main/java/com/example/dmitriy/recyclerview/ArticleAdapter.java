package com.example.dmitriy.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder>{
    private ArrayList<Article> articles;
    PopOutMenuInterface popOutMenuInterface;



    public class ArticleHolder extends RecyclerView.ViewHolder{
        public TextView header,desc,text;
        LinearLayout lin;
        Button btn;

        public ArticleHolder(final View itemView) {
            super(itemView);
            header = (TextView)itemView.findViewById(R.id.articleLayoutHeader);
            desc = (TextView)itemView.findViewById(R.id.articleLayoutDesc);
            lin= (LinearLayout)itemView.findViewById(R.id.articleLayout);
            lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popOutMenuInterface.showPopupMenu(view,getAdapterPosition());
                }
            });
        }


    }

    public ArticleAdapter(ArrayList<Article>articles,PopOutMenuInterface popOutMenuInterface){
        this.articles=articles;
        this.popOutMenuInterface=popOutMenuInterface;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_layout,parent,false);

        return new ArticleHolder(articleView);
        /*
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
         */
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {

        Article article = articles.get(position);
        holder.desc.setText(article.descriprion);
        holder.header.setText(article.headder);
        holder.lin.setTag(position);

    /*
    Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
     */
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }




}
/*public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
} */