import { Component } from '@angular/core';
import { Login } from './login/login';
import { Routes } from '@angular/router';
import { Back } from './back/back';
import { BackAdd } from './back/back-add/back-add';
import { Front } from './front/front';
import { QuestionPreview } from './back/question-preview/question-preview';
import { Question } from './question/question';
import { Register } from './register/register';
import { Preview } from './preview/preview';
import { Singlestatistics } from './singlestatistics/singlestatistics';
import { Feedback } from './feedback/feedback';
import { Edit } from './edit/edit';






export const routes: Routes = [
  {path:'',redirectTo:'/front',pathMatch:'full'},
  {path:'login',component:Login},
  {path:'register',component:Register},
  {path:'front',component:Front},
  {path:'question/:id',component:Question},
  {path:'preview/:id',component:Preview},
  {path:'singlestatistics/:id',component:Singlestatistics},
  {path:'feedback',component:Feedback},
  {path:'back',component:Back},
  {path:'back/add',component:BackAdd},
  {path:'back/questionPreview',component:QuestionPreview},
  {path:'edit/:id',component:Edit}

];
