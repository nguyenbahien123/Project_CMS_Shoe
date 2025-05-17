package com.CMS_Project.service.impl;

import com.CMS_Project.dto.request.BlogRequest;
import com.CMS_Project.dto.response.BlogPageResponse;
import com.CMS_Project.dto.response.BlogResponse;
import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.BlogMapper;
import com.CMS_Project.repository.BlogRepository;
import com.CMS_Project.repository.UserRepository;
import com.CMS_Project.service.BlogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogServiceImpl implements BlogService {

    BlogRepository blogRepository;
    BlogMapper blogMapper;
    UserRepository userRepository;

    @Override
    public BlogResponse create(BlogRequest blogRequest) {
        Blogs blogs = blogMapper.toBlog(blogRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        blogs.setUser(user);
        blogs = blogRepository.save(blogs);
        return blogMapper.toBlogResponse(blogs);
    }

    @Override
    public List<BlogResponse> getAll() {
        return blogRepository.findAll().stream().map(blogMapper::toBlogResponse).toList();
    }

    @Override
    public BlogResponse update(int id,BlogRequest blogRequest) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXISTED));
        blogMapper.updateBlog(blog, blogRequest);
        blog = blogRepository.save(blog);
        return blogMapper.toBlogResponse(blog);
    }

    @Override
    public void delete(Integer blogId) {
        blogRepository.deleteById(blogId);
    }


    @Override
    public BlogPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"blogId");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("^(\\w+):(asc|desc)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(2).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,columnName);
                }else{
                    order = new Sort.Order(Sort.Direction.DESC,columnName);
                }
            }
        }

        int pageNo = 0;
        if(page > 0){
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));

        Page<Blogs> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = blogRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = blogRepository.findAll(pageable);
        }

        List<BlogResponse> blogResponseList = entityPage.stream().map(blogMapper::toBlogResponse).toList();

        BlogPageResponse blogPageResponse = new BlogPageResponse();
        blogPageResponse.setTotalElements(entityPage.getTotalElements());
        blogPageResponse.setTotalPages(entityPage.getTotalPages());
        blogPageResponse.setPageNumber(entityPage.getNumber());
        blogPageResponse.setPageSize(entityPage.getSize());
        blogPageResponse.setBlogs(blogResponseList);
        return blogPageResponse;
    }
}
