import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { addDays, format } from 'date-fns'
import {MatTabsModule} from '@angular/material/tabs';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import { Service } from '../../@service/service';


@Component({
  selector: 'app-back-add',
  imports: [MatIconModule,FormsModule,CommonModule,MatTabsModule,MatFormFieldModule, MatSelectModule, MatInputModule],
  templateUrl: './back-add.html',
  styleUrl: './back-add.scss'
})

export class BackAdd {
  titleCheck: boolean = false;
  selectedTabIndex = 0;
  constructor(private router:Router,
    private service:Service){}

  questionName!:string;
  questionText!:string;
  questionState!:string;
  questionStart!:Date;
  questionEnd!:Date

  quizList=[{
    quiz_id:0,
    name:'',
    type:'',
    option:[] as any,
    is_required:false
  }]


  today:string='';
  maxStartTime:string='';
  maxEndTime: string = '';

  ngOnInit(): void {

    this.service.$questionDataSource.subscribe((res:any) => {
      this.questionName = res.name;
      this.questionText = res.text;
      this.questionStart = res.start_Time;
      this.questionEnd = res.end_Time;
      this.quizList = res.quizList;
    })


    this.today = format(new Date(),'yyyy-MM-dd');
    this.maxStartTime = format(addDays(new Date(),2),'yyyy-MM-dd');
    this.maxEndTime = format(addDays(new Date(),7),'yyyy-MM-dd');
  }


  questionTitleCheck(){
    if(this.questionName == null || this.questionName.trim() === ''){
      alert("問卷標題不能為空");
      return;
    }
    if(this.questionText == null || this.questionText.trim() === ''){
      alert("問卷敘述不能為空");
      return;
    }
    if(this.questionEnd < this.questionStart){
      alert("結束時間不可小於開始時間");
      return;
    }
    this.titleCheck = true;
    this.selectedTabIndex=1;
  }

  /**
   * 將資料暫存到service方便preview使用
   */
  questionTopicCheck(){
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
  };
    this.service.setQuestionData(data);
    this.router.navigate(['back/questionPreview'])
  }
  questionTopicCancel(){
    this.titleCheck=false;
    this.selectedTabIndex=0;
  }

  AddTopic(){
    this.quizList.push({
      quiz_id:0,
      name: "",
      type: "single",
      option: ["",""],
      is_required: false
    });
  }
  AddAnswer(index:number){
    this.quizList[index].option.push("");
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

removeAnswer(topicIndex: number, answerIndex: number) {
  const answers = this.quizList[topicIndex].option;
  if (answers.length > 2) {
    answers.splice(answerIndex, 1);
  } else {
    alert('至少要保留二個答案');
  }
}

  deleteTopic(index:number){
    const confirmDelete = confirm(`確定要刪除第 ${index + 1} 題嗎？`);
    if (confirmDelete) {
      this.quizList.splice(index, 1);
    }
  }
  questionTitleCancel(){
    this.service.setQuestionData(null);
    this.router.navigate(['/back']);
  }

}

