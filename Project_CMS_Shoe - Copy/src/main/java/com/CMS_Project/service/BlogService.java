package com.CMS_Project.service;

import com.CMS_Project.dto.request.BlogRequest;
import com.CMS_Project.dto.response.BlogResponse;
import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.BlogMapper;
import com.CMS_Project.repository.BlogRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogService {

    BlogRepository blogRepository;
    BlogMapper blogMapper;
    UserRepository userRepository;

    public BlogResponse create(BlogRequest blogRequest) {
        Blogs blogs = blogMapper.toBlog(blogRequest);
        blogs.setCreatedAt(LocalDateTime.now());
        blogs.setUpdatedAt(LocalDateTime.now());
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        blogs.setUser(user);
        blogs.setCreatedBy(user.getEmail());
        blogs.setUpdatedBy(user.getEmail());
        blogs = blogRepository.save(blogs);
        return blogMapper.toBlogResponse(blogs);
    }


    public List<BlogResponse> getAll() {
        return blogRepository.findAll().stream().map(blogMapper::toBlogResponse).toList();
    }


    public BlogResponse update(int id,BlogRequest blogRequest) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXISTED));
        blogMapper.updateBlog(blog, blogRequest);
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        blog.setUser(user);
        blog.setUpdatedAt(LocalDateTime.now());
        blog.setUpdatedBy(user.getEmail());
        blog = blogRepository.save(blog);
        return blogMapper.toBlogResponse(blog);
    }

    public void delete(Integer blogId) {
        blogRepository.deleteById(blogId);
    }
}
