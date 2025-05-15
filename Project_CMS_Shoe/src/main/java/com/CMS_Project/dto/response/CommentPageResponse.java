package com.CMS_Project.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentPageResponse extends PageResponseAbstract {
    List<CommentResponse> comments;
}
