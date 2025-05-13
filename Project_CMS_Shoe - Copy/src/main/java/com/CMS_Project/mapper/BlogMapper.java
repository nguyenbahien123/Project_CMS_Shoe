package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.BlogRequest;
import com.CMS_Project.dto.response.BlogResponse;
import com.CMS_Project.entity.Blogs;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BlogMapper {

    Blogs toBlog(BlogRequest blogRequest);

    BlogResponse toBlogResponse(Blogs blogs);

    void updateBlog(@MappingTarget Blogs blogs, BlogRequest blogRequest);
}
