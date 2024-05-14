package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;

    public Board save(Board board) {

        Users users= new Users();

        users.setUserId("qnftlstm");

        users.setEmail("qnftlstm@naver.com");

        users.setRole(Role.pUSER);

        users.setPassword("chl153");

        users.setNickName("뿡뿡이");

        usersRepository.save(users);


        board.setUserid(users);

        Board result = boardRepository.save(board);

        return result;
    }

    public List<Board> findAll() {
    List<Board> boards = boardRepository.findAll();

    return boards;
    }


    public Board findById(Long id) {

       Board board = boardRepository.findById(id).get();

       return board;
    }

    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Board> findAllByTitle(Pageable pageable, String title){
        return boardRepository.findByTitleContaining(title,pageable);
    }
}
