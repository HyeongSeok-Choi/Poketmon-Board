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


    //게시물 저장
    public Map<String,Board> save(Board board, Principal principal, Long boardId) {

        Map<String,Board> saves = new HashMap<>();

            if(boardId != null){

                Board existboard =boardRepository.findById(boardId).get();
                Users user = userRepository.findById(existboard.getUserid().getUserId()).get();

                board.setViewCount(existboard.getViewCount());
                board.setId(boardId);
                board.setUserid(user);
                Board result = boardRepository.save(board);
                String value ="modify";

                saves.put(value,result);

                return saves;
            }

            Users user;

            user = userRepository.findById(principal.getName()).get();

            board.setUserid(user);

        board.setViewCount(0L);

        Board result = boardRepository.save(board);

        String value ="create";
        saves.put(value,result);

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
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    //모든 게시물 목록 출력
    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    //제목으로 검색 -리스트 출력
    public Page<Board> findAllByTitle(Pageable pageable, String title){
        return boardRepository.findByTitleContaining(title,pageable);
    }

    //본문으로 검색 -리스트 출력
    public Page<Board> findAllByContent(Pageable pageable, String content){
        return boardRepository.findByContentContaining(content,pageable);
    }

    //작성자로 검색 -리스트 출력
    public Page<Board> findAllByUserId(Pageable pageable, String userId){
       Users findByUserIdContaining= userRepository.findByUserIdAndIsDeletedFalse(userId);

        return boardRepository.findByUserid(findByUserIdContaining,pageable);
    }


    //해시태그로 검색 - 리스트 출력
    public Page<Board> findAllByHashTag(Pageable pageable, String hashTag){

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


        // 요청으로 들어온 page와 한 page당 원하는 데이터의 갯수
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), boards_hashTag2.size());
        Page<Board> BoardhashList = new PageImpl<>(boards_hashTag2.subList(start, end), pageRequest, boards_hashTag2.size());

        return BoardhashList;

    }




}
