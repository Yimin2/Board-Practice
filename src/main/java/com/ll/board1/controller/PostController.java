package com.ll.board1.controller;

import com.ll.board1.dto.PostDto;
import com.ll.board1.entity.Post;
import com.ll.board1.repository.PostRepository;
import com.ll.board1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping
    public String list(Model model,
                       @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> postPage = postService.getpostsPage(pageable);

        int currentPage = postPage.getNumber();
        int totalPages = postPage.getTotalPages();
        int startPage = Math.max(0, currentPage-5);
        int endPage= Math.min(totalPages-1, currentPage+5);

        model.addAttribute("posts", postPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        com.ll.board1.entity.Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new PostDto());
        return "posts/form";
    }

    @PostMapping
    public String create(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "posts/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Post post) {
        postService.updatePost(id, post);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/test/cache")
    public String testCache() {
        postService.testFirstLevelCache();
        return "redirect:/posts";
    }

    @GetMapping("/test/write-behind")
    public String testWriteBehind() {
        postService.testWriteBehind();
        return "redirect:/posts";
    }

    @GetMapping("/test/dirty-checking")
    public String testDirtyChecking() {
        postService.testDirtyChecking();
        return "redirect:/posts";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, @PageableDefault(sort="id") Pageable pageable, Model model) {
        Page<Post> posts = postService.searchPostPage(keyword, pageable);

        int currentPage = posts.getNumber();
        int totalPages = posts.getTotalPages();
        int startPage = Math.max(0, currentPage-5);
        int endPage = Math.min(totalPages, currentPage+5);

        model.addAttribute("posts", posts);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "posts/list";
    }

    @GetMapping("/recent")
    public String recent(Model model) {
        List<Post> posts = postService.getRecentPosts();
        model.addAttribute("posts", posts);

        return "posts/list";
    }

    @GetMapping("/dummy")
    public String dummy() {
        postService.createDummyPosts(100);
        return "redirect:/posts";
    }

    @GetMapping("/more")
    public String more(@PageableDefault Pageable pageable, Model model) {
        Slice<Post> postSlice = postService.getPostsSlice(pageable);
        model.addAttribute("postSlice", postSlice);
        return "posts/list-more";
    }
}
