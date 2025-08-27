import { Component } from '@angular/core';
import { HttpClientService } from '../@service/HttpClientService';
import { ActivatedRoute, Router } from '@angular/router';
import { Service } from '../@service/service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update',
  imports: [CommonModule,FormsModule],
  templateUrl: './update.html',
  styleUrl: './update.scss'
})
export class Update {

  username='';
  userphone='';
  usermail='';
  userage='';
  userList=
  {
    name:"",
    phone:"",
    email:"",
    age:0,
  }

  //題目資訊
  name:string='';
  text:string='';
  start:string='';
  end:string='';

  //選項資訊
  quizList:any[]=[];
  //選完的答案
  answerList:any[] = [];

  question_ID:number=0;

  constructor(
    private http:HttpClientService,
    private router:Router,
    private route:ActivatedRoute,
    private service:Service,
    private location:Location){}

  ngOnInit(): void {
    this.question_ID = Number(this.route.snapshot.paramMap.get('id'));
    //找出對應id值得問卷
    this.http.getApi(`http://localhost:8080/searchQuestion/${this.question_ID}`).subscribe((res:any)=>{
      this.name = res.question.name;
      this.text = res.question.text;
      this.start = res.question.start_Time;
      this.end = res.question.end_Time;
    })

    //根據對應id值問卷找出對應的題目
    this.http.getApi(`http://localhost:8080/searchQuiz/${this.question_ID}`).subscribe((res:any)=>{
      this.quizList = res.allQuestion;
      this.answerList = this.quizList.map((item: any) => {
        if (item.type === 'multiple') {
          return { value: [] };
        } else {
          return { value: [] };
        }
      });
    })
    //自動填入使用者之訊息
    this.userList = this.service.getUserData();
    this.username = this.userList.name
    this.usermail = this.userList.email
    this.userphone = this.userList.phone
    this.userage = this.userList.age+""

  }

  onCheckboxChange(index: number, value: string, event: any) {
    const checked = event.target.checked;
    const currentValues = this.answerList[index].value;

    if (checked) {
      if (!currentValues.includes(value)) {
        currentValues.push(value);
      }
    } else {
      const i = currentValues.indexOf(value);
      if (i >= 0) {
        currentValues.splice(i, 1);
      }
    }
  }

  update(){
    const formattedAnswers = this.quizList.map((quiz, index) => {
      return {
        question_id: this.question_ID,
        quiz_id: quiz.quiz_id,
        userName:this.usermail,
        answeroption: Array.isArray(this.answerList[index].value)
          ? this.answerList[index].value      // 多選題
          : [this.answerList[index].value]    // 單選題/文字題也轉成陣列
      };
    });
    const data = {
      answerList: formattedAnswers
    };
    this.service.setUpdateData(data);
    this.router.navigate(['/preview',this.question_ID],{queryParams:{update:true}})
  }

  back_location(){
    this.location.back();
  }

}
