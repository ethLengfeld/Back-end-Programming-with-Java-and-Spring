package com.openclassrooms.watchlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WatchlistController {

    @GetMapping("/watchlist")
    public ModelAndView getWatchlist(){
        String viewName = "watchlist";

        Map<String, Object> model = new HashMap<>();

        model.put("numberOfMovies", "1234");

        return new ModelAndView(viewName , model);
    }
}
