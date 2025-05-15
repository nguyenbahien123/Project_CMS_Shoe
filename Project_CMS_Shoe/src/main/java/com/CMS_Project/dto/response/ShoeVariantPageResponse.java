package com.CMS_Project.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ShoeVariantPageResponse extends PageResponseAbstract {
    List<ShoeVariantResponse> shoeVariants;
}
