package com.udxp.service;

import com.udxp.dto.request.DirectorCreateRequest;
import com.udxp.dto.response.DirectorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DirectorService {
    DirectorResponse createDirector(DirectorCreateRequest request);
    DirectorResponse updateDirector(Long id, DirectorCreateRequest request);
    void deleteDirector(Long id);
    Page<String> getDirector(Pageable pageable);
}
