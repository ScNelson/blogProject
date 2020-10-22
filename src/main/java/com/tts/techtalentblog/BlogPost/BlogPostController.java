package com.tts.techtalentblog.BlogPost;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private List<BlogPost> posts = new ArrayList<>();
    
    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model) {

        model.addAttribute("posts", posts);

        //blogPost needs to match the folder name in templates
        //index is the html file in the blogPost folder
        return "blogPost/index";
    }

    @GetMapping(value = "blogpost/new")
    public String newBlog(BlogPost post) {
        return "blogpost/new";
    }

    @PostMapping(value = "/blogpost") 
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
        
        posts.add(blogPost);

        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
	    model.addAttribute("blogEntry", blogPost.getBlogEntry());
        
        return "blogpost/result";
    }

    @RequestMapping(value = "/blogpost/{id}", method = RequestMethod.DELETE)
    public String deletePostWithID(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "/blogpost/index";
    }
}
