package com.example.reading.service.impl;

import com.example.reading.api.ChapterController;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.ChapterEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.ChapterRepository;
import com.example.reading.repository.NewRepository;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.converter.ChapterConverter;
import com.example.reading.service.IChapterService;
import com.example.reading.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.reading.utils.AppConstants.*;

@RequiredArgsConstructor
@Service
public class ChapterService implements IChapterService {
    private final ChapterRepository chapterRepository;

    private final ChapterConverter chapterConverter;

    private final NewRepository newRepository;

    private final RoleRepository roleRepository;




    @Override
    public List<ChapterDTO> getChapter(Long id) {
        ChapterEntity chapterEntity = chapterRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(CHAPTER, ID, id));
        Optional<ChapterEntity> prevChapter = chapterRepository.findChapterEntityByIdAndParentId(id,chapterEntity.getParent().getId());
        Optional<ChapterEntity> childChapter = chapterRepository.findChapterEntityByIdAndChildId(id, chapterEntity.getChild().getId());
        ChapterDTO responsePrev = chapterConverter.toDTO(Optional.ofNullable(prevChapter.get()).get());
        ChapterDTO responseChild = chapterConverter.toDTO(Optional.ofNullable(childChapter.get()).get());
        ChapterDTO responseChap = chapterConverter.toDTO(chapterEntity);
        List<ChapterDTO> listChap = new ArrayList<>();
        listChap.add(responseChap);
        listChap.add(responsePrev);
        listChap.add(responseChild);
        return listChap;

    }

    @Override
    public PagedResponse<ChapterDTO> getAllChapter(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_DATE);

        Page<ChapterEntity> chapters = chapterRepository.findByNewsId(id, pageable);

        List<ChapterEntity> contents = chapters.getNumberOfElements() == 0 ?
                Collections.emptyList()
                :
                chapters.getContent();
        List<ChapterDTO> result = new ArrayList<>();
        contents.forEach(temp ->{
            result.add(chapterConverter.toDTO(temp));
        });
        return new PagedResponse<>(result, chapters.getNumber(), chapters.getSize(),
                chapters.getTotalElements(), chapters.getTotalPages(), chapters.isLast());
    }

    @Override
//    Cấu trúc các chapter dạng linked list: Mỗi chapter liên kết với prevChapter(chapter trước) và childChapter(chapter sau)
    public ChapterDTO addChapter(ChapterDTO chapterRequest, UserPrincipal currentUser) {
        if(newRepository.existsById(chapterRequest.getNewId())
                ){


            NewEntity newEntity = newRepository.findById(chapterRequest.getNewId()).get();
            if(newEntity.getCreatedBy().equals(currentUser.getUsername())
                    || currentUser.getAuthorities().contains(
                    new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){
                ChapterEntity chapterEntity = chapterConverter.toEntity(chapterRequest);

                chapterEntity.setNews(newRepository.findById(chapterRequest.getNewId()).get());

                Long parentId = chapterRequest.getParentId();

                Long childId = chapterRequest.getChildId();

                ChapterEntity tempPrevChapter =null;
                ChapterEntity tempChildChapter =null;
                if(parentId != null){
                    Optional<ChapterEntity> prevChapter =chapterRepository.findById(
                            parentId);
                    if(prevChapter.isPresent()){
                        chapterEntity.setParent(prevChapter.get());
                        tempPrevChapter=prevChapter.get();
                    }


                }

                if(childId != null){
                    Optional<ChapterEntity> childChapter = chapterRepository.findById(
                            childId);
                    if(childChapter.isPresent()){
                        chapterEntity.setChild(childChapter.get());
                        tempChildChapter=childChapter.get();
                    }
                }

                if(tempPrevChapter != null)
                    tempPrevChapter.setChild(chapterEntity);

                if (tempChildChapter !=null)
                    tempChildChapter.setParent(chapterEntity);



                chapterRepository.save(chapterEntity);

                ChapterDTO result = chapterConverter.toDTO(chapterEntity);

                return result;
            }

        }
        else {
            throw  new ResourceNotFoundException("New, prevChapter, childChapter", "", chapterRequest.getNewId());
        }
        return null;
    }
}
