package com.CMS_Project.service;


import com.CMS_Project.dto.response.CommentResponse;
import com.CMS_Project.mapper.CommentMapper;
import com.CMS_Project.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;

    public List<CommentResponse> getAll() {
        return commentRepository.findAll().stream().map(commentMapper::toCommentResponse).toList();
    }

}
