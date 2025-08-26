import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Service {

  private checkLogin = new BehaviorSubject<any>(null);
  public $checkLogin = this.checkLogin.asObservable();

  setCheckLogin(data:boolean){
    this.checkLogin.next(data);
  }

  setUserData(data:any){
    localStorage.setItem("userData",JSON.stringify(data));
  }
  getUserData(){
    const data = localStorage.getItem("userData");
    return data ? JSON.parse(data) : null;
  }
  clearUserData(){
    localStorage.removeItem('userData');
  }

  private questionDateSource = new BehaviorSubject<any>(null);
  public $questionDataSource = this.questionDateSource.asObservable();

  setQuestionData(data:any){
    this.questionDateSource.next(data);
  }

  private feedbackData = new BehaviorSubject<any>(null);
  public $feedbackData = this.feedbackData.asObservable();

  setFeedBackData(data:any){
    this.feedbackData.next(data);
  }

}
