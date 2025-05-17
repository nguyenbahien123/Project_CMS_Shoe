package com.CMS_Project.service.impl;





import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.SizePageResponse;
import com.CMS_Project.dto.response.SizeResponse;
import com.CMS_Project.entity.Sizes;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.SizeMapper;
import com.CMS_Project.repository.SizeRepository;
import com.CMS_Project.repository.UserRepository;
import com.CMS_Project.service.SizeService;
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
public class SizeServiceImpl implements SizeService {

    SizeRepository sizeRepository;
    UserRepository userRepository;
    SizeMapper sizeMapper;

    @Override
    public SizeResponse create(SizeRequest sizeRequest) {
        Sizes size = sizeMapper.toSize(sizeRequest);
        sizeRepository.save(size);
        return sizeMapper.toSizeResponse(size);
    }

    @Override
    public SizeResponse update(Integer sizeId, SizeRequest sizeRequest) {
        Sizes sizes = sizeRepository.findById(sizeId).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTED));
        sizeMapper.updateSize(sizes, sizeRequest);
        sizeRepository.save(sizes);
        return sizeMapper.toSizeResponse(sizeRepository.save(sizes));
    }

    @Override
    public List<SizeResponse> getAll() {
        return sizeRepository.findAll().stream().map(sizeMapper::toSizeResponse).toList();
    }

    @Override
    public void delete(Integer sizeId) {
        sizeRepository.deleteById(sizeId);
    }

    @Override
    public SizePageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"sizeId");
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

        Page<Sizes> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = sizeRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = sizeRepository.findAll(pageable);
        }

        List<SizeResponse> sizeResponseList = entityPage.stream().map(sizeMapper::toSizeResponse).toList();
        SizePageResponse sizePageResponse = new SizePageResponse();
        sizePageResponse.setSizes(sizeResponseList);
        sizePageResponse.setTotalElements(entityPage.getTotalElements());
        sizePageResponse.setTotalPages(entityPage.getTotalPages());
        sizePageResponse.setPageNumber(entityPage.getNumber());
        sizePageResponse.setPageSize(entityPage.getSize());
        return sizePageResponse;

    }
}
