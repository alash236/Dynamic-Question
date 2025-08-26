    package com.example.quiz15.service.question.impl;

    import com.example.quiz15.constants.question.QuestionResMessageCodeEnum;
    import com.example.quiz15.dao.quiz.QuizDao;
    import com.example.quiz15.dao.question.QuestionDao;
    import com.example.quiz15.entity.question.Question;
    import com.example.quiz15.service.question.ifs.QuestionService;
    import com.example.quiz15.vo.question.*;
    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.List;

    import static com.example.quiz15.constants.question.QuestionResMessageCodeEnum.SEARCH_FAIL;
    import static com.example.quiz15.constants.quiz.QuizType.*;


    @Service
    public class QuestionServiceImpl implements QuestionService {

        @Autowired
        QuestionDao QuestionDao;

        @Autowired
        QuizDao quizDao;

        @Autowired
        private ObjectMapper mapper;


        /*  @Transactional 有效回朔的異常預設是 RunTimeException，若發生的異常不是 RunTimeException
         * 或其子類別的異常類型，資料皆不會回朔，因此想要讓只要發生任何一種異常時資料都要可以回朔，可以
         * 將 @Transactional 的有效範圍從 RunTimeException 提高至 Exception
         */
        @Transactional(rollbackOn = Exception.class)
        @Override
        public QuestionResponse addQuestion(AddQuestionRequest addQuestionRequest) throws Exception {
            try {
                //時間判定
                QuestionResponse checktime = checkTime(addQuestionRequest);
                if(checktime!=null){
                    throw new Exception(checktime.getMessage());
                }
                 QuestionDao.addQuestion(
                        addQuestionRequest.getName(),
                        addQuestionRequest.getText(),
                        addQuestionRequest.getStart_Time(),
                        addQuestionRequest.getEnd_Time(),
                        addQuestionRequest.isPublish());
                int question_id = quizDao.getLatestQuizId();
                List<QuizVo> quizvo = addQuestionRequest.getQuizList();
                for(QuizVo item:quizvo) {
                    List<String> optionList = item.getOption();
                    String str = mapper.writeValueAsString(optionList);
                    QuestionResponse check = checkInfo(item);
                    if(check!=null){
                        throw new Exception(check.getMessage());
                    }
                    quizDao.addQuiz(
                            item.getQuiz_id(),
                            question_id,
                            item.getName(),
                            item.getType(),
                            item.isIs_required(),
                            str);
                }
                return new QuestionResponse(
                        QuestionResMessageCodeEnum.SUCCESS_ADD.getCode(),
                        QuestionResMessageCodeEnum.SUCCESS_ADD.getMessage()
                );
            } catch (Exception e) {
                throw e;
            }
        }

        @Transactional(rollbackOn = Exception.class)
        @Override
        public QuestionResponse updateQuestion(int question_id,UpdateQuestionRequest updateQuestionRequest) throws Exception {
            try {
                QuestionDao.updateQuestion(
                        question_id,
                        updateQuestionRequest.getName(),
                        updateQuestionRequest.getText(),
                        updateQuestionRequest.getStart_Time(),
                        updateQuestionRequest.getEnd_Time(),
                        updateQuestionRequest.getPublish());
                quizDao.deleteQuiz(question_id);
                List<QuizVo> quizList = updateQuestionRequest.getQuizList();
                for(QuizVo item : quizList){
                    List<String> optionList = item.getOption();
                    String str = mapper.writeValueAsString(optionList);
                    QuestionResponse check = checkInfo(item);
                    if(check!=null){
                        throw new Exception(check.getMessage());
                    }
                    quizDao.addQuiz(
                            item.getQuiz_id(),
                            question_id,
                            item.getName(),
                            item.getType(),
                            item.isIs_required(),
                            str);
                }
                return new QuestionResponse(QuestionResMessageCodeEnum.SUCCESS_UPDATE.getCode(),
                        QuestionResMessageCodeEnum.SUCCESS_UPDATE.getMessage());
            }catch (Exception e){
                throw e;
            }
        }

        private QuestionResponse checkInfo(QuizVo item){
            if(!item.getType().equals(SINGLE.getType()) &&
                    !item.getType().equals(TEXT.getType()) &&
                    !item.getType().equals(MULTI.getType())){
                return new QuestionResponse(QuestionResMessageCodeEnum.TYPE_ERROR.getMessage());
            }
            if(!item.getType().equalsIgnoreCase(TEXT.getType()) &&
                    item.getOption().size() < 2){
                return new QuestionResponse(QuestionResMessageCodeEnum.OPTION_SIZE_ERROR.getMessage());
            }else if(item.getType().equalsIgnoreCase(TEXT.getType()) &&
                    !item.getOption().isEmpty()){
                return new QuestionResponse(QuestionResMessageCodeEnum.OPTION_TEXT_ERROR.getMessage());
            }
            return null;
        }

        private QuestionResponse checkTime(AddQuestionRequest addQuestionRequest){
            if(addQuestionRequest.getStart_Time().isAfter(addQuestionRequest.getEnd_Time())||
                    addQuestionRequest.getStart_Time().isBefore(LocalDate.now())){
                return new QuestionResponse(QuestionResMessageCodeEnum.START_TIME_ERROR.getMessage());
            }else if(addQuestionRequest.getEnd_Time().isBefore(LocalDate.now())||
                    addQuestionRequest.getEnd_Time().isBefore(addQuestionRequest.getStart_Time())) {
                return new QuestionResponse(QuestionResMessageCodeEnum.END_TIME_ERROR.getMessage());
            }
            return null;
        }


        @Transactional(rollbackOn = Exception.class)
        @Override
        public QuestionResponse deleteQuestion(int id) throws Exception{
            try {
                Question question = QuestionDao.getQuestion(id);
                if(question==null){
                    throw new Exception(SEARCH_FAIL.getMessage());
                }
                QuestionDao.delete(id);
                quizDao.deleteQuiz(id);
                return new QuestionResponse(
                        QuestionResMessageCodeEnum.SUCCESS_DELETE.getCode(),
                        QuestionResMessageCodeEnum.SUCCESS_DELETE.getMessage()
                );
            } catch (Exception e) {
                throw e;
            }
        }


        @Override
        public QuestionResponse searchAllQuestion() {
            List<Question> res = QuestionDao.getAllQuestion();
            return new QuestionResponse(QuestionResMessageCodeEnum.SUCCESS_SEARCH.getCode(),res);
        }


        @Override
        public QuestionResponse searchQuestion(int id) {
            Question question = QuestionDao.getQuestion(id);
            if(question == null){
                return new QuestionResponse(SEARCH_FAIL.getCode(),
                        SEARCH_FAIL.getMessage());
            }
            return new QuestionResponse(QuestionResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                     question);
        }


        @Override
        public QuestionResponse searchQuestionQuiz(int question_id) {
            List<QuizRes> quiz = quizDao.searchQuiz(question_id);
            if(quiz == null){
                return new QuestionResponse(SEARCH_FAIL.getCode(),
                SEARCH_FAIL.getMessage());
            }
            return new QuestionResponse(QuestionResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                    quiz);
        }

        @Override
        public QuestionResponse searchAllPublish(){
            List<Question> res = QuestionDao.getAllPublish();
            return new QuestionResponse(QuestionResMessageCodeEnum.SUCCESS_SEARCH.getCode(),res);
        }

        @Override
        public QuestionResponse searchAllQuiz() {
            List<QuizRes> res = quizDao.searchAllQuiz();
            if(res==null) return new QuestionResponse(404,"找不到題目列表資訊");
            for(QuizRes item:res){
                if(item == null){
                    return new QuestionResponse(404,"找不到題目資訊");
                }
            }
            return new QuestionResponse(QuestionResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                    res);
        }
    }
