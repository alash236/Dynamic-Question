import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {  Router } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import { HttpClientService } from '../@service/HttpClientService';
import { Service } from '../@service/service';

@Component({
  selector: 'app-login',
  imports: [FormsModule,MatIconModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  constructor(private service:Service,private router:Router,private http:HttpClientService){}

  userEmail:string="";
  userPassword:string="";
  changetype=false;

  submitForm() {
    if(this.userEmail=="admin" && this.userPassword=="admin"){
      alert("歡迎你Administrator!!!");
      this.router.navigate(['/back'])
    }else{
      let user = {
        email: this.userEmail,
        password: this.userPassword
      };
      this.http.postApi("http://localhost:8080/login",user).subscribe((res:any) =>{
        if(res.code===200){
          alert(JSON.stringify(res.message).replaceAll('"',""))
          this.service.setUserData(res.user)
          this.service.setCheckLogin(true);
          this.router.navigate(['/front'])
        }else{
          alert(JSON.stringify(res.message))
          this.clear();
        }
      });
    }
  }

  clear(){
    this.userEmail = "";
    this.userPassword = "";
  }

  changeType(){
    this.changetype = !this.changetype;
  }

  toggleSignUp(){
    this.router.navigate(['/register']);
  }
}
