package com.jerry.controller;

import com.jerry.model.BlogEntity;
import com.jerry.model.UserEntity;
import com.jerry.repository.BlogRepository;
import com.jerry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by lufeisong on 16/6/30.
 */
@Controller
public class BlogController {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;

    // 查看所有博文
    @RequestMapping(value = "/admin/blogs", method = RequestMethod.GET)
    public String showBlogs(ModelMap modelMap) {
        List<BlogEntity> blogList = blogRepository.findAll();
        modelMap.addAttribute("blogList", blogList);
        return "admin/blogs";
    }

    // 添加博文
    @RequestMapping(value = "/admin/blogs/add", method = RequestMethod.GET)
    public String addBlog(ModelMap modelMap) {
        List<UserEntity> userList = userRepository.findAll();
        // 向jsp注入用户列表
        modelMap.addAttribute("userList", userList);
        return "admin/addBlog";
    }

    // 添加博文，POST请求，重定向为查看博客页面
    @RequestMapping(value = "/admin/blogs/addP", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") BlogEntity blogEntity) {
        // 打印博客标题
        System.out.println(blogEntity.getTitle());
        // 打印博客作者
        System.out.println(blogEntity.getUserByUserId().getNickname());
        // 存库
        blogRepository.saveAndFlush(blogEntity);
        // 重定向地址
        return "redirect:/admin/blogs";
    }

    //查看博文详情
    @RequestMapping(value = "/admin/blogs/show/{id}", method = RequestMethod.GET)
    public String showBlog(@PathVariable("id") Integer id, ModelMap modelMap) {
        BlogEntity blogEntity = blogRepository.findOne(id);
        modelMap.addAttribute("blog", blogEntity);
        return "admin/blogDetail";
    }
    //修改博文详情
    @RequestMapping(value = "/admin/blogs/update/{id}",method = RequestMethod.GET)
    public String updateBlog(@PathVariable("id") Integer id,ModelMap modelMap){
        BlogEntity blogEntity = blogRepository.findOne(id);
        List<UserEntity> userList = userRepository.findAll();
        modelMap.addAttribute("blog",blogEntity);
        modelMap.addAttribute("userList",userList);
        return "admin/updateBlog";
    }
    @RequestMapping(value = "/admin/blogs/updateP",method = RequestMethod.POST)
    public String updateBlogPost(@ModelAttribute("blog") BlogEntity blogEntity){
        blogRepository.updateBlog(blogEntity.getTitle(),blogEntity.getUserByUserId().getId(),blogEntity.getContent(),blogEntity.getPubDate(),blogEntity.getId());
        blogRepository.flush();
        return "redirect:/admin/blogs";

    }
    //删除博文
    @RequestMapping(value = "/admin/blogs/delete/{id}",method = RequestMethod.GET)
    public String deleteBlog(@PathVariable("id") Integer id){
        blogRepository.delete(id);
        blogRepository.flush();
        return "redirect:/admin/blogs";

    }
}
