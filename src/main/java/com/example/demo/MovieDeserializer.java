package com.example.demo;

import com.example.models.AggregateRating;
import com.example.models.Review;
import com.example.models.Movie;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MovieDeserializer implements JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final String title = jsonObject.get("name").getAsString();

        ArrayList<String> genres = new ArrayList<>();
        if (jsonObject.has("genre")) {
            final JsonArray genre = jsonObject.getAsJsonArray("genre");
            for (JsonElement g : genre) {
                genres.add(g.getAsString());
            }
        }
        String description = null;
        if (jsonObject.has("description")) {
            description = jsonObject.get("description").getAsString();
        }

        String datePublished = null;
        if (jsonObject.has("datePublished")) {
            datePublished = jsonObject.get("datePublished").getAsString();
        }
        String duration = null;
        if (jsonObject.has("duration")) {
            duration = jsonObject.get("duration").getAsString().substring(2);
        }
        String url = null;
        if (jsonObject.has("url")) {
            url = jsonObject.get("url").getAsString();
        }

        JsonArray actor = null;
        ArrayList<String> actors = null;
        if (jsonObject.has("actor")) {
            actor = jsonObject.getAsJsonArray("actor");
            actors = new ArrayList<>();
            for (JsonElement a : actor) {
                actors.add(a.getAsString());
            }
        }

        JsonArray director = null;
        ArrayList<String> directors = null;
        if (jsonObject.has("director")) {
            director = jsonObject.getAsJsonArray("director");
            directors = new ArrayList<>();
            for (JsonElement d : director) {
                directors.add(d.getAsString());
            }
        }

        String keywords = null;
        if (jsonObject.has("keywords")) {
            keywords = jsonObject.get("keywords").getAsString();
        }

        String image = "https://www.nilfiskcfm.com/wp-content/uploads/2016/12/placeholder.png";
        if (jsonObject.has("image")) {
            image = jsonObject.get("image").getAsString();
        }


        AggregateRating aggregateRating = null;
        if (jsonObject.has("aggregateRating")) {
            JsonObject Rating = jsonObject.get("aggregateRating").getAsJsonObject();
            int count = -1;
            if (jsonObject.get("aggregateRating").getAsJsonObject().has("ratingCount")) {
                JsonObject ratingCount = Rating.get("ratingCount").getAsJsonObject();
                count = ratingCount.get("$numberInt").getAsInt();
            }
            String value = null;
            if (jsonObject.get("aggregateRating").getAsJsonObject().has("ratingValue")) {
                value = Rating.get("ratingValue").getAsString();
            }
            aggregateRating = new AggregateRating(count, value);
        }

        JsonObject reviewObject = null;
        if (jsonObject.has("review")) {
            reviewObject = jsonObject.get("review").getAsJsonObject();
        }

        Review review = null;
        if (reviewObject != null) {
            String authorName = null;
            String dateCreated = null;
            String reviewName = null;
            String reviewBody = null;
            String ratingValue = null;
            boolean flag = false;
            if (jsonObject.has("authorName")) {
                authorName = jsonObject.get("authorName").getAsString();
                flag = true;
            }
            if (jsonObject.has("dateCreated")) {
                dateCreated = jsonObject.get("dateCreated").getAsString();
                flag = true;
            }
            if (jsonObject.has("reviewName")) {
                reviewName = jsonObject.get("reviewName").getAsString();
                flag = true;
            }
            if (jsonObject.has("reviewBody")) {
                reviewBody = jsonObject.get("reviewBody").getAsString();
                flag = true;
            }
            if (jsonObject.has("ratingValue")) {
                ratingValue = jsonObject.get("ratingValue").getAsString();
                flag = true;
            }
            if (flag) {
                review = new Review(authorName, dateCreated, reviewName, reviewBody, ratingValue);
            }
        }

        /**
         * <iframe src="https://www.imdb.com/videoembed/vi2511843353" allowfullscreen width="854" height="400"></iframe>
         *
         * /video/imdb/vi2511843353
         */

        String trailer = null;
        if (jsonObject.has("trailer")) {
            String prefix = "https://www.imdb.com/videoembed/";
            trailer = jsonObject.get("trailer").getAsString();
            String[] contents = trailer.split("/");
            prefix += contents[3];
            trailer = prefix;
        }

        final Movie movie = new Movie();

        movie.setUrl(url);

        movie.setName(title);

        movie.setGenre(genres);

        movie.setActor(actors);

        movie.setDirector(directors);

        movie.setAggregateRating(aggregateRating);

        movie.setDatePublished(datePublished);

        movie.setDescription(description);

        movie.setDuration(duration);

        movie.setImage(image);

        movie.setKeywords(keywords);

        movie.setReview(review);

        movie.setTrailer(trailer);

        return movie;
    }
}
