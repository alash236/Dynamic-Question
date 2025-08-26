import { Component, ViewChild} from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {provideNativeDateAdapter} from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import { addDays, format } from 'date-fns';
import { HttpClientService } from '../@service/HttpClientService';

@Component({
  selector: 'app-back',
  providers: [provideNativeDateAdapter()],
  imports: [FormsModule,CommonModule,MatTableModule, MatPaginatorModule,MatIconModule,MatButtonModule, MatDividerModule,RouterModule],
  templateUrl: './back.html',
  styleUrl: './back.scss'
})
export class Back {
  search_name:string="";
  search_start_time:string="";
  search_end_time:string="";
  searchArray:any[]=[];

  displayedColumns: any[] = ['question_ID','question_name','question_state','date_start','date_end','edit','action'];

  dataSource = new MatTableDataSource<any>();

  today:string='';
  maxStartTime:string='';
  maxEndTime:string='';

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router:Router,private http:HttpClientService){}

  ngOnInit(): void {
    this.today = format(new Date(),"yyyy-MM-dd");
    this.maxStartTime = format(addDays(new Date(),2),'yyyy-MM-dd');
    this.maxEndTime = format(addDays(new Date(),7),'yyyy-MM-dd');
    this.http.getApi("http://localhost:8080/searchAllquestion").subscribe((res:any)=>{
      this.searchArray = res.allQuestion
      this.dataSource.data = res.allQuestion
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

  getQuestionState(row: any): string {
    const today = new Date();
    const start = new Date(row.start_Time);
    const end = new Date(row.end_Time);
    const publish = row.publish;

    if (!publish) {
      return "未發布";
    }

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

  add(){
    this.router.navigate(['/back/add']);
  }

  login_out(){
    alert("已登出！");
    this.router.navigate(['/front'])
  }

  delete(index: number) {
    const id = this.dataSource.data[index].id;
    this.http.delApi(`http://localhost:8080/delete/question/${id}`).subscribe((res:any)=>{
      const newData = [...this.dataSource.data];
      newData.splice(index, 1);
      this.dataSource.data = newData;
      this.searchArray = newData;

      this.http.delApi(`http://localhost:8080/delete/answer/${id}`).subscribe((res:any)=>{})
      this.http.delApi(`http://localhost:8080/delete/feedback/${id}`).subscribe((res:any)=>{})
    })
  }

  go_feedback(){
    this.router.navigate(['/feedback'])
  }

}

