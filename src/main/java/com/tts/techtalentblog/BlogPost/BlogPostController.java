package com.tts.techtalentblog.BlogPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private List<BlogPost> posts = new ArrayList<>();
    
    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model) {
        // Clear out the List
        posts.removeAll(posts);

        // Refill the List with updated set of BlogPosts
        for (BlogPost postFromDB : blogPostRepository.findAll()) {
            posts.add(postFromDB);
        }

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
        blogPostRepository.save(blogPost);
    

        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
	    model.addAttribute("blogEntry", blogPost.getBlogEntry());
        
        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/update/{id}")
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            actualPost.setTitle(blogPost.getTitle());
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());
            blogPostRepository.save(actualPost);
            model.addAttribute("blogPost", actualPost);
        }
 
        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/{id}")
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            model.addAttribute("blogPost", actualPost);
        } else {
            return "Error"; //Using Optional helps prevent null value errors by checking if present
        }
        return "blogpost/edit";
    }

    @RequestMapping(value = "/blogposts/delete/{id}")
    public String deletePostWithID(@PathVariable Long id, BlogPost blogPost, Model model) {
        blogPostRepository.deleteById(id);
        return "redirect:/";
    }
}
