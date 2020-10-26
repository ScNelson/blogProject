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

        // blogPost needs to match the folder name in templates
        // index is the html file in the blogPost folder
        return "blogPost/index";
    }

    @GetMapping(value = "blogpost/new")
    public String newBlog(BlogPost post) {
        return "blogpost/new";
    }

    @GetMapping(value = "/blogposts/view/{id}")
    public String viewPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);

        BlogPost result;
        if (post.isPresent()) {
            result = post.get();
            model.addAttribute("blogPost", result);
        }
        return "blogpost/view";
    }

    public void addModelAttributes(BlogPost blogPost, Model model) {
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("author", blogPost.getTopic());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
    }

    @PostMapping(value = "/blogpost")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(blogPost);

        addModelAttributes(blogPost, model);

        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/update/{id}")
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost updatedPost = post.get();
            updatedPost.setTitle(blogPost.getTitle());
            updatedPost.setAuthor(blogPost.getAuthor());
            updatedPost.setTopic(blogPost.getTopic());
            updatedPost.setBlogEntry(blogPost.getBlogEntry());
            blogPostRepository.save(updatedPost);
            //model.addAttribute("blogPost", actualPost);

            addModelAttributes(blogPost, model);
        }

        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/edit/{id}")
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);

        BlogPost result;

        if (post.isPresent()) {
            result = post.get();
            model.addAttribute("blogPost", result);
        } else {
            return "Error"; // Using Optional helps prevent null value errors by checking if present
        }
        return "blogpost/edit";
    }

    @RequestMapping(value = "/blogposts/delete/{id}")
    public String deletePostWithID(@PathVariable Long id, BlogPost blogPost, Model model) {
        blogPostRepository.deleteById(id);
        return "redirect:/";
    }
}
