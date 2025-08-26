import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {  Router } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import { HttpClientService } from '../@service/HttpClientService';

@Component({
  selector: 'app-login',
  imports: [FormsModule,MatIconModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  constructor(private router:Router,private http:HttpClientService){}
  signUPPhone="";
  signUpAge="";
  signUpEmail = '';
  signUpUsername = '';
  signUpPassword = '';
  signUpConfirmPassword = '';
  changetype = false
  changeTypeEmail = false
  checkEmail:string[] = [];

  ngOnInit(): void {
    this.http.getApi(`http://localhost:8080/searchAllUser`).subscribe((res:any)=>{
      this.checkEmail = res.allUser.map((user: any) => user.email);
    })
  }


  async submitForm() {
    if(this.signUpEmail === ""){
      alert("電子信箱不能為空 請重新輸入!!!");
      this.clear();
      return;
    }
    if(this.signUpUsername === ""){
      alert("名稱不能為空 請重新輸入!!!");
      this.clear();
      return;
    }
    if(this.signUpPassword === ""){
      alert("密碼不能為空 請重新輸入!!!");
      this.clear();
      return;
    }
    if(this.signUpConfirmPassword === ""){
      alert("確認密碼不能為空 請重新輸入!!!");
      this.clear();
      return;
    }
    if (this.signUpPassword !== this.signUpConfirmPassword) {
      alert("密碼不一致");
      this.clear();
      return;
    }
    if(this.signUPPhone === ""){
      alert("手機號碼不能為空 請重新輸入!!!");
      this.clear();
      return;
    }
    let data = {
      name:this.signUpUsername,
      phone:this.signUPPhone,
      email:this.signUpEmail,
      password:this.signUpPassword,
      age:Number.parseInt(this.signUpAge)
    }
    this.http.postApi(`http://localhost:8080/add/user`,data).subscribe((res:any)=>{
      alert("註冊成功");
    })
    await this.sleep(2000);
    this.router.navigate(['/login'])

  }
  clear(){
    this.signUpEmail = "";
    this.signUpUsername = "";
    this.signUpPassword = "";
    this.signUpConfirmPassword = "";
    this.signUPPhone = "";
    this.signUpAge = "";
  }
  checkEmailFunction(value: string){
     this.changeTypeEmail = this.checkEmail.some((res: string) => value === res);
  }

  changeType(){
    this.changetype = !this.changetype;
  }

  sleep(ms: number) {
  return new Promise(resolve => setTimeout(resolve, ms));
}
}
