package com.udxp.masterdata.director.service;

import com.udxp.masterdata.director.dto.response.DirectorResponse;
import com.udxp.masterdata.director.dto.request.DirectorCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DirectorService {
    DirectorResponse createDirector(DirectorCreateRequest request);
    DirectorResponse updateDirector(Long id, DirectorCreateRequest request);
    void deleteDirector(Long id);
    Page<String> getDirector(Pageable pageable);
}
