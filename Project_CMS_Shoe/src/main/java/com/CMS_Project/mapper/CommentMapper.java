package com.CMS_Project.mapper;

import com.CMS_Project.dto.response.CommentResponse;
import com.CMS_Project.entity.Comments;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {
    CommentResponse toCommentResponse(Comments comments);
}
