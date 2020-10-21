package com.tts.techtalentblog.BlogPost;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogPostController {
    
    @GetMapping(value = "/")
    public String index(BlogPost blogPost) {

        //blogPost needs to match the folder name in templates
        //index is the html file in the blogPost folder
        return "blogPost/index";
    }
}
