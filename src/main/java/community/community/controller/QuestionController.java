package community.community.controller;

import community.community.dto.CommentDbDTO;
import community.community.dto.QuestionDTO;
import community.community.enums.CommentTypeEnum;
import community.community.service.CommentService;
import community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Coder on 2023/10/7 & 22:27.
 **/
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> questionRelated = questionService.selectRelated(questionDTO);
        List<CommentDbDTO> comments = commentService.listByTargetid(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relateds", questionRelated);
        return "question";
    }
}
