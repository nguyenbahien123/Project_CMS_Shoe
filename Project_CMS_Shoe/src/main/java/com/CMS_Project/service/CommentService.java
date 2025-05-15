package com.CMS_Project.service;


import com.CMS_Project.dto.response.CommentPageResponse;
import com.CMS_Project.dto.response.CommentResponse;
import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Comments;
import com.CMS_Project.mapper.CommentMapper;
import com.CMS_Project.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<CommentResponse> getAll() {
        return commentRepository.findAll().stream().map(commentMapper::toCommentResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CommentPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"commentId");
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

        Page<Comments> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = commentRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = commentRepository.findAll(pageable);
        }

        List<CommentResponse> commentResponseList = entityPage.stream().map(commentMapper::toCommentResponse).toList();
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        commentPageResponse.setComments(commentResponseList);
        commentPageResponse.setPageNumber(entityPage.getNumber());
        commentPageResponse.setPageSize(entityPage.getSize());
        commentPageResponse.setTotalElements(entityPage.getTotalElements());
        commentPageResponse.setTotalPages(entityPage.getTotalPages());
        return commentPageResponse;
    }
}
