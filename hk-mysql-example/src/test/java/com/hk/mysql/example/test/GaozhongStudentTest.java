package com.hk.mysql.example.test;

import com.hk.commons.poi.excel.annotations.ReadExcelField;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.ReadResult;
import com.hk.commons.poi.excel.read.DomReadExcel;
import com.hk.commons.poi.excel.read.ReadableExcel;
import com.hk.commons.util.JsonUtils;
import com.hk.commons.util.StringUtils;
import com.hk.core.data.jdbc.JdbcSession;
import com.hk.core.test.BaseTest;
import com.hk.mysql.examples.MysqlExampleApplication;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 非学业测试修改3稿\【3】高中二年级\【1】高中学生问卷
 *
 * @author huangkai
 * @date 2019-02-14 11:19
 */
@SpringBootTest(classes = {MysqlExampleApplication.class})
public class GaozhongStudentTest extends BaseTest {

    @Autowired
    private JdbcSession jdbcSession;

    @Test
    public void gaozhongStudent() throws IOException {
        ReadParam<ScoringRules> readParam = ReadParam.<ScoringRules>builder()
                .beanClazz(ScoringRules.class)
                .build();
        ReadableExcel<ScoringRules> readableExcel = new DomReadExcel<>(readParam);
        ReadResult<ScoringRules> readResult = readableExcel.read(new File("C:/Users/sjq-278/Desktop/非学业测试修改3稿/【3】高中二年级/【1】高中学生问卷/高中学生问卷计分规则.xlsx"));
        List<ScoringRules> data = readResult.getAllSheetData();

        FileInputStream fileInputStream = new FileInputStream("C:/Users/sjq-278/Desktop/非学业测试修改3稿/【3】高中二年级/【1】高中学生问卷/1【问卷测试题】高中学生问卷.docx");

        XWPFDocument document = new XWPFDocument(fileInputStream);
        // 获取所有段落
        List<QuestionnaireQuestion> questions = new ArrayList<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String text = StringUtils.trimToEmpty(paragraph.getParagraphText());
            if (StringUtils.startWithNumber(text)) {
                QuestionnaireQuestion question = new QuestionnaireQuestion();
                question.setQuestionCode(StringUtils.substringBefore(text, ". "));
                question.setQuestionContent(StringUtils.substringAfter(text, ". "));
                question.setQuestionId(UUID.randomUUID().toString());
                questions.add(question);
            } else if (StringUtils.startWithLetter(text)) {
                QuestionnaireQuestion question = questions.get(questions.size() - 1);
                List<QuestionnaireAnswer> answers = question.getAnswers();
                if (answers == null) {
                    answers = new ArrayList<>();
                }
                QuestionnaireAnswer answer = new QuestionnaireAnswer();
                answer.setAnswer(StringUtils.trimToEmpty(StringUtils.substring(text, 2)));
                answer.setAnswerCode(StringUtils.substring(text, 0, 1));
                answer.setQuestionId(question.getQuestionId());
                answer.setAnswerId(UUID.randomUUID().toString());
                answer.setScore(data.stream().filter(item -> StringUtils.equals(item.no, question.questionCode)).findFirst()
                        .get().getScore(answer.answerCode));//分数
                answers.add(answer);
                question.setAnswers(answers);
            }
        }
        System.out.println(JsonUtils.serialize(questions, true));
        System.out.println(questions.size());
        jdbcSession.batchUpdate("INSERT INTO `et_questionnaire_question_temp`(`question_id`, `questionnaire_id`, `question_code`,`question_content`, `question_type`,`cfgtype`, `last_up_time`, `create_time`) VALUES " +
                "(:questionId,:questionnaireId,:questionCode,:questionContent,:questionType,:cfgtype,:lastUpTime,:createTime)", questions);
        for (QuestionnaireQuestion question : questions) {
            jdbcSession.batchUpdate("INSERT INTO `et_questionnaire_answer_temp`(`answer_id`, `question_id`, `questionnaire_id`, `answer_code`, `answer`,`score`, `last_up_time`, `create_time`) VALUES " +
                    "(:answerId,:questionId,:questionnaireId,:answerCode,:answer,:score,:lastUpTime,:createTime)", question.getAnswers());
        }
    }


    /**
     * 计分规则
     */
    @Data
    private static class ScoringRules {

        @ReadExcelField(start = 0)
        private String no;

        @ReadExcelField(start = 4)
        private String a;

        @ReadExcelField(start = 5)
        private String b;

        @ReadExcelField(start = 6)
        private String c;

        @ReadExcelField(start = 7)
        private String d;

        @ReadExcelField(start = 8)
        private String e;

        @ReadExcelField(start = 9)
        private String f;

        @ReadExcelField(start = 10)
        private String g;

        @ReadExcelField(start = 11)
        private String h;

        @ReadExcelField(start = 12)
        private String i;

        @ReadExcelField(start = 13)
        private String j;

        public Double getScore(String code) {
            code = code.toUpperCase();
            String value;
            switch (code) {
                case "A":
                    value = a;
                    break;
                case "B":
                    value = b;
                    break;
                case "C":
                    value = c;
                    break;
                case "D":
                    value = d;
                    break;
                case "E":
                    value = e;
                    break;
                case "F":
                    value = f;
                    break;
                case "G":
                    value = g;
                    break;
                case "H":
                    value = h;
                    break;
                case "I":
                    value = i;
                    break;
                case "J":
                    value = j;
                    break;
                default:
                    value = "0";
            }
            try {
                return Double.parseDouble(value);
            } catch (Exception e) {
                return 0D;
            }
        }

    }

    /**
     * 卷题目 : et_questionnaire_question
     */
    @Data
    private static class QuestionnaireQuestion {

        private String questionId;

        private String questionnaireId = "eb07e651-51ec-42c5-8919-8bb0f3537549";

        private String questionCode;


        private String questionContent;

        /**
         * 单选
         */
        private String questionType = "01";

        /**
         *
         */
        private String cfgtype = "0";

        private Date lastUpTime = new Date();

        private Date createTime = new Date();

        private List<QuestionnaireAnswer> answers;
    }

    /**
     * 卷答案: et_questionnaire_answer
     */
    @Data
    private static class QuestionnaireAnswer {

        private String answerId;

        private String questionnaireId = "eb07e651-51ec-42c5-8919-8bb0f3537549";

        private String questionId;

        private String answerCode;

        private String answer;

        private Double score = 0D;

        private Date lastUpTime = new Date();

        private Date createTime = new Date();

    }
}