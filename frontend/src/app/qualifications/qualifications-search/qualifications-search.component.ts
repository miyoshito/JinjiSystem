import { Component, OnInit } from '@angular/core';
import { BsDatepickerConfig, BsDatepickerViewMode, BsLocaleService } from 'ngx-bootstrap/datepicker';
import { FormBuilder, FormGroup } from '@angular/forms';
import { QualificationsService } from 'src/app/services/qualifications.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-qualifications-search',
  templateUrl: './qualifications-search.component.html',
  styleUrls: ['./qualifications-search.component.css']
})
export class QualificationsSearchComponent implements OnInit {

  constructor(
    private localeService: BsLocaleService,
    private fb: FormBuilder,
    private _qualificationsService: QualificationsService
  ) { localeService.use('ja') }

  title = '資格情報検索画面'
  minMode: BsDatepickerViewMode = 'day';
  bsConfig: Partial<BsDatepickerConfig>;
  includeRetired: boolean

  searchForm: FormGroup

  pullDownSLists$: Observable<any[]>

  ngOnInit() {
    this.pullDownSLists$ = this._qualificationsService.getShikakuSearchParams()

    this.buildSearchForm()

    this.bsConfig = Object.assign(
      { containerClass: "theme-red" },
      { dateInputFormat: 'YYYY/MM/DD' },
      { dateRangeFormat: 'YYYY/MM/DD'});
  }

  doSearch(){
    alert('開発中です')
  }

  includeRetiredShains(e) {
    this.includeRetired = e.target.checked
  }

  buildSearchForm(){
    this.searchForm = this.fb.group({
      sponsor: [''],
      qName: [''],
      examDate: [''],
      results: [''],
      costs: [''],
      id: [''],
      name: [''],
      kana: [''],
      op: ['']
    })
  }

}
