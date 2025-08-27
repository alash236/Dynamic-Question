import { Component } from '@angular/core';
import { HttpClientService } from '../@service/HttpClientService';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';
import { addDays, format } from 'date-fns';

@Component({
  selector: 'app-edit',
  imports: [FormsModule],
  templateUrl: './edit.html',
  styleUrl: './edit.scss'
})
export class Edit {
  question_id!:number;
  questionName!:string;
  questionText!:string;
  questionStart!:Date;
  questionEnd!:Date;
  questionPublish!:boolean;
  today:string='';
  maxStartTime:string='';
  maxEndTime: string = '';
  quizList:any[]=[];

  constructor(private http:HttpClientService,private route:ActivatedRoute,private location:Location){}

  ngOnInit(): void {
    this.question_id = Number(this.route.snapshot.paramMap.get('id'));
    this.today = format(new Date(),'yyyy-MM-dd');
    this.maxStartTime = format(addDays(new Date(),2),'yyyy-MM-dd');
    this.maxEndTime = format(addDays(new Date(),7),'yyyy-MM-dd');


    this.http.getApi(`http://localhost:8080/searchQuestion/${this.question_id}`).subscribe((questionRes:any)=>{
      this.questionName = questionRes.question.name;
      this.questionText = questionRes.question.text;
      this.questionStart = questionRes.question.start_Time;
      this.questionEnd = questionRes.question.end_Time;
      this.questionPublish = questionRes.question.publish;

      this.http.getApi(`http://localhost:8080/searchQuiz/${this.question_id}`).subscribe((quizRes:any)=>{
        this.quizList = quizRes.allQuestion;
      })
    })

  }

  onSubmit(){
    if(!this.questionTitleCheck()){
      return;
    }
    if (!this.validateAnswers()) {
      return;
    }
    const data = {
      name: this.questionName,
      text: this.questionText,
      start_Time: this.questionStart,
      end_Time: this.questionEnd,
      publish: true,
      quizList: this.quizList.map((quiz, index) => ({
        quiz_id: index + 1,
        name: quiz.name,
        type: quiz.type,
        option: quiz.type === 'text' ? [] : quiz.option,
        is_required: quiz.is_required
      }))
    }
    this.http.putApi(`http://localhost:8080/update/question/${this.question_id}`,data).subscribe((res:any)=>{

    })
  }

  OnCache(){
    if(!this.questionTitleCheck()){
      return;
    }
    if (!this.validateAnswers()) {
      return;
    }
    const data = {
      name: this.questionName,
      text: this.questionText,
      start_Time: this.questionStart,
      end_Time: this.questionEnd,
      publish: false,
      quizList: this.quizList.map((quiz, index) => ({
        quiz_id: index + 1,
        name: quiz.name,
        type: quiz.type,
        option: quiz.type === 'text' ? [] : quiz.option,
        is_required: quiz.is_required
      }))
    }
    this.http.putApi(`http://localhost:8080/update/question/${this.question_id}`,data).subscribe((res:any)=>{

    })
  }

  onCancel(){
    this.location.back();
  }

  onAnswerTypeChange(index: number) {
    const type = this.quizList[index].type;
    if (type === 'text') {
      this.quizList[index].option = [];
    } else {
      if (!this.quizList[index].option || this.quizList[index].option.length === 0) {
        this.quizList[index].option = ['', ''];
      }
    }
  }

  questionTitleCheck():boolean{
    if(this.questionName == null || this.questionName.trim() === ''){
      alert("問卷標題不能為空");
      return false;
    }
    if(this.questionText == null || this.questionText.trim() === ''){
      alert("問卷敘述不能為空");
      return false;
    }
    if(this.questionEnd < this.questionStart){
      alert("結束時間不可小於開始時間");
      return false;
    }
    return true;
  }

  validateAnswers(): boolean {
    for (let i = 0; i < this.quizList.length; i++) {
      const quiz = this.quizList[i];

      if (quiz.type === 'text') continue; // 跳過文字題

      const trimmedAnswers = quiz.option.map((ans: string) => ans.trim());
      const hasEmpty = trimmedAnswers.some((ans:string)=> ans === '');
      const hasDuplicate = new Set(trimmedAnswers).size !== trimmedAnswers.length;

      if (hasEmpty) {
        alert(`第 ${i + 1} 題有空白的答案，請確認所有答案都有填寫`);
        return false;
      }

      if (hasDuplicate) {
        alert(`第 ${i + 1} 題的答案有重複，請確認所有答案唯一`);
        return false;
      }
    }
    return true;
  }

  addTopic(){
    this.quizList.push({
      quiz_id:0,
      name: "",
      type: "single",
      option: ["",""],
      is_required: false
    });
  }

  deleteTopic(index:number){
    this.quizList.splice(index, 1);
  }

  AddAnswer(index:number){
    this.quizList[index].option.push("");
  }
  removeAnswer(topicIndex: number, answerIndex: number) {
    const answers = this.quizList[topicIndex].option;
    if (answers.length > 2) {
      answers.splice(answerIndex, 1);
    } else {
      alert('至少要保留二個答案');
    }
}

  deleteAnswer(topicIndex: number, answerIndex: number) {
    this.quizList[topicIndex].option.splice(answerIndex, 1);
  }
}
