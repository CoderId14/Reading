package com.example.reading.service.impl;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.entity.ChapterEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.exception.UnauthorizedException;
import com.example.reading.exception.WebapiException;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.ChapterRepository;
import com.example.reading.repository.NewRepository;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.converter.ChapterConverter;
import com.example.reading.service.IChapterService;
import com.example.reading.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
//    Thứ tự data: chap hiện tại -> chap trước -> chap sau
    public List<ChapterDTO> getChapter(Long newId, Long id) {
        ChapterEntity chapterEntity = chapterRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(CHAPTER, ID, id));
        NewEntity newEntity = newRepository.findById(newId).
                orElseThrow(() -> new ResourceNotFoundException(NEWS, ID, id));

        if(chapterEntity.getNews().getId() == newEntity.getId()){
            Optional<ChapterEntity> tempParentChapter = Optional.ofNullable(chapterEntity.getParent());

            Optional<ChapterEntity> tempChildChapter = Optional.ofNullable(chapterEntity.getChild());
            ChapterDTO responseChap = chapterConverter.toDTO(chapterEntity);

            List<ChapterDTO> listChap = new ArrayList<>();

            listChap.add(responseChap);
            if(tempParentChapter.isPresent()){

                Long parentId = tempParentChapter.get().getId();


                    Optional<ChapterEntity> prevChapter = chapterRepository.findById(parentId);
                    if(prevChapter.isPresent()){
                        ChapterDTO responsePrev = chapterConverter.toDTO(prevChapter.get());

                        listChap.add(responsePrev);
                    }

            }
            if(tempChildChapter.isPresent()){
                Long childId = tempChildChapter.get().getId();


                    Optional<ChapterEntity> childChapter = chapterRepository.findById(childId);
                    if(childChapter.isPresent()){
                        ChapterDTO responseChild = chapterConverter.toDTO(childChapter.get());
                        listChap.add(responseChild);
                    }

            }

            return listChap;
        }
        else{
            throw new WebapiException(HttpStatus.BAD_REQUEST, CHAPTER_DONT_BELONG_TO_NEWS);
        }


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
        contents.forEach(temp -> result.add(chapterConverter.toDTO(temp)));
        return new PagedResponse<>(result, chapters.getNumber(), chapters.getSize(),
                chapters.getTotalElements(), chapters.getTotalPages(), chapters.isLast());
    }

    @Override
//    Cấu trúc các chapter dạng linked list: Mỗi chapter liên kết với prevChapter(chapter trước) và childChapter(chapter sau)
//    Không nên nhận thêm giá trị childId vì khá khó quản lý(VD parentId =2 childId =5 thì nó chapter 3->4 cũ bị cô lập không truy xuất được)
    public ChapterDTO addChapter(ChapterDTO chapterRequest, UserPrincipal currentUser) {
        if(newRepository.existsById(chapterRequest.getNewId())
                ){

            NewEntity newEntity = newRepository.findById(chapterRequest.getNewId()).
                    orElseThrow(() -> new ResourceNotFoundException(NEWS, ID, chapterRequest.getNewId()));
            if(newEntity.getCreatedBy().equals(currentUser.getUsername())
                    || currentUser.getAuthorities().contains(
                    new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){
                ChapterEntity chapterEntity = chapterConverter.toEntity(chapterRequest);

                chapterEntity.setNews(newRepository.findById(
                        chapterRequest.getNewId()).orElseThrow(()
                        -> new ResourceNotFoundException(NEWS, ID, chapterRequest.getNewId())));
//                Phải kiểm tra null trước vì method findById không nhận id null
                Long parentId = chapterRequest.getParentId();

                Long childId = chapterRequest.getChildId();

//                Sau khi check id != null sẽ gán ngược lại
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

//              Set liên kết của parent và child với chapter thêm vào
                if(tempPrevChapter != null)
                    tempPrevChapter.setChild(chapterEntity);

                if (tempChildChapter !=null)
                    tempChildChapter.setParent(chapterEntity);



                chapterRepository.save(chapterEntity);

                return chapterConverter.toDTO(chapterEntity);
            }

        }
        else {
            throw  new ResourceNotFoundException("News", "Id not found", chapterRequest.getNewId());
        }
        return null;
    }

    @Override
    public ApiResponse deleteChapter(Long newId, Long id, UserPrincipal currentUser) {
        NewEntity newEntity = newRepository.findById(newId).
                orElseThrow(() -> new ResourceNotFoundException(NEWS, ID, id));

        if(newEntity.getCreatedBy().equals(currentUser.getUsername()) || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){
            ChapterEntity chapterEntity = chapterRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException(CHAPTER, ID, id));

            Optional<ChapterEntity> prevChapter = Optional.ofNullable(chapterEntity.getParent());
            Optional<ChapterEntity> childChapter = Optional.ofNullable(chapterEntity.getChild());

//            Check vị trí của chapter trong linked list
            if(prevChapter.isPresent() || childChapter.isPresent()){

//                Nếu chapter nằm giữa
                if(childChapter.isPresent() && prevChapter.isPresent()){
                    chapterEntity.setParent(null);
                    chapterEntity.setChild(null);
                    prevChapter.get().setChild(childChapter.get());
                    childChapter.get().setParent(prevChapter.get());
                }
//                Chapter nằm ở đầu hoặc cuối
                else {
//                    Chapter nằm cuối
                    if(prevChapter.isPresent()){
                        chapterEntity.setParent(null);
                        prevChapter.get().setChild(null);
                    }
//                    Chapter nằm đầu
                    else{
                        chapterEntity.setChild(null);
                        childChapter.get().setParent(null);
                    }

                }
            }

            chapterRepository.delete(chapterEntity);

            return new ApiResponse(Boolean.TRUE, "You successfully deleted chapter");

        }
        ApiResponse apiResponse = new ApiResponse(
                Boolean.FALSE,
                "You don't have permission to delete this chapter");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
//    Chỉ update được nội dung chứ không thay đổi được vị trí node chapter
    public ChapterDTO updateChapter(Long newId,Long id, ChapterDTO chapterRequest, UserPrincipal currentUser) {
        NewEntity newEntity = newRepository.findById(newId).orElseThrow(
                () -> new ResourceNotFoundException(NEWS, ID, chapterRequest.getNewId()));

        ChapterEntity chapterEntity = chapterRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CHAPTER, ID, id));

        if(!chapterEntity.getNews().equals(newEntity)){
            throw new WebapiException(HttpStatus.BAD_REQUEST, CHAPTER_DONT_BELONG_TO_NEWS);
        }

        if(newEntity.getCreatedBy().equals(currentUser.getUsername())
        || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            chapterEntity.setContent(chapterRequest.getContent());
            chapterEntity.setDescription(chapterRequest.getDescription());
            chapterRepository.save(chapterEntity);

            return chapterConverter.toDTO(chapterEntity);
        }
        throw new WebapiException(HttpStatus.UNAUTHORIZED, "You don't have permission to update this chapter");
    }
}
