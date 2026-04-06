package com.udxp.metadata.director;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/director")
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping("/create_director")
    public DirectorResponse createDirector(@RequestBody DirectorCreateRequest request) {
        return directorService.createDirector(request);
    }
    @GetMapping("/directorNames")
    public Page<String> getDirectorNames(Pageable pageable) {
        return directorService.getDirector(pageable);
    }
    @PutMapping("/{id}")
    public DirectorResponse updateDirector(@PathVariable Long id, @RequestBody DirectorCreateRequest request) {
        return directorService.updateDirector(id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
    }
}
