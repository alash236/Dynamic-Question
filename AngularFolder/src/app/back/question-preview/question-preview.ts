import { Service } from './../../@service/service';
import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { HttpClientService } from '../../@service/HttpClientService';


@Component({
  selector: 'app-question-preview',
  imports: [],
  templateUrl: './question-preview.html',
  styleUrl: './question-preview.scss'
})
export class QuestionPreview {

  constructor(
    private router:Router,
    private location: Location,
    private http:HttpClientService,
    private service:Service){}

  id!:string

  name!:string
  text!:string
  start_Time!:string
  end_Time!:string
  publish!:boolean

  quizList:any[] = [];

  ngOnInit(): void {
    this.service.$questionDataSource.subscribe((res:any)=>{
      this.name = res.name;
      this.text = res.text;
      this.start_Time = res.start_Time
      this.end_Time = res.end_Time;
      this.quizList = res.quizList;
    })
  }

  /**
   * 將資料寫進DB
   */
  save(){
    //將資料包裝成物件格式
    const data = {
      name: this.name,
      text: this.text,
      start_Time: this.start_Time,
      end_Time: this.end_Time,
      publish: true,
      quizList: this.quizList.map((quiz, index) => ({
        quiz_id: index + 1,
        name: quiz.name,
        type: quiz.type,
        option: quiz.type === 'text' ? [] : quiz.option,
        is_required: quiz.is_required
    }))
  };
  //透過http post 出去
    this.http.postApi("http://localhost:8080/add/question",data).subscribe((res:any)=>{
      alert(res.message);
      this.service.setQuestionData(null);
      this.router.navigate(['/back']);
    });
  }

  cache(){
    //將資料包裝成物件格式
    const data = {
      name: this.name,
      text: this.text,
      start_Time: this.start_Time,
      end_Time: this.end_Time,
      publish:false,
      quizList: this.quizList.map((quiz, index) => ({
        quiz_id: index + 1,
        name: quiz.name,
        type: quiz.type,
        option: quiz.type === 'text' ? [] : quiz.option,
        is_required: quiz.is_required
    }))
  };
  //透過http post 出去
    this.http.postApi("http://localhost:8080/add/question",data).subscribe((res:any)=>{
      alert(res.message);
      this.service.setQuestionData(null);
      this.router.navigate(['/back']);
    });
  }

  /**
   * 回到上個location
   */
  go_pre(){
    this.location.back();
  }

}
