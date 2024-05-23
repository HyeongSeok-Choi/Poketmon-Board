package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.HashTag;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.HashTagRepository;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;


    //
    //게시물저장
    public Map<String,Board> save(Board board, Principal principal, Long boardId) {

        Map<String,Board> saves = new HashMap<>();

            // 게시판 조회 및 수정의 경우 boardId가 null이 아닌 조건을 사용함.
            if(boardId != null){

                // 존재하는 게시판과 해당하는 작성자를 repository를 통해 불러와서 변수에 저장.
                Board existboard =boardRepository.findById(boardId).get();
                Users user = userRepository.findById(existboard.getUserid().getUserId()).get();

                // 파라미터로 가져온 게시물 객체에 1.게시판 id, 2. 작성자, 3. 조회수를 저장함.
                board.setViewCount(existboard.getViewCount());
                board.setId(boardId);
                board.setUserid(user);
                Board value = boardRepository.save(board);
                String key ="modify";

                saves.put(key,value);

                return saves;
            }

            Users user;

            user = userRepository.findById(principal.getName()).get();

            board.setUserid(user);

        board.setViewCount(0L);

        Board value = boardRepository.save(board);

        String key ="create";
        saves.put(key,value);

        return saves;
    }

    //모든 게시물 출력
    public List<Board> findAll() {
    List<Board> boards = boardRepository.findAll();

    return boards;
    }

    //게시물 조회수 업데이트
    @Transactional
    public int getViewCounting(Long id) {
        
        return boardRepository.updateViews(id);

    }


    //게시물 번호로 조회
    public Board findById(Long id) {

       Board board = boardRepository.findById(id).get();

       return board;
    }

    //게시물 삭제
    public void deleteByIdbyboolean(Long id) {
        Board board = boardRepository.findById(id).get();

        board.setDeleted(true);

        boardRepository.save(board);
    }

    //삭제된 게시물 목록(엑셀 다운에 사용)
    public List<Board> getDeletedBoards() {

        List<Board> boards = boardRepository.findAllByDeleted(true);

        return boards;
    }

    //삭제된 게시물 복구
    public String restore(Long id){

     Board restoredBoard=  boardRepository.findById(id).get();

     restoredBoard.setDeleted(false);

     boardRepository.save(restoredBoard);

      return "복구되었습니다.";

    }

    //모든 게시물 목록 출력
    public Page<Board> findAll(Pageable pageable,String mainOrAdmin){

        if(mainOrAdmin.equals("admin")){

           return boardRepository.findAllByDeletedIsTrue(pageable);

        }

        return boardRepository.findAllByDeletedIsFalse(pageable);
    }

    //제목으로 검색 -리스트 출력
    public Page<Board> findAllByTitle(Pageable pageable, String title,String mainOrAdmin){

        if(mainOrAdmin.equals("admin")){

            return boardRepository.findByTitleContainingAndDeletedIsTrue(title,pageable);

        }
        return boardRepository.findByTitleContainingAndDeletedIsFalse(title,pageable);
    }

    //본문으로 검색 -리스트 출력
    public Page<Board> findAllByContent(Pageable pageable, String content,String mainOrAdmin){

        if(mainOrAdmin.equals("admin")){

            return boardRepository.findByContentContainingAndDeletedIsTrue(content,pageable);

        }

        return boardRepository.findByContentContainingAndDeletedIsFalse(content,pageable);
    }

    //작성자로 검색 -리스트 출력
    public Page<Board> findAllByUserId(Pageable pageable, String userId,String mainOrAdmin){
       Users findByUserIdContaining= userRepository.findByUserIdAndIsDeletedFalse(userId);

        if(mainOrAdmin.equals("admin")){

            return boardRepository.findByUseridAndDeletedIsTrue(findByUserIdContaining,pageable);

        }

        return boardRepository.findByUseridAndDeletedIsFalse(findByUserIdContaining,pageable);
    }


    //해시태그로 검색 - 리스트 출력
    public Page<Board> findAllByHashTag(Pageable pageable, String hashTag,String mainOrAdmin){

        String[] hashvalues = hashTag.split("#");

        List<HashTag> findHash = new ArrayList<>();

        for (String hash : hashvalues){
            System.out.println(hash+"해시값들");
            String trimhash = hash.trim();
            System.out.println();
            findHash.addAll(hashTagRepository.findByHashTagContent(trimhash));
        }


        //List<HashTag> findHash = hashTagRepository.findByHashTagContentContains(hashTag);
        List<Board> boards_hashTag = new ArrayList<>();

        for (HashTag hashTag1 : findHash) {
            boards_hashTag.add(hashTag1.getBoardId());
        }

        List<Board> boards_hashTag2 =boards_hashTag.stream().distinct().collect(Collectors.toList());

        List<Board> boards_hashTag3 = boards_hashTag2.stream().filter(board -> board.isDeleted() == false).collect(Collectors.toList());

        List<Board> boards_hashTag4 = boards_hashTag2.stream().filter(board -> board.isDeleted() == true).collect(Collectors.toList());

        if(mainOrAdmin.equals("admin")){
            PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), boards_hashTag4.size());
            Page<Board> BoardhashList = new PageImpl<>(boards_hashTag4.subList(start, end), pageRequest, boards_hashTag4.size());

            return BoardhashList;
        }

        // 요청으로 들어온 page와 한 page당 원하는 데이터의 갯수
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), boards_hashTag3.size());
        Page<Board> BoardhashList = new PageImpl<>(boards_hashTag3.subList(start, end), pageRequest, boards_hashTag3.size());

        return BoardhashList;

    }




}
