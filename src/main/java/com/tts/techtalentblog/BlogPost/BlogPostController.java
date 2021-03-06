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

    @Autowired
    private BlogPostService blogPostService;

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

    @GetMapping(value = "/blogposts/tags/{tag}")
    public String indexTagged(BlogPost blogPost, @PathVariable(value = "tag") String tag, Model model) {
        // Clear out the List
        posts.removeAll(posts);

        // Refill the List with updated set of BlogPosts
        for (BlogPost postFromDB : blogPostService.findAllWithTag(tag)) {
            posts.add(postFromDB);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("tag", tag);

        // blogPost needs to match the folder name in templates
        // index is the html file in the blogPost folder
        return "blogPost/taggedPosts";
    }

    @GetMapping(value = "blogpost/new")
    public String newBlog(BlogPost post) {
        return "blogPost/new";
    }

    @GetMapping(value = "/blogposts/view/{id}")
    public String viewPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);

        BlogPost result;
        if (post.isPresent()) {
            result = post.get();
            model.addAttribute("blogPost", result);
        }
        return "blogPost/view";
    }

    public void addModelAttributes(BlogPost blogPost, Model model) {
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("author", blogPost.getTopic());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        model.addAttribute("tag", blogPost.getTag());
        model.addAttribute("tags", blogPost.getTags());
    }

    @PostMapping(value = "/blogpost")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostService.save(blogPost);

        addModelAttributes(blogPost, model);

        return "blogPost/result";
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
            updatedPost.setTag(blogPost.getTag());
            blogPostService.save(updatedPost);
            model.addAttribute("blogPost", updatedPost);

            addModelAttributes(updatedPost, model);
        }

        return "blogPost/result";
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
        return "blogPost/edit";
    }

    @RequestMapping(value = "/blogposts/delete/{id}")
    public String deletePostWithID(@PathVariable Long id, BlogPost blogPost, Model model) {
        blogPostRepository.deleteById(id);
        
        return "redirect:/";
    }
}
