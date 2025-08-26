import { Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArcElement, Chart, Legend, PieController, Title, Tooltip } from 'chart.js';
import { ActivatedRoute } from '@angular/router';
import { HttpClientService } from '../@service/HttpClientService';
import { Location } from '@angular/common';

Chart.register(PieController, ArcElement, Tooltip, Legend, Title);
@Component({
  selector: 'app-singlestatistics',
  imports: [CommonModule],
  templateUrl: './singlestatistics.html',
  styleUrl: './singlestatistics.scss'
})
export class Singlestatistics {
  questionName:string="";
  quizList:any[] = [];
  answerList:any[]=[];
  question_ID:number=0;
  charts:Chart[]=[];

  constructor(private route:ActivatedRoute,private http:HttpClientService,private location: Location) {}

  ngOnInit(): void {
    this.question_ID = Number(this.route.snapshot.paramMap.get('id'));
    this.http.getApi(`http://localhost:8080/searchQuestion/${this.question_ID}`).subscribe((questionRes:any)=>{

      this.questionName = questionRes.name;

      this.http.getApi(`http://localhost:8080/searchQuiz/${this.question_ID}`).subscribe((quizRes:any)=>{

        this.quizList = quizRes.allQuestion;

        this.http.getApi(`http://localhost:8080/search/answer/${this.question_ID}`).subscribe((answerRes:any)=>{

          this.answerList = answerRes.allAnswer;

          setTimeout(() => {
            for (let i = 0; i < this.quizList.length; i++) {
              const chart = this.createCharts(i);
              if (chart) this.charts.push(chart);
            }
          }, 0);

        })

      })

    })
  }

  createCharts(index:number) {
    const ctx = document.getElementById(`chart-${index}`) as HTMLCanvasElement;

    if (!ctx){
      console.error(`Canvas not found for chart-${index}`);
      return null;
    }

    let dataCount = this.quizList[index].option.map((option:string) => this.answerList
      .filter(answer => answer.quiz_id === this.quizList[index].quiz_id && answer.answeroption.includes(option)).length);


    let backgroundColor = this.quizList[index].option.map((_:any, i:number)=>{
      const hue = Math.floor((360 / this.quizList[index].option.length) * i);
      return `hsl(${hue}, 70%, 60%)`;
    });

    let data ={
      labels:this.quizList[index].option,
      datasets:[{
        data:dataCount,
        backgroundColor:backgroundColor,
        hoverOffset:4
      }]
    };

     let chart = new Chart(ctx,{
      type:'pie',
      data:data,    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'bottom'
        },
        title: {
          display: true,
          text: this.quizList[index].name
        }
      }
    }
    });
    return chart;
  }


  back(){
    this.location.back();
  }
}

