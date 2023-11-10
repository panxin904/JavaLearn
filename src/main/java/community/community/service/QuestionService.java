package community.community.service;

import community.community.dto.PaginationDTO;
import community.community.dto.QuestionDTO;
import community.community.dto.QuestionQueryDTO;
import community.community.enums.SortEnum;
import community.community.exception.CustomizeErrorCode;
import community.community.exception.CustomizeException;
import community.community.mapper.QuestionExtMapper;
import community.community.mapper.QuestionMapper;
import community.community.mapper.UserMapper;
import community.community.model.Question;
import community.community.model.QuestionExample;
import community.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Coder on 2023/9/25 & 21:07.
 **/
@Service
@Slf4j
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(String search, String tag, String sort, Integer page, Integer size) {
//        if (StringUtils.isNotBlank(search)) {
//            String[] tags =StringUtils.split(search, " ");
//            search = Arrays.stream(tags).collect(Collectors.joining("|"));
//        }
        // 修复因为*和+号产生的搜索异常问题
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }
        /*添加首页按照最新、最热、零回复排序*/
        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);

                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        questionQueryDTO.setSearch(search);
        questionQueryDTO.setTag(tag);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
//        Integer offset = size * (page - 1);
//        RowBounds rowBounds = new RowBounds(offset, size);
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.setOrderByClause("gmt_create desc");
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(page);
        List<Question> list = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question qusetion : list) {
            User user = userMapper.selectByPrimaryKey(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(qusetion, questionDTO); // 快速将source bean中的属性拷贝到target bean
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        RowBounds rowBounds = new RowBounds(offset, size);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(userId);
        questionExample1.setOrderByClause("gmt_create desc");
        List<Question> list = questionMapper.selectByExampleWithRowbounds(questionExample1, rowBounds);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO); // 快速将source bean中的属性拷贝到target bean
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            log.info("create question id: {}", question.getCreator());
            questionMapper.insertSelective(question);
        } else {
            // 更新
            Question updateQuesion = questionMapper.selectByPrimaryKey(question.getId());
            updateQuesion.setTag(question.getTag());
            updateQuesion.setDescription(question.getDescription());
            updateQuesion.setTitle(question.getTitle());
            QuestionExample updateExample = new QuestionExample();
            updateExample.createCriteria()
                    .andIdEqualTo(question.getId());
            Integer updateResult = questionMapper.updateByExampleSelective(question, updateExample);
            if (updateResult == 0) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question questionRelated = new Question();
        questionRelated.setId(questionDTO.getId());
        questionRelated.setTag(regexpTag);

        List<Question> questionRelatedList = questionExtMapper.selectRelated(questionRelated);
        List<QuestionDTO> questionDTOList = questionRelatedList.stream().map(question -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
}
