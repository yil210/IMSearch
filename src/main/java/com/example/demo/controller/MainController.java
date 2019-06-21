package com.example.demo.controller;

import com.example.IMSearch.AllSearch;
import com.example.demo.MovieDeserializer;
import com.example.models.AggregateRating;
import com.example.models.Movie;
import com.example.models.Review;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.google.gson.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private HashMap<String, Movie> mp;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    String getIndex() {
        mp = new HashMap<>();
        return "index";
    }

    @RequestMapping(value="/index",  method = RequestMethod.POST)
    String search(@RequestParam("query") String query, Model model) throws Exception {

        ArrayList<String> jsons = null;

        AllSearch allSearch = new AllSearch();

        if (query.contains("*") || query.contains("?")) {
            jsons = allSearch.wildcardSearch(query);
        } else {
            jsons = allSearch.generalSearch(query);
        }

        System.out.println(query);




        System.out.println(jsons.size());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();


        ArrayList<Movie> movies = new ArrayList<>();

        mp = new HashMap<>();

        for (String json : jsons) {
            Movie movie = gson.fromJson(json, Movie.class);
            AggregateRating aggregateRating = movie.getAggregateRating();
            Review review = movie.getReview();
            movies.add(movie);

            mp.put(movie.getUrl(), movie);
//            System.out.println(movie.getImage());
        }

        model.addAttribute("movies", movies);

        model.addAttribute("query", query);

//        model.addObject("movies", movies);

        return "result";
    }


    @RequestMapping(value = "/detail/{url}", method = RequestMethod.GET)
    String detail(@PathVariable("url") String url, Model model) {
//        System.out.println("Rating: " + mp.get(url).getAggregateRating().getRatingValue());
        model.addAttribute("movie", mp.get(url));
        return "detail";
    }
    @RequestMapping(value="/advanced_search",method = RequestMethod.GET)
    String get_advanced_search() {
        return "advanced_search";
    }

    @RequestMapping(value="/advanced_title_search",method = RequestMethod.POST)
    String advanced_title_search(@RequestParam("title") String title, @RequestParam("category") String[] genres,
                                 @RequestParam("date_from") String dateFrom, @RequestParam("date_to") String dateTo,
                                 @RequestParam("rating_from") String ratingFrom, @RequestParam("rating_to") String ratingTo,
                                 @RequestParam("keyword") String keyword, Model model) throws IOException, ParseException {

        AllSearch allSearch = new AllSearch();

        StringBuilder sb = new StringBuilder();
        if (genres != null) {
            for (String str : genres) {
                sb.append(str).append(" ");
            }
        }
        String gstr = sb.toString();
        if (gstr.equals("0")) {
            gstr = null;
        }

        ArrayList<String> jsons = allSearch.advancedSearch(title, gstr, keyword, dateFrom, dateTo, ratingFrom, ratingTo);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();

        ArrayList<Movie> movies = new ArrayList<>();

        mp = new HashMap<>();

        for (String json : jsons) {
            Movie movie = gson.fromJson(json, Movie.class);
            AggregateRating aggregateRating = movie.getAggregateRating();
            Review review = movie.getReview();
            movies.add(movie);

            mp.put(movie.getUrl(), movie);
        }

        model.addAttribute("movies", movies);

        model.addAttribute("query", "ADVANCED SEARCH");


        System.out.println("advanced_title_search    ************");

        return "result";
    }

    @RequestMapping(value="/collaboration_search1",method = RequestMethod.POST)
    String collaboration_search1(@RequestParam("name1") String name1, @RequestParam("name2") String name2, Model model) throws IOException, ParseException {


        AllSearch allSearch = new AllSearch();
        ArrayList<String> jsons = allSearch.collabrationByName(name1, name2);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();

        ArrayList<Movie> movies = new ArrayList<>();

        mp = new HashMap<>();

        for (String json : jsons) {
            Movie movie = gson.fromJson(json, Movie.class);
            AggregateRating aggregateRating = movie.getAggregateRating();
            Review review = movie.getReview();
            movies.add(movie);

            mp.put(movie.getUrl(), movie);
        }

        model.addAttribute("movies", movies);

        model.addAttribute("query", name1 + name2);

        System.out.println("collaboration_search1    ************");

        return "result";
    }

    @RequestMapping(value="/collaboration_search2",method = RequestMethod.POST)
    String collaboration_search2(@RequestParam("keyword1") String keyword1, @RequestParam("keyword2") String keyword2, Model model) throws IOException, ParseException {

        System.out.println("collaboration_search2    ************");

        AllSearch allSearch = new AllSearch();

        ArrayList<String> jsons = allSearch.collabrationByKeywords(keyword1, keyword2);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();

        ArrayList<Movie> movies = new ArrayList<>();

        mp = new HashMap<>();

        for (String json : jsons) {
            Movie movie = gson.fromJson(json, Movie.class);
            AggregateRating aggregateRating = movie.getAggregateRating();
            Review review = movie.getReview();
            movies.add(movie);

            mp.put(movie.getUrl(), movie);
        }

        model.addAttribute("movies", movies);

        model.addAttribute("query", keyword1 + keyword2);

        return "result";
    }


    @RequestMapping(value="/search_genre/{genre}",method = RequestMethod.GET)
    String search_genre(@PathVariable("genre") String genre, Model model) throws IOException, ParseException {

        System.out.println("genre search    ************");

        AllSearch allSearch = new AllSearch();

        ArrayList<String> jsons = allSearch.genreSearch(genre);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();

        ArrayList<Movie> movies = new ArrayList<>();

        mp = new HashMap<>();

        for (String json : jsons) {
            Movie movie = gson.fromJson(json, Movie.class);
            AggregateRating aggregateRating = movie.getAggregateRating();
            Review review = movie.getReview();
            movies.add(movie);

            mp.put(movie.getUrl(), movie);
        }

        model.addAttribute("movies", movies);

        model.addAttribute("query", genre);

        return "result";
    }





}
