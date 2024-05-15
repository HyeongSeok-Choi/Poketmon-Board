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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;


    //더미 데이터로 테스트중
    public Board save(Board board) {

        Users users= new Users();

        users.setUserId("qnftlstm");

        users.setEmail("qnftlstm@naver.com");

        users.setRole(Role.pUSER);

        users.setPassword("chl153");

        users.setNickName("뿡뿡이");

        userRepository.save(users);


        board.setUserid(users);

        Board result = boardRepository.save(board);

        return result;
    }

    //모든 게시물 출력
    public List<Board> findAll() {
    List<Board> boards = boardRepository.findAll();

    return boards;
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
       Users findByUserIdContaining= userRepository.findByUserIdContaining(userId);

        return boardRepository.findByUserid(findByUserIdContaining,pageable);
    }


    //해시태그로 검색 - 리스트 출력
    public Page<Board> findAllByHashTag(Pageable pageable, String hashTag){


        List<HashTag> findHash = hashTagRepository.findByHashTagContentContaining(hashTag);
        List<Board> boards_hashTag = new ArrayList<>();


        for (HashTag hashTag1 : findHash) {
            boards_hashTag.add(hashTag1.getBoardId());
        }


        return new PageImpl<>(boards_hashTag,pageable,boards_hashTag.size());

    }




}
