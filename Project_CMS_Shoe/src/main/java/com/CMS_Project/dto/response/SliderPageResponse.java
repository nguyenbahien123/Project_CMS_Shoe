package com.CMS_Project.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SliderPageResponse extends PageResponseAbstract{
    List<SliderResponse> sliders;
}
