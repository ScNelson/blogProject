package com.tts.techtalentblog.BlogPost;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<BlogPost> findAllWithTag(String tag) {
        List<BlogPost> posts = postRepository.findByTags_Phrase(tag);
        return posts;
    }

    public void save(BlogPost post) {
        handleTags(post);
        postRepository.save(post);
    }

    private void handleTags(BlogPost post) {
        List<Tag> tags = new ArrayList<Tag>();
        String[] tagSplit = post.getTag().split(",");
        for (int i = 0; i < tagSplit.length; i++) {
            Tag tag = tagRepository.findByPhrase(tagSplit[i]);
            tag = new Tag();
            tag.setPhrase(tagSplit[i]);
            tagRepository.save(tag);
            tags.add(tag);
        }
        post.setTags(tags);
    }
}
