package com.leverx.service;

import com.leverx.model.Comment;
import com.leverx.repository.CommentRepository;
import com.leverx.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CommentServiceTest {

    private CommentService service;
    @Mock
    private CommentRepository repository;
    @Autowired
    private TokenUtil tokenUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CommentService(repository, tokenUtil);
    }

    @Test
    void findAllComments() {
        List<Comment> comments = service.findAllComments(3);
        assertNotNull(comments);
    }

    @Test
    void findComment() {
        Comment comment = service.findComment(2, 3);
        assertNotNull(comment);
    }
}