import { Component } from '@angular/core';
import { Service } from '../@service/service';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from '../@service/HttpClientService';
import { format } from 'date-fns';
import { RouterModule } from '@angular/router';
import { isEmpty } from 'rxjs';
@Component({
  selector: 'app-preview',
  imports: [CommonModule, RouterModule],
  templateUrl: './preview.html',
  styleUrl: './preview.scss'
})
export class Preview {
  //問卷
  question_ID:number = 0;
  name = '';
  text = '';
  start = '';
  end = '';
  //題目
  quizList:any[]=[];
  //答案
  answerList: any[] = [];
  //使用者資訊
  username = '';
  userphone = '';
  usermail = '';
  userage = '';
  userList=
  {
    name:"",
    phone:"",
    email:"",
    age:0,
  }

  backcheck = false;
  updateCheck = false;
element: any;
  constructor(
    private location: Location,
    private service: Service,
    private route:ActivatedRoute,
    private router:Router,
    private http:HttpClientService) {}

  ngOnInit() {
    this.question_ID = Number(this.route.snapshot.paramMap.get('id'));
    this.http.getApi(`http://localhost:8080/searchQuestion/${this.question_ID}`).subscribe((res:any)=>{
      this.name = res.question.name;
      this.text = res.question.text;
      this.start = res.question.start_Time;
      this.end = res.question.end_Time;
    })
    this.http.getApi(`http://localhost:8080/searchQuiz/${this.question_ID}`).subscribe((res:any)=>{
      this.quizList = res.allQuestion
      console.log(this.quizList)
    })

    this.route.queryParams.subscribe((params)=>{
      const update = params['update'];
      const back = params['backcheck'];
      if(update){
        this.updateCheck = true;
        this.service.$updateDataSource.subscribe((res:any)=>{
          for(let item of res.answerList){
            this.answerList.push(item.answeroption)
          }
        })

        this.userList = this.service.getUserData();
        this.username = this.userList.name
        this.usermail = this.userList.email
        this.userphone = this.userList.phone
        this.userage = this.userList.age+""

      }else if(back){
        this.backcheck=true;
        this.service.$feedbackData.subscribe((feedbackData:any)=>{
          this.http.getApi(`http://localhost:8080/searchUser/${feedbackData.email}`).subscribe((userdata:any)=>{
            this.username = userdata.user.name;
            this.usermail = userdata.user.email;
            this.userphone = userdata.user.phone;
            this.userage = userdata.user.age;

            this.http.getApi(`http://localhost:8080/search/answer/${feedbackData.question_id}/${feedbackData.email}`).subscribe((answer:any)=>{
              for(let item of answer.allAnswer){
                this.answerList.push(item.answeroption)
              }
            })
          })
        })
      }else{
        this.service.$questionDataSource.subscribe((res:any)=>{
          for(let item of res.answerList){
            this.answerList.push(item.answeroption)
          }
        })

        this.userList = this.service.getUserData();
        this.username = this.userList.name
        this.usermail = this.userList.email
        this.userphone = this.userList.phone
        this.userage = this.userList.age+""
      }
    });
  }
  back(){
    this.location.back();
  }
  go_statistics() {
    const hasUnansweredRequired = this.quizList.some((quiz, index) => {
      const answer = this.answerList[index];

      if (!quiz.is_required) return false;

      return (
        answer === undefined ||
        answer === null ||
        (Array.isArray(answer) && answer.length === 0)
      )
    });

    if (hasUnansweredRequired) {
      alert("請完成所有必填題");
      return;
    }

    const formattedAnswers = this.quizList.map((quiz, index) => {
      return {
        question_id: this.question_ID,
        quiz_id: quiz.quiz_id,
        username:this.usermail,
        answeroption: this.answerList[index]
      };
    });
    const data = {
      answerList: formattedAnswers
    };

    const feedbackdata = {
      question_id:this.question_ID,
      name:this.username,
      email:this.usermail,
      writetime:format(new Date(), "yyyy-MM-dd'T'HH:mm:ss")
    }
    this.http.postApi(`http://localhost:8080/add/answer`,data).subscribe((res:any)=>{
      this.http.postApi(`http://localhost:8080/add/feedback`,feedbackdata).subscribe((t:any)=>{
      })
      alert("儲存答案成功");
      this.router.navigate(['/front'])
    })
  }

  update(){
    const hasUnansweredRequired = this.quizList.some((quiz, index) => {
      const answer = this.answerList[index];

      if (!quiz.is_required) return false;

      return (
        answer === undefined ||
        answer === null ||
        (Array.isArray(answer) && answer.length === 0)
      )
    });

    if (hasUnansweredRequired) {
      alert("請完成所有必填題");
      return;
    }

    const formattedAnswers = this.quizList.map((quiz, index) => {
      return {
        question_id: this.question_ID,
        quiz_id: quiz.quiz_id,
        username:this.usermail,
        answeroption: this.answerList[index]
      };
    });
    const data = {
      answerList: formattedAnswers
    };
    this.http.delApi(`http://localhost:8080/delete/answerFromMail/${this.question_ID}/${this.usermail}`).subscribe((res:any)=>{
      this.http.postApi(`http://localhost:8080/add/answer`,data).subscribe((res:any)=>{
        alert("更新答案成功");
        this.router.navigate(['/front'])
      })
    })
  }
}
