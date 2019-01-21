import { Component, OnInit } from '@angular/core';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';

@Component({
  selector: 'app-qualifications-search',
  templateUrl: './qualifications-search.component.html',
  styleUrls: ['./qualifications-search.component.css']
})
export class QualificationsSearchComponent implements OnInit {

  constructor(
    private localeService: BsLocaleService
  ) { localeService.use('ja') }

  title = '資格情報検索画面'
  minMode: BsDatepickerViewMode = 'day';
  bsConfig: Partial<BsDatepickerConfig>;

  ngOnInit() {
    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD'});
  }

  doSearch(){
    alert('開発中です')
  }

}
