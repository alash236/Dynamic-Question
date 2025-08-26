import { Service } from './../@service/service';
import { Component, ViewChild} from '@angular/core';
import { Router} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {provideNativeDateAdapter} from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { addDays,format } from 'date-fns';
import { HttpClientService } from '../@service/HttpClientService';


@Component({
  selector: 'app-front',
  providers: [provideNativeDateAdapter()],
  imports: [FormsModule, CommonModule, MatTableModule, MatPaginatorModule, MatIconModule, MatButtonModule, MatDividerModule, RouterModule],
  templateUrl: './front.html',
  styleUrl: './front.scss'
})
export class Front {
  search_name:string="";
  search_start_time:string="";
  search_end_time:string="";
  searchArray:any[]=[];
  checkLogin:boolean = false;

  displayedColumns: any[] = ['question_ID','question_name','question_state','date_start','date_end','date_write'];
  dataSource = new MatTableDataSource<any>();

  today:string='';
  maxStartTime:string='';
  maxEndTime:string='';
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  frontList:any[]=[];

  constructor(
    private http:HttpClientService,
    private router:Router,
    private service:Service){}

  ngOnInit(): void {
    this.today = format(new Date(),"yyyy-MM-dd");
    this.maxStartTime = format(addDays(new Date(),2),'yyyy-MM-dd');
    this.maxEndTime = format(addDays(new Date(),7),'yyyy-MM-dd');
    this.http.getApi("http://localhost:8080/searchAllPublish").subscribe((res:any)=>{
      this.dataSource.data = res.allQuestion;
      this.searchArray = res.allQuestion;
      this.service.$checkLogin.subscribe((res:boolean)=>{
        this.checkLogin = res;
      })
    });


  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }


 search_name_function() {
    if (!this.search_start_time) {
      this.search_end_time = '';
    }

    const tidyData = this.searchArray.filter(res => {
      const matchesName = this.search_name === '' || res.name.includes(this.search_name);
      const matchesStart = this.search_start_time === '' || res.start_Time >= this.search_start_time;
      const matchesEnd = this.search_end_time === '' || res.end_Time <= this.search_end_time;
      return matchesName && matchesStart && matchesEnd;
    });
    this.dataSource.data = tidyData;
  }

  login(){
    this.router.navigate(['/login']);
  }
  loginout(){
    this.checkLogin = false;
    this.service.clearUserData();
    alert("已登出!!!")
    this.router.navigate(['/front']);
  }

  getQuestionState(row: any): string {
    const today = new Date();
    const start = new Date(row.start_Time);
    const end = new Date(row.end_Time);

    if (today < start) {
      return "尚未開始";
    }

    if (today >= start && today <= end) {
      return "進行中";
    }

    if (today > end) {
      return "已結束";
    }

    return "未知狀態";
  }



}

